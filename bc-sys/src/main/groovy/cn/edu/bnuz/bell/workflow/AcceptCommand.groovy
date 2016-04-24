package cn.edu.bnuz.bell.workflow

import groovy.transform.ToString

/**
 * 工作流同意命令
 */
@ToString
class AcceptCommand {
    Long id
    String comment
    String title
    String to
}
