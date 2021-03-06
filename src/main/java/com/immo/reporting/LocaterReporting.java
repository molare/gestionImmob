package com.immo.reporting;

import com.immo.entities.Locater;
import com.immo.reporting.Templates;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.expression.AbstractValueFormatter;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.ImageBuilder;
import net.sf.dynamicreports.report.builder.component.RectangleBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

/**
 * Created by olivier on 30/08/2019.
 */
public class LocaterReporting {

    private List<Locater> reportData;
    private String userName;
    /*private static InputStream inImg = JasperReportBuilder.class.getResourceAsStream("/static/images/integration-pivotal.png");*/
    private static ImageBuilder logoImg;
    URL image = Templates.class.getResource("/static/html/dist/assets/img/key.jpg");


    public LocaterReporting(List<Locater> reportData, String userName) {
        this.reportData = reportData;
        this.userName = userName;
    }

    public JasperReportBuilder build(HttpServletRequest request) throws DRException {
        JasperReportBuilder subreport = report();
        StyleBuilder textStyle = stl.style(Templates.columnStyle).setBorder(stl.pen1Point());
        StyleBuilder someStyle= stl.style(Templates.columnStyle).setBorder(stl.pen1Point()).setFontSize(10).setBackgroundColor(Color.LIGHT_GRAY);
        StyleBuilder titleStyle= stl.style(Templates.columnStyle).setBorder(stl.pen1Point()).setFontSize(10).setBackgroundColor(new Color(62,162,97));

        TextColumnBuilder<String> firstColumn = col.column("Nom", "firstName", type.stringType());
        TextColumnBuilder<String> lastColumn = col.column("Prenom", "lastName", type.stringType());
        TextColumnBuilder<String> codeColumn = col.column("Code", "code", type.stringType());
        TextColumnBuilder<String> dateColumn = col.column("Date creation", "dateTransient", type.stringType());
        TextColumnBuilder<String> emailColumn = col.column("Email", "email", type.stringType());
        TextColumnBuilder<String> phoneColumn = col.column("Telephone", "phone", type.stringType());
        /*AggregationSubtotalBuilder<Double> montantVar = sbt.sum(amountColumn).setValueFormatter(new ValueFormatter())
                .setLabel("Total montant paye");*/


        //logo
        logoImg = cmp.image(image).setStyle(DynamicReports.stl.style().setHorizontalAlignment(HorizontalAlignment.LEFT));
        logoImg.setDimension(80,80);

        subreport
                .setPageMargin(margin(40))
                .setTemplate(Templates.reportTemplate)
                .setColumnStyle(textStyle)
                .columns(dateColumn,codeColumn, firstColumn, lastColumn, emailColumn, phoneColumn)
                .setColumnTitleStyle(titleStyle)
               /* .subtotalsAtSummary(montantVar)sbt.text("Total", codeStockColumn).setLabelPosition(Position.LEFT), sbt.text("", productColumn),sbt.text("", quantityColumn), sbt.text("", priceColumn)*//*.setStyle(stl.style().setRightIndent(10).setTopPadding(4).setBottomPadding(4).setRightBorder(stl.pen1Point()).setAlignment(HorizontalAlignment.LEFT, VerticalAlignment.MIDDLE))*//*, sbt.sum(amountColumn).setValueFormatter(new ValueFormatter()).setStyle(stl.style().setForegroundColor(Color.red).setBorder(stl.pen1Point()).setFontSize(10).setBackgroundColor(Color.LIGHT_GRAY)).setHeight(17)).
        setSubtotalStyle(someStyle)*/
                .summary(cmp.verticalGap(20),
                        cmp.text("Siege social : ABIDJAN - COTE D'IVOIRE, BOULEVARD VALERY GISCARD D'ESTAING IMMEUBLE GANAMET").setStyle(stl.style().setFontSize(8).setHorizontalAlignment(HorizontalAlignment.CENTER)),
                        cmp.text("Email: infos@pivot.ci   -   (225) 21 28 27 72 / 21 22 00 50").setStyle(stl.style().setFontSize(8).setHorizontalAlignment(HorizontalAlignment.CENTER)))
                .setPageFormat(PageType.A3, PageOrientation.PORTRAIT)
                .title(
                        logoImg,
                        cmp.horizontalFlowList(createInfosTitleComponent()).setStyle(stl.style(10)).setGap(10),
                        cmp.centerHorizontal(createTitreComponent()).setStyle(Templates.boldStyle)
                )

                .pageFooter(Templates.footerComponent)
                .setDataSource(createDataSource())
                .setLocale(Locale.FRENCH);

        return subreport;
    }

