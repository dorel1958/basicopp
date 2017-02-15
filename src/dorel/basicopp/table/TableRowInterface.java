package dorel.basicopp.table;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface TableRowInterface {

    public void setId(int id);

    public int getId();

    public String getLabel();

    public String getComandaSave();

    public String getColumnLabel(String colName);

    public List<ColumnInfo> getListaColoane();

    public String getComandaSelect();

    public String getComandaDelete();

    public List<ParametriUnique> getParametriUnique();

    public List<ParametriIR> getListaParametriIR();

    public String testValues();

    public TableRowInterface getNewRow(ResultSet rs);
    
    public TableRowInterface getNewRow(Map<String, String> newValori, User userCurent);

    public Object getValue(String numeColoana);
    
    public String getStringValue(String numeColoana);

    public void setStringValue(String numeColoana, String newValue);

    public void setValues(ResultSet rs);
}
