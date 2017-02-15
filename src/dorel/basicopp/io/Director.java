package dorel.basicopp.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Director {

    int Files = 0, Directory = 0, HiddenFiles = 0, HiddenDirectory = 0;
    String directoryName;

    public Director(String directoryName) {
        this.directoryName = directoryName;
    }

    public void listf(List<File> listaFiles) {
        listf(directoryName, listaFiles);
    }

    public void listf(String directoryName, List<File> listaFiles) {
        // functie recurenta
        File file = new File(directoryName);
        File[] fileList = file.listFiles();
        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isHidden()) {
                    if (fileList[i].isFile()) {
                        listaFiles.add(fileList[i]);
                        //System.out.println(fileList[i]);
                        HiddenFiles++;
                    } else {
                        listf(String.valueOf(fileList[i]), listaFiles);
                        HiddenDirectory++;
                    }
                } else if (fileList[i].isFile()) {
                    listaFiles.add(fileList[i]);
                    //System.out.println(fileList[i]);
                    Files++;
                } else if (fileList[i].isDirectory()) {
                    Directory++;
                    listf(String.valueOf(fileList[i]), listaFiles);
                }
            }
        }
    }

    public void showNumbers() {
        System.out.println("Files: " + Files + " HiddenFiles: " + HiddenFiles + "Hidden Directories" + HiddenDirectory + " Directories: " + Directory);
    }

    public List<File> getFiles() {
        return getFiles(directoryName);
    }

    public List<File> getFiles(String directoryName) {
        List<File> listaFiles = new ArrayList<>();
        File file = new File(directoryName);
        File[] fileList = file.listFiles();
        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isHidden()) {
                    if (fileList[i].isFile()) {
                        listaFiles.add(fileList[i]);
                        HiddenFiles++;
                    } else {
                        listf(String.valueOf(fileList[i]), listaFiles);
                        HiddenDirectory++;
                    }
                } else if (fileList[i].isFile()) {
                    listaFiles.add(fileList[i]);
                    Files++;
                } else if (fileList[i].isDirectory()) {
                    Directory++;
                    listf(String.valueOf(fileList[i]),listaFiles);
                }
            }
        }
        return listaFiles;
    }
}
