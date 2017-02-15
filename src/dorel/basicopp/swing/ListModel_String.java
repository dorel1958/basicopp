package dorel.basicopp.swing;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

//http://docs.oracle.com/javase/7/docs/api/javax/swing/JList.html
public class ListModel_String<String> implements ListModel<String> {

    private final List<String> data;

    public ListModel_String() {
        data = new ArrayList<>();
    }

    public ListModel_String(List<String> data) {
        this.data = data;
    }

    public void addElement(String element) {
        data.add(element);
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public String getElementAt(int index) {
        return data.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
