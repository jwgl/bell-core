package cn.edu.bnuz.bell.workflow

import cn.edu.bnuz.bell.workflow.actions.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.statemachine.StateMachinePersist
import org.springframework.statemachine.action.Action
import org.springframework.statemachine.config.EnableStateMachine
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer
import org.springframework.statemachine.persist.DefaultStateMachinePersister
import org.springframework.statemachine.persist.StateMachinePersister

@Configuration
@EnableStateMachine(name = "defaultStateMachine")
class DefaultStateMachineConfiguration extends EnumStateMachineConfigurerAdapter<States, Events> {
    @Override
    void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states
                .withStates()
                .initial(States.CREATED)
                .state(States.CREATED,   [logEntryAction()], null)
                .state(States.COMMITTED, [committedEntryAction(), logEntryAction()], [workitemProcessedAction()])
                .state(States.CHECKED,   [checkedEntryAction(),   logEntryAction()], [workitemProcessedAction()])
                .state(States.REJECTED,  [rejectedEntryAction(),  logEntryAction()], [workitemProcessedAction()])
                .state(States.APPROVED,  [approvedEntryAction(),  logEntryAction()], null)
    }

    @Override
    void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
            .withInternal()
                .source(States.CREATED)
                .event(Events.UPDATE)
                .action(logTransitionAction())
                .and()
            .withExternal()
                .source(States.CREATED)
                .event(Events.COMMIT)
                .target(States.COMMITTED)
                .and()
            .withExternal()
                .source(States.COMMITTED)
                .event(Events.CANCEL)
                .target(States.CREATED)
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
                .action(logTransitionAction())
                .and()
            .withExternal()
                .source(States.REJECTED)
                .event(Events.COMMIT)
                .target(States.COMMITTED)
    }

    @Bean
    StateMachinePersister<States, Events, IStateObject> stateMachinePersister(StateMachinePersist<States, Events, IStateObject> stateMachinePersist) {
        new DefaultStateMachinePersister<>(stateMachinePersist)
    }

    @Bean
    StateMachinePersist<States, Events, IStateObject> stateMachinePersist() {
        new DomainStateMachinePersist()
    }

    @Bean
    Action<States, Events> logTransitionAction() {
        new LogTransitionAction()
    }


    @Bean
    Action<States, Events> logEntryAction() {
        new LogEntryAction()
    }

    @Bean
    Action<States, Events> committedEntryAction() {
        new CommittedEntryAction()
    }

    @Bean
    Action<States, Events> checkedEntryAction() {
        new CheckedEntryAction()
    }

    @Bean
    Action<States, Events> approvedEntryAction() {
        new ApprovedEntryAction()
    }

    @Bean
    Action<States, Events> rejectedEntryAction() {
        new RejectedEntryAction()
    }

    @Bean
    Action<States, Events> workitemProcessedAction() {
        new WorkitemProcessedAction()
    }
}
