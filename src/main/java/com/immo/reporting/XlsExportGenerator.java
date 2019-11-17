/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package com.immo.reporting;


import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.constant.JasperProperty;
import net.sf.dynamicreports.report.base.datatype.AbstractDataType;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.constant.VerticalAlignment;


import net.sf.dynamicreports.jasper.builder.export.Exporters;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsExporterBuilder;


/**
 *
 * @author karim.coulibaly
 */
public class XlsExportGenerator  {     
    
    public static final int INTEGER_TYPE = 1;
    public static final int DECIMAL_TYPE = 2;
    public static final int DATE_TYPE = 3;
    public static final int STRING_TYPE = 4;
    public static final int FLOAT_TYPE = 5;
    public static final int BOOLEAN_TYPE = 6;
    public static final int LONG_TYPE = 7;
    public static final int CHARACTER_TYPE = 8;
    public static final int DOUBLE_TYPE = 9;
    public static final int BIG_DECIMAL_TYPE = 10;
    
    
    //SubTotal Type
    public static final int SUM_SUB_TOTAL = 1;
    public static final int COUNT_SUB_TOTAL = 2;
    public static final int AVG_SUB_TOTAL = 3;
    
    //SubTotal Section
    public static final int SUMMARY_SUB_TOTAL = 1;
    public static final int GROUP_FOOTER_SUB_TOTAL = 2;
    
    
    
    
    private static StyleBuilder lblStyle;
    private static StyleBuilder colStyle;
    private static StyleBuilder groupStyle;
    private static StyleBuilder  manyGroupStyle;
    private static JasperReportBuilder report ;
    private static Map<String, TextColumnBuilder> drColumns ;
    private static List<String> groups ;
    private static List<String> subtotals ;
    
    private static StyleBuilder boldStyle;

    
    // Genere un rapport dynamique
    public static JasperReportBuilder generateReport(ExportData metaData){
                                                
        
        
        report = report(); // Creer le rapport
        
        //report.setColumnTitleStyle(Templates.columnTitleStyle);
        report.addProperty(JasperProperty.EXPORT_XLS_FREEZE_ROW, "2");
        report.ignorePageWidth();
        report.ignorePagination();
        report.setLocale(Locale.FRENCH);
        
        
        
        //Definit le style des colonnes d'entête
        //report.setColumnTitleStyle(getHeaderStyle(metaData));
 
        
        /*LinkedHashMap <String,String> criteria = metaData.getCriteria();    
        //Ajout la date d'édition au critere d'impression
        String valDateProperty = getDateTxt(metaData.getLang()) ;
        String keyDateProperty = metaData.getLang().equals("fr")? "Edité le " : "Print the";
        //criteria.put(keyDateProperty,valDateProperty);*/
        
       /* StyleBuilder  titleStyle = stl.style(boldCenteredStyle).setFontSize(16).setForegroundColor(new Color(0, 0, 0)).setHorizontalAlignment(HorizontalAlignment.RIGHT).setRightIndent(20);
        ComponentBuilder<?, ?> logoComponent = cmp.horizontalList(
            cmp.verticalList(
                cmp.text(metaData.getTitle()).setStyle(titleStyle)
            )
        ).newRow()
         .add(cmp.horizontalList().add(cmp.hListCell(createCriteriaComponent(criteria,metaData)).heightFixedOnTop()))
         .newRow();     
        
        report.noData(logoComponent, cmp.text("Aucun enregistrement trouvé").setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER).setTopPadding(30)));
       */   
        
         
        lblStyle = stl.style(Templates.columnStyle).setForegroundColor(new Color(60, 91, 31));  // Couleur du text      
        colStyle = stl.style(Templates.columnStyle).setHorizontalAlignment(HorizontalAlignment.CENTER);
        
        
        /*groupStyle = stl.style().setForegroundColor(new Color(60, 91, 31))               
                                                       .setBold(Boolean.TRUE)
                                                       .setPadding(5)
                                                       .setFontSize(13)
                                                       .setHorizontalAlignment(HorizontalAlignment.CENTER); */

        
         /*if(metaData.getTitle().equals(ITitle.PARCEL)){
            report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.PLANTING)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.BLOCK)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }*/
        
       
        
        drColumns = new HashMap<String, TextColumnBuilder>();        
        groups = new ArrayList<String>();
        subtotals = new ArrayList<String>();
     
       
        
        //Definit la liste des colonnes du rapport
        XlsExportGenerator.getColumns(metaData);        
        //Definit la liste des sous-totaux du rapport
        //XlsExportGenerator.getSubTotals(metaData);
   
        
        //Genration des sous totaux
        /*for (String group : groups) {
            ColumnGroupBuilder group2 = grp.group(drColumns.get(group));
            report.groupBy(group2);
            for (String subtotal : subtotals) {
               report.subtotalsAtGroupFooter(group2,sbt.sum(drColumns.get(subtotal)));
            }
         }

         for (String subtotal : subtotals) {
                report.subtotalsAtSummary(sbt.sum(drColumns.get(subtotal)));  
         }*/
         
         
        //Genere la source de données du rapport
        report.setDataSource(metaData.getData());
        
        
        //report.highlightDetailEvenRows(); 
        
        
        report.setPageMargin(margin(0).setBottom(0));
        report.addProperty("net.sf.jasperreports.export.xls.white.page.background", "false");
        //<property name="net.sf.jasperreports.export.xls.detect.cell.type" value="true"/>
        // pour detecter le type des cellules en enlevant les triangles verts dans les cellules
        report.addProperty("net.sf.jasperreports.export.xls.detect.cell.type", "true");  
        //report.setIgnorePageWidth(Boolean.TRUE);
        
        

        
        
        
       // Definition de l'entete : valable uniquement  pour la 1ere page
       /*  HorizontalListBuilder hlb= null;
         
                hlb = cmp.horizontalList().add(
                        cmp.hListCell(createCriteriaComponent(criteria,metaData)).heightFixedOnTop()
                        );*/

        
        /*report.title(
            createCustomTitleComponent(metaData.getTitle()), // Titre
            hlb,// Liste Horizontal des Critères 
            cmp.verticalGap(12) // Marge de 12 px entre chaque critère
        ); */
        
        
        
        /*report.setPageMargin(DynamicReports.margin().setLeft(15).setTop(30).setRight(15).setBottom(30));
        report.pageFooter(DynamicReports.cmp.pageXslashY());*/
        return report;
       
    }
   
