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
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;

import com.immo.entities.ITitle;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.datatype.AbstractDataType;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.ImageBuilder;
/*import net.sf.dynamicreports.report.builder.crosstab.CrosstabBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabRowGroupBuilder;*/
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
//import net.sf.dynamicreports.report.constant.Calculation;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.LineStyle;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
//import net.sf.dynamicreports.report.constant.PageOrientation;
//import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import org.apache.http.HttpRequest;



/**
 *
 * @author karim.coulibaly
 */
public class ExportGenerator  {     
    

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
    
    //private static  TextFieldBuilder<String> title;
    private static JasperReportBuilder report ;
    //private static InputStream inImg = JasperReportBuilder.class.getResourceAsStream("/logopalm111.png");
    private static ImageBuilder logoImg;
    private static Map<String, TextColumnBuilder> drColumns ;
    private static List<String> groups ;
    private static List<String> subtotals ;
    
    private static StyleBuilder boldStyle;
    private static HttpSession session; 
    
    
    // Genere un rapport dynamique
    public static JasperReportBuilder  generateReport(ExportData metaData,HttpServletRequest request){
        session = request.getSession(false);
        report = report(); // Creer le rapport
        
         //Logo
//        logoImg = cmp.image(inImg).setStyle(DynamicReports.stl.style().setHorizontalAlignment(HorizontalAlignment.LEFT));       
        //logoImg.setDimension(80,80);  
        
        //Definit le style des colonnes d'entàte
        report.setColumnTitleStyle(getHeaderStyle(metaData));
 
        
        
        LinkedHashMap <String,String> criteria = metaData.getCriteria();    
        //Ajout la date d'àdition au critere d'impression
        String valDateProperty = getDateTxt(metaData.getLang()) ;
        String keyDateProperty = "" ;
        if ( session.getAttribute("activedLocale").equals("fr") ) {
        keyDateProperty = "Edité le " ;
        }
        else {
        keyDateProperty = "Printed on " ;
        }
        criteria.put(keyDateProperty,valDateProperty);
        
        StyleBuilder  titleStyle = stl.style(boldStyle).setFontSize(16).setForegroundColor(new Color(0, 0, 0)).setHorizontalAlignment(HorizontalAlignment.RIGHT).setRightIndent(20);
        ComponentBuilder<?, ?> logoComponent = cmp.horizontalList(
            //cmp.image(Templates.class.getResource("/logopalm.png")).setFixedDimension(150, 50).setStyle(stl.style().setLeftIndent(20)),
            cmp.verticalList(
                cmp.text(metaData.getTitle()).setStyle(titleStyle)
            )
        ).newRow()
         .add(cmp.horizontalList().add(cmp.hListCell(createCriteriaComponent(criteria,metaData)).heightFixedOnTop()))
         .newRow();     
        
        report.noData(logoComponent, cmp.text("Aucun enregistrement trouvà").setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER).setTopPadding(30)));

        
         
        lblStyle = stl.style(Templates.columnStyle).setForegroundColor(new Color(60, 91, 31));  // Couleur du text      
        colStyle = stl.style(Templates.columnStyle).setHorizontalAlignment(HorizontalAlignment.CENTER);
        
        groupStyle = stl.style().setForegroundColor(new Color(60, 91, 31))               
                                                       .setBold(Boolean.TRUE)
                                                       .setPadding(5)
                                                       .setFontSize(13)
                                                       .setHorizontalAlignment(HorizontalAlignment.CENTER);  
        

        
         if(metaData.getTitle().equals(ITitle.PARCEL)){
            report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.PARCEL_en)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.PLANTING)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.PLANTING_en)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.SECTOR)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.SECTOR_en)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.BLOCK)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
             }else if(metaData.getTitle().equals(ITitle.BLOCK_en)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.ATTACK_PHYTO)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.TREATMENT)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.TREATMENT_en)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.HARVEST)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.HARVEST_en)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.SERVICING)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.SERVICING_en)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.EVENT_PLANTING)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.EVENT_PLANTING_en)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.FERTILIZATION)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
             }else if(metaData.getTitle().equals(ITitle.FERTILIZATION_en)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.CONTROL_PHYTO)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.CONTROL_PHYTO_en)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.EVENT_FOLDING)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.EVENT_FOLDING_en)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.MORTALITY)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.MORTALITY_en)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.HARVESTING)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
             }else if(metaData.getTitle().equals(ITitle.HARVESTING_en)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.BUDGET)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.REPLACEMENT)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.REPLACEMENT_en)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.METEOROLOGY)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.METEOROLOGY_en)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.PLANTING_MATERIAL)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.PLANTING_MATERIAL_en)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.PLUVIOMETRY)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.PLUVIOMETRY_en)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.ETP)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.ETP_en)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.HYGROMETRY)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.HYGROMETRY_en)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.TEMPERATURE)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.TEMPERATURE_en)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.INSULATION)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.INSULATION_en)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.WORK)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }else if(metaData.getTitle().equals(ITitle.WORK_en)){
             report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
         }
        
        
        drColumns = new HashMap<String, TextColumnBuilder>();        
        groups = new ArrayList<String>();
        subtotals = new ArrayList<String>();
        
        //Definit la liste des colonnes du rapport
        ExportGenerator.getColumns(metaData);        
        //Definit la liste des sous-totaux du rapport
        ExportGenerator.getSubTotals(metaData);
   
        
        //Genration des sous totaux
        for (String group : groups) {
            ColumnGroupBuilder group2 = grp.group(drColumns.get(group));
            report.groupBy(group2);
            for (String subtotal : subtotals) {
               report.subtotalsAtGroupFooter(group2,sbt.sum(drColumns.get(subtotal)));
            }
         }

         for (String subtotal : subtotals) {
                report.subtotalsAtSummary(sbt.sum(drColumns.get(subtotal)));  
         }
         
         /*if(ExportGenerator.getColumnByNameField("plantingName") != null){
             report.subtotalsAtSummary(sbt.aggregate(exp.text("TOTAL "), ExportGenerator.getColumnByNameField("plantingName"), Calculation.NOTHING));
         }*//*else if(ExportGenerator.getColumnByNameField("invoiceCode") != null){
             report.subtotalsAtSummary(sbt.aggregate(exp.text("TOTAL GENERAL "), ExportGenerator.getColumnByNameField("invoiceCode"), Calculation.NOTHING));
         }else if(ExportGenerator.getColumnByNameField("planterCode") != null){
             report.subtotalsAtSummary(sbt.aggregate(exp.text("TOTAL"), ExportGenerator.getColumnByNameField("planterCode"), Calculation.NOTHING));
         }else if(ExportGenerator.getColumnByNameField("transportTicket") != null){
             report.subtotalsAtSummary(sbt.aggregate(exp.text("TOTAL GENERAL "), ExportGenerator.getColumnByNameField("transportTicket"), Calculation.NOTHING));
         }else if(ExportGenerator.getColumnByNameField("deliveryDate") != null){
             report.subtotalsAtSummary(sbt.aggregate(exp.text("TOTAL GENERAL "), ExportGenerator.getColumnByNameField("deliveryDate"), Calculation.NOTHING));
         }
         else if(ExportGenerator.getColumnByNameField("arroundPlantingTicketNumber") != null){
             report.subtotalsAtSummary(sbt.aggregate(exp.text("TOTAL GENERAL "), ExportGenerator.getColumnByNameField("arroundPlantingTicketNumber"), Calculation.NOTHING));
         }
         else if(ExportGenerator.getColumnByNameField("planterNumber") != null){
             report.subtotalsAtSummary(sbt.aggregate(exp.text("TOTAL GENERAL "), ExportGenerator.getColumnByNameField("planterNumber"), Calculation.NOTHING));
         }else if(ExportGenerator.getColumnByNameField("startPaymentDate") != null){
             report.subtotalsAtSummary(sbt.aggregate(exp.text("TOTAL GENERAL "), ExportGenerator.getColumnByNameField("startPaymentDate"), Calculation.NOTHING));
         }else if(ExportGenerator.getColumnByNameField("sectorCode") != null){
             report.subtotalsAtSummary(sbt.aggregate(exp.text("TOTAL GENERAL "), ExportGenerator.getColumnByNameField("sectorCode"), Calculation.NOTHING));
         }else if(ExportGenerator.getColumnByNameField("month") != null){
             report.subtotalsAtSummary(sbt.aggregate(exp.text("TOTAL "), ExportGenerator.getColumnByNameField("month"), Calculation.NOTHING));
         }*/
         

         
         
        //Genere la source de donnàes du rapport
        report.setDataSource(metaData.getData());
        
        report.highlightDetailEvenRows();        
        
        
       // Definition de l'entete : valable uniquement  pour la 1ere page
         HorizontalListBuilder hlb= null;
         
                hlb = cmp.horizontalList().add(
                        cmp.hListCell(createCriteriaComponent(criteria,metaData)).heightFixedOnTop()
                        );

        
        report.title(
            createCustomTitleComponent(metaData.getTitle()), // Titre
            hlb,// Liste Horizontal des Critàres 
            cmp.verticalGap(12) // Marge de 12 px entre chaque critàre
        ); 
        
        
        
        report.setPageMargin(DynamicReports.margin().setLeft(15).setTop(30).setRight(15).setBottom(30));
        report.setPageFormat(PageType.A3, PageOrientation.LANDSCAPE);
        report.pageFooter(DynamicReports.cmp.pageXslashY());
        return report;
       
    }
   
    
    /**
     * Generate subtotals
     * @param metaData 
     */
    private static void getSubTotals(ExportData metaData){ 
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
    }
    
    
    /**
     * Get a column by name field Entity
     * @param nameField
     * @return 
     */
    private static TextColumnBuilder getColumnByNameField(String nameField){        
      return drColumns.get(nameField);
    }
    
    /**
     * createSubtotal
     * @param column
     * @param label
     * @param type
     * @return 
     */
    private static AggregationSubtotalBuilder createSubtotal(TextColumnBuilder column,String label,int type) {
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
   }


   
   /**
    * Construit le panel des metadonnàes(Date et critàres de recherche)
    * @param criteria
    * @return 
    */
    private static ComponentBuilder<?, ?> createCriteriaComponent(LinkedHashMap <String,String> criteria,ExportData metaData ) {
         
        HorizontalListBuilder list = cmp.horizontalList().setBaseStyle(DynamicReports.stl.style()
                                          .setTopBorder(DynamicReports.stl.pen1Point().setLineColor(Color.BLACK))
                                          .setBottomBorder(DynamicReports.stl.pen1Point().setLineColor(Color.BLACK))
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
    }
    
    
   /**
    * imprime une ligne dans le panel des metadonnàes
    * @param list
    * @param label
    * @param value 
    */
    private static void addCriteriaAttribute(HorizontalListBuilder list, String label, String value,ExportData metaData) {
        if (value != null) {              
             int totalCell = list.getList().getListCells().size();
             //System.out.println("totalCell:"+totalCell);
             if( (totalCell % 2) == 0){
                 list.add(cmp.text(label + ":").setFixedColumns(10).setStyle(lblStyle), cmp.text(value)).newRow();
             }else{
                 list.add(cmp.text(label + ":").setFixedColumns(10).setStyle(lblStyle).setStyle(stl.style().setLeftPadding(5)), cmp.text(value));
             }
             
        }
    }

    /**
     * COnstruit l'entete du rapport
     * @param label
     * @return 
     */
    public static ComponentBuilder<?, ?> createCustomTitleComponent(String label) {

        StyleBuilder  bold12CenteredStyle = stl.style(boldStyle).setFontSize(12);
        StyleBuilder  bold16CenteredStyle = stl.style(boldStyle).setFontSize(16).setForegroundColor(new Color(0, 0, 0));
        //StyleBuilder italicStyle = stl.style(rootStyle).italic();
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

    
    private static StyleBuilder getHeaderStyle(ExportData metaData){
                
                /*pour la police des entàtes de HARVESTING*/
                if(metaData.getTitle().equals(ITitle.HARVESTING)){
                        return stl.style().setBorder(stl.pen1Point().setLineColor(Color.BLACK))
                       .setPadding(5)
                       .setHorizontalAlignment(HorizontalAlignment.CENTER)
                       .setVerticalAlignment(VerticalAlignment.MIDDLE)
                       .setForegroundColor(Color.WHITE)
                       .setFontSize(7)
                       .setBackgroundColor(new Color(60, 91, 31))
                       .bold();  

                }/*pour la police des entetes de SERVICING*/
                if(metaData.getTitle().equals(ITitle.SERVICING)){
                        return stl.style().setBorder(stl.pen1Point().setLineColor(Color.BLACK))
                       .setPadding(5)
                       .setHorizontalAlignment(HorizontalAlignment.CENTER)
                       .setVerticalAlignment(VerticalAlignment.MIDDLE)
                       .setForegroundColor(Color.WHITE)
                       .setFontSize(7)
                       .setBackgroundColor(new Color(60, 91, 31))
                       .bold();  

                }/*pour la police des entetes de FERTILIZATION*/
                if(metaData.getTitle().equals(ITitle.FERTILIZATION)){
                        return stl.style().setBorder(stl.pen1Point().setLineColor(Color.BLACK))
                       .setPadding(5)
                       .setHorizontalAlignment(HorizontalAlignment.CENTER)
                       .setVerticalAlignment(VerticalAlignment.MIDDLE)
                       .setForegroundColor(Color.WHITE)
                       .setFontSize(7)
                       .setBackgroundColor(new Color(60, 91, 31))
                       .bold();  

                }/*pour la police des entetes de CONTROL_PHYTO*/
                if(metaData.getTitle().equals(ITitle.CONTROL_PHYTO)){
                        return stl.style().setBorder(stl.pen1Point().setLineColor(Color.BLACK))
                       .setPadding(5)
                       .setHorizontalAlignment(HorizontalAlignment.CENTER)
                       .setVerticalAlignment(VerticalAlignment.MIDDLE)
                       .setForegroundColor(Color.WHITE)
                       .setFontSize(7)
                       .setBackgroundColor(new Color(60, 91, 31))
                       .bold();  

                }else{
                        return stl.style().setBorder(stl.pen1Point().setLineColor(Color.BLACK))
                        .setPadding(5)
                        .setHorizontalAlignment(HorizontalAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .setForegroundColor(Color.WHITE)
                        .setBackgroundColor(new Color(60, 91, 31))
                        .bold();  
                }
        
    }
    
    /**
     * Genere les colums du rappors et lance la recuperation des données
     * @param metaData 
     */
    private static void getColumns(ExportData metaData){
        
        //Recuperation des données
        Map<String, Properties> cols =   metaData.getHeaderCols();
        Set<String> keys =  cols.keySet();
        //List ds = metaData.getData();
        boolean isAlreadyGrouped = false;
        for (String key : keys) {           
            Properties colData  = cols.get(key);
            String val = colData.getProperty("name");
            int typeId = new Integer(colData.getProperty("type"));
            TextColumnBuilder column = null;
            
            /*Formatage de la sdate des colonnes*/
            if(typeId == DATE_TYPE){
                if(metaData.getLang().equals("fr")){
                     column = col.column(val,key,getTypeColumn(typeId));
                     column.setPattern("dd/MM/yyyy");
                }else{
                     column = col.column(val,key,getTypeColumn(typeId));
                     column.setPattern("#.##");
                }
            }else{
                     column = col.column(val,key,getTypeColumn(typeId));
                     column.setPattern("#.##");
            }

            
            /*Police des cellules de HARVESTING*/
            if(metaData.getTitle().equals(ITitle.HARVESTING)){
                column.setStyle(stl.style().setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setHorizontalAlignment(HorizontalAlignment.CENTER).setFontSize(7));
            }/*Police des cellules de SERVICING*/
            else if(metaData.getTitle().equals(ITitle.SERVICING)){
                column.setStyle(stl.style().setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setHorizontalAlignment(HorizontalAlignment.CENTER).setFontSize(7));
            }/*Police des cellules de SERVICING*/
            else if(metaData.getTitle().equals(ITitle.FERTILIZATION)){
                column.setStyle(stl.style().setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setHorizontalAlignment(HorizontalAlignment.CENTER).setFontSize(7));
            }/*Police des cellules de CONTROL_PHYTO*/
            else if(metaData.getTitle().equals(ITitle.CONTROL_PHYTO)){
                column.setStyle(stl.style().setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setHorizontalAlignment(HorizontalAlignment.CENTER).setFontSize(7));
            }else{
                column.setStyle(stl.style().setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setHorizontalAlignment(HorizontalAlignment.CENTER));
            }
                
            report.addColumn(column); 
            drColumns.put(key,column); // sauvegarde des colums du rapport en cours
            
            if(colData.getProperty("isGroupedColumn")!= null){                
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
            }
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
       
       if ( session.getAttribute("activedLocale").equals("fr") ) {
       spf = new SimpleDateFormat("dd/MM/yyyy : HH:mm");
       } else {
       spf = new SimpleDateFormat("dd/MM/yyyy : HH:mm");   
       }
       
       
       //if(locale.equals("fr")){
       //    spf = new SimpleDateFormat("dd/MM/yyyy à  HH:mm");
       //}else{
       //    spf = new SimpleDateFormat("MM/dd/yyyy at  HH:mm");
       //}       
       return spf.format(new Date());
   }

    public static JasperReportBuilder generateReport(ExportData exportData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
