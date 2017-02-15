package cn.edu.bnuz.bell.workflow.commands

import groovy.transform.ToString

/**
 * 工作流回收命令
 * @author Yang Lin
 */
@ToString
class RevokeCommand {
    Long id
    String comment
    String title
}
