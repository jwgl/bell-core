package cn.edu.bnuz.bell.workflow.config

import cn.edu.bnuz.bell.workflow.Activities
import cn.edu.bnuz.bell.workflow.Event
import cn.edu.bnuz.bell.workflow.State
import cn.edu.bnuz.bell.workflow.actions.AutoEntryAction
import cn.edu.bnuz.bell.workflow.actions.ManualEntryAction
import cn.edu.bnuz.bell.workflow.actions.SubmittedEntryAction
import cn.edu.bnuz.bell.workflow.actions.LogEntryAction
import cn.edu.bnuz.bell.workflow.actions.LogTransitionAction
import cn.edu.bnuz.bell.workflow.actions.NotifySubmitterAction
import cn.edu.bnuz.bell.workflow.actions.RejectedEntryAction
import cn.edu.bnuz.bell.workflow.actions.WorkitemProcessedAction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.statemachine.action.Action

@Configuration
class StandardActionConfiguration {
    @Bean
    Action<State, Event> logTransitionAction() {
        new LogTransitionAction()
    }

    @Bean
    Action<State, Event> logEntryAction() {
        new LogEntryAction()
    }

    @Bean
    Action<State, Event> submittedEntryAction() {
        new SubmittedEntryAction(Activities.CHECK)
    }

    @Bean
    Action<State, Event> closedEntryAction() {
        new RejectedEntryAction()
    }

    @Bean
    Action<State, Event> checkedEntryAction() {
        new ManualEntryAction(Activities.APPROVE)
    }

    @Bean
    Action<State, Event> rejectedEntryAction() {
        new RejectedEntryAction()
    }

    @Bean
    Action<State, Event> notifySubmitterAction() {
        new NotifySubmitterAction()
    }

    @Bean
    Action<State, Event> workitemProcessedAction() {
        new WorkitemProcessedAction()
    }

    @Bean
    Action<State, Event> revokedEntryAction() {
        new RejectedEntryAction()
    }
}
