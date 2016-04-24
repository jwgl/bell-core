package cn.edu.bnuz.bell.workflow

import groovy.transform.CompileStatic

/**
 * Created by yanglin on 2016/3/31.
 */
@CompileStatic
interface Activities {
    String CHECK   = 'check'
    String REVIEW  = 'review'
    String APPROVE = 'approve'
    String VIEW    = 'view'
}