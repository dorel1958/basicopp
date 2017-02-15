package dorel.basicopp.table;

import dorel.basicopp.datatypes.MyDate;
import dorel.basicopp.server.ServerHelper;
import dorel.basicopp.server.ServerTools;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.swing.JOptionPane;

public abstract class TableRowHelper implements TableRowInterface {

    protected String schemaName = "";
    protected String tableName = "";
    //
    protected User userCurent;
    protected Map<String, Object> valori;
    private String mesajEroare;
    private final boolean eDesktop = true;
    protected List<ColumnInfo> coloane;

    protected static final DateFormat dfm;
    protected static final DateFormat dfmExact;
    protected static final GregorianCalendar cal;
    protected final MyDate myDate;

    static {
        dfm = new SimpleDateFormat("dd.MM.yyyy");
        dfm.setTimeZone(TimeZone.getTimeZone("Europe/Bucharest")); // EET, Eastern Eeuropean Time
        cal = new GregorianCalendar(TimeZone.getTimeZone("Europe/Bucharest"));
        dfmExact = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dfmExact.setTimeZone(TimeZone.getTimeZone("Europe/Bucharest")); // EET, Eastern Eeuropean Time
    }

    // <editor-fold defaultstate="collapsed" desc="Get Set">
    public String getMesajEroare() {
        return mesajEroare;
    }

    @Override
    public Object getValue(String numeColoana) {
        return valori.get(numeColoana);
    }

    private boolean testValue(ColumnInfo.JavaType tipColoana, ColumnInfo.SqlType tipSql, Object valoare) {
        if (valoare == null) {
            switch (tipColoana) {
                case BOOLEAN:
                    valoare = false;
                    break;
                case BYTE:
                    valoare = new Byte("0");
                    break;
                case DATE:
                    break;
                case DOUBLE:
                    valoare = 0d;
                    break;
                case FLOAT:
                    valoare = 0f;
                    break;
                case INT:
                    valoare = 0;
                    break;
                case LONG:
                    valoare = 0L;
                    break;
                case SHORT:
                    valoare = new Short("0");
                    break;
                case STRING:
                    valoare = "";
                    break;
            }
            return true;
        } else {
            try {
                switch (tipColoana) {
                    case BOOLEAN:
                        boolean bovaloare = (boolean) valoare;
                        break;
                    case BYTE:
                        byte bivaloare = (Byte) valoare;
                        break;
                    case DATE:
                        if (tipSql == ColumnInfo.SqlType.MY_DATE_LONG) {
                            long datavaloarel = (Long) valoare;
                        } else {
                            Date davaloared = (Date) valoare;
                        }
                        break;
                    case DOUBLE:
                        Double dval = (Double) valoare;
                        break;
                    case FLOAT:
                        Float flo = (Float) valoare;
                        break;
                    case INT:
                        int intre = (Integer) valoare;
                        break;
                    case LONG:
                        Long lon = (Long) valoare;
                        break;
                    case SHORT:
                        short sh = (Short) valoare;
                        break;
                    case STRING:
                        String stri = (String) valoare;
                        break;
                }
                return true;
            } catch (Exception ex) {
                eroare("TableRowHelper.testValue dataType eronat - datatype:" + tipColoana + " - :" + ex.getLocalizedMessage());
                return false;
            }
        }
    }

    @Override
    public String getStringValue(String numeColoana) {
        return getStringValue(numeColoana, false);
    }

