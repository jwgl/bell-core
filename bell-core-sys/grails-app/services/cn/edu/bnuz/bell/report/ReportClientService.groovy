package cn.edu.bnuz.bell.report

import grails.plugins.rest.client.RestBuilder
import org.grails.web.util.WebUtils
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate

class ReportClientService {
    RestTemplate restTemplate

    ReportResponse runAndRender(ReportRequest reportRequest) {
        RestBuilder rest = new RestBuilder(restTemplate)
        def restResponse = rest.get(reportRequest.requestUrl) {
            auth WebUtils.retrieveGrailsWebRequest().currentRequest.getHeader('authorization')
            accept byte[]
        }

        def reportResponse = new ReportResponse(
                statusCode: restResponse.statusCode,
                format: reportRequest.format,
        )

        if (reportResponse.statusCode == HttpStatus.OK) {
            reportResponse.title = restResponse.headers.getFirst('X-Report-Title')
            reportResponse.reportId = reportRequest.reportId
            reportResponse.content = (byte[])restResponse.body
        }

        reportResponse
    }
}
