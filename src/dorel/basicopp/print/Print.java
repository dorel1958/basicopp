package dorel.basicopp.print;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class Print {

    private String mesajEroare;
    private boolean cuPageDialog = true;
    private boolean cuPrintDialog = true;

// <editor-fold defaultstate="collapsed" desc="Get Set">
    public boolean isCuPrintDialog() {
        return cuPrintDialog;
    }

    public void setCuPrintDialog(boolean cuPrintDialog) {
        this.cuPrintDialog = cuPrintDialog;
    }

    public boolean isCuPageDialog() {
        return cuPageDialog;
    }

    public void setCuPageDialog(boolean cuPageDialog) {
        this.cuPageDialog = cuPageDialog;
    }

    public String getMesajEroare() {
        return mesajEroare;
    }
//</editor-fold>

    public boolean tipareste(Printable content, PageFormat pf) {
        boolean raspuns = false;
        PrinterJob job = PrinterJob.getPrinterJob();

        if (this.isCuPageDialog()) {
            // afisaza PageSetup dialog
            pf = job.pageDialog(pf);
        }

        job.setPrintable(content, pf);

        boolean doPrint = true;
        if (this.isCuPrintDialog()) {
            // afisaza Print dialog
            doPrint = job.printDialog();
        }
        if (doPrint) {
            try {
                job.print();
                raspuns = true;
            } catch (PrinterException ex) {
                mesajEroare = ex.getLocalizedMessage();
            }
        }
        return raspuns;
    }

    public PageFormat getPageFormatA4() {
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
        PageFormat pageFormat = new PageFormat();
        pageFormat.setPaper(paper);
        pageFormat.setOrientation(pageOrientation);
        return pageFormat;
    }
}
