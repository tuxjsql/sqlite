package dev.tuxjsql.sqlite;

import dev.tuxjsql.basic.builders.BasicTableBuilder;
import dev.tuxjsql.basic.sql.BasicSQLTable;
import dev.tuxjsql.core.TuxJSQL;
import dev.tuxjsql.core.builders.ColumnBuilder;
import dev.tuxjsql.core.sql.SQLTable;

import java.util.stream.Collectors;

public class SQLITETableBuilder extends BasicTableBuilder {
    public SQLITETableBuilder(TuxJSQL jsql) {
        super(jsql);
    }

    @Override
    public SQLTable createTable() {
        SQLITETable table = new SQLITETable(getJsql(), getName(), getColumnBuilders().stream().map(ColumnBuilder::build).collect(Collectors.toList()));
        getJsql().getExecutor().execute(table::prepareTable);
        table.prepareTable();
        return table;
    }
}