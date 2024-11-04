package tn.esprit.GestionZina.marchefinancier.Service;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import tn.esprit.GestionZina.marchefinancier.Entites.Titre;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserExcelExporter {

    public void ajouterLigneDansExcel(Titre titre) {
       try {
            File file = new File("../Cotations.xlsx");
            FileInputStream fis=null;
            Workbook workbook;
            if (file.exists()) {
                fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
            } else {
                workbook = new XSSFWorkbook();
            }

            Sheet sheet = workbook.getSheet("Données de cotations");
            if (sheet == null) {
                sheet = workbook.createSheet("Données de cotations");
            }

            CellStyle dataCellStyle = createDataCellStyle(workbook);

            int ligneIndex = sheet.getLastRowNum() + 1;
            Row dataRow = sheet.createRow(ligneIndex);
           DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
           String currentDateTime = dateFormatter.format(new Date());
            createCell(dataRow, 0, currentDateTime, dataCellStyle);
            createCell(dataRow, 1, titre.getIdTitre(), dataCellStyle);
            createCell(dataRow, 2, titre.getOuverture(), dataCellStyle);
            createCell(dataRow, 3, titre.getHaut(), dataCellStyle);
            createCell(dataRow, 4, titre.getBas(), dataCellStyle);
            createCell(dataRow, 5, titre.getDernier(), dataCellStyle);

            fis.close();

            // Enregistrez le classeur Excel dans le même fichier
            FileOutputStream fileOut = new FileOutputStream("../Cotations.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static CellStyle createDataCellStyle(Workbook workbook) {
        return workbook.createCellStyle();
    }

    private static void createCell(Row row, int column, Object value, CellStyle style) {
        Cell cell = row.createCell(column);
        if (value instanceof Date) {
            cell.setCellValue((Date) value);
        } else if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    public  List<List<Double>> getMapsfromEXCEL(int id) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("../Cotations.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0); // Vous pouvez ajuster l'index de la feuille si nécessaire
        List<List<Double>> dataList = new ArrayList<>();
        // Commencez par la deuxième ligne (index 1) pour ignorer la première ligne (l'en-tête)
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            Cell titreIDCell = row.getCell(1);
            if (titreIDCell != null) {
                if (titreIDCell.getCellType() == CellType.NUMERIC) {
                    int titreID = (int) titreIDCell.getNumericCellValue();// Colonne pour le Titre ID (ajustez l'index)
                   if (titreID==id){
                       try {
                           if (row.getCell(0).getCellType() == CellType.STRING) {
                               String cellValue = row.getCell(0).getStringCellValue();

                               SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

                                   double date = dateFormatter.parse(cellValue).getTime();
                                   System.out.println("Valeur de la cellule en tant que date : " + date);



                    double haut = row.getCell(3).getNumericCellValue(); // Colonne pour le plus haut (ajustez l'index)
                    double bas = row.getCell(4).getNumericCellValue(); // Colonne pour le plus bas (ajustez l'index)
                    double ouverture = row.getCell(2).getNumericCellValue(); // Colonne pour l'ouverture (ajustez l'index)
                    double fermeture = row.getCell(5).getNumericCellValue(); // Colonne pour la fermeture (ajustez l'index)
                       List<Double> data=new ArrayList<>();

                    data.add(date) ;
                    data.add(ouverture);
                    data.add(haut);
                    data.add(bas);
                    data.add(fermeture);

                    dataList.add(data);
                           }
                       } catch (Exception e) {
                           e.printStackTrace();
                       }
                }
            } }
        }

        workbook.close();
        fileInputStream.close();
       return dataList;

    }
    public List<List<Double>> getDataForWeeks(int id) throws IOException {
        List<List<Double>> dataList = getMapsfromEXCEL(id);
        List<List<Double>> weeklyDataList = new ArrayList<>();

        for (int i = 0; i < dataList.size(); i += 7) {

                weeklyDataList.add( dataList.get(i));

        }
        System.out.println("weeklyDataList.size()");
        System.out.println(weeklyDataList.size());
        return weeklyDataList;
    }

    // Method to get data for each month
    public List<List<Double>> getDataForMonths(int id) throws IOException {
        List<List<Double>> dataList = getMapsfromEXCEL(id);
        List<List<Double>> MonthlyDataList = new ArrayList<>();

        int i = 0;

            while ( i <= dataList.size()) {
                MonthlyDataList.add( dataList.get(i));
                i += 30;

        }

        return MonthlyDataList;
    }

    // Method to get data for each year
    public List<List<Double>> getDataForYears(int id) throws IOException {
        List<List<Double>> dataList = getMapsfromEXCEL(id);
        List<List<Double>> YearlyDataList = new ArrayList<>();

        for (int i = 0; i < dataList.size(); i += 364) {


                YearlyDataList.add( dataList.get(i));

        }

        return YearlyDataList;
    }

    public List<List<List<Double>>> getAllDataLists(int id) throws IOException {
        List<List<List<Double>>> allDataLists = new ArrayList<>();

        // Get daily data
        List<List<Double>> dailyDataList = getMapsfromEXCEL(id);
        allDataLists.add(dailyDataList);

        // Get weekly data
        List<List<Double>> weeklyDataList = getDataForWeeks(id);
        allDataLists.add(weeklyDataList);

        // Get monthly data
        List<List<Double>> monthlyDataList = getDataForMonths(id);
        allDataLists.add(monthlyDataList);

        // Get yearly data
        List<List<Double>> yearlyDataList = getDataForYears(id);
        allDataLists.add(yearlyDataList);

        return allDataLists;
    }


    public List<Double> getHistoricalPricesForAsset( int assetId) throws IOException {
        List<Double> historicalPrices = new ArrayList<>();

        FileInputStream fileInputStream = new FileInputStream("../Cotations.xlsx");
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheet("Données de cotations"); // Assuming the sheet name is "Données de cotations"

        for (Row row : sheet) {
            Cell assetIdCell = row.getCell(1); // Column 1 contains the asset ID
            Cell priceCell = row.getCell(5);   // Column 5 contains the historical price

            if (assetIdCell != null && assetIdCell.getCellType() == CellType.NUMERIC ) {
                long assetIdFromSheet = (long) assetIdCell.getNumericCellValue();
                if (assetIdFromSheet == assetId && priceCell != null && priceCell.getCellType() == CellType.NUMERIC) {
                    double historicalPrice = priceCell.getNumericCellValue();
                    historicalPrices.add(historicalPrice);
                }
            }
        }

        workbook.close();
        fileInputStream.close();

        return historicalPrices;
    }
    public double GetRendementMoyeParTitre(int idtitre) throws IOException {
        double ouv=0;
        double ferm=0;
        FileInputStream fileInputStream = new FileInputStream("../Cotations.xlsx");
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheet("Données de cotations"); // Assuming the sheet name is "Données de cotations"
        for (Row row : sheet) {
            Cell assetIdCell = row.getCell(1); // Column 1 contains the asset ID
            Cell ouverture = row.getCell(2);   // Column 3 contains ouverture
            Cell fermeture = row.getCell(5);   // Column 6 contains fermeture
            if (assetIdCell != null && assetIdCell.getCellType() == CellType.NUMERIC ) {
                long assetIdFromSheet = (long) assetIdCell.getNumericCellValue();
                if (assetIdFromSheet == idtitre && ouverture != null && ouverture.getCellType() == CellType.NUMERIC && fermeture != null && fermeture.getCellType() == CellType.NUMERIC) {
                    ouv=ouverture.getNumericCellValue()+ouv;
                    ferm=fermeture.getNumericCellValue()+ferm;
                }
            }
        }

        return (ferm-ouv)/ouv;
    }
}
