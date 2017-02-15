package dorel.basicopp.table;

import dorel.basicopp.server.ServerInterface;
import dorel.basicopp.ConfigLocalHelper;
import dorel.basicopp.ConfigLocalInterface;
import dorel.basicopp.server.ServerHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public abstract class DataSourceHelper {

    private final boolean eDesktop;
    protected ConfigLocalInterface setLocal;
    protected ServerInterface server;
    protected String mesajEroare;

    public String getMesajEroare() {
        return mesajEroare;
    }

    public ConfigLocalInterface getSetLocal() {
        return setLocal;
    }

    public final ServerInterface.Tip getServerType() {
        return server.getServerType();
    }

    public DataSourceHelper(boolean eDesktop, String serverType) {
        // OBSERVATIE - serverType este numai sugeastia pentru prima initializare, REAL el citeste din setari
        mesajEroare = "";
        this.eDesktop = eDesktop;
        setLocal = new ConfigLocalHelper(serverType);
        setLocal.readSettings();
        //
        server = new ServerHelper(setLocal);
        server.setDesktop(eDesktop);
        if (server.openConnection()) {
            server.closeConnection();
            // e bine, continua
        } else {
            //eroare("Aplicatia nu se poate conecta la Server.");
            // Nu pune eroare() pentru a nu da prea multe mesaje la initializarea aplicatiei
            // DataSource - va incerca crearea BD daca are mesaj de eroare la deschidreea BD
            mesajEroare = "Nu se poate conecta la server.";
        }
    }

    public abstract boolean creazaBazaDate();

    // <editor-fold defaultstate="collapsed" desc="save, delete, list">
    public boolean saveInreg(TableRowInterface tablerow) {
        boolean raspuns = false;
        if (tablerow == null) {
            eroare("Înregistrarea de salvat este nulă.");
        } else {
            String mesaj = tablerow.testValues();
            if (mesaj.length() > 0) {
                eroare(mesaj);
                return false;
            }
            if (server.openConnection()) {
                if (testUnic(tablerow.getParametriUnique())) {
                    int initId = tablerow.getId();
                    String comanda = tablerow.getComandaSave();
                    //System.out.println("comanda save in dataSource:" + comanda);
                    if (getServerType() == ServerHelper.Tip.Postgres) {
                        if (initId == 0) {
                            // insert postgress
                            comanda += " RETURNING id;";
                            if (server.execQueryRs(comanda)) {
                                ResultSet rs = server.getResultSet();
                                try {
                                    if (rs.next()) {
                                        int lid = rs.getInt("id");
                                        if (lid > 0) {
                                            tablerow.setId(lid);
                                            raspuns = true;
                                        } else {
                                            eroare("Last insert id Postgress este 0.");
                                        }
                                    }
                                } catch (SQLException ex) {
                                    eroare("SQLException - Last insert id Postgres:" + ex.getLocalizedMessage());
                                }
                            }
                        } else {
                            //Update Postgress
                            if (server.execQuery(comanda)) {
                                raspuns = true;
                            } else {
                                eroare("Eroare SQL:" + server.getMesajEroare());
                            }
                        }
                    } else {
                        if (server.execQuery(comanda)) {
                            if (initId == 0) {
                                // Insert
                                int lid = server.getLastInsertId();
                                if (lid > 0) {
                                    tablerow.setId(lid);
                                    raspuns = true;
                                } else {
                                    eroare("Last insert id este 0.");
                                }
                            } else {
                                // Update
                                raspuns = true;
                            }
                        } else {
                            eroare("Eroare SQL:" + server.getMesajEroare());
                        }
                    }
                }
                server.closeConnection();
            } else {
                eroare("Eroare SQL:" + server.getMesajEroare());
            }
        }
        return raspuns;
    }

    private boolean testUnic(List<ParametriUnique> lPu) {
        if (lPu == null) {
            return true;
        }
        if (lPu.isEmpty()) {
            return true;
        }
        String mesaj = "";
        boolean eroare = false;
        //if (server.openConnection(setLocal.getMySQLhost(), String.valueOf(setLocal.getMySQLport()), setLocal.getMySQLdatabase(), setLocal.getMySQLuserName(), setLocal.getMySQLuserPass())) {
        String comanda;
        for (ParametriUnique pu : lPu) {
            int nrInreg = -1;
            comanda = "SELECT id FROM " + pu.getTableName();
            comanda += " WHERE ";
            comanda += pu.getConditia();
            if (server.execQueryRs(comanda)) {
                ResultSet rs = server.getResultSet();
                nrInreg++;  // ajunge la 0
                try {
                    while (rs.next()) {
                        if (rs.getInt("id") == pu.getId()) {
                            // inreg mea are acest nume
                        } else {
                            // alta inreg cu acest nume
                            nrInreg++;
                        }
                    }
                    rs.close();
                    if (nrInreg > 0) {
                        mesaj += pu.getTableName() + " " + pu.getConditia() + " " + pu.getMesajEroare() + "\n\r";
                        eroare = true;
                    }
                } catch (SQLException ex) {
                    eroare("testUnic - SQLException: " + ex.getLocalizedMessage());
                }
            } else {
                mesaj = " - Eroare executie comanda:" + comanda;
                eroare = true;
            }
        }
        //server.closeConnection();
        //} else {
        //    eroare(server.getMesajEroare());
        //}
        if (eroare) {
            eroare("DataSource.testUnic\n\r" + " nu e îndeplinită condiția de unicitate:" + mesaj);
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteInreg(TableRowInterface tabela) {
        boolean raspuns = false;
        if (tabela == null) {
            return false;
        }
        if (server.openConnection()) {
            if (testIntegritateReferentiala(tabela.getListaParametriIR())) {
                String comanda = tabela.getComandaDelete();
                raspuns = server.execQuery(comanda);
            }
            server.closeConnection();
        }
        return raspuns;
    }

    public List<TableRowInterface> listInreg(TableRowInterface row, String orderBy, String filter) {
        List<TableRowInterface> lista = new ArrayList<>();
        if (server.openConnection()) {
            String comanda = row.getComandaSelect();
            if (!filter.isEmpty()) {
                comanda += " WHERE ";
                comanda += filter;
            }
            if (!orderBy.isEmpty()) {
                comanda += " ORDER BY ";
                comanda += orderBy;
            }
            if (getServerType() == ServerHelper.Tip.MySQL) {
                comanda += " LIMIT 1000";
            }
            //System.out.println(comanda);
            if (server.execQueryRs(comanda)) {
                ResultSet rs = server.getResultSet();
                try {
                    TableRowInterface elementLista;
                    while (rs.next()) {
                        elementLista = row.getNewRow(rs);
                        lista.add(elementLista);
                    }
                    rs.close();
                } catch (SQLException ex) {
                    eroare("SQLException: " + ex.getLocalizedMessage());
                }
            } else {
                eroare("Eroare MySQL: " + server.getMesajEroare());
            }
            server.closeConnection();
        } else {
            eroare(server.getMesajEroare());
        }
        return lista;
    }

    private boolean testIntegritateReferentiala(List<ParametriIR> lParametriIR) {
        // comanda se da cu serverul conectat
        boolean response = true;
        String mesaj = "";
        if (lParametriIR == null) {
            return response;
        }
        if (lParametriIR.isEmpty()) {
            return response;
        }
        String comanda;
        for (ParametriIR parametriIR : lParametriIR) {
            if (parametriIR.getTabela().isEmpty() || parametriIR.getConditia().isEmpty()) {
                // nu are conditie
            } else {
                comanda = "SELECT count(*) AS nr_inr FROM " + parametriIR.getTabela();
                comanda += " WHERE ";
                comanda += parametriIR.getConditia();
                if (server.execQueryRs(comanda)) {
                    ResultSet rs = server.getResultSet();
                    try {
                        if (rs.next()) {
                            int nrInr = rs.getInt("nr_inr");
                            if (nrInr > 0) {
                                mesaj += parametriIR.getMesajEroare() + "\n\r";
                                response = false;
                                break;
                            }
                        }
                    } catch (SQLException ex) {
                        eroare("SQLException: " + ex.getLocalizedMessage());
                    }
                }
            }
        }
        if (!response) {
            eroare("DataSource.testIntegritateReferentiala\n\r" + mesaj + "Aceasta inregistrare nu poate fi stearsa.");
        }
        return response;
    }
    //</editor-fold>

    protected final void eroare(String mesaj) {
        mesajEroare = mesaj;
        System.out.println(mesajEroare);
        if (eDesktop) {
            JOptionPane.showMessageDialog(null, mesajEroare);
        }
    }
}
//   DatabaseMetaData dbm = conn.getMetaData();
//   rs = dbm.getTables(null, null, "%", new String[] { "TABLE" });
//   while (rs.next()) { System.out.println(rs.getString("TABLE_NAME")); }
