package com.fighter.ace.code.db;

import com.fighter.ace.code.render.pojo.hibernate.HibernateConfigProperty;
import com.fighter.ace.code.util.DatabaseDataTypesUtils;
import com.fighter.ace.code.util.JavaPrimitiveTypeMapping;
import com.fighter.ace.code.util.JdbcType;
import com.fighter.ace.code.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hanebert on 17/10/31.
 */
public class Column extends HibernateConfigProperty {

    private static final Logger logger = LoggerFactory.getLogger(Column.class);

    private Table _table;
    private int _sqlType;
    private String _sqlTypeName;
    private boolean _isFk;
    private int _decimalDigits;
    private boolean _isIndexed;
    private boolean _isUnique;
    private String _defaultValue;
    private String _remarks;
    private String javaType;
    private String columnAlias;

    public Column(Table table, int sqlType, String sqlTypeName, String sqlName, int size, int decimalDigits, boolean isPk, boolean isNullable, boolean isIndexed, boolean isUnique, String defaultValue, String remarks) {
        this._table = table;
        this._sqlType = sqlType;
        this.field = sqlName;
        this._sqlTypeName = sqlTypeName;
        this.length = Integer.valueOf(size);
        this._decimalDigits = decimalDigits;
        this.primary = isPk;
        this.nullable = isNullable;
        this._isIndexed = isIndexed;
        this._isUnique = isUnique;
        this._defaultValue = defaultValue;
        this._remarks = remarks;
    }

    public Column(Column c) {
        this(c.getTable(), c.getSqlType(), c.getSqlTypeName(), c.getName(), c.getLength().intValue(), c.getDecimalDigits(), c.isPrimary(), c.isNullable(), c.isIndexed(), c.isUnique(), c.getDefaultValue(), c.getRemarks());
    }

    public Column() {
    }

    public int getSqlType() {
        return this._sqlType;
    }

    public Table getTable() {
        return this._table;
    }

    public int getDecimalDigits() {
        return this._decimalDigits;
    }

    public String getSqlTypeName() {
        return this._sqlTypeName;
    }

    public String getField() {
        return this.field;
    }

    public boolean isFk() {
        return this._isFk;
    }

    public boolean isIndexed() {
        return this._isIndexed;
    }

    public boolean isUnique() {
        return this._isUnique;
    }

    public String getDefaultValue() {
        return this._defaultValue;
    }

    public String getRemarks() {
        return this._remarks;
    }

    public void setUnique(boolean unique) {
        this._isUnique = unique;
    }

    public int hashCode() {
        return this.getTable() != null?(this.getTable().getTableName() + "#" + this.getField()).hashCode():this.getField().hashCode();
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else {
            if(o instanceof Column) {
                Column other = (Column)o;
                if(this.getField().equals(other.getField())) {
                    return true;
                }
            }

            return false;
        }
    }

    public String toString() {
        return this.getField();
    }

    protected String prefsPrefix() {
        return "tables/" + this.getTable().getTableName() + "/columns/" + this.getField();
    }

    void setFk(boolean flag) {
        this._isFk = flag;
    }

    public String getUnderscoreName() {
        return this.getField().toLowerCase();
    }

    public String getName() {
        this.name = StringHelper.toJavaVariableName(this.field);
        return this.name;
    }

    public String getColumnNameFirstLower() {
        return StringHelper.uncapitalize(this.getName());
    }

    public String getColumnNameLowerCase() {
        return this.getName().toLowerCase();
    }

    public String getJdbcSqlTypeName() {
        String result = JdbcType.getJdbcSqlTypeName(this.getSqlType());
        return result;
    }

    public String getColumnAlias() {
        return this.columnAlias;
    }

    public String getConstantName() {
        return StringHelper.toUnderscoreName(this.getName()).toUpperCase();
    }

    public boolean getIsStringColumn() {
        return DatabaseDataTypesUtils.isString(this.getJavaType());
    }

    public boolean getIsDateTimeColumn() {
        return DatabaseDataTypesUtils.isDate(this.getJavaType());
    }

    public boolean getIsNumberColumn() {
        return DatabaseDataTypesUtils.isFloatNumber(this.getJavaType()) || DatabaseDataTypesUtils.isIntegerNumber(this.getJavaType());
    }

    public boolean contains(String keywords) {
        if(keywords == null) {
            throw new IllegalArgumentException("\'keywords\' must be not null");
        } else {
            return StringHelper.contains(this.getField(), keywords.split(","));
        }
    }

    public String getJavaType() {
        this.javaType = DatabaseDataTypesUtils.getPreferredJavaType(this.getSqlType(), this.getLength().intValue(), this.getDecimalDigits());
        return this.javaType;
    }

    public String getSimpleJavaType() {
        return "java.util.Date".equals(this.getJavaType())?"Date":("java.math.BigDecimal".equals(this.getJavaType())?"Long":(!"java.sql.Clob".equals(this.getJavaType()) && !"java.sql.Blob".equals(this.getJavaType())?StringHelper.removePrefix(this.getJavaType(), "java.lang."):"String"));
    }

    public String getType() {
        return this.getSimpleJavaType();
    }

    public String getPrimitiveJavaType() {
        return JavaPrimitiveTypeMapping.getPrimitiveType(this.getSimpleJavaType());
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public void setColumnAlias(String columnAlias) {
        this.columnAlias = columnAlias;
    }

}
