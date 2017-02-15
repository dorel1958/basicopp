package dorel.basicopp.print;

import java.awt.Graphics;
import java.awt.print.PageFormat;

public interface ContentInterface {

    public PageFormat getPageFormat();

    public void paintPage(Graphics g, int pageIndex);
    
    public int getLastPageIndex();
}
