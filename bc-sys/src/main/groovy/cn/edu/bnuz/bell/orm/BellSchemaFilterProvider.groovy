package cn.edu.bnuz.bell.orm

import grails.util.Holders
import groovy.util.logging.Log4j
import org.hibernate.boot.model.relational.Namespace
import org.hibernate.boot.model.relational.Sequence
import org.hibernate.mapping.Table
import org.hibernate.tool.schema.spi.SchemaFilter
import org.hibernate.tool.schema.spi.SchemaFilterProvider

/**
 * Created by yanglin on 2016/2/12.
 */
@Log4j
class BellSchemaFilterProvider implements SchemaFilterProvider {
    private BellSchemaFilter filter = new BellSchemaFilter();
    SchemaFilter getCreateFilter() {
        return filter;
    }

    SchemaFilter getDropFilter() {
        return filter;
    }

    SchemaFilter getMigrateFilter() {
        return filter;
    }

    SchemaFilter getValidateFilter() {
        return filter;
    }

    static class BellSchemaFilter implements SchemaFilter {
        def ignoredSchemas = Holders.config.bell.orm.ignored.schemas
        def ignoredTables = Holders.config.bell.orm.ignored.tables
        def ignoredViewPrefix = Holders.config.bell.orm.ignored.viewPrefix

        boolean includeNamespace(Namespace namespace) {
            return true
        }

        boolean includeTable(Table table) {
            if (ignoredSchemas && ignoredSchemas.contains(table.schema.toString())) {
                log.debug("Ignored scheme: $table.name")
                return false
            }

            if (ignoredTables && ignoredTables.contains(table.name)) {
                log.debug "Ignored table: $table.name"
                return false
            }

            if (ignoredViewPrefix && table.name.startsWith(ignoredViewPrefix)) {
                log.debug "Ignored view: $table.name"
                return false
            }

            return true
        }

        boolean includeSequence(Sequence sequence) {
            return true
        }
    }
}
