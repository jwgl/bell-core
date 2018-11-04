package cn.edu.bnuz.bell.workflow

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.statemachine.StateMachineContext
import org.springframework.statemachine.StateMachinePersist
import org.springframework.statemachine.support.DefaultExtendedState
import org.springframework.statemachine.support.DefaultStateMachineContext

@Slf4j
@CompileStatic
class DomainStateMachinePersist implements StateMachinePersist<State, Event, StateObject> {
    @Override
    void write(StateMachineContext<State, Event> context, StateObject contextOjb) throws Exception {
        log.debug(contextOjb.class.name + '#' + contextOjb.id + ': ' + contextOjb.status + '->' + context.state)
        contextOjb.status = context.state
    }

    @Override
    StateMachineContext<State, Event> read(StateObject contextOjb) throws Exception {
        def extendedState = new DefaultExtendedState()
        extendedState.variables['StateObject'] = contextOjb
        return new DefaultStateMachineContext<State, Event>(contextOjb.status, null, null, extendedState, null)
    }
}
