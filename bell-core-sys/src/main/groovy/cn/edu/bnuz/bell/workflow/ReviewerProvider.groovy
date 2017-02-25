package cn.edu.bnuz.bell.workflow

interface ReviewerProvider {
    List<Map> getReviewers(Object id, String type)
}
