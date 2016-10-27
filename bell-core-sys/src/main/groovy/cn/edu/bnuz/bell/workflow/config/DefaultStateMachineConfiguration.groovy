package cn.edu.bnuz.bell.workflow.config

import cn.edu.bnuz.bell.workflow.Events
import cn.edu.bnuz.bell.workflow.States
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
class DefaultStateMachineConfiguration extends EnumStateMachineConfigurerAdapter<States, Events> {
    @Autowired
    StandardActionConfiguration actions

    @Override
    void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states
            .withStates()
                .initial(States.CREATED)
                .state(States.CREATED,   [actions.logEntryAction()], null)
                .state(States.COMMITTED, [actions.logEntryAction(), actions.committedEntryAction()],  [actions.workitemProcessedAction()])
                .state(States.CHECKED,   [actions.logEntryAction(), actions.checkedEntryAction()],    [actions.workitemProcessedAction()])
                .state(States.REJECTED,  [actions.logEntryAction(), actions.rejectedEntryAction()],   [actions.workitemProcessedAction()])
                .state(States.APPROVED,  [actions.logEntryAction(), actions.notifyCommitterAction()], null)
    }

    @Override
    void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
            .withInternal()
                .source(States.CREATED)
                .event(Events.UPDATE)
                .action(actions.logTransitionAction())
                .and()
            .withExternal()
                .source(States.CREATED)
                .event(Events.COMMIT)
                .target(States.COMMITTED)
                .and()
            .withExternal()
                .source(States.COMMITTED)
                .event(Events.ACCEPT)
                .target(States.CHECKED)
                .and()
            .withExternal()
                .source(States.COMMITTED)
                .event(Events.REJECT)
                .target(States.REJECTED)
                .and()
            .withExternal()
                .source(States.CHECKED)
                .event(Events.ACCEPT)
                .target(States.APPROVED)
                .and()
            .withExternal()
                .source(States.CHECKED)
                .event(Events.REJECT)
                .target(States.REJECTED)
                .and()
            .withInternal()
                .source(States.REJECTED)
                .event(Events.UPDATE)
                .action(actions.logTransitionAction())
                .and()
            .withExternal()
                .source(States.REJECTED)
                .event(Events.COMMIT)
                .target(States.COMMITTED)
    }
}
