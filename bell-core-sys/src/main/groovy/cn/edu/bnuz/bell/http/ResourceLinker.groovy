package cn.edu.bnuz.bell.http

import grails.artefact.Enhances
import org.grails.core.artefact.ControllerArtefactHandler

/**
 * 控制器中可用的生成链接的方法
 * @author Yang Lin
 */
@Enhances(ControllerArtefactHandler.TYPE)
trait ResourceLinker {

    String linkIndex() {
        linkIndex(controllerName, null)
    }

    String linkIndex(Map params) {
        linkIndex(controllerName, params)
    }

    String linkIndex(String controller, Map params) {
        grailsLinkGenerator.link(resource: controller, action: 'index', params: params)
    }

    String linkItem(id) {
        grailsLinkGenerator.link(resource: controllerName, action: 'show', id: id)
    }

    String linkCreate(Map params) {
        grailsLinkGenerator.link(resource: controllerName, action: 'create', params: params)
    }

    String linkEdit(id) {
        grailsLinkGenerator.link(resource: controllerName, action: 'edit', id: id)
    }

    String linkPatch(id, Map params) {
        grailsLinkGenerator.link(resource: controllerName, action: 'show', id: id, params: params)
    }

    String linkSub(id, String action) {
        grailsLinkGenerator.link(resource: controllerName, action: action, params:[(controllerName + 'Id'): id])
    }

    String linkAction(String action) {
        grailsLinkGenerator.link(resource: controllerName, action: action)
    }
}