    private JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("code","dateTransient","firstName","lastName","email","phone");
        for(Locater e : reportData){
            dataSource.add( e.getCode(),e.getDateTransient(),e.getFirstName(), e.getLastName(), e.getEmail(),e.getPhone());
        }
        return dataSource;
    }

    private ComponentBuilder<?, ?> createInfosTitleComponent() {
        RectangleBuilder rectangle = cmp.rectangle();
        HorizontalListBuilder list = cmp.horizontalList();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy : HH:mm");
        Date dateJour = new Date();
        String d = sdf.format(dateJour);

        addCustomerAttributeTitle(list, " Edite le : "+d+" par "+userName);
/*            addCustomerAttributeTitle(list, " Code : "+reportData.get(0).getCustomerCodeTransient());
            addCustomerAttributeTitle(list, " Client : "+reportData.get(0).getCustomerTransient());
            addCustomerAttributeTitle(list, " Email : "+reportData.get(0).getEmail());
            addCustomerAttributeTitle(list, " Telephone : "+reportData.get(0).getPhone());
            addCustomerAttributeTitle(list, " Date d'achat : "+reportData.get(0).getDateTransient());*/
        return cmp.centerVertical(list)/*.setBackgrdoundComponent(rectangle)*/;
    }

    private ComponentBuilder<?, ?> createTitreComponent() {

        HorizontalListBuilder list = cmp.horizontalList();//.setBaseStyle(stl.style().setTopBorder(stl.pen1Point()).setLeftPadding(10));
        addCustomerAttributeTitle2(list, "Liste Locataire");

        return cmp.verticalList(list).setStyle(Templates.boldStyle);
    }

    private void addCustomerAttribute(HorizontalListBuilder list, String label, String value){
        if (value != null) {
            list.add(cmp.text(label + " :").setStyle(Templates.boldStyle), cmp.text(value)).newRow();
//            list.add(cmp.text(label + " :").setFixedColumns(12).setStyle(Templates.boldStyle), cmp.text(value)).newRow();
        }
    }

    private void addCustomerAttributeTitle(HorizontalListBuilder list, String label) {
        StyleBuilder shippingStyle = stl.style(Templates.boldStyle);
        if (label != null) {
            list.add(cmp.text(label + "").setFixedColumns(50).setStyle(shippingStyle)).newRow();
//            list.add(cmp.text(label + "").setFixedColumns(12).setStyle(Templates.boldStyle), cmp.text(value)).newRow();
        }
    }

    private void addCustomerAttributeTitle2(HorizontalListBuilder list, String label) {
        StyleBuilder shippingStyle = stl.style(Templates.bold12CenteredStyle).setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.TOP);
        if (label != null) {
            list.add(cmp.text(label + "").setFixedColumns(50).setStyle(shippingStyle)).newRow();
//            list.add(cmp.text(label + "").setFixedColumns(12).setStyle(Templates.boldStyle), cmp.text(value)).newRow();
        }
    }

    private static class ValueFormatter extends AbstractValueFormatter<String, Number> {
        private static final long serialVersionUID = 1L;

        @Override
        public String format(Number value, ReportParameters reportParameters) {
            return type.bigDecimalType().valueToString(value, reportParameters.getLocale()) + " FCFA";
        }
    }
}
