package com.fighter.ace.code.db;

import com.fighter.ace.code.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

/**
 * Created by hanebert on 17/10/31.
 */
public class TableFactory {

    private static final Logger logger = LoggerFactory.getLogger(TableFactory.class);

    private DbHelper dbHelper = new DbHelper();
    private static TableFactory instance = null;

    private TableFactory() {
    }

    public static synchronized TableFactory getInstance() {
        if(instance == null) {
            instance = new TableFactory();
        }

        return instance;
    }

    public List getAllTables(boolean alltables) {
        try {
            Connection e = this.dbHelper.getConnection();
            return this.getAllTables(e, alltables);
        } catch (Exception var3) {
            var3.printStackTrace();
            throw new RuntimeException(var3);
        }
    }

    public Table getTable(String tableName) {
        return this.getTable(this.dbHelper.getSchema(), tableName);
    }

    private Table getTable(String schema, String tableName) {
        return this.getTable(this.dbHelper.getCatalog(), schema, tableName);
    }

    private Table getTable(String catalog, String schema, String tableName) {
        Table t = null;

        try {
            t = this._getTable(catalog, schema, tableName);
            if(t == null && !tableName.equals(tableName.toUpperCase())) {
                t = this._getTable(catalog, schema, tableName.toUpperCase());
            }

            if(t == null && !tableName.equals(tableName.toLowerCase())) {
                t = this._getTable(catalog, schema, tableName.toLowerCase());
            }
        } catch (Exception var6) {
            throw new RuntimeException(var6);
        }

        if(t == null) {
            throw new TableFactory.NotFoundTableException("not found table with give name:" + tableName + (this.dbHelper.isOracleDataBase()?" \n databaseStructureInfo:" + this.getDatabaseStructureInfo():""));
        } else {
            return t;
        }
    }

    private Table _getTable(String catalog, String schema, String tableName) throws SQLException {
        if(tableName != null && tableName.trim().length() != 0) {
            catalog = StringHelper.defaultIfEmpty(catalog, (String) null);
            schema = StringHelper.defaultIfEmpty(schema, (String)null);
            Connection conn = this.dbHelper.getConnection();
            DatabaseMetaData dbMetaData = conn.getMetaData();
            ResultSet rs = dbMetaData.getTables(catalog, schema, tableName, (String[])null);
            if(rs.next()) {
                Table table = this.createTable(conn, rs);
                return table;
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException("tableName must be not empty");
        }
    }

    private Table createTable(Connection conn, ResultSet rs) throws SQLException {
        String realTableName = null;

        try {
            ResultSetMetaData e = rs.getMetaData();
            if(rs.getString("TABLE_SCHEM") == null) {
                String var10000 = "";
            } else {
                rs.getString("TABLE_SCHEM");
            }

            realTableName = rs.getString("TABLE_NAME");
            String tableType = rs.getString("TABLE_TYPE");
            String remarks = rs.getString("REMARKS");
            if(remarks == null && this.dbHelper.isOracleDataBase()) {
                remarks = this.getOracleTableComments(realTableName);
            }

            Table table = new Table();
            table.setTableName(realTableName);
            table.setRemarks(remarks);
            if("SYNONYM".equals(tableType) && this.dbHelper.isOracleDataBase()) {
                table.setOwnerSynonymName(this.getSynonymOwner(realTableName));
            }

            this.retriveTableColumns(table);
            return table;
        } catch (SQLException var9) {
            throw new RuntimeException("create table object error,tableName:" + realTableName, var9);
        }
    }

    private List getAllTables(Connection conn, boolean alltables) throws SQLException {
        DatabaseMetaData dbMetaData = conn.getMetaData();
        String[] tableFlag = null;
        if(!alltables) {
            tableFlag = new String[]{"TABLE"};
        }

        ResultSet rs = dbMetaData.getTables(this.dbHelper.getCatalog(), this.dbHelper.getSchema(), "%", tableFlag);
        ArrayList tables = new ArrayList();

        while(rs.next()) {
            tables.add(this.createTable(conn, rs));
        }

        return tables;
    }

    private String getSynonymOwner(String synonymName) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String ret = null;

        try {
            ps = this.dbHelper.getConnection().prepareStatement("select table_owner from sys.all_synonyms where table_name=? and owner=?");
            ps.setString(1, synonymName);
            ps.setString(2, this.dbHelper.getSchema());
            rs = ps.executeQuery();
            if(!rs.next()) {
                String e = this.getDatabaseStructureInfo();
                throw new RuntimeException("Wow! Synonym " + synonymName + " not found. How can it happen? " + e);
            }

            ret = rs.getString(1);
        } catch (SQLException var10) {
            String databaseStructure = this.getDatabaseStructureInfo();
            logger.error(var10.getMessage(), var10);
            throw new RuntimeException("Exception in getting synonym owner " + databaseStructure);
        } finally {
            this.dbHelper.close(rs, ps, new Statement[0]);
        }

        return ret;
    }

