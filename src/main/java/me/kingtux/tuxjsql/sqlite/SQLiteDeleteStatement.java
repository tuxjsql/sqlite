package me.kingtux.tuxjsql.sqlite;

import me.kingtux.tuxjsql.basic.response.BasicDBDelete;
import me.kingtux.tuxjsql.basic.sql.BasicDeleteStatement;
import me.kingtux.tuxjsql.basic.sql.where.BasicWhereStatement;
import me.kingtux.tuxjsql.core.TuxJSQL;
import me.kingtux.tuxjsql.core.response.DBAction;
import me.kingtux.tuxjsql.core.response.DBDelete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLiteDeleteStatement extends BasicDeleteStatement {
    public SQLiteDeleteStatement(TuxJSQL tuxJSQL) {
        super(tuxJSQL);
    }

    @Override
    public DBAction<DBDelete> execute() {
        return new DBAction<>(this::dbDelete, tuxJSQL);
    }

    private DBDelete dbDelete() {
        ((BasicWhereStatement) whereStatement).setTable(table);
        DBDelete delete = null;
        String sql = String.format(Queries.DELETE.getString(), table.getName());
        if (whereStatement.getValues().length != 0) {
            sql += " " + String.format(Queries.WHERE.getString(), whereStatement.getQuery());
        }
        TuxJSQL.getLogger().debug(sql);
        try (Connection connection = tuxJSQL.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                int i = 1;
                for (Object object : whereStatement.getValues()) {
                    preparedStatement.setObject(i++, object);
                }
                delete = new BasicDBDelete(table, preparedStatement.executeUpdate(), true);
            }
        } catch (SQLException e) {
            TuxJSQL.getLogger().error("Unable to execute delete query", e);
            return new BasicDBDelete(table, 0, false);
        }
        return delete;
    }
}
