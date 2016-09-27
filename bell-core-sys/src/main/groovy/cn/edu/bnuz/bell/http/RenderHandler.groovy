package cn.edu.bnuz.bell.http

import grails.artefact.Enhances
import grails.converters.JSON
import org.grails.core.artefact.ControllerArtefactHandler
import org.springframework.http.HttpStatus

/**
 * 控制器中可用的Render方法
 * @author Yang Lin
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

    def renderJson(Object data) {
        render data as JSON
    }
}
