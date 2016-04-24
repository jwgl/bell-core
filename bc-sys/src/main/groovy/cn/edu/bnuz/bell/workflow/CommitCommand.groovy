package cn.edu.bnuz.bell.workflow

import groovy.transform.ToString

/**
 * 工作流提交命令
 * @author Yang Lin
 */
@ToString
class CommitCommand {
    Long id
    String comment
    String title
    String to
}
