package cn.edu.bnuz.bell.workflow.commands

import groovy.transform.ToString

/**
 * 工作流提交命令
 * @author Yang Lin
 */
@ToString
class SubmitCommand {
    Long id
    String comment
    String title
    String to
}
