package cn.edu.bnuz.bell.workflow

import cn.edu.bnuz.bell.http.BadRequestException
import cn.edu.bnuz.bell.http.ForbiddenException
import groovy.transform.CompileStatic

@CompileStatic
abstract class AbstractReviewService {
    void checkReviewer(Long id, String reviewType, String reviewer) {
        List<Map> reviewers = getReviewers(reviewType, id)
        switch (reviewType) {
            case ReviewTypes.CHECK:
            case ReviewTypes.APPROVE:
                if (!reviewers.find {it.id == reviewer}) {
                    throw new ForbiddenException()
                }
                break
            case ReviewTypes.REVIEW:
                // 已在WorkflowController中进行了身份验证
                break;
            default:
                throw new BadRequestException()
        }
    }

    abstract List<Map> getReviewers(String type, Long id);
}
