package cn.edu.bnuz.bell.workflow.config

import cn.edu.bnuz.bell.workflow.Event
import cn.edu.bnuz.bell.workflow.State
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.statemachine.config.EnableStateMachine
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer

@Configuration
@EnableStateMachine(name = "defaultStateMachine")
@Import(StandardActionConfiguration)
class DefaultStateMachineConfiguration extends EnumStateMachineConfigurerAdapter<State, Event> {
    @Autowired
    StandardActionConfiguration actions

    @Override
    void configure(StateMachineStateConfigurer<State, Event> states) throws Exception {
        states
            .withStates()
                .initial(State.CREATED)
                .state(State.CREATED,   [actions.logEntryAction()], null)
                .state(State.SUBMITTED, [actions.logEntryAction(), actions.submittedEntryAction()],  [actions.workitemProcessedAction()])
                .state(State.CLOSED,    [actions.logEntryAction(), actions.closedEntryAction()], null)
                .state(State.CHECKED,   [actions.logEntryAction(), actions.checkedEntryAction()],    [actions.workitemProcessedAction()])
                .state(State.REJECTED,  [actions.logEntryAction(), actions.rejectedEntryAction()],   [actions.workitemProcessedAction()])
                .state(State.APPROVED,  [actions.logEntryAction(), actions.notifySubmitterAction()], null)
                .state(State.REVOKED,   [actions.logEntryAction(), actions.revokedEntryAction()], null)
    }

    @Override
    void configure(StateMachineTransitionConfigurer<State, Event> transitions) throws Exception {
        transitions
            .withInternal()
                .source(State.CREATED)
                .event(Event.UPDATE)
                .action(actions.logTransitionAction())
                .and()
            .withExternal()
                .source(State.CREATED)
                .event(Event.SUBMIT)
                .target(State.SUBMITTED)
                .and()
            .withExternal()
                .source(State.SUBMITTED)
                .event(Event.ACCEPT)
                .target(State.CHECKED)
                .and()
            .withExternal()
                .source(State.SUBMITTED)
                .event(Event.REJECT)
                .target(State.REJECTED)
                .and()
            .withExternal()
                .source(State.SUBMITTED)
                .event(Event.CLOSE)
                .target(State.CLOSED)
                .and()
            .withExternal()
                .source(State.CHECKED)
                .event(Event.ACCEPT)
                .target(State.APPROVED)
                .and()
            .withExternal()
                .source(State.CHECKED)
                .event(Event.REJECT)
                .target(State.REJECTED)
                .and()
            .withExternal()
                .source(State.CHECKED)
                .event(Event.CLOSE)
                .target(State.CLOSED)
                .and()
            .withInternal()
                .source(State.REJECTED)
                .event(Event.UPDATE)
                .action(actions.logTransitionAction())
                .and()
            .withExternal()
                .source(State.REJECTED)
                .event(Event.SUBMIT)
                .target(State.SUBMITTED)
                .and()
            .withExternal()
                .source(State.APPROVED)
                .event(Event.REVOKE)
                .target(State.REVOKED)

    }
}
