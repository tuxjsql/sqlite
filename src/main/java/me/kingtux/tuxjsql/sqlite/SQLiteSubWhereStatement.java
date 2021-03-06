package me.kingtux.tuxjsql.sqlite;

import me.kingtux.tuxjsql.basic.sql.where.BasicSubWhereStatement;
import me.kingtux.tuxjsql.basic.sql.where.BasicWhereResponse;
import me.kingtux.tuxjsql.basic.sql.where.WhereUtils;
import me.kingtux.tuxjsql.core.TuxJSQL;


public class SQLiteSubWhereStatement<T> extends BasicSubWhereStatement<T> {
    private BasicWhereResponse response;
    public SQLiteSubWhereStatement(T and, TuxJSQL core) {
        super(and, core);
    }

    public SQLiteSubWhereStatement(TuxJSQL core) {
        super(core);
    }

    @Override
    public String getQuery() {
        if(whereObjects.isEmpty()) return "";
        if(response==null){
            response = WhereUtils.doubleBuild(whereObjects.toArray(),table);
        }
        return response.getQuery();
    }

    @Override
    public Object[] getValues() {
        if(whereObjects.isEmpty()) return whereObjects.toArray();
        if(response==null){
            response = WhereUtils.doubleBuild(whereObjects.toArray(),table);
        }
        return response.getValues();
    }

}
