package cn.edu.bnuz.bell.workflow

import cn.edu.bnuz.bell.orm.PostgreSQLEnumUserType
import groovy.transform.CompileStatic

@CompileStatic
class StateUserType extends PostgreSQLEnumUserType {
    @Override
    Class<? extends Enum> getEnumClass() {
        State
    }
}
