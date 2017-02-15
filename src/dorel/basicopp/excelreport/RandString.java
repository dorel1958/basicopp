package dorel.basicopp.excelreport;

import java.util.ArrayList;
import java.util.List;

public class RandString {

    List<String> lista;
    int indexCurent;

    public RandString() {
        lista = new ArrayList<>();
        indexCurent = -1;
    }

    public void addColoana(String content) {
        lista.add(content);
    }

    public List<String> getRandul() {
        return lista;
    }

    public int getNrColoane(){
        return lista.size();
    }
}
