package cn.edu.bnuz.bell.workflow

import groovy.transform.CompileStatic

/**
 * 活动常量
 * @author Yang Lin
 */
@CompileStatic
interface Activities {
    String CHECK   = 'check'
    String REVIEW  = 'review'
    String APPROVE = 'approve'
    String VIEW    = 'view'
}