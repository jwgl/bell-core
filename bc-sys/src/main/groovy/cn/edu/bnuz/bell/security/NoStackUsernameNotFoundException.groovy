package cn.edu.bnuz.bell.security

import org.springframework.security.core.userdetails.UsernameNotFoundException

/**
 * Lightweight exception that avoids the cost of filling in the stack frames.
 * From Grails 2.0 Plugin Spring Security Core
 * Created by yanglin on 2015/3/15.
 */
class NoStackUsernameNotFoundException extends UsernameNotFoundException{
    static final long serialVersionUID = 1;

    NoStackUsernameNotFoundException() {
        super("User not found");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
