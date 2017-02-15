package dorel.basicopp.server;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class TransactionHelper {

    private Statement transactionStatement;
    private ServerInterface server;
    private String mesajEroare = "";
    boolean eDesktop;
    private boolean inTranzactie = false;
    private ResultSet rs;
    private boolean eCorect = false;

    //<editor-fold defaultstate="collapsed" desc="GetSet">
    public ResultSet getResultSet() {
        if (eCorect) {
            return rs;
        } else {
            return null;
        }
    }

    public String getMesajEroare() {
        return mesajEroare;
    }
    //</editor-fold>

    public TransactionHelper(ServerInterface server) {
        this.server = server;
        this.eDesktop = server.getDesktop();
        if (server.createStatement()) {
            transactionStatement = server.getStatement();
        } else {
            transactionStatement = null;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Transaction">
    public boolean startTransaction() {
        if (transactionStatement != null) {
            if (!inTranzactie) {
                String comanda = "START TRANSACTION";
                if (server.execQueryStat(comanda, transactionStatement)) {
                    inTranzactie = true;
                    return true;
                } else {
                    eroare("TransactionHelper.startTransaction - Nu a putut fi pornita trasnzactia:" + server.getMesajEroare());
                }
            } else {
                eroare("TransactionHelper.startTransaction - sunteti deja in tranzactie;");
            }
        } else {
            eroare("TransactionHelper.startTransaction - Statement null;");
        }
        return false;
    }

    public boolean commitTransaction() {
        if (transactionStatement != null && inTranzactie) {
            String comanda = "COMMIT";
            if (server.execQueryStat(comanda, transactionStatement)) {
                inTranzactie = false;
                return true;
            } else {
                eroare(server.getMesajEroare());
            }
        } else {
            eroare("TransactionHelper.commitTransaction - Statement null / tranzactie ne pornita;");
        }
        return false;
    }

    public boolean rollbackTransaction() {
        if (transactionStatement != null && inTranzactie) {
            String comanda = "ROLLBACK";
            if (server.execQueryStat(comanda, transactionStatement)) {
                inTranzactie = false;
                return true;
            } else {
                eroare(server.getMesajEroare());
            }
        } else {
            eroare("TransactionHelper.rollbackTransaction - Statement null / tranzactie ne pornita;");
        }
        return false;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ExecQuery">
    public boolean execQuery(String comanda) {
        if (inTranzactie) {
            if (server.execQueryStat(comanda, transactionStatement)) {
                return true;
            } else {
                eroare(server.getMesajEroare());
                return false;
            }
        } else {
            eroare("TransactionHelper.execQuerry - Tranzactie ne pornita;");
            return false;
        }
    }

    public boolean execQueryRs(String comanda) {
        eCorect = false;
        if (inTranzactie) {
            if (server.execQueryRsStat(comanda, transactionStatement)) {
                eCorect = true;
                this.rs = server.getResultSet();
                return true;
            } else {
                eroare(server.getMesajEroare());
                return false;
            }
        } else {
            eroare("TransactionHelper.execQuerryRs - Tranzactie ne pornita;");
            return false;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Utile">
    protected void eroare(String mesaj) {
        mesajEroare = mesaj;
        System.out.println(mesajEroare);
        if (eDesktop) {
            JOptionPane.showMessageDialog(null, mesajEroare);
        }
    }
    //</editor-fold>
}
