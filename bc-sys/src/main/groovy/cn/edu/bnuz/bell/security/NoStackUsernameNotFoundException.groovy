package cn.edu.bnuz.bell.security

import org.springframework.security.core.userdetails.UsernameNotFoundException

/**
 * 未找到用户异常
 * @author Yang Lin
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
