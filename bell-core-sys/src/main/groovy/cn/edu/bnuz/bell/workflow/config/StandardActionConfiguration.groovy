package cn.edu.bnuz.bell.workflow.config

import cn.edu.bnuz.bell.workflow.Activities
import cn.edu.bnuz.bell.workflow.Events
import cn.edu.bnuz.bell.workflow.States
import cn.edu.bnuz.bell.workflow.actions.ManualEntryAction
import cn.edu.bnuz.bell.workflow.actions.CommittedEntryAction
import cn.edu.bnuz.bell.workflow.actions.LogEntryAction
import cn.edu.bnuz.bell.workflow.actions.LogTransitionAction
import cn.edu.bnuz.bell.workflow.actions.NotifyCommitterAction
import cn.edu.bnuz.bell.workflow.actions.RejectedEntryAction
import cn.edu.bnuz.bell.workflow.actions.WorkitemProcessedAction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.statemachine.action.Action

@Configuration
class StandardActionConfiguration {
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
        new ManualEntryAction(Activities.APPROVE)
    }

    @Bean
    Action<States, Events> rejectedEntryAction() {
        new RejectedEntryAction()
    }

    @Bean
    Action<States, Events> notifyCommitterAction() {
        new NotifyCommitterAction()
    }

    @Bean
    Action<States, Events> workitemProcessedAction() {
        new WorkitemProcessedAction()
    }
}