    public String getStringValue(String numeColoana, boolean eSql) {
        for (ColumnInfo col : coloane) {
            if (col.getSqlColumnName().equals(numeColoana)) {
                try {
                    Object valoare = valori.get(numeColoana);
                    if (valoare == null) {
                        switch (col.getJavaType()) {
                            case BOOLEAN:
                                return "false";
                            case BYTE:
                                return "0";
                            case DATE:
                                if (eSql) {
                                    return "null";
                                } else {
                                    return "";
                                }
                            case DOUBLE:
                                return "0";
                            case FLOAT:
                                return "0";
                            case INT:
                                return "0";
                            case LONG:
                                return "0";
                            case SHORT:
                                return "0";
                            case STRING:
                                if (eSql) {
                                    return "''";
                                } else {
                                    return "";
                                }
                        }
                    } else {
                        switch (col.getJavaType()) {
                            case BOOLEAN:
                                if ((boolean) valoare) {
                                    return "true";
                                } else {
                                    return "false";
                                }
                            case BYTE:
                                return String.valueOf((Byte) valoare);
                            case DATE:
                                if (eSql) {
                                    if (col.getSqlDataType() == ColumnInfo.SqlType.MY_DATE_LONG) {
                                        return String.valueOf(((Date) valoare).getTime());
                                    } else {
                                        return ServerTools.sqlDate((Date) valoare);
                                    }
                                } else {
                                    return dfm.format((Date) valoare);
                                }
                            case DOUBLE:
                                return String.valueOf((Double) valoare);
                            case FLOAT:
                                return String.valueOf((Float) valoare);
                            case INT:
                                return String.valueOf((Integer) valoare);
                            case LONG:
                                return String.valueOf((Long) valoare);
                            case SHORT:
                                return String.valueOf((Short) valoare);
                            case STRING:
                                if (eSql) {
                                    String strTestat = (String) valori.get(numeColoana);
                                    if (strTestat.length() > col.getSqDataLength()) {  // test lungime string - evit erorile SQL
                                        strTestat = strTestat.substring(0, col.getSqDataLength());
                                    }
                                    return ServerTools.sqlString(strTestat);
                                } else {
                                    if (col.isPassword()) {
                                        return "";  // NUMAI asa
                                    } else {
                                        return (String) valori.get(numeColoana);
                                    }
                                }
                        }
                    }
                } catch (Exception ex) {
                    eroare("TableRowHelper.getStringValue - tip data eronat pe coloana:" + col.getSqlColumnName() + " tip corect=" + col.getJavaType() + " - eroare:" + ex.getLocalizedMessage());
                }
            }
        }
        return "";
    }

    public final void setValue(String numeColoana, Object newValue) {
        for (ColumnInfo col : coloane) {
            if (col.getSqlColumnName().equals(numeColoana)) {
                if (testValue(col.getJavaType(), col.getSqlDataType(), newValue)) {
                    if (col.isPassword()) {
                        valori.put(numeColoana, User.getMD5((String) newValue));
                    } else {
                        valori.put(numeColoana, newValue);
                    }
                    return;
                }
            }
        }
        eroare("TableRowHelper.setValue - nume coloana necunoscut:" + numeColoana);
    }

    @Override
    public final void setValues(ResultSet rs) {
        for (ColumnInfo ci : coloane) {
            if (ci.getSqlDataType() != ColumnInfo.SqlType.EXCLUDE) {
                try {
                    Object valoare = null;
                    switch (ci.getJavaType()) {
                        case BOOLEAN:
                            valoare = rs.getBoolean(ci.getSqlColumnName());
                            break;
                        case BYTE:
                            valoare = rs.getByte(ci.getSqlColumnName());
                            break;
                        case DATE:
                            if (ci.getSqlDataType() == ColumnInfo.SqlType.MY_DATE_LONG) {
                                long time = rs.getLong(ci.getSqlColumnName());
                                // time poate fi negativ (an 1961)
                                cal.setTimeInMillis(time);
                                valoare = cal.getTime();
                            } else {
                                valoare = rs.getDate(ci.getSqlColumnName());
                            }
                            break;
                        case DOUBLE:
                            valoare = rs.getDouble(ci.getSqlColumnName());
                            break;
                        case FLOAT:
                            valoare = rs.getFloat(ci.getSqlColumnName());
                            break;
                        case INT:
                            valoare = rs.getInt(ci.getSqlColumnName());
                            break;
                        case LONG:
                            valoare = rs.getLong(ci.getSqlColumnName());
                            break;
                        case SHORT:
                            valoare = rs.getShort(ci.getSqlColumnName());
                            break;
                        case STRING:
                            valoare = rs.getString(ci.getSqlColumnName());
                            break;
                    }
                    //setValue(ci.getSqlColumnName(), valoare);
                    valori.put(ci.getSqlColumnName(), valoare);
                } catch (SQLException ex) {
                    eroare("TableRowHelper.setValues coloana:" + ci.getSqlColumnName() + " - SQLException:" + ex.getLocalizedMessage());
                }
            }
        }
    }

