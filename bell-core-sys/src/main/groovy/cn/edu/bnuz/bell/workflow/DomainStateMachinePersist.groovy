package cn.edu.bnuz.bell.workflow

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.statemachine.StateMachineContext
import org.springframework.statemachine.StateMachinePersist
import org.springframework.statemachine.support.DefaultStateMachineContext

@Slf4j
@CompileStatic
class DomainStateMachinePersist implements StateMachinePersist<States, Events, IStateObject> {
    @Override
    public void write(StateMachineContext<States, Events> context, IStateObject contextOjb) throws Exception {
        log.debug(contextOjb.class.name + '#' + contextOjb.id + ': ' + contextOjb.status + '->' + context.state)
        contextOjb.status = context.state
    }

    @Override
    public StateMachineContext<States, Events> read(IStateObject contextOjb) throws Exception {
        return new DefaultStateMachineContext<States, Events>(contextOjb.status, null, null, null, null)
    }
}
