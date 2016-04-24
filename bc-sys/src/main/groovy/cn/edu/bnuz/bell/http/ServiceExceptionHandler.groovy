package cn.edu.bnuz.bell.http

import cn.edu.bnuz.bell.http.ServiceException

/**
 * Created by yanglin on 2016/3/31.
 */
trait ServiceExceptionHandler {
    def handlerServiceException(ServiceException e) {
        render(status: e.status)
    }
}
