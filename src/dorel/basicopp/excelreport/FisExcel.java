package dorel.basicopp.excelreport;

import dorel.basicopp.datatypes.Numere;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FisExcel {
    
    private Workbook wb;
    //
    private Sheet currentSheet;
    private Row currentRow;
    private Cell currentCell;
    //
    int ncurrentRow;
    //
    private final TipFisExcel tipFisExcel;
    private final String numeFisExcel;
    private String pathExcelExe;
    //
    CellStyle integerStyle;
    CellStyle decimalStyle;
    CellStyle stringStyle;
    CellStyle titleStringStyle;

    //
    public enum TipFisExcel {
        
        xls,
        xlsx
    }
    
    public void setPathExcelExe(String pathExcelExe) {
        this.pathExcelExe = pathExcelExe;
    }
    
    public FisExcel(String numeFisExcel, TipFisExcel tipFisExcel) {
        pathExcelExe = "\"C:\\Program Files (x86)\\Microsoft Office\\Office15\\excel.exe\"";
        this.numeFisExcel = numeFisExcel;
        this.tipFisExcel = tipFisExcel;
    }

    //<editor-fold defaultstate="collapsed" desc="Operatii fisier Excel">
    public void genExcel(String sheetName) {
        if (tipFisExcel == TipFisExcel.xls) {
            wb = new HSSFWorkbook();
        } else {
            wb = new XSSFWorkbook();
        }
        String safeName = WorkbookUtil.createSafeSheetName(sheetName);
        currentSheet = wb.createSheet(safeName);
    }
    
    public void openFile() {
        try {
            InputStream is = new FileInputStream(numeFisExcel);
            if (tipFisExcel == TipFisExcel.xls) {
                wb = new HSSFWorkbook(is);
            } else {
                wb = new XSSFWorkbook(is);
            }
            is.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
        } catch (IOException ioex) {
            JOptionPane.showMessageDialog(null, ioex.getLocalizedMessage());
        }
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

    //<editor-fold defaultstate="collapsed" desc="pozitionare in fisier Excel">
    public int getLastRowNum() {
        return currentSheet.getLastRowNum();
    }
    
    public int getLastCellNum() {
        return currentRow.getLastCellNum();
    }

    // set current
    public boolean setCurrentSheet(String sheetName, boolean createNew) {
        if (wb != null) {
            currentSheet = wb.getSheet(sheetName);
            if (currentSheet == null) {
                if (createNew) {
                    currentSheet = wb.createSheet(sheetName);
                }
            }
            if (currentSheet != null) {
                int i = wb.getSheetIndex(sheetName);
                wb.setActiveSheet(i);
                return true;
            }
        }
        return false;
    }
    
    public boolean setCurrentRow(int nRow, boolean createNew) {
        if (wb != null) {
            if (currentSheet != null) {
                if (nRow <= getLastRowNum()) {
                    currentRow = currentSheet.getRow(nRow);
                    if (currentRow == null) {
                        if (createNew) {
                            currentRow = currentSheet.createRow(nRow);
                        }
                    }
                    if (currentRow != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean setCurrentCell(int nCell, boolean createNew) {
        if (wb != null) {
            if (currentSheet != null) {
                if (currentRow != null) {
                    if (nCell <= getLastCellNum()) {
                        currentCell = currentRow.getCell(nCell);
                    }
                    if (currentCell == null) {
                        if (createNew) {
                            currentCell = currentRow.createCell(nCell);
                        }
                    }
                    if (currentCell != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // cell
    private Cell getCell(String sheetName, int nRow, int nCell) {
        Sheet sheet = wb.getSheet(sheetName);
        if (sheet != null) {
            if (nRow <= sheet.getLastRowNum()) {
                Row row = sheet.getRow(nRow);
                if (row != null) {
                    if (nCell <= row.getLastCellNum()) {
                        return row.getCell(nCell);
                    }
                }
            }
            
        }
        return null;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="extrage date din fisier Excel">
    // double
    public double getDoubleCellValue(String sheetName, int nRow, int nCell) {
        Cell cell = getCell(sheetName, nRow, nCell);
        return getDoubleCellValue(cell);
    }
    
    public double getDoubleCellValue(int nCel) {
        int maxCel = currentRow.getLastCellNum();
        if (nCel <= maxCel) {
            Cell cell = currentRow.getCell(nCel);
            return getDoubleCellValue(cell);
        }
        return 0;
    }
    
    private double getDoubleCellValue(Cell cell) {
        if (cell != null) {
            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                return cell.getNumericCellValue();
            }
        }
        return 0;
    }

    // date
    private Date getDateCellValue(Cell cell) {
        if (cell != null) {
            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                return cell.getDateCellValue();
            }
        }
        return null;
    }
    
    public Date getDateCellValue(String sheetName, int nRow, int nCell) {
        Cell cell = getCell(sheetName, nRow, nCell);
        return getDateCellValue(cell);
    }
    
    public Date getDateCellValue() {
        return getDateCellValue(currentCell);
    }
    
    public Date getDateCellValue(int nCel) {
        int maxCel = currentRow.getLastCellNum();
        if (nCel <= maxCel) {
            Cell cell = currentRow.getCell(nCel);
            if (cell != null) {
                return getDateCellValue(cell);
            }
        }
        return null;
    }

    // String
    private String getStringCellValue(Cell cell) {
        String raspuns;
        if (cell == null) {
            raspuns = "";
        } else {
            int cellTip = cell.getCellType();
            switch (cellTip) {
                case Cell.CELL_TYPE_NUMERIC:
                    double dd = cell.getNumericCellValue();
                    // test if a date!
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        // format 
                        Date date = HSSFDateUtil.getJavaDate(dd);
                        DateFormat dfm = new SimpleDateFormat("dd.MM.yyyy");
                        dfm.setTimeZone(TimeZone.getTimeZone("Europe/Bucharest")); // EET, Eastern European Time
                        raspuns = dfm.format(date);
                    } else {
                        long ld = Math.round(dd);
                        if (dd == ld) {
                            raspuns = String.valueOf(ld);
                        } else {
                            raspuns = String.valueOf(dd);
                        }
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    raspuns = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    raspuns = cell.getCellFormula();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    raspuns = "";
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    boolean result = cell.getBooleanCellValue();
                    if (result) {
                        raspuns = "true";
                    } else {
                        raspuns = "false";
                    }
                    break;
                case Cell.CELL_TYPE_ERROR:
                    raspuns = String.valueOf(cell.getErrorCellValue());
                    break;
                default:
                    raspuns = "cellTip necunoscut (0 - 5) cellTip=" + cellTip;
                    break;
            }
        }
        return raspuns;
    }
    
    public String getStringCellValue() {
        return getStringCellValue(currentCell);
    }
    
    public String getStringCellValue(String sheetName, int nRow, int nCell) {
        Cell cell = getCell(sheetName, nRow, nCell);
        return getStringCellValue(cell);
    }
    
    public String getStringCellValue(int nCel) {
        int maxCel = currentRow.getLastCellNum();
        if (nCel <= maxCel) {
            Cell cell = currentRow.getCell(nCel);
            return getStringCellValue(cell);
        }
        return "nCel mai mare dacat maxCell";
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="introduce date in fisier Excel">
    public void startNew() {
        if (wb == null) {
            JOptionPane.showMessageDialog(null, "FisExcel.startNew - Nu ati generat WorkBook-ul");
        } else {
            ncurrentRow = 0;
            // stiluri
            DataFormat format = wb.createDataFormat();
            //
            integerStyle = wb.createCellStyle();
            integerStyle.setDataFormat(format.getFormat("0"));
            //font.setFontHeight((short)200);
            //fontInt.setFontHeightInPoints((short) 12);
            //font.setItalic(true);
            //font.setFontName("Times New Roman");
            //integerStyle.setFont(fontInt);

            //Font fontStr = wb.getFontAt((short) 0);
            stringStyle = wb.createCellStyle();
            //fontStr.setItalic(true);
            //stringStyle.setFont(fontStr);

            decimalStyle = wb.createCellStyle();
            decimalStyle.setDataFormat(format.getFormat("0.00"));
            
            titleStringStyle = wb.createCellStyle();
            //titleStringStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
            //titleStringStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            //titleStringStyle.setFillPattern(HSSFCellStyle.FINE_DOTS);
            //titleStringStyle.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
            //titleStringStyle.setFillPattern((short)1);// black - solid
            //titleStringStyle.setFillPattern((short)2);// black - puncte dese
            //titleStringStyle.setFillPattern((short)3);// black - puncte si mai mari si dese
            //titleStringStyle.setFillPattern((short)4);// black - puncte mici
            //titleStringStyle.setFillPattern((short)5);// black - linii orizontale
            //titleStringStyle.setFillPattern((short)6);// black - linii verticale
            //titleStringStyle.setFillPattern((short)7);// black - linii oblice \\\\\
            //titleStringStyle.setFillPattern((short)8);// black - linii oblice \\\\\
            //titleStringStyle.setFillPattern((short)9);// black - puncte mai mari
            //titleStringStyle.setFillPattern((short)10);// black - puncte si mai mari
            //titleStringStyle.setFillPattern((short)11);// black - linii orizontale
            //titleStringStyle.setFillPattern((short)999);//  Invalid FillPatternType code: 999
            //titleStringStyle.setFillPattern(EscherProperties.FILL__FILLBACKCOLOR);  //Invalid FillPatternType code: 387
            titleStringStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            titleStringStyle.setFillPattern((short) 1);  // solid
            //
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 11);
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            font.setColor(IndexedColors.BLACK.getIndex());
            font.setFontName("Arial");  // "Times New Roman", "Courier", "Courier New"
            titleStringStyle.setFont(font);
        }
    }
    
    public void addRow(int skipRows, int fromX, String[] content, boolean isTitle) {
        int currentX = fromX;
        ncurrentRow += skipRows;
        if (currentSheet == null) {
            JOptionPane.showMessageDialog(null, "FisExcel.addRow - currentSheet==null");
            return;
        }
        Row row = currentSheet.createRow(ncurrentRow);
        
        ncurrentRow += 1;
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
                        cell.setCellStyle(integerStyle);
                        cell.setCellValue(Integer.parseInt(cellContent));
                    } else {
                        if (Numere.isLong(cellContent)) {
                            Cell cell = row.createCell(currentX);
                            cell.setCellStyle(integerStyle);
                            cell.setCellValue(Long.parseLong(cellContent));
                        } else {
                            if (Numere.isNumeric(cellContent)) {
                                Cell cell = row.createCell(currentX);
                                cell.setCellStyle(decimalStyle);
                                cell.setCellValue(Double.parseDouble(cellContent));
                            } else {
                                Cell cell = row.createCell(currentX);
                                if (isTitle) {
                                    cell.setCellStyle(titleStringStyle);
                                } else {
                                    cell.setCellStyle(stringStyle);
                                }
                                cell.setCellValue(cellContent);
                            }
                        }
                    }
                }
            }
            currentX += 1;
        }
    }
    
    public void addRow(int skipRows, int fromX, List<String> content, boolean isTitle) {
        int currentX = fromX;
        ncurrentRow += skipRows;
        if (currentSheet == null) {
            JOptionPane.showMessageDialog(null, "FisExcel.addRow - currentSheet==null");
            return;
        }
        Row row = currentSheet.createRow(ncurrentRow);
        
        ncurrentRow += 1;
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
                        cell.setCellStyle(integerStyle);
                        cell.setCellValue(Integer.parseInt(cellContent));
                    } else {
                        if (Numere.isLong(cellContent)) {
                            Cell cell = row.createCell(currentX);
                            cell.setCellStyle(integerStyle);
                            cell.setCellValue(Long.parseLong(cellContent));
                        } else {
                            if (Numere.isNumeric(cellContent)) {
                                Cell cell = row.createCell(currentX);
                                cell.setCellStyle(decimalStyle);
                                cell.setCellValue(Double.parseDouble(cellContent));
                            } else {
                                Cell cell = row.createCell(currentX);
                                if (isTitle) {
                                    cell.setCellStyle(titleStringStyle);
                                } else {
                                    cell.setCellStyle(stringStyle);
                                }
                                cell.setCellValue(cellContent);
                            }
                        }
                    }
                }
            }
            currentX += 1;
        }
    }
    
    public void autoSizeColumns(int deLa, int panaLa) {
        for (int i = deLa; i < panaLa; i++) {
            currentSheet.autoSizeColumn(i);
        }
    }
    //</editor-fold>

}