    @Override
    public void setStringValue(String numeColoana, String newValue) {
        for (ColumnInfo col : coloane) {
            if (col.getSqlColumnName().equals(numeColoana)) {
                if (newValue == null) {
                    puneValoareNull(col.getJavaType(), numeColoana);
                    return;
                } else {
                    try {
                        switch (col.getJavaType()) {
                            case BOOLEAN:
                                valori.put(numeColoana, newValue.equals("true"));
                                return;
                            case BYTE:
                                valori.put(numeColoana, Byte.parseByte(newValue));
                                return;
                            case DATE:
                                valori.put(numeColoana, myDate.stringDMYToData(newValue));
                                return;
                            case DOUBLE:
                                valori.put(numeColoana, Double.parseDouble(newValue));
                                return;
                            case FLOAT:
                                valori.put(numeColoana, Float.parseFloat(newValue));
                                return;
                            case INT:
                                valori.put(numeColoana, Integer.parseInt(newValue));
                                return;
                            case LONG:
                                valori.put(numeColoana, Long.parseLong(newValue));
                                return;
                            case SHORT:
                                valori.put(numeColoana, Short.parseShort(newValue));
                                return;
                            case STRING:
                                valori.put(numeColoana, newValue);
                                return;
                        }
                    } catch (NumberFormatException ex) {
                        puneValoareNull(col.getJavaType(), numeColoana);
                        eroare("TableRowHelper.setStringValue - nume coloana:" + numeColoana + " - NumberFormatException:" + ex.getLocalizedMessage());
                        return;
                    }
                }
            }
        }
        eroare("TableRowHelper.setStringValue - nume coloana necunoscut:" + numeColoana);
    }

    private void puneValoareNull(ColumnInfo.JavaType javaType, String numeColoana) {
        switch (javaType) {
            case BOOLEAN:
                valori.put(numeColoana, false);
                break;
            case BYTE:
                valori.put(numeColoana, new Byte("0"));
                break;
            case DATE:
                valori.put(numeColoana, null);
                break;
            case DOUBLE:
                valori.put(numeColoana, 0d);
                break;
            case FLOAT:
                valori.put(numeColoana, 0f);
                break;
            case INT:
                valori.put(numeColoana, 0);
                break;
            case LONG:
                valori.put(numeColoana, 0L);
                break;
            case SHORT:
                valori.put(numeColoana, new Short("0"));
                break;
            case STRING:
                valori.put(numeColoana, "");
                break;
        }
    }

    @Override
    public void setId(int id) {
        valori.put("id", (Integer) id);
    }

    @Override
    public int getId() {
        return (Integer) valori.get("id");
    }

    @Override
    public String getColumnLabel(String colName) {
        for (ColumnInfo ci : coloane) {
            if (ci.getSqlColumnName().equals(colName)) {
                return ci.getColumnLabel();
            }
        }
        return "";
    }
    // </editor-fold>

    @Override
    public List<ColumnInfo> getListaColoane() {
        return coloane;
    }

