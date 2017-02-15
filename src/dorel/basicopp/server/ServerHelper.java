package dorel.basicopp.server;

import dorel.basicopp.ConfigLocalInterface;
import dorel.basicopp.datatypes.Numere;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ServerHelper implements Serializable, ServerInterface {

    private static final long serialVersionUID = 7526472295622776147L;
    protected boolean eDesktop = false;
    protected boolean eCorect;
    protected Connection conn;
    protected Statement s;  // statementul ultimei comenzi executate - se modifica la executarea unei noi comenzi
    protected ResultSet rs;
    protected String mesajEroare = "";
    protected String connectionString;
    protected String className;
    private Tip tip;
    private boolean cuConnectionString;

    private String host;
    private int port;
    private String database;
    private String user;
    private String pass;
    private boolean eUnicode;

    //<editor-fold defaultstate="collapsed" desc="GetSet">
    @Override
    public void setParametriConexiune(String host, int port, String database, String user, String pass, boolean eUnicode) {
        if (!host.isEmpty()) {
            this.host = host;
        }
        if (port > 0) {
            this.port = port;
        }
        if (!database.isEmpty()) {
            this.database = database;
        }
        if (!user.isEmpty()) {
            this.user = user;
        }
        if (!pass.isEmpty()) {
            this.pass = pass;
        }
        this.eUnicode = eUnicode;
        cuConnectionString = false;
    }

    @Override
    public void setParametriConexiune(ConfigLocalInterface configLocal) {
        String thost = configLocal.getValue("Host");
        String tdatabase = configLocal.getValue("Database");
        String tuser = configLocal.getValue("User");
        String tpass = configLocal.getValue("Pass");
        String sp = configLocal.getValue("destinatiePort");
        int iport = 0;
        if (Numere.isInteger(sp)) {
            iport = Integer.parseInt(sp);
        }
        setParametriConexiune(thost, iport, tdatabase, tuser, tpass, true);
    }

    public void setUnicode(boolean str) {
        this.eUnicode = str;
    }

    public Tip getTip() {
        return tip;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }

    @Override
    public void setDesktop(boolean eDesktop) {
        this.eDesktop = eDesktop;
    }

    @Override
    public boolean getDesktop() {
        return eDesktop;
    }

    @Override
    public String getMesajEroare() {
        return mesajEroare;
    }

    @Override
    public Statement getStatement() {
        return s;
    }

    @Override
    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
        cuConnectionString = true;
    }

    @Override
    public ResultSet getResultSet() {
        mesajEroare = "";
        if (eCorect) {
            return rs;
        } else {
            return null;
        }
    }
    //</editor-fold>

    public ServerHelper() {
        this.tip = Tip.Unknown;
        // versiunea initiala
    }

    public ServerHelper(ConfigLocalInterface setLocal) {
        host = "localhost";
        switch (setLocal.getValue("Server")) {
            case "MySQL":
                tip = Tip.MySQL;
                eUnicode = true;
                className = "com.mysql.jdbc.Driver";
                port = 3306;
//                database = "test";
                user = "root";
                pass = "";
                break;
            case "MSSQL":
                tip = Tip.MSSQL;
                className = "net.sourceforge.jtds.jdbc.Driver";
                port = 1433;
//                database = "test";
//                user = "sa";
//                pass = "MisiPisi";
                break;
            case "Postgres":
                tip = Tip.Postgres;
                className = "org.postgresql.Driver";
                port = 5432;
//                database = "postgres";
//                user = "postgres";
//                pass = "MisiPisi";
                break;
        }
        if (!setLocal.getValue("Host").isEmpty()) {
            this.host = setLocal.getValue("Host");
        }
        if (Numere.isInteger(setLocal.getValue("Port"))) {
            this.port = Numere.stringToInt(setLocal.getValue("Port"));
        }
        this.database = setLocal.getValue("Database");
        if (!setLocal.getValue("User").isEmpty()) {
            this.user = setLocal.getValue("User");
        }
        this.pass = setLocal.getValue("Pass");
        cuConnectionString = false;
    }

    public ServerHelper(String sTip) {
        this(getTipFromString(sTip));
    }

    public ServerHelper(Tip tip) {
        this.tip = tip;
        host = "localhost";
        switch (tip) {
            case MySQL:
                eUnicode = true;
                className = "com.mysql.jdbc.Driver";
                port = 3306;
                database = "test";
                user = "root";
                pass = "";
                eUnicode = true;
                break;
            case MSSQL:
                //  jdbc:jtds:<server_type>://<server>[:<port>][/<database>][;<property>=<value>[;...]]
                className = "net.sourceforge.jtds.jdbc.Driver";
                port = 1433;
                database = "test";
                user = "sa";
                pass = "MisiPisi";
                break;
            case Postgres:
                className = "org.postgresql.Driver";
                port = 5432;
                database = "postgres";
                user = "postgres";
                pass = "MisiPisi";
                break;
            default:
        }
        cuConnectionString = false;
    }

    private static Tip getTipFromString(String sTip) {
        switch (sTip) {
            case "MySQL":
                return Tip.MySQL;
            case "MSSQL":
                return Tip.MSSQL;
            case "Postgres":
                return Tip.Postgres;
            default:
                JOptionPane.showMessageDialog(null, "ServerHelper - string tip server necunoscut:'" + sTip + "' cunosc NUMAI: 'MySQL', 'MSSQL', 'Postgres'");
                return Tip.MySQL;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Connection">
    @Override
    public boolean openConnection() {
        // pentru cazul in care connection string contine user si parola: MySQL, Postgres
        mesajEroare = "";
        eCorect = false;
        if (!cuConnectionString) {
            switch (tip) {
                case MySQL:
                    if (eUnicode) {
                        connectionString = "jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + user + "&password=" + pass + "&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull"; // &zeroDateTimeBehavior=convertToNull fara efect
                    } else {
                        connectionString = "jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + user + "&password=" + pass + "&zeroDateTimeBehavior=convertToNull";
                    }
                    break;
                case MSSQL:
                    connectionString = "jdbc:jtds:sqlserver://" + host + ":" + port + "/" + database + ";tds=8.0;lastupdatecount=true"; //?user=" + user + "&password=" + pass;
                    //
                    //connectionString = "jdbc:jtds:sqlserver://" + host + "/" + database + ";"; //?user=" + user + "&password=" + pass;
                    break;
                case Postgres:
                    connectionString = "jdbc:postgresql://" + host + ":" + port + "/" + database + "?user=" + user + "&password=" + pass;
                    break;
                default:
            }
        }
        try {
            // Specify to the DriverManager which JDBC drivers to try to make Connections with.
            // The newInstance() call is a work around for some broken Java implementations.
            Class.forName(className).newInstance();
            if (tip == Tip.MSSQL) {
                conn = DriverManager.getConnection(connectionString, user, pass);
            } else {
                conn = DriverManager.getConnection(connectionString);
            }
            //JOptionPane.showMessageDialog(null, "S-a conectat");
            eCorect = true;
        } catch (SQLException ex) {
            eroare("ServerHelper.openConnection - SQLException: " + ex.getLocalizedMessage() + " - connectionString=" + connectionString);
        } catch (InstantiationException ex) {
            eroare("ServerHelper.openConnection - InstantiationException - " + ex.getLocalizedMessage());
        } catch (IllegalAccessException ex) {
            eroare("ServerHelper.openConnection - IllegalAccessException - " + ex.getLocalizedMessage());
        } catch (ClassNotFoundException ex) {
            eroare("ServerHelper.openConnection - ClassNotFoundException - " + ex.getLocalizedMessage());
        } catch (Exception ex) {
            eroare("ServerHelper.openConnection - Exception - " + ex.getLocalizedMessage());
        }
        return eCorect;
    }

//    @Override
//    public boolean openConnection(String user, String pass) {
//        // pentru cazul in care connection string NU contine user si parola: MSSQL
//        mesajEroare = "";
//        eCorect = false;
//        if (tip == Tip.Unknown) {
//            try {
//                // Specify to the DriverManager which JDBC drivers to try to make Connections with.
//                // The newInstance() call is a work around for some broken Java implementations.
//                Class.forName(className).newInstance();
//                conn = (Connection) DriverManager.getConnection(connectionString, user, pass);
//
//                JOptionPane.showMessageDialog(null, "S-a conectat");
//                eCorect = true;
//            } catch (SQLException ex) {
//                eroare("ServerHelper.openConnection - SQLException - " + ex.getLocalizedMessage());
//            } catch (InstantiationException ex) {
//                eroare("ServerHelper.openConnection - InstantiationException - " + ex.getLocalizedMessage());
//            } catch (IllegalAccessException ex) {
//                eroare("ServerHelper.openConnection - IllegalAccessException - " + ex.getLocalizedMessage());
//            } catch (ClassNotFoundException ex) {
//                eroare("ServerHelper.openConnection - ClassNotFoundException - " + ex.getLocalizedMessage());
//            }
//        } else {
//            eroare("Comanda openConnection cu parametri se foloseste numai la tipul Unknown");
//        }
//        return eCorect;
//    }
    @Override
    public void closeConnection() {
        mesajEroare = "";
        try {
            if (s != null) {
                s.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            eroare("ServerHelper.openConnection - SQLException - nu a putut inchide conexiunea: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public boolean isConnected() {
        mesajEroare = "";
        try {
            if (conn == null) {
                return false;
            } else {
                if (tip == Tip.MSSQL) {
                    // eroare la executie
                    return true;
                } else {
                    return conn.isValid(0);
                }
            }
        } catch (SQLException ex) {
            eroare("ServerHelper.openConnection - SQLException: " + ex.getLocalizedMessage());
            return false;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ExecQuery">
    @Override
    public boolean createStatement() {
        if (eCorect && conn != null) {
            try {
                s = conn.createStatement();
                return true;
            } catch (SQLException ex) {
                eroare("SQLException createStatement - statement ne creat' - " + ex.getLocalizedMessage());
            }
        } else {
            eroare("Eroare createStatement - Server neconectat - statement ne creat");
        }
        return false;
    }

    @Override
    public boolean execQuery(String comanda) {
        return execQueryStat(comanda, null);
    }

    @Override
    public boolean execQueryStat(String comanda, Statement stat) {
        mesajEroare = "";
        if (eCorect && conn != null) {
            try {
                if (stat == null) {
                    s = conn.createStatement();
                    s.executeUpdate(comanda);
                    s.close();
                } else {
                    stat.executeUpdate(comanda);
                }
                // raspuns -  nr randuri afectate sau 0 la randuri neafectate
                return true;
            } catch (SQLException ex) {
                eroare("ServerHelper.execQueryStat - SQLException: comanda neexecutata '" + comanda + "' - " + ex.getLocalizedMessage());
            }
        } else {
            eroare("Eroare ServerHelper.execQueryStat - Server neconectat - comanda neexecutata '" + comanda + "'");
        }
        return false;
    }

    @Override
    public boolean execQueryRs(String comanda) {
        return execQueryRsStat(comanda, null);
    }

    @Override
    public boolean execQueryRsStat(String comanda, Statement stat) {
        mesajEroare = "";
        rs = null;
        if (eCorect && conn != null) {
            try {
                if (stat == null) {
                    s = conn.createStatement();
                    s.executeQuery(comanda);
                    rs = s.getResultSet();
                } else {
                    stat.executeQuery(comanda);
                    rs = stat.getResultSet();
                }
                return true;
            } catch (SQLException ex) {
                eroare("ServerHelper.execQueryRsStat - SQLException: comanda neexecutata '" + comanda + "' - " + ex.getLocalizedMessage());
            }
        } else {
            eroare("Eroare ServerHelper.execQueryRsStat - Server neconectat - comanda neexecutata '" + comanda + "'");
        }
        return false;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Utile">
    @Override
    public int getLastInsertId() {
        if (isConnected()) {
            mesajEroare = "";
            if (tip == Tip.MySQL) {
                int lid = 0;
                String comanda = "SELECT LAST_INSERT_ID() AS lid";
                if (execQueryRs(comanda)) {
                    int count = 0;
                    int idVal = 0;
                    try {
                        while (rs.next()) {
                            idVal = rs.getInt("lid");
                            ++count;
                        }
                        switch (count) {
                            case 0:
                                eroare("ServerHelper.getLastInsertId LAST_INSERT_ID() = 0");
                                break;
                            case 1:
                                lid = idVal;
                                break;
                            default:
                                eroare("ServerHelper.getLastInsertId LAST_INSERT_ID() ERROR count=" + count);
                                break;
                        }
                        rs.close();
                    } catch (SQLException ex) {
                        eroare("ServerHelper.getLastInsertId - SQLException: " + ex.getLocalizedMessage());
                    }
                } else {
                    eroare(getMesajEroare());
                }
                return lid;
            }
            if (tip == Tip.MSSQL) {
                int lid = 0;
                String comanda = "SELECT SCOPE_IDENTITY() AS lid;";
                if (execQueryRs(comanda)) {
                    int count = 0;
                    int idVal = 0;
                    try {
                        while (rs.next()) {
                            idVal = rs.getInt("lid");
                            ++count;
                        }
                        switch (count) {
                            case 0:
                                eroare("ServerHelper.getLastInsertId SCOPE_IDENTITY() = 0");
                                break;
                            case 1:
                                lid = idVal;
                                break;
                            default:
                                eroare("ServerHelper.getLastInsertId SCOPE_IDENTITY() ERROR count=" + count);
                                break;
                        }
                        rs.close();
                    } catch (SQLException ex) {
                        eroare("ServerHelper.getLastInsertId - SQLException: " + ex.getLocalizedMessage());
                    }
                } else {
                    eroare(getMesajEroare());
                }
                return lid;
            }
            try {
                rs = s.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException ex) {
                eroare("ServerHelper.getLastInsertId - SQLException: " + ex.getLocalizedMessage());
            }
        } else {
            eroare("Pentru a afla ultimul insert id TREBUIE sa nu inchideti conexiunea.");
        }
        return 0;
    }

    @Override
    public boolean isResultSetEmpty() {
        // NU e bine
        // merge si:  if(rs.next ()){ ... }
        mesajEroare = "";
        try {
            rs.beforeFirst();
            if (rs.next()) {
                rs.beforeFirst();
                return true;
            }
        } catch (SQLException ex) {
            eroare("ServerHelper.isResultSetEmpty - SQLException: " + ex.getLocalizedMessage());
        }
        return false;
    }

    protected void eroare(String mesaj) {
        mesajEroare = mesaj;
        System.out.println(mesajEroare);
        if (eDesktop) {
            JOptionPane.showMessageDialog(null, mesajEroare);
        }
    }
    //</editor-fold>

    @Override
    public Tip getServerType() {
        return this.tip;
    }

    @Override
    public void setDatabase(String database) {
        this.database = database;
    }

    @Override
    public void changeBD(String database) {
        if (eCorect && conn != null) {
            String comanda;
            comanda = "USE `" + database + "`;";
            execQuery(comanda);
            closeConnection();
        } else {
            eroare(getMesajEroare());
        }
    }

    @Override
    public boolean tableExists(String tableName) {
        boolean raspuns = false;
        if (eCorect && conn != null) {
            String comanda;
            comanda = "SELECT * FROM `" + tableName + "` LIMIT 1;";
            if (execQueryRs(comanda)) {
                raspuns = true;
            }
            closeConnection();
        } else {
            eroare(getMesajEroare());
        }
        return raspuns;
    }

}

// //EXEMPLU UTILIZARE
//
//   MySQLServer s = new MySQLServer();
//   ResultSet rs = s.getResultSet ();
//   int count = 0;
//   while (rs.next ())
//   {
//       int idVal = rs.getInt ("id");
//       String nameVal = rs.getString ("name");
//       String catVal = rs.getString ("category");
//       System.out.println (
//               "id = " + idVal
//               + ", name = " + nameVal
//               + ", category = " + catVal);
//       ++count;
//   }
//   rs.close ();
//
//
//
// Placeholders
//   PreparedStatement s;
//   s = conn.prepareStatement (
//               "INSERT INTO animal (name, category) VALUES(?,?)");
//   s.setString (1, nameVal);
//   s.setString (2, catVal);
//   int count = s.executeUpdate ();
//   s.close ();
//   System.out.println (count + " rows were inserted");
//
//The '?' characters in the query string act as placeholders--special markers indicating where data values should be placed. The setString() method takes a placeholder position and a string value and binds the value to the appropriate placeholder, performing any special-character escaping that may be necessary. The method you use to bind a value depends on the data type. For example, setString() binds string values and setInt() binds integer values.
//    public boolean prepareStatement(String comanda){
//        PreparedStatement ps;
//        try {
//            ps = (com.mysql.jdbc.PreparedStatement)conn.prepareStatement (comanda);
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "SQLException - " + ex.getLocalizedMessage());
//        }
//        return false;
//    }
