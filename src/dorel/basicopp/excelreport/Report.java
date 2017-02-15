package dorel.basicopp.excelreport;

import dorel.basicopp.datatypes.Numere;
import dorel.basicopp.excelreport.FisExcel.TipFisExcel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Report {
    private final TipFisExcel tipFisExcel;
    //private final String numeFisDef; //- apare insa in Constructor!!!
    private final String numeFisExcel;
    //private String pathExcelExe = "\"C:\\Program Files (x86)\\Microsoft Office\\OFFICE11\\excel.exe\"";
    private String pathExcelExe =  "\"C:\\Program Files (x86)\\Microsoft Office\\Office15\\excel.exe\"";
    Map<String, String> mCommon;
    String[] columnNames;
    List<String[]> lDate;
    private Workbook wb;
    private Sheet sheet;
    private int currentRow;
    //

    public void setPathExcelExe(String path) {
        pathExcelExe = path;
    }

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public Report(String numeFisDef, String numeFisExcel, TipFisExcel tipFisExcel, Map<String, String> mCommon, String[] columnNames, List<String[]> lDate) {
        //this.numeFisDef = numeFisDef;
        this.numeFisExcel = numeFisExcel;
        this.tipFisExcel = tipFisExcel;
        this.mCommon = mCommon;
        this.columnNames = columnNames;
        this.lDate = lDate;
        currentRow = 0;
    }

    public Report(String numeFisExcel, TipFisExcel tipFisExcel) {
        //this.numeFisDef = "";
        this.numeFisExcel = numeFisExcel;
        this.tipFisExcel = tipFisExcel;
        this.mCommon = null;
        this.columnNames = null;
        this.lDate = null;
        currentRow = 0;
    }
    //</editor-fold>

    public void addRow(int skipRows, int fromX, String[] content) {
        int currentX = fromX;
        currentRow += skipRows;
        Row row = sheet.createRow(currentRow);
        DataFormat format = wb.createDataFormat();
        CellStyle integerStyle = wb.createCellStyle();
        integerStyle.setDataFormat(format.getFormat("0"));
        currentRow += 1;
        if (content == null) {
            return;
        }
        for (String cellContent : content) {
            if (cellContent != null) {
                if (cellContent.startsWith("=")) {
                    // celulele ce incep cu = sunt formule
                    row.createCell(currentX).setCellFormula(cellContent.substring(1));
                } else {
                    if (Numere.isInteger(cellContent)) {
                        Cell cell = row.createCell(currentX);
                        //cell.setCellStyle(integerStyle);
                        cell.setCellValue(Integer.parseInt(cellContent));
                    } else {
                        if (Numere.isLong(cellContent)) {
                            Cell cell = row.createCell(currentX);
                            cell.setCellStyle(integerStyle);
                            cell.setCellValue(Long.parseLong(cellContent));
                        } else {
                            if (Numere.isNumeric(cellContent)) {
                                Cell cell = row.createCell(currentX);
                                cell.setCellValue(Double.parseDouble(cellContent));
                            } else {
                                Cell cell = row.createCell(currentX);
                                cell.setCellValue(cellContent);
                            }
                        }
                    }
                }
            }
            currentX += 1;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Formatare celule">
    public void autoSizeColumns(List<Integer> lista) {
        for (int ncol : lista) {
            sheet.autoSizeColumn(ncol);
        }
    }

    public void bold(int firstRow, int lastRow, int firstColumn, int lastColumn) {
        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        CellStyle oldStyle;
        CellStyle style;
        Row row;
        Cell cell;
        for (int i = firstRow; i <= lastRow; i++) {
            row = sheet.getRow(i);
            if (row == null) {
                row = sheet.createRow(i);
            }
            for (int j = firstColumn; j <= lastColumn; j++) {
                cell = row.getCell(j);
                if (cell == null) {
                    cell = row.createCell(j);
                }
                oldStyle = cell.getCellStyle();
                style = wb.createCellStyle();
                style.cloneStyleFrom(oldStyle);
                style.setFont(font);
                cell.setCellStyle(style);
            }
        }
    }

    public void drawConturLine(short borderLine, int firstRow, int lastRow, int firstColumn, int lastColumn) {
        CellStyle oldStyle;
        CellStyle style;
        Row row;
        Cell cell;
        // line Top
        row = sheet.getRow(firstRow);
        if (row == null) {
            row = sheet.createRow(firstRow);
        }
        for (int i = firstColumn; i <= lastColumn; i++) {
            cell = row.getCell(i);
            if (cell == null) {
                cell = row.createCell(i);
            }
            oldStyle = cell.getCellStyle();
            style = wb.createCellStyle();
            style.cloneStyleFrom(oldStyle);
            style.setBorderTop(borderLine);
            cell.setCellStyle(style);
        }
        // line Bottom
        row = sheet.getRow(lastRow);
        if (row == null) {
            row = sheet.createRow(lastRow);
        }
        for (int i = firstColumn; i <= lastColumn; i++) {
            cell = row.getCell(i);
            if (cell == null) {
                cell = row.createCell(i);
            }
            oldStyle = cell.getCellStyle();
            style = wb.createCellStyle();
            style.cloneStyleFrom(oldStyle);
            style.setBorderBottom(borderLine);
            cell.setCellStyle(style);
        }
        // linie Left
        for (int i = firstRow; i <= lastRow; i++) {
            row = sheet.getRow(i);
            if (row == null) {
                row = sheet.createRow(i);
            }
            // linie Left
            cell = row.getCell(firstColumn);
            if (cell == null) {
                cell = row.createCell(firstColumn);
            }
            oldStyle = cell.getCellStyle();
            style = wb.createCellStyle();
            style.cloneStyleFrom(oldStyle);
            style.setBorderLeft(borderLine);
            cell.setCellStyle(style);
            // linie Right
            cell = row.getCell(lastColumn);
            if (cell == null) {
                cell = row.createCell(lastColumn);
            }
            oldStyle = cell.getCellStyle();
            style = wb.createCellStyle();
            style.cloneStyleFrom(oldStyle);
            style.setBorderRight(borderLine);
            cell.setCellStyle(style);
        }
    }

    public void mergeCells(int firstRow, int lastRow, int firstColumn, int lastColumn, boolean centrat) {
        // 0 based
        if (centrat) {
            Row row = sheet.getRow(firstRow);
            if (row == null) {
                row = sheet.createRow(firstRow);
            }
            Cell cell = row.getCell(firstColumn);
            if (cell == null) {
                cell = row.createCell(firstColumn);
            }
            CellStyle oldStyle = cell.getCellStyle();
            CellStyle style = wb.createCellStyle();
            style.cloneStyleFrom(oldStyle);
            style.setAlignment(CellStyle.ALIGN_CENTER);
            cell.setCellStyle(style);
        }
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstColumn, lastColumn));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Operatii fisier Excel">
    public void genExcel(String sheetName) {
        if (tipFisExcel == TipFisExcel.xls) {
            wb = new HSSFWorkbook();
        } else {
            wb = new XSSFWorkbook();
        }
        String safeName = WorkbookUtil.createSafeSheetName(sheetName);
        sheet = wb.createSheet(safeName);
    }

    public boolean writeToFile() {
        return writeToFile(numeFisExcel);
    }

    public boolean writeToFile(String newNumeFisExcel) {
        // salvare pe disc
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(newNumeFisExcel);
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error Report.writeToFile:" + ex.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public void viewFisExcel() {
        try {
            Process p = Runtime.getRuntime().exec(pathExcelExe + " " + numeFisExcel);
//            String mesaj = "";
//            int resp = p.waitFor();
//            if (resp == 0) {
//                mesaj+="corect\n";
//                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
//                String line = reader.readLine();
//                while (line != null) {
//                    line = reader.readLine();
//                    mesaj+=line+"\n";
//                }
//            } else {
//                 mesaj+="eroare\n";
//            }
            //JOptionPane.showMessageDialog(null, "Continua ... mesaj=" + mesaj);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error - Report.viewFisExcel: " + ex.getLocalizedMessage());
        }
    }

    public void print() {
        // creaza un fisier gol
        genExcel("Sheet");
        writeToFile();
        viewFisExcel();
    }
    //</editor-fold>

}
