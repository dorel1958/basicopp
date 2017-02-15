package dorel.basicopp.table;

public class ColumnInfo {

    public enum SqlType {

        CHAR,
        VARCHAR,
        TEXT,
        AUTO_INCREMENT,
        BYTE,
        SMALLINT,
        INT,
        BIGINT,
        DECIMAL,
        REAL,
        DOUBLE,
        DATE,
        MY_DATE_LONG,
        DATETIME,
        EXCLUDE,
    }

    public enum JavaType {

        BOOLEAN,
        STRING,
        BYTE,
        SHORT,
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        DATE
    }

    private String sqlColumnName;
    private String columnLabel;
    //
    private SqlType sqlDataType;
    private JavaType javaType;
    private int sqDataLength;  // unde este cazul
    private int sqlDataDecimals;  // unde este cazul
    private Object initialValue;
    private boolean password;
    private boolean sistem;

    public Object getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(Object initialValue) {
        this.initialValue = initialValue;
    }

    //<editor-fold defaultstate="collapsed" desc="Set / Get">
    public boolean isSistem() {
        return sistem;
    }

    public void setSistem(boolean sistem) {
        this.sistem = sistem;
    }

    public boolean isPassword() {
        return password;
    }

    public void setPassword(boolean password) {
        this.password = password;
    }

    public JavaType getJavaType() {
        return javaType;
    }

    public void setJavaType(JavaType javaType) {
        this.javaType = javaType;
    }

    public int getSqlDataDecimals() {
        return sqlDataDecimals;
    }

    public void setSqlDataDecimals(int sqlDataDecimals) {
        this.sqlDataDecimals = sqlDataDecimals;
    }

    public int getSqDataLength() {
        return sqDataLength;
    }

    public void setSqDataLength(int sqDatalLength) {
        this.sqDataLength = sqDatalLength;
    }

    public SqlType getSqlDataType() {
        return sqlDataType;
    }

    public void setSqlDataType(SqlType sqlDataType) {
        this.sqlDataType = sqlDataType;
    }

    public String getSqlColumnName() {
        return sqlColumnName;
    }

    public void setSqlColumnName(String sqlColumnName) {
        this.sqlColumnName = sqlColumnName;
    }

    public String getColumnLabel() {
        return columnLabel;
    }

    public void setColumnLabel(String columnLabel) {
        this.columnLabel = columnLabel;
    }
    //</editor-fold>

    public ColumnInfo(String columnLabel, String sqlColumnName, SqlType sqlDataType, int sqDatalLength, int sqlDataDecimals, JavaType javaType, Object initialValue, boolean password) {
        this.columnLabel = columnLabel;
        this.sqlColumnName = sqlColumnName;
        this.sqlDataType = sqlDataType;
        this.sqDataLength = sqDatalLength;
        this.sqlDataDecimals = sqlDataDecimals;
        this.javaType = javaType;
        this.initialValue = initialValue;
        this.password = password;
        this.sistem = false;
    }

    public ColumnInfo(String columnLabel, String sqlColumnName, SqlType sqlDataType, int sqDatalLength, int sqlDataDecimals, JavaType javaType, Object initialValue, boolean password, boolean sistem) {
        this.columnLabel = columnLabel;
        this.sqlColumnName = sqlColumnName;
        this.sqlDataType = sqlDataType;
        this.sqDataLength = sqDatalLength;
        this.sqlDataDecimals = sqlDataDecimals;
        this.javaType = javaType;
        this.initialValue = initialValue;
        this.password = password;
        this.sistem = sistem;
    }

}