    public String getDatabaseStructureInfo() {
        ResultSet schemaRs = null;
        ResultSet catalogRs = null;
        String nl = System.getProperty("line.separator");
        StringBuffer sb = new StringBuffer(nl);
        sb.append("Configured schema:").append(this.dbHelper.getSchema()).append(nl);
        sb.append("Configured catalog:").append(this.dbHelper.getCatalog()).append(nl);

        try {
            schemaRs = this.getMetaData().getSchemas();
            sb.append("Available schemas:").append(nl);

            while(schemaRs.next()) {
                sb.append("  ").append(schemaRs.getString("TABLE_SCHEM")).append(nl);
            }
        } catch (SQLException var18) {
            logger.error("Couldn\'t get schemas", var18);
            sb.append("  ?? Couldn\'t get schemas ??").append(nl);
        } finally {
            this.dbHelper.close(schemaRs, (PreparedStatement)null, new Statement[0]);
        }

        try {
            catalogRs = this.getMetaData().getCatalogs();
            sb.append("Available catalogs:").append(nl);

            while(catalogRs.next()) {
                sb.append("  ").append(catalogRs.getString("TABLE_CAT")).append(nl);
            }
        } catch (SQLException var16) {
            logger.error("Couldn\'t get catalogs", var16);
            sb.append("  ?? Couldn\'t get catalogs ??").append(nl);
        } finally {
            this.dbHelper.close(catalogRs, (PreparedStatement)null, new Statement[0]);
        }

        return sb.toString();
    }

    private DatabaseMetaData getMetaData() throws SQLException {
        return this.dbHelper.getConnection().getMetaData();
    }

    private void retriveTableColumns(Table table) throws SQLException {
        List primaryKeys = this.getTablePrimaryKeys(table);
        table.setPrimaryKeyColumns(primaryKeys);
        LinkedList indices = new LinkedList();
        HashMap uniqueIndices = new HashMap();
        HashMap uniqueColumns = new HashMap();
        ResultSet indexRs = null;

        try {
            if(table.getOwnerSynonymName() != null) {
                indexRs = this.getMetaData().getIndexInfo(this.dbHelper.getCatalog(), table.getOwnerSynonymName(), table.getTableName(), false, true);
            } else {
                indexRs = this.getMetaData().getIndexInfo(this.dbHelper.getCatalog(), this.dbHelper.getSchema(), table.getTableName(), false, true);
            }

            while(indexRs.next()) {
                String columns = indexRs.getString("COLUMN_NAME");
                String i = indexRs.getString("TYPE_NAME");
                if(columns != null) {
                    logger.trace("index:" + columns);
                    indices.add(columns);
                }

                String column = indexRs.getString("INDEX_NAME");
                boolean nonUnique = indexRs.getBoolean("NON_UNIQUE");
                if(!nonUnique && columns != null && column != null) {
                    Object l = (List)uniqueColumns.get(column);
                    if(l == null) {
                        l = new ArrayList();
                        uniqueColumns.put(column, l);
                    }

                    ((List)l).add(columns);
                    uniqueIndices.put(columns, column);
                    logger.trace("unique:" + columns + " (" + column + ")");
                }
            }
        } catch (Throwable var15) {
            ;
        } finally {
            this.dbHelper.close(indexRs, (PreparedStatement)null, new Statement[0]);
        }

        List columns1 = this.getTableColumns(table, primaryKeys, indices, uniqueIndices, uniqueColumns);
        Iterator i1 = columns1.iterator();

        while(i1.hasNext()) {
            Column column1 = (Column)i1.next();
            table.addColumn(column1);
        }

        if(primaryKeys.size() == 0) {
            logger.error("WARNING: The JDBC driver didn\'t report any primary key columns in " + table.getTableName());
        }

    }

