package cn.edu.bnuz.bell.workflow.config

import cn.edu.bnuz.bell.workflow.DomainStateMachinePersist
import cn.edu.bnuz.bell.workflow.Event
import cn.edu.bnuz.bell.workflow.StateObject
import cn.edu.bnuz.bell.workflow.State
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.statemachine.StateMachinePersist
import org.springframework.statemachine.persist.DefaultStateMachinePersister
import org.springframework.statemachine.persist.StateMachinePersister

@Configuration
class DefaultStateMachinePersistConfiguration {
    @Bean
    StateMachinePersister<State, Event, StateObject> stateMachinePersister(StateMachinePersist<State, Event, StateObject> stateMachinePersist) {
        new DefaultStateMachinePersister<>(stateMachinePersist)
    }

    @Bean
    StateMachinePersist<State, Event, StateObject> stateMachinePersist() {
        new DomainStateMachinePersist()
    }
}
