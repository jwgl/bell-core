package cn.edu.bnuz.bell.report

import grails.io.IOUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.client.OAuth2RestOperations

import javax.annotation.Resource
import javax.servlet.http.HttpServletResponse

class ReportClientService {
    @Resource(name = "reportRestTemplate")
    OAuth2RestOperations restTemplate

    @Value('${bell.report.service}')
    String reportService

    @Deprecated
    ReportResponse runAndRender(ReportRequest reportRequest) {
        ResponseEntity<byte[]> responseEntity = restTemplate.getForEntity(reportRequest.requestUrl, byte[])

        if (responseEntity.statusCode == HttpStatus.OK) {
            new ReportResponse(
                    statusCode: responseEntity.statusCode,
                    format: reportRequest.format,
                    title: responseEntity.headers.getFirst('X-Report-Title'),
                    reportId: reportRequest.reportId,
                    content: responseEntity.body,
                    contentType: responseEntity.headers.getFirst('Content-Type'),
            )
        } else {
            new ReportResponse(
                    statusCode: responseEntity.statusCode,
            )
        }
    }

    void runAndRender(ReportRequest reportRequest, HttpServletResponse servletResponse) {
        restTemplate.execute(
                "http://${reportService}/${reportRequest.requestUrl}",
                HttpMethod.GET,
                { clientHttpRequest -> },
                { clientHttpResponse ->
                    def statusCode = clientHttpResponse.statusCode
                    servletResponse.setStatus(statusCode.value())
                    if (statusCode == HttpStatus.OK) {
                        def title = clientHttpResponse.headers.getFirst('X-Report-Title')
                        def fileName = reportRequest.reportId ?
                                "${title}-${reportRequest.reportId}.${reportRequest.format}" :
                                "${title}.${reportRequest.format}"
                        servletResponse.setHeader('Content-Disposition', "attachment; filename=${fileName}")
                        servletResponse.setContentType(clientHttpResponse.headers.getFirst('Content-Type'))
                        IOUtils.copy(clientHttpResponse.getBody(), servletResponse.getOutputStream())
                    }
                },
        )
    }
}
