package cn.edu.bnuz.bell.workflow.commands

import groovy.transform.ToString

/**
 * 工作流同意命令
 * @author Yang Lin
 */
@ToString
class AcceptCommand {
    Long id
    String comment
    String title
    String to
}
