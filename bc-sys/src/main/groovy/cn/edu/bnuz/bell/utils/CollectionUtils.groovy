package cn.edu.bnuz.bell.utils

/**
 * Created by yanglin on 2015/4/17.
 */
class CollectionUtils {
    static groupBy(List data, List<GroupCondition> conditions, Closure c = null) {
        if(!conditions) {
            if(c) {
                return c(data)
            } else {
                return data
            }
        }

        GroupCondition condition = conditions[0]
        def result = []
        def groups = [:]
        def into = condition.into
        data.each { row ->
            def groupValue = row[condition.groupBy]
            def item = groups[groupValue]
            if (item == null) {
                item = [:]
                condition.mappings.each { k, v ->
                    item[v] = row[k]
                }
                item[into] = []
                result << item
                groups[groupValue] = item
            }

            item[into] << row.findAll {
                condition.mappings[it.key] == null
            }
        }

        groups.each { k, v ->
            v[into] = groupBy(v[into], conditions.size() > 1 ? conditions[1..-1] : null, c)
        }
        return result
    }
}

class GroupCondition {
    String groupBy
    String into
    Map mappings
}