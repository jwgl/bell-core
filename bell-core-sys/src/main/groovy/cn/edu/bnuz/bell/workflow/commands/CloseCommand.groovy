package cn.edu.bnuz.bell.workflow.commands

import groovy.transform.ToString

/**
 * 工作流关闭命令
 * @author Yang Lin
 */
@ToString
class CloseCommand {
    Long id
    String comment
    String title
}
