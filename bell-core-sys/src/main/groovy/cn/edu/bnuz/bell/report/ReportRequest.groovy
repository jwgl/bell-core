package cn.edu.bnuz.bell.report

import javax.servlet.http.HttpServletRequest

/**
 * 报表请求
 * Created by yanglin on 2016/9/26.
 */
class ReportRequest {
    String reportService
    String reportName
    String format
    Map<String, Object> parameters = [:]

    String getQueryString() {
        def result = "format=${format}"
        if (parameters) {
            result += '&' + parameters.collect {key, value -> "${key}=${URLEncoder.encode(value.toString(), 'UTF-8')}"}.join('&')
        }
        result
    }

    String getRequestUrl() {
        "http://${reportService}/reports/${reportName}?${queryString}"
    }

    String getReportId() {
        if (parameters.idKey) {
            parameters[parameters.idKey.toString()]
        } else if (parameters.size() == 1) {
            parameters.values().toArray()[0].toString()
        } else {
            null
        }
    }
}
