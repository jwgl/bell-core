package cn.edu.bnuz.bell.http

import org.springframework.http.HttpStatus

/**
 * 服务异常
 * @author Yang Lin
 */
abstract class ServiceException extends RuntimeException {
    HttpStatus status

    ServiceException(HttpStatus status) {
        super(status.name())
        this.status = status
    }

    ServiceException(HttpStatus status, String message) {
        super(message)
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

    BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message)
    }
}