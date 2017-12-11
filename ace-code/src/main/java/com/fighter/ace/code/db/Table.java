package com.fighter.ace.code.db;

import com.fighter.ace.code.render.pojo.hibernate.HibernateConfig;
import com.fighter.ace.code.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by hanebert on 17/10/31.
 */
public class Table extends HibernateConfig {

    private static final Logger logger = LoggerFactory.getLogger(Table.class);


    String remarks;
    private String ownerSynonymName = null;
    LinkedHashSet<Column> columns = new LinkedHashSet();
    List<Column> primaryKeyColumns = new ArrayList();

    public boolean equals(Object obj) {
        Table table = (Table)obj;
        return table.getTableName().equals(this.tableName);
    }

    public Table() {
    }

    public Table(Table t) {
        this.setTableName(t.getTableName());
        this.remarks = t.getRemarks();
        this.clazzName = t.getTableName();
        this.ownerSynonymName = t.getOwnerSynonymName();
        this.columns = t.getColumns();
        this.primaryKeyColumns = t.getPkColumns();
    }

    public LinkedHashSet<Column> getColumns() {
        return this.columns;
    }

    public void setColumns(LinkedHashSet<Column> columns) {
        this.columns = columns;
    }

    public String getOwnerSynonymName() {
        return this.ownerSynonymName;
    }

    public void setOwnerSynonymName(String ownerSynonymName) {
        this.ownerSynonymName = ownerSynonymName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void addColumn(Column column) {
        this.columns.add(column);
    }

    public void setClazzName(String customClassName) {
        this.clazzName = customClassName;
    }

    public String getClazzName() {
        if(StringHelper.isBlank(this.clazzName)) {
            String removedPrefixtableName = removeTabletableNamePrefix(this.tableName);
            this.clazzName = StringHelper.makeAllWordFirstLetterUpperCase(StringHelper.toUnderscoreName(removedPrefixtableName));
        }

        return this.clazzName;
    }

    public static String removeTabletableNamePrefix(String tableName) {
        String prefixs = "_,T_";
        String[] arr$ = prefixs.split(",");
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String prefix = arr$[i$];
            String removedPrefixtableName = StringHelper.removePrefix(tableName, prefix, true);
            if(!removedPrefixtableName.equals(tableName)) {
                return removedPrefixtableName;
            }
        }

        return tableName;
    }

    public String getClazzNameLowerCase() {
        return this.getClazzName().toLowerCase();
    }

    public String getUnderscoreName() {
        return StringHelper.toUnderscoreName(this.getClazzName()).toLowerCase();
    }

    public String getClazzNameFirstLower() {
        return StringHelper.uncapitalize(this.getClazzName());
    }

    public String getConstantName() {
        return StringHelper.toUnderscoreName(this.getClazzName()).toUpperCase();
    }

    public int getPkCount() {
        int pkCount = 0;
        Iterator i$ = this.columns.iterator();

        while(i$.hasNext()) {
            Column c = (Column)i$.next();
            if(c.isPrimary()) {
                ++pkCount;
            }
        }

        return pkCount;
    }

    public List<Column> getPkColumns() {
        ArrayList results = new ArrayList();
        Iterator i$ = this.getColumns().iterator();

        while(i$.hasNext()) {
            Column c = (Column)i$.next();
            if(c.isPrimary()) {
                results.add(c);
            }
        }

        return results;
    }

    public List<Column> getNotPkColumns() {
        ArrayList results = new ArrayList();
        Iterator i$ = this.getColumns().iterator();

        while(i$.hasNext()) {
            Column c = (Column)i$.next();
            if(!c.isPrimary()) {
                results.add(c);
            }
        }

        return results;
    }

    public Column getPkColumn() {
        if(this.getPkColumns().isEmpty()) {
            throw new IllegalStateException("not found primary key on table:" + this.getTableName());
        } else {
            return (Column)this.getPkColumns().get(0);
        }
    }

    /** @deprecated */
    @Deprecated
    public Column getIdColumn() {
        return this.getPkColumn();
    }

    public Column getColumnByName(String name) {
        Column c = this.getColumnBytableName(name);
        if(c == null) {
            c = this.getColumnBytableName(StringHelper.toUnderscoreName(name));
        }

        return c;
    }

    public Column getColumnBytableName(String tableName) {
        Iterator i$ = this.getColumns().iterator();

        Column c;
        do {
            if(!i$.hasNext()) {
                return null;
            }

            c = (Column)i$.next();
        } while(!c.getField().equalsIgnoreCase(tableName));

        return c;
    }

    public Column getRequiredColumnBytableName(String tableName) {
        if(this.getColumnBytableName(tableName) == null) {
            throw new IllegalArgumentException("not found column with tableName:" + tableName + " on table:" + this.getTableName());
        } else {
            return this.getColumnBytableName(tableName);
        }
    }

    public List<Column> getIgnoreKeywordsColumns(String ignoreKeywords) {
        ArrayList results = new ArrayList();
        Iterator i$ = this.getColumns().iterator();

        while(i$.hasNext()) {
            Column c = (Column)i$.next();
            String tableName = c.getField().toLowerCase();
            if(!StringHelper.contains(tableName, ignoreKeywords.split(","))) {
                results.add(c);
            }
        }

        return results;
    }

    public void setPrimaryKeyColumns(List<Column> primaryKeyColumns) {
        this.primaryKeyColumns = primaryKeyColumns;
    }

    public void showStructureInfo() {
        this.logger.debug("tableName:" + this.getTableName());

        String tableName;
        for(Iterator i$ = this.getColumns().iterator(); i$.hasNext(); this.logger.debug("column:" + tableName)) {
            Column c = (Column)i$.next();
            tableName = c.getField().toLowerCase();
            if(c.isPrimary()) {
                this.logger.debug("primary key :" + tableName);
            }
        }

        this.logger.debug("tableName:" + this.getTableName());
    }

}
