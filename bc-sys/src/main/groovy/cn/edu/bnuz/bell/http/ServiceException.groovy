package cn.edu.bnuz.bell.http

import org.springframework.http.HttpStatus

/**
 * Created by yanglin on 2015/4/16.
 */
abstract class ServiceException extends RuntimeException {
    HttpStatus status
    ServiceException(HttpStatus status) {
        super(status.name())
        this.status = status
    }
}

class NotFoundException extends ServiceException {
    NotFoundException() {
        super(HttpStatus.NOT_FOUND)
    }
}

class ForbiddenException extends  ServiceException {
    ForbiddenException() {
        super(HttpStatus.FORBIDDEN)
    }
}

class BadRequestException extends ServiceException {
    BadRequestException() {
        super(HttpStatus.BAD_REQUEST)
    }
}