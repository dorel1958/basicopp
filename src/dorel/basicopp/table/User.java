package dorel.basicopp.table;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JOptionPane;

public class User implements Serializable {

    private static final long serialVersionUID = 7526472295622776146L;
    protected int id;
    protected String user;
    protected String name;
    protected String drepturi;
    //

    // <editor-fold defaultstate="collapsed" desc="Get Set">
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDrepturi() {
        return drepturi;
    }

    public void setDrepturi(String drepturi) {
        this.drepturi = drepturi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    // </editor-fold>

    public User() {
        id = 0;
        user = "";
        name = "";
        drepturi = "";
    }

    public User(int id, String user, String name, String drepturi) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.drepturi = drepturi;
    }

    public static String getMD5(String pass) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] dataBytes = pass.getBytes();
            //
            md.update(dataBytes, 0, dataBytes.length);
            //
            byte[] mdbytes = md.digest();
            //convert the byte to hex format
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < mdbytes.length; i++) {
                String hex = Integer.toHexString(0xff & mdbytes[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            JOptionPane.showMessageDialog(null, "MD5 exception:" + ex.getLocalizedMessage());
        }
        return "";
    }
}
