package cn.edu.bnuz.bell.workflow.actions

import cn.edu.bnuz.bell.security.UserLogService
import cn.edu.bnuz.bell.workflow.Event
import cn.edu.bnuz.bell.workflow.State
import cn.edu.bnuz.bell.workflow.events.EventData
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.statemachine.StateContext
import org.springframework.statemachine.action.Action

@CompileStatic
class LogEntryAction implements Action<State, Event> {
    @Autowired
    private UserLogService userLogService

    @Override
    void execute(StateContext<State, Event> context) {
        EventData eventData = context.getMessageHeader(EventData.KEY) as EventData
        userLogService.log(eventData.fromUser, eventData.ipAddress, context.event.name(), eventData.entity)
    }
}