    // <editor-fold defaultstate="collapsed" desc="Constructor">
    public TableRowHelper(User userCurent) {
        this.userCurent = userCurent;
        coloane = new ArrayList<>();
        coloane.add(new ColumnInfo("ID", "id", ColumnInfo.SqlType.AUTO_INCREMENT, 0, 0, ColumnInfo.JavaType.INT, 0, false, true));
        coloane.add(new ColumnInfo("user_add", "user_add", ColumnInfo.SqlType.INT, 0, 0, ColumnInfo.JavaType.INT, 0, false, true));
        coloane.add(new ColumnInfo("data_add", "data_add", ColumnInfo.SqlType.MY_DATE_LONG, 0, 0, ColumnInfo.JavaType.DATE, null, false, true));
        coloane.add(new ColumnInfo("user_mod", "user_mod", ColumnInfo.SqlType.INT, 0, 0, ColumnInfo.JavaType.INT, 0, false, true));
        coloane.add(new ColumnInfo("data_mod", "data_mod", ColumnInfo.SqlType.MY_DATE_LONG, 0, 0, ColumnInfo.JavaType.DATE, null, false, true));
        myDate = new MyDate(true);
    }

    protected void valoriInitiale() {
        // este apelata dupa executia functiei init() a clasi copil
        valori = new HashMap<>();
        for (ColumnInfo ci : coloane) {
            if (testValue(ci.getJavaType(), ci.getSqlDataType(), ci.getInitialValue())) {
                valori.put(ci.getSqlColumnName(), ci.getInitialValue());
            } else {
                eroare("TableRowHelper.valoriInitiale  - eroare valoare initiala la coloana:" + ci.getSqlColumnName());
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Comenzi SQL">
    @Override
    public String getComandaSelect() {
        return "SELECT * FROM " + getSchemaName();
    }

    public String getSchemaName() {
        String tn = "";
        if (!schemaName.isEmpty()) {
            tn += schemaName + ".";
        }
        tn += tableName;
        return tn;
    }

    public String getComandaCreateTable(ServerHelper.Tip serverType) {
        String comanda;
        comanda = "CREATE TABLE " + getSchemaName() + "(";
        boolean ePrima = true;
        for (ColumnInfo ci : coloane) {
            if (ci.getSqlDataType() != ColumnInfo.SqlType.EXCLUDE) {
                if (ePrima) {
                    ePrima = false;
                } else {
                    comanda += ",";
                }
                comanda += ci.getSqlColumnName();
                switch (ci.getSqlDataType()) {
                    case AUTO_INCREMENT:
                        switch (serverType) {
                            case Postgres:
                                comanda += " SERIAL PRIMARY KEY NOT NULL";
                                break;
                            case MySQL:
                                comanda += " INT AUTO_INCREMENT PRIMARY KEY";
                                break;
                            case MSSQL:
                                comanda += " INT IDENTITY(1,1) NOT NULL";
                                break;
                        }
                        break;
                    case BIGINT:
                        comanda += " BIGINT";
                        break;
                    case BYTE:
                        comanda += " BYTE";
                        break;
                    case CHAR:
                        comanda += " CHAR" + "(" + ci.getSqDataLength() + ")";
                        break;
                    case MY_DATE_LONG:
                        comanda += " BIGINT";
                        break;
                    case DATE:
                        comanda += " DATE";
                        break;
                    case DATETIME:
                        comanda += " DATETIME";
                        break;
                    case DECIMAL:
                        comanda += " DECIMAL(" + ci.getSqDataLength() + "," + ci.getSqlDataDecimals() + ")";
                        break;
                    case DOUBLE:
                        comanda += " DOUBLE";
                        break;
                    case INT:
                        comanda += " INT";
                        break;
                    case REAL:
                        comanda += " REAL";
                        break;
                    case SMALLINT:
                        comanda += " SMALLINT";
                        break;
                    case TEXT:
                        comanda += " TEXT";
                        break;
                    case VARCHAR:
                        comanda += " VARCHAR(" + ci.getSqDataLength() + ")";
                        break;
                    default:
                    // orice alt tip sau null nu apare in tabela - modificParola la tabela users
                }
                //comanda += " NOT NULL";
            }
        }
        comanda += ");";
        //System.out.println(comanda);
        return comanda;
    }

    @Override
    public String getComandaSave() {
        String comanda;
        Date dataNow = new Date();
        if ((Integer) valori.get("id") == 0) {
            valori.put("user_add", userCurent.getId());
            valori.put("data_add", dataNow);
        } else {
            valori.put("user_mod", userCurent.getId());
            valori.put("data_mod", dataNow);
        }
        List<LinieInsertUpdate> lista = new ArrayList<>();
        for (ColumnInfo ci : coloane) {
            // pentru a nu pune coloana id in lista
            if (!(ci.getSqlColumnName().equals("id") || ci.getSqlDataType() == ColumnInfo.SqlType.EXCLUDE)) {
                lista.add(new LinieInsertUpdate(ci.getSqlColumnName(), getStringValue(ci.getSqlColumnName(), true)));
            }
        }
        if ((Integer) valori.get("id") == 0) {
            comanda = LinieInsertUpdate.getStringInsert(getSchemaName(), lista);
        } else {
            comanda = LinieInsertUpdate.getStringUpdate(getSchemaName(), (Integer) valori.get("id"), lista);
        }
        return comanda;
    }

    @Override
    public String getComandaDelete() {
        String comanda = "DELETE FROM " + getSchemaName();
        comanda += " WHERE ";
        comanda += "id=" + String.valueOf((Integer) valori.get("id"));
        return comanda;
    }

    @Override
    public List<ParametriUnique> getParametriUnique() {
        List<ParametriUnique> lista = new ArrayList<>();
        //ParametriUnique param;
        //
        //param = new ParametriUnique(tableName, "user=" + ServerTools.sqlString(user), id, "Utilizatorul '" + user + "' mai există");
        //lista.add(param);
        //
        return lista;
    }

    @Override
    public List<ParametriIR> getListaParametriIR() {
        // parametri integritate referentiala
        List<ParametriIR> lista = new ArrayList<>();
        //ParametriIR param;
        //
        //param = new ParametriIR("grouplist", "user_id=" + id, "Aveți acest utilizator aparține la mai multe grupuri. Mai întâi ștergeți grupurile de la utilizator.");
        //lista.add(param);
        //
        return lista;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Utile">
    protected final void eroare(String mesaj) {
        mesajEroare = mesaj;
        System.out.println(mesajEroare);
        if (eDesktop) {
            JOptionPane.showMessageDialog(null, mesajEroare);
        }
    }
//
//    public static Date StringToData(String strData) {
//        try {
//            if (strData != null) {
//                if (strData.equals("null") || strData.isEmpty()){
//                    //
//                } else {
//                    if (!strData.equals("  .  .    ")){
//                        return dfm.parse(strData);
//                    }
//                }
//            }
//        } catch (ParseException ex) {
//            System.out.println("Eroare parse Data:'" + strData + "' - ParseException:" + ex.getLocalizedMessage());
//        }
//        return null;
//    }
//
//    public static Integer StringToInt(String strData) {
//        try {
//            if (strData == null) {
//                return 0;
//            } else {
//                return Integer.parseInt(strData);
//            }
//        } catch (NumberFormatException ex) {
//            System.out.println("Eroare parse Int:" + ex.getLocalizedMessage());
//            return 0;
//        }
//    }
//
//    public static Double StringToDouble(String strData) {
//        try {
//            if (strData == null) {
//                return 0D;
//            } else {
//                return Double.parseDouble(strData);
//            }
//        } catch (NumberFormatException ex) {
//            System.out.println("Eroare parse Double:" + ex.getLocalizedMessage());
//            return 0D;
//        }
//    }
    // </editor-fold>
}
