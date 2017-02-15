package dorel.basicopp.server;

import dorel.basicopp.ConfigLocalInterface;
import java.sql.ResultSet;
import java.sql.Statement;

public interface ServerInterface {

    public enum Tip {

        Unknown,
        MySQL,
        MSSQL,
        Postgres
    }

    public void setDesktop(boolean eDesktop);

    public boolean getDesktop();

    public String getMesajEroare();

    public Tip getServerType();

    public void changeBD(String database);

    public void setParametriConexiune(String host, int port, String database, String user, String pass, boolean eUTF8);

    public void setParametriConexiune(ConfigLocalInterface configLocal);

    public void setConnectionString(String connectionString);

    public void setDatabase(String database);

    public boolean openConnection();

    //public boolean openConnection(String user, String pass);
    public void closeConnection();

    public boolean isConnected();

    public boolean execQuery(String comanda);

    public boolean execQueryRs(String comanda);

    public boolean execQueryStat(String comanda, Statement statement);

    public boolean execQueryRsStat(String comanda, Statement statement);

    public ResultSet getResultSet();

    public int getLastInsertId();

    public boolean isResultSetEmpty();

    public boolean createStatement();

    public Statement getStatement();

    boolean tableExists(String tableName);
}
