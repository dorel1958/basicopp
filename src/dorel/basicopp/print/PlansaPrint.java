package dorel.basicopp.print;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

public class PlansaPrint extends JPanel implements Scrollable, Printable {

    protected final int maxUnitIncrement = 1;  // pasul la Scroll
    private static final long serialVersionUID = 7526472295622776148L;

    private final ContentInterface content;
    private boolean isPrint = false;
    private int pageIndex = 0;

    public int getLastPageIndex() {
        return content.getLastPageIndex();
    }

    //<editor-fold defaultstate="collapsed" desc="Navigatie in document">
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void printFirstPage() {
        pageIndex = 0;
        repaint();
    }

    public void printPreviousPage() {
        if (pageIndex > 0) {
            pageIndex -= 1;
            repaint();
        }
    }

    public void printNextPage() {
        if (pageIndex < content.getLastPageIndex()) {
            pageIndex += 1;
            repaint();
        }
    }

    public void printLastPage() {
        pageIndex = content.getLastPageIndex();
        repaint();
    }
    //</editor-fold>

    public PlansaPrint(ContentHelper content) {
        //setAutoscrolls(true); //enable synthetic drag events
        this.content = content;
    }

    //<editor-fold defaultstate="collapsed" desc="Printable">
    @Override
    public void paint(Graphics g) {
        // continutul tiparit - o pagina in fereastra, paginile 0 -> getLastPageIndex() la tiparire.
        if (isPrint) {
            // AICI deseneaza
            content.paintPage(g, pageIndex);
        } else {
            this.setBackground(Color.LIGHT_GRAY);  // pentru fond gri pe ecran
            //
            g.setColor(Color.white);  // deseneaza pagina alba pe ecran
            int wh = (int) content.getPageFormat().getPaper().getWidth();
            int hh = (int) content.getPageFormat().getPaper().getHeight();
            g.fillRect(0, 0, wh, hh);
            //
            g.setColor(Color.lightGray);  // deseneaza contur imageable pe ecran
            int xi = (int) content.getPageFormat().getPaper().getImageableX();
            int yi = (int) content.getPageFormat().getPaper().getImageableY();
            int wi = (int) content.getPageFormat().getPaper().getImageableWidth();
            int hi = (int) content.getPageFormat().getPaper().getImageableHeight();
            g.drawRect(xi, yi, wi, hi);
            // AICI deseneaza
            content.paintPage(g, pageIndex);
        }
    }

    @Override
    public Dimension getMinimumSize() {
        //if (isPrint) {
        return new Dimension((int) content.getPageFormat().getWidth(), (int) content.getPageFormat().getHeight());
        //} else {
        //    return new Dimension((int) content.pageFormat.getWidth()+shiftEcranX, (int) content.pageFormat.getHeight()+shiftEcranY);
        //}
    }

    @Override
    public Dimension getMaximumSize() {
        //if (isPrint) {
        return new Dimension((int) content.getPageFormat().getWidth(), (int) content.getPageFormat().getHeight());
        //} else {
        //    return new Dimension((int) content.pageFormat.getWidth()+shiftEcranX, (int) content.pageFormat.getHeight()+shiftEcranY);
        //}
    }

    @Override
    public Dimension getPreferredSize() {
        //if (isPrint) {
        return new Dimension((int) content.getPageFormat().getWidth(), (int) content.getPageFormat().getHeight());
        //} else {
        //    return new Dimension((int) content.pageFormat.getWidth()+shiftEcranX, (int) content.pageFormat.getHeight()+shiftEcranY);
        //}
    }

    @Override
    public int print(Graphics graphics, PageFormat pf, int pageIndex) throws PrinterException {
        this.pageIndex = pageIndex;
        graphics.translate((int) content.getPageFormat().getImageableX(), (int) content.getPageFormat().getImageableY());
        if (pageIndex > content.getLastPageIndex()) {
            return Printable.NO_SUCH_PAGE;
        }
        isPrint = true;
        paint(graphics);
        isPrint = false;
        return Printable.PAGE_EXISTS;
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Scrollable">
    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        //Get the current position.
        int currentPosition;
        if (orientation == SwingConstants.HORIZONTAL) {
            currentPosition = visibleRect.x;
        } else {
            currentPosition = visibleRect.y;
        }

        //Return the number of pixels between currentPosition and the nearest tick mark in the indicated direction.
        if (direction < 0) {
            int newPosition = currentPosition - (currentPosition / maxUnitIncrement) * maxUnitIncrement;
            return (newPosition == 0) ? maxUnitIncrement : newPosition;
        } else {
            return ((currentPosition / maxUnitIncrement) + 1) * maxUnitIncrement - currentPosition;
        }
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        if (orientation == SwingConstants.HORIZONTAL) {
            return visibleRect.width - maxUnitIncrement;
        } else {
            return visibleRect.height - maxUnitIncrement;
        }
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
//</editor-fold>
}