    private List getTableColumns(Table table, List primaryKeys, List indices, Map uniqueIndices, Map uniqueColumns) throws SQLException {
        LinkedList columns = new LinkedList();
        ResultSet columnRs = this.getColumnsResultSet(table);

        while(columnRs.next()) {
            int sqlType = columnRs.getInt("DATA_TYPE");
            String sqlTypeName = columnRs.getString("TYPE_NAME");
            String columnName = columnRs.getString("COLUMN_NAME");
            String columnDefaultValue = columnRs.getString("COLUMN_DEF");
            String remarks = columnRs.getString("REMARKS");
            if(remarks == null && this.dbHelper.isOracleDataBase()) {
                remarks = this.getOracleColumnComments(table.getTableName(), columnName);
            }

            boolean isNullable = 1 == columnRs.getInt("NULLABLE");
            int size = columnRs.getInt("COLUMN_SIZE");
            int decimalDigits = columnRs.getInt("DECIMAL_DIGITS");
            boolean isPk = primaryKeys.contains(columnName);
            boolean isIndexed = indices.contains(columnName);
            String uniqueIndex = (String)uniqueIndices.get(columnName);
            List columnsInUniqueIndex = null;
            if(uniqueIndex != null) {
                columnsInUniqueIndex = (List)uniqueColumns.get(uniqueIndex);
            }

            boolean isUnique = columnsInUniqueIndex != null && columnsInUniqueIndex.size() == 1;
            if(isUnique) {
                logger.trace("unique column:" + columnName);
            }

            Column column = new Column(table, sqlType, sqlTypeName, columnName, size, decimalDigits, isPk, isNullable, isIndexed, isUnique, columnDefaultValue, remarks);
            columns.add(column);
        }

        columnRs.close();
        return columns;
    }

    private ResultSet getColumnsResultSet(Table table) throws SQLException {
        ResultSet columnRs = null;
        if(table.getOwnerSynonymName() != null) {
            columnRs = this.getMetaData().getColumns(this.dbHelper.getCatalog(), table.getOwnerSynonymName(), table.getTableName(), (String)null);
        } else {
            columnRs = this.getMetaData().getColumns(this.dbHelper.getCatalog(), this.dbHelper.getSchema(), table.getTableName(), (String)null);
        }

        return columnRs;
    }

    private List<String> getTablePrimaryKeys(Table table) throws SQLException {
        LinkedList primaryKeys = new LinkedList();
        ResultSet primaryKeyRs = null;
        if(table.getOwnerSynonymName() != null) {
            primaryKeyRs = this.getMetaData().getPrimaryKeys(this.dbHelper.getCatalog(), table.getOwnerSynonymName(), table.getTableName());
        } else {
            primaryKeyRs = this.getMetaData().getPrimaryKeys(this.dbHelper.getCatalog(), this.dbHelper.getSchema(), table.getTableName());
        }

        while(primaryKeyRs.next()) {
            String columnName = primaryKeyRs.getString("COLUMN_NAME");
            logger.trace(table.getTableName() + " primary key:" + columnName);
            primaryKeys.add(columnName);
        }

        primaryKeyRs.close();
        return primaryKeys;
    }

    private String getOracleTableComments(String table) {
        String sql = "SELECT comments FROM user_tab_comments WHERE table_name=\'" + table + "\'";
        return this.dbHelper.queryForString(sql);
    }

    private String getOracleColumnComments(String table, String column) {
        String sql = "SELECT comments FROM user_col_comments WHERE table_name=\'" + table + "\' AND column_name = \'" + column + "\'";
        return this.dbHelper.queryForString(sql);
    }

    public static class NotFoundTableException extends RuntimeException {
        private static final long serialVersionUID = 5976869128012158628L;

        public NotFoundTableException(String message) {
            super(message);
        }
    }


}
