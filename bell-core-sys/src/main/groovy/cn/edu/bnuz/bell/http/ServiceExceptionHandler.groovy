package cn.edu.bnuz.bell.http

/**
 * 服务异常处理器
 * @author Yang Lin
 */
trait ServiceExceptionHandler {
    def handlerServiceException(ServiceException e) {
        render(status: e.status, message: e.message)
    }
}
