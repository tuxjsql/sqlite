package dev.tuxjsql.sqlite;

import dev.tuxjsql.basic.sql.BasicSQLColumn;
import dev.tuxjsql.core.sql.SQLColumn;
import dev.tuxjsql.core.sql.SQLDataType;
import dev.tuxjsql.core.sql.SQLTable;

import java.util.List;

public class SQLITEColumn extends BasicSQLColumn {
    private static final String AUTOINCREMENT = " AUTOINCREMENT";
    private static final String PRIMARY_KEY = " PRIMARY KEY";

    public SQLITEColumn(String name, Object defaultValue, List<String> dataTypeRules, boolean notNull, boolean unique, boolean autoIncrement, boolean primaryKey, SQLColumn foreignKey, SQLTable table, SQLDataType type) {
        super(name, defaultValue, dataTypeRules, notNull, unique, autoIncrement, primaryKey, foreignKey, table, type);
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append("`").append(name).append("`");
        builder.append(" ").append(buildDataType());
        builder.append(primaryKey() ? PRIMARY_KEY : "");
        builder.append(autoIncrement() ? AUTOINCREMENT : "");
        if (!autoIncrement()) {
            builder.append(notNull() ? " NOT NULL" : "");
            builder.append(unique() ? " UNIQUE" : "");
        }
        if (defaultValue != null) {
            builder.append(" DEFAULT ");
            builder.append("`").append(defaultValue).append("`");
        }
        return builder.toString();
    }

    @Override
    public String foreignBuild() {
        return String.format(Queries.FOREIGN_VALUE.getString(), getName(), foreignKey().getTable().getName(), foreignKey().getName());
    }
}
