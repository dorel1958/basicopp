package dorel.basicopp.io;

import java.io.File;

public class FileFilterXML extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File file) {
        //Always accept directories so that the user can navigate around the file system
        if (file.isDirectory()) {
            return true;
        }
        return file.getName().toLowerCase().endsWith(".xml");
//        String extension = getExtension(file);
//        if (extension != null) {
//            if (extension.equals("xml")) {
//                return true;
//            }
//        }
//        return false;
    }

    @Override
    public String getDescription() {
        return "XML Documents (*.xml)";
    }

//    public static String getExtension(File file) {
//        String ext = null;
//        String s = file.getName();
//        int i = s.lastIndexOf('.');
//        if (i > 0 && i < s.length() - 1) {
//            ext = s.substring(i + 1).toLowerCase();
//        }
//        return ext;
//    }

}
