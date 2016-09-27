package cn.edu.bnuz.bell.report

import org.springframework.http.HttpStatus

/**
 * 报表响应
 * Created by yanglin on 2016/9/26.
 */
class ReportResponse {
    HttpStatus statusCode
    String title
    String format
    String reportId
    byte[] content

    String getContentDisposition() {
        if (reportId) {
            "attachment; filename=${title}-${reportId}.${format}"
        } else {
            "attachment; filename=${title}.${format}"
        }
    }
}
