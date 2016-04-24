package cn.edu.bnuz.bell.http

import grails.artefact.Enhances
import grails.converters.JSON
import org.grails.core.artefact.ControllerArtefactHandler
import org.springframework.http.HttpStatus

/**
 * Created by yanglin on 2015/4/8.
 */
@Enhances(ControllerArtefactHandler.TYPE)
trait RenderHandler {
    def renderOk() {
        render(status: HttpStatus.OK)
    }

    def renderCreated() {
        render(status: HttpStatus.CREATED)
    }

    def renderBadRequest() {
        render(status: HttpStatus.BAD_REQUEST)
    }

    def renderForbidden() {
        render(status: HttpStatus.FORBIDDEN)
    }

    def renderNotFound() {
        render(status: HttpStatus.NOT_FOUND)
    }

    def renderStatus(ServiceStatus status) {
        switch (status) {
            case ServiceStatus.OK:
                renderOk()
                break
            case ServiceStatus.CREATED:
                renderCreated()
                break
            case ServiceStatus.BAD_REQUEST:
                renderBadRequest()
                break
            case ServiceStatus.FORBIDDEN:
                renderForbidden()
                break;
            case ServiceStatus.NOT_FOUND:
                renderNotFound()
                break;
        }
    }

    def renderJson(Object data) {
        render data as JSON
    }
}
