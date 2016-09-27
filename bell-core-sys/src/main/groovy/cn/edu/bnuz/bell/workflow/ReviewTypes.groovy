package cn.edu.bnuz.bell.workflow

import groovy.transform.CompileStatic

/**
 * 审核类型常量
 * @author Yang Lin
 */
@CompileStatic
interface ReviewTypes {
    String CHECK   = 'check'
    String REVIEW  = 'review'
    String APPROVE = 'approve'
}