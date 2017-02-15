package dorel.basicopp.print;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Paper;

public class ContentHelper implements ContentInterface {

    private PageFormat pageFormat;

    public ContentHelper() {
        setPageFormatA4();
    }

    private void setPageFormatA4() {
        //
        int paper_width = 595; //210mm  = 595; //(int) 8.27inch * 72dpi = 595 ug;
        int paper_height = 841; //297mm = 841;  //(int) 11.69 * 72;
        int paper_marginLeft = 60; // = 15;
        int paper_marginRight = 20; //5 = 14;
        int paper_marginTop = 40; //10 = 28;
        int paper_marginBottom = 20; //5 = 14;
        int pageOrientation = PageFormat.PORTRAIT;
        Paper paper = new Paper();
        paper.setSize(paper_width, paper_height);
        double xs = paper_marginLeft;
        double ys = paper_marginTop;
        double ws = paper_width - paper_marginLeft - paper_marginRight;
        double hs = paper_height - paper_marginTop - paper_marginBottom;
        paper.setImageableArea(xs, ys, ws, hs);
        pageFormat = new PageFormat();
        pageFormat.setPaper(paper);
        pageFormat.setOrientation(pageOrientation);
    }

    @Override
    public PageFormat getPageFormat() {
        return pageFormat;
    }

    @Override
    public void paintPage(Graphics g, int pageIndex) {
        int pX = 80;
        int pY = 40;
        int dX = 100;
        int dY = 80;
        switch (pageIndex) {
            case 0:
                g.setColor(Color.black);
                pX += 10;
                pY += 20;
                break;
            case 1:
                g.setColor(Color.red);
                pX += 20;
                pY += 40;
                break;
            case 2:
                g.setColor(Color.green);
                pX += 30;
                pY += 60;
                break;
            case 3:
                g.setColor(Color.blue);
                pX += 40;
                pY += 80;
                break;
            default:
                g.setColor(Color.cyan);
                break;
        }

        g.drawRect(pX, pY, dX, dY);
        g.drawString("Pagina index:" + String.valueOf(pageIndex), pX + dX / 2, pY + dY / 2);
    }

    @Override
    public int getLastPageIndex() {
        return 3;
    }

}
