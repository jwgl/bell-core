package cn.edu.bnuz.bell.report

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.client.OAuth2RestOperations

import javax.annotation.Resource

class ReportClientService {
    @Resource(name="reportRestTemplate")
    OAuth2RestOperations restTemplate

    ReportResponse runAndRender(ReportRequest reportRequest) {
        ResponseEntity<byte[]> responseEntity = restTemplate.getForEntity(reportRequest.requestUrl, byte[])

        if (responseEntity.statusCode == HttpStatus.OK) {
            new ReportResponse(
                    statusCode: responseEntity.statusCode,
                    format: reportRequest.format,
                    title: responseEntity.headers.getFirst('X-Report-Title'),
                    reportId: reportRequest.reportId,
                    content: responseEntity.body,
            )
        } else {
            new ReportResponse(
                    statusCode: responseEntity.statusCode,
            )
        }
    }
}
