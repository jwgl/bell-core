package cn.edu.bnuz.bell.report
/**
 * 报表请求
 * Created by yanglin on 2016/9/26.
 */
class ReportRequest {
    String reportName
    String format = 'xlsx'
    Map<String, ?> parameters = [:]

    private String _reportId

    String getQueryString() {
        def result = "format=${format}"
        if (parameters) {
            result += '&' + parameters.collect {key, value -> "${key}=${URLEncoder.encode(value.toString(), 'UTF-8')}"}.join('&')
        }
        result
    }

    String getRequestUrl() {
        "reports/${reportName}?${queryString}"
    }

    String getReportId() {
        if (_reportId) {
            this._reportId
        } else if (parameters.size() == 1) {
            parameters.values().toArray()[0].toString()
        } else {
            null
        }
    }

    String setReportId(String reportId) {
        this._reportId = reportId
    }
}
