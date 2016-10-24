package cn.edu.bnuz.bell.workflow.actions

import cn.edu.bnuz.bell.security.UserLogService
import cn.edu.bnuz.bell.workflow.Events
import cn.edu.bnuz.bell.workflow.States
import cn.edu.bnuz.bell.workflow.events.EventData
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.statemachine.StateContext
import org.springframework.statemachine.action.Action

@CompileStatic
class LogTransitionAction implements Action<States, Events> {
    @Autowired
    private UserLogService userLogService

    @Override
    void execute(StateContext<States, Events> context) {
        EventData eventData = context.getMessageHeader(EventData.KEY) as EventData
        userLogService.log(eventData.fromUser, eventData.ipAddress, context.event.name(), eventData.entity)
    }
}
