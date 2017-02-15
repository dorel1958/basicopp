package dorel.basicopp.sumecontrol;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CalculMD5 {

    public static String getFileMD5(String numeFis) {
        String mesajEroare;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(numeFis);
            byte[] dataBytes = new byte[1024];
            int nread;
            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }
            byte[] mdbytes = md.digest();

            //convert the byte to hex format method 1
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //System.out.println("Digest(in hex format):: " + sb.toString());
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            mesajEroare = "NoSuchAlgorithmException:" + ex.getLocalizedMessage();
            return mesajEroare;
        } catch (FileNotFoundException ex) {
            mesajEroare = "FileNotFoundException:" + ex.getLocalizedMessage();
            return mesajEroare;
        } catch (IOException ex) {
            mesajEroare = "IOException:" + ex.getLocalizedMessage();
            return mesajEroare;
        }
    }
    
    public static String getStringMD5(String text) {
        String mesajEroare;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] zzz=text.getBytes();
            md.update(text.getBytes());
            byte[] mdbytes = md.digest();

            //convert the byte to hex format method 1
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //System.out.println("Digest(in hex format):: " + sb.toString());
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            mesajEroare = "NoSuchAlgorithmException:" + ex.getLocalizedMessage();
            return mesajEroare;
        }
    }
    
    public static String getCharsMD5(char[] text) {
        // utila la parole
        //String mesajEroare;
        String str=new String(text);
        return getStringMD5(str);
        
        // convert char[] to byte[]
//        byte[] bytes = new byte[text.length<<1];
//        for (int i=0; i<text.length; i++){
//            int bpos = i << 1;
//            bytes[bpos] = (byte) ((text[i]&0xFF00)>>8);
//            bytes[bpos + 1] = (byte) (text[i]&0x00FF);
//        }
        //
//        byte[] bytes;
//        
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(bytes);
//            byte[] mdbytes = md.digest();
//
//            //convert the byte to hex format method 1
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < mdbytes.length; i++) {
//                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
//            }
//            //System.out.println("Digest(in hex format):: " + sb.toString());
//            return sb.toString();
//        } catch (NoSuchAlgorithmException ex) {
//            mesajEroare = "NoSuchAlgorithmException:" + ex.getLocalizedMessage();
//            return mesajEroare;
//        }
    }
}
