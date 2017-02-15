package dorel.basicopp.swing;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class MyListDataListener implements ListDataListener {

    @Override
    public void contentsChanged(ListDataEvent e) {
        System.out.print("contentsChanged: " + e.getIndex0() + ", " + e.getIndex1());
    }

    @Override
    public void intervalAdded(ListDataEvent e) {
        System.out.print("intervalAdded: " + e.getIndex0() + ", " + e.getIndex1());
    }

    @Override
    public void intervalRemoved(ListDataEvent e) {
        System.out.print("intervalRemoved: " + e.getIndex0() + ", " + e.getIndex1());
    }

    public void info(ListDataEvent e) {
        Object source = e.getSource(); //Return the object that fired the event.
        int index_firstItemChanged = e.getIndex0(); //Return the index of the first item whose value has changed.
        int index_lastItemChanged = e.getIndex1(); //Return the index of the last item whose value has changed.
        int type = e.getType(); //Return the event type.
        //The possible values are: CONTENTS_CHANGED, INTERVAL_ADDED, or INTERVAL_REMOVED.
        //
        //public final Class<?> getClass()
        //Returns the runtime class of this Object. The returned Class object is the object that is locked by static synchronized methods of the represented class.
        //The actual result type is Class<? extends |X|> where |X| is the erasure of the static type of the expression on which getClass is called. For example, no cast is required in this code fragment:
        //Number n = 0;  Class<? extends Number> c = n.getClass(); 
        //Returns:
        //The Class object that represents the runtime class of this object.
    }
}