    /*
    /**
     * Generate subtotals
     * @param metaData 
     */
    /*private static void getSubTotals(ExportData metaData){ 
        LinkedHashMap<String, Properties> subTotals = metaData.getSubTotals();
      
        if(subTotals != null){
            
            Set<String> keys = subTotals.keySet();
           
            for( String key  : keys){               
                Properties subtotal = subTotals.get(key);               
                String nameField= subtotal.getProperty("nameField");
                String type = subtotal.getProperty("type");
                String lib = subtotal.getProperty("libelle");
                String zone =subtotal.getProperty("zone");  
                
                subtotals.add(nameField);
                
            }
        }
    }*/
    
    /*
    /**
     * Get a column by name field Entity
     * @param nameField
     * @return 
     */
    /*private static TextColumnBuilder getColumnByNameField(String nameField){        
      return drColumns.get(nameField);
    }
    
    /**
     * createSubtotal
     * @param column
     * @param label
     * @param type
     * @return 
     */
    /*private static AggregationSubtotalBuilder createSubtotal(TextColumnBuilder column,String label,int type) {
        AggregationSubtotalBuilder res = null ;
        switch(type){
            case SUM_SUB_TOTAL :
                 res = (AggregationSubtotalBuilder) sbt.sum(column).setLabel(label).setLabelFixedWidth(475).setStyle(stl.style().setBottomBorder(stl.pen1Point().setLineColor(Color.BLACK)));
            case COUNT_SUB_TOTAL : 
                res = (AggregationSubtotalBuilder) sbt.sum(column).setLabel(label).setLabelStyle(groupStyle);
            case AVG_SUB_TOTAL :
                res = (AggregationSubtotalBuilder) sbt.sum(column).setLabel(label).setLabelStyle(groupStyle);
        }
        return res;
   }*/


   /*
   /**
    * Construit le panel des metadonnées(Date et critères de recherche)
    * @param criteria
    * @return 
    */
   /* private static ComponentBuilder<?, ?> createCriteriaComponent(LinkedHashMap <String,String> criteria,ExportData metaData ) {
         
        HorizontalListBuilder list = cmp.horizontalList().setBaseStyle(DynamicReports.stl.style()
                                          //.setTopBorder(DynamicReports.stl.pen1Point().setLineColor(Color.BLACK))
                                          //.setBottomBorder(DynamicReports.stl.pen1Point().setLineColor(Color.BLACK))
                                          .setTopPadding(5)
                                          .setBottomPadding(5)
                                       );
        
        
         
         Set criteriaKeys=criteria.keySet();
         Iterator iterateur=criteriaKeys.iterator();        
         while(iterateur.hasNext()){
            String key= (String)iterateur.next();
            addCriteriaAttribute(list, key, criteria.get(key),metaData);
         }
         return cmp.verticalList(cmp.text(" ").setStyle(Templates.boldStyle),list);
    }*/
    
    /*
   /**
    * imprime une ligne dans le panel des metadonnées
    * @param list
    * @param label
    * @param value 
    */
    /*private static void addCriteriaAttribute(HorizontalListBuilder list, String label, String value,ExportData metaData) {
        if (value != null) {              
             int totalCell = list.getList().getListCells().size();
             //System.out.println("totalCell:"+totalCell);
             if( (totalCell % 2) == 0){
                 list.add(cmp.text(label + ":").setFixedColumns(10).setStyle(lblStyle), cmp.text(value)).newRow();
             }else{
                 list.add(cmp.text(label + ":").setFixedColumns(10).setStyle(lblStyle).setStyle(stl.style().setLeftPadding(5)), cmp.text(value));
             }
             
        }
    }*/
/*
    /**
     * COnstruit l'entete du rapport
     * @param label
     * @return 
     */
    /*public static ComponentBuilder<?, ?> createCustomTitleComponent(String label) {

        StyleBuilder  bold12CenteredStyle = stl.style(boldCenteredStyle).setFontSize(12);
        StyleBuilder  bold16CenteredStyle = stl.style(boldCenteredStyle).setFontSize(16).setForegroundColor(new Color(0, 0, 0));
        StyleBuilder italicStyle = stl.style(rootStyle).italic();
        ComponentBuilder<?, ?> logoComponent = cmp.verticalList(
            //cmp.image(Templates.class.getResource("/logopalm.png")).setFixedDimension(150, 60),
            cmp.text(label).setStyle(bold16CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER)                  
        );
        return logoComponent;
    }

    /**
     * Retourne le style des colonnes d'entete
     * @return 
     */

