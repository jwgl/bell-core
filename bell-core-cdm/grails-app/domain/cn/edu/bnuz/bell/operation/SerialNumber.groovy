package cn.edu.bnuz.bell.operation

/**
 * 序列数字
 */
class SerialNumber {
    Integer id
    Integer oddEven

    static mapping = {
        comment    '序列数字'
        table      schema: 'ea'
        id         comment: '序列数字ID'
        oddEven    comment: '单双数-1:单;2:双'
    }
}
