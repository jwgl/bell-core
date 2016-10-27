package cn.edu.bnuz.bell.workflow.config

import cn.edu.bnuz.bell.workflow.DomainStateMachinePersist
import cn.edu.bnuz.bell.workflow.Events
import cn.edu.bnuz.bell.workflow.IStateObject
import cn.edu.bnuz.bell.workflow.States
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.statemachine.StateMachinePersist
import org.springframework.statemachine.persist.DefaultStateMachinePersister
import org.springframework.statemachine.persist.StateMachinePersister

@Configuration
class DefaultStateMachinePersistConfiguration {
    @Bean
    StateMachinePersister<States, Events, IStateObject> stateMachinePersister(StateMachinePersist<States, Events, IStateObject> stateMachinePersist) {
        new DefaultStateMachinePersister<>(stateMachinePersist)
    }

    @Bean
    StateMachinePersist<States, Events, IStateObject> stateMachinePersist() {
        new DomainStateMachinePersist()
    }
}