    /*
    private static StyleBuilder getHeaderStyle(ExportData metaData){
                return stl.style().setBorder(stl.pen1Point().setLineColor(Color.BLACK))
                .setPadding(5)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setForegroundColor(Color.BLACK)
                //.setForegroundColor(Color.WHITE)
                //.setBackgroundColor(new Color(60, 91, 31))
                .bold();  
    }*/
    
    /**
     * Genere les colums du rappors et lance la recuperation des données
     * @param metaData 
     */
    private static void getColumns(ExportData metaData){
        
        //Recuperation des données
        Map<String, Properties> cols =   metaData.getHeaderCols();
        Set<String> keys =  cols.keySet();
        //List ds = metaData.getData();
        //boolean isAlreadyGrouped = false;
        for (String key : keys) {           
            Properties colData  = cols.get(key);
            String val = colData.getProperty("name");
            int typeId = new Integer(colData.getProperty("type"));
            TextColumnBuilder column = null;
            
            /*Formatage de la sdate des colonnes*/
            if(typeId == DATE_TYPE){
                if(metaData.getLang().equals("fr")){
                     column = (TextColumnBuilder) col.column(val,key,getTypeColumn(typeId)).addProperty("net.sf.jasperreports.export.xls.auto.fit.column", "true");
                     column.setPattern("dd/MM/yyyy");
                }else{
                     column = (TextColumnBuilder) col.column(val,key,getTypeColumn(typeId)).addProperty("net.sf.jasperreports.export.xls.auto.fit.column", "true"); 
                     column.addProperty("net.sf.jasperreports.export.xls.detect.cell.type", "true");
                     column.setPattern("#.##");
                }
            }else{
                     column = (TextColumnBuilder) col.column(val,key,getTypeColumn(typeId)).addProperty("net.sf.jasperreports.export.xls.auto.fit.column", "true");
                     column.addProperty("net.sf.jasperreports.export.xls.detect.cell.type", "true");
                      //avant
                     // column.setPattern("");
                    // fait le 16/10/2018 pour arrondir a deux chiffre dans les exports excel dans budget
                     column.setPattern("#.##");
                     
            }

                
            //column.setStyle(stl.style().setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setHorizontalAlignment(HorizontalAlignment.CENTER));
                
            report.addColumn(column); 
            drColumns.put(key,column); // sauvegarde des colums du rapport en cours
            
            /*if(colData.getProperty("isGroupedColumn")!= null){                
                column.setStyle(stl.style().setBorder(stl.pen(0f, LineStyle.SOLID)));
                if(!isAlreadyGrouped){
                    column.setStyle(groupStyle);
                    isAlreadyGrouped= true;
                }else{
                    column.setStyle(stl.style().setForegroundColor(Color.BLACK)            
                                                       .setBold(Boolean.TRUE)
                                                       .setLeftPadding(10)
                                                       .setFontSize(11)
                                                       .setHorizontalAlignment(HorizontalAlignment.LEFT));
                }
                
                groups.add(key);
                //report.groupBy(column);
            }*/
        }
     
    }

    private static AbstractDataType getTypeColumn(int labelType){
        
        switch(labelType){
            case INTEGER_TYPE :  
                return DynamicReports.type.integerType();
            case DECIMAL_TYPE :  
                return DynamicReports.type.bigDecimalType();
            case DATE_TYPE :
                return DynamicReports.type.dateType();
            case STRING_TYPE :  
                return DynamicReports.type.stringType();
            case FLOAT_TYPE :  
                return DynamicReports.type.floatType();
            case BOOLEAN_TYPE :  
                return DynamicReports.type.booleanType();
            case LONG_TYPE :  
                return DynamicReports.type.longType();
            case CHARACTER_TYPE :  
                return DynamicReports.type.characterType();   
            case DOUBLE_TYPE :  
                return DynamicReports.type.doubleType(); 
            case BIG_DECIMAL_TYPE :  
                return DynamicReports.type.bigDecimalType(); 
            default:
                return DynamicReports.type.stringType();
        }
        
    }
    
   public static String getDateTxt(String locale){
       
       SimpleDateFormat spf ;
       
       if(locale.equals("fr")){
           spf = new SimpleDateFormat("dd/MM/yyyy à  HH:mm");
       }else{
           spf = new SimpleDateFormat("MM/dd/yyyy à  HH:mm");
       }       
       return spf.format(new Date());
   }

    
}
