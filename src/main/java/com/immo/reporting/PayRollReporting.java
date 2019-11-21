package com.immo.reporting;

import com.immo.entities.Contrat;
import com.immo.entities.PayRoll;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.expression.AbstractValueFormatter;
import net.sf.dynamicreports.report.builder.DynamicReports;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

/**
 * Created by olivier on 08/11/2019.
 */
public class PayRollReporting {


//    private String userName;
    private static ImageBuilder logoImg;
    URL image = Templates.class.getResource("/static/html/dist/assets/img/key.jpg");
    private List<PayRoll> reportData;

    public PayRollReporting(List<PayRoll> reportData) {
        this.reportData = reportData;
    }

    DecimalFormat df = new DecimalFormat("#");
    public JasperReportBuilder build(HttpServletRequest request) throws DRException {
        JasperReportBuilder subreport = report();
        StyleBuilder subtotalStyle = stl.style(Templates.bold12CenteredStyle).setForegroundColor(Color.blue);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy : HH:mm");
        Date dateJour = new Date();
        String d = sdf.format(dateJour);

        //logo
        logoImg = cmp.image(image).setStyle(DynamicReports.stl.style().setHorizontalAlignment(HorizontalAlignment.LEFT));
        logoImg.setDimension(80,80);
        subreport
                .setPageMargin(margin(40))
                .setTemplate(Templates.reportTemplate)
                .summary(cmp.verticalGap(20),
                        cmp.text("Siege social : ABIDJAN - COTE D'IVOIRE, BOULEVARD VALERY GISCARD D'ESTAING IMMEUBLE GANAMET").setStyle(stl.style().setFontSize(8).setHorizontalAlignment(HorizontalAlignment.CENTER)),
                        cmp.text("Email: infos@pivot.ci   -   (225) 21 28 27 72 / 21 22 00 50").setStyle(stl.style().setFontSize(8).setHorizontalAlignment(HorizontalAlignment.CENTER)))
                .setPageFormat(PageType.A3, PageOrientation.PORTRAIT)
                .title(
                        logoImg,
                        cmp.horizontalFlowList(createInfosTitleComponent()).setStyle(stl.style(10)).setGap(10),
                        cmp.horizontalFlowList(createInfosTitleComponentLocater()).setStyle(Templates.boldStyle),
                        cmp.verticalGap(30),
                        cmp.horizontalFlowList(createTitreComponent(), cmp.gap(47, 47)),
                        cmp.verticalGap(30),

                        cmp.horizontalFlowList(cmp.text("Recu de : " + reportData.get(0).getContrat().getLocater().getCivility().getName()
                                + " " + reportData.get(0).getContrat().getLocater().getFirstName() + " " +
                                reportData.get(0).getContrat().getLocater().getLastName())),
                        cmp.verticalGap(15),

                        cmp.horizontalFlowList(cmp.text("La somme de " + df.format(reportData.get(0).getAmount())
                                + " " + reportData.get(0).getContrat().getLocative().getDevis().getName()), cmp.horizontalGap(140), cmp.text("Fait a Abidjan le " + d)),


                        cmp.horizontalFlowList(cmp.text("pour loyer et accessoires des locaux  sis a : "), cmp.horizontalGap(140), cmp.text("par le bailleur " + reportData.get(0).getContrat().getLocative()
                                .getBien().getProperty().getCivility().getName() + " " +
                                reportData.get(0).getContrat().getLocative()
                                        .getBien().getProperty().getFirstName() + " " + reportData.get(0).getContrat().getLocative()
                                .getBien().getProperty().getLastName())),

                        cmp.horizontalFlowList(cmp.text(reportData.get(0).getContrat().getLocative()
                                .getBien().getCity().getName() + " dans la commune de " + reportData.get(0).getContrat().getLocative()
                                .getBien().getCity().getCommune().getName())),
                        cmp.horizontalFlowList(cmp.text("Detail : ")),

                        cmp.horizontalFlowList(cmp.text("- Date de paiement : "+reportData.get(0).getDateTransient())),


                        cmp.horizontalFlowList(cmp.text("- Bien : " + reportData.get(0).getContrat().getLocative().getBien().getDesignation() + " = " +
                                reportData.get(0).getContrat().getLocative().getBien().getTypeBien().getName())),


                        cmp.horizontalFlowList(cmp.text("- Locative : " + reportData.get(0).getContrat().getLocative().getDesignation() + " = " +
                                reportData.get(0).getContrat().getLocative().getTypeLocative().getName())),


                        cmp.horizontalFlowList(cmp.text("- Loyer nu : " + df.format(reportData.get(0).getContrat().getLocative().getAmount())
                                + " " + reportData.get(0).getContrat().getLocative().getDevis().getName())),

                        cmp.horizontalFlowList(cmp.text("- Charges / provisions de charges : " + df.format(reportData.get(0).getContrat().getLocative().getCharge())
                                + " " + reportData.get(0).getContrat().getLocative().getDevis().getName())),

                        cmp.horizontalFlowList(cmp.text("Montant total du terme : " + df.format(reportData.get(0).getAmount())
                                + " " + reportData.get(0).getContrat().getLocative().getDevis().getName())),

                        cmp.horizontalFlowList(cmp.text("Paiement locataire : " + df.format(reportData.get(0).getAmount())
                                + " " + reportData.get(0).getContrat().getLocative().getDevis().getName())),

                        cmp.horizontalFlowList(cmp.text("Solde a payer : " + df.format(0)
                                + " " + reportData.get(0).getContrat().getLocative().getDevis().getName())),

                        cmp.verticalGap(10),
                        cmp.horizontalFlowList(cmp.text("ARTICLE 1 : ").setStyle(stl.style(Templates.boldStyle).setForegroundColor(Color.blue).setUnderline(true))),
                        cmp.horizontalFlowList(cmp.text("HABITATION EXCLUSSIVEMENT")).setGap(10),
                        cmp.text("Le logement est destine a l'usage definie dans le contrat de bail, dans le cas de l exercice d un autre activite " +
                                "le present contrat ne sera plus valable ."),
                        cmp.verticalGap(10),
                        cmp.horizontalFlowList(cmp.text("ARTICLE 2 : ").setStyle(stl.style(Templates.boldStyle).setForegroundColor(Color.blue).setUnderline(true))),
                        // cmp.horizontalFlowList(cmp.text("HABITATION EXCLUSSIVEMENT")).setGap(10),
                        cmp.text("Le Present bail est concenti a accepte moyennement un loyer brut mensuel de " +
                                df.format(reportData.get(0).getContrat().getLocative().getAmount()) + " FCFA plus " + df.format(reportData.get(0).getContrat().getLocative().getCharge()) + " FCFA de charge sur loyer pour un total " + df.format((reportData.get(0).getContrat().getLocative().getAmount() + reportData.get(0).getContrat().getLocative().getCharge())) + " FCFA payable mensuellement"),
                        cmp.text("Apres le premier versement de " +
                                reportData.get(0).getContrat().getMonthNber() + " mois le loyer est verse de maniere mensuel et en espece ."),
                        cmp.verticalGap(10),
                        cmp.horizontalFlowList(cmp.text("Signature bailleur").setStyle(stl.style(Templates.italicStyle).setHorizontalAlignment(HorizontalAlignment.RIGHT)))
                )

                .pageFooter(Templates.footerComponent)
                .setDataSource(createDataSource())
                                .setLocale(Locale.FRENCH);

        return subreport;
    }

    private JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("code","dateTransient","firstName","lastName","email","phone");

        return dataSource;
    }

   private ComponentBuilder<?, ?> createInfosTitleComponent() {
        RectangleBuilder rectangle = cmp.rectangle();
        HorizontalListBuilder list = cmp.horizontalList();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy : HH:mm");
        Date dateJour = new Date();
        String d = sdf.format(dateJour);

        //addCustomerAttributeTitle(list, " Edite le : "+d+" par "+userName);
           addCustomerAttributeTitle(list,"Proprietaire : "+reportData.get(0).getContrat().getLocative().getBien().getProperty()
           .getCivility().getName()+" "+reportData.get(0).getContrat().getLocative().getBien().getProperty().getFirstName()+" "+
           reportData.get(0).getContrat().getLocative().getBien().getProperty().getLastName());
            addCustomerAttributeTitle(list, "Telephone : "+reportData.get(0).getContrat().getLocative().getBien().getProperty().getPhone());
            addCustomerAttributeTitle(list, "Email : "+reportData.get(0).getContrat().getLocative().getBien().getProperty().getEmail());
        return cmp.centerVertical(list);
    }

    private ComponentBuilder<?, ?> createInfosTitleComponentLocater() {
        RectangleBuilder rectangle = cmp.rectangle();
        HorizontalListBuilder list = cmp.horizontalList();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy : HH:mm");
        Date dateJour = new Date();
        String d = sdf.format(dateJour);

        addCustomerAttributeTitles(list,"Locataire : "+reportData.get(0).getContrat().getLocater().getCivility().getName()
                + " " +reportData.get(0).getContrat().getLocater().getFirstName() + " " +
                reportData.get(0).getContrat().getLocater().getLastName());
        addCustomerAttributeTitles(list,"Telephone : " + reportData.get(0).getContrat().getLocater().getPhone());
        addCustomerAttributeTitles(list,"Email : " + reportData.get(0).getContrat().getLocater().getEmail());
       // addCustomerAttributeTitles(list,"Fait le : " +d);
        return cmp.horizontalList(list);
    }


    private ComponentBuilder<?, ?> createTitreComponent() {


   /*     String bailName="";
        String typeBail = reportData.get(0).getLocative().getUsageLocative();
        if(typeBail.equals("1")){
            bailName ="BAIL HABITATION VIDE" ;
        }else if(typeBail.equals("2")){
            bailName ="BAIL MEUBLE" ;
        }else if(typeBail.equals("3")){
            bailName ="BAIL MIXTE" ;
        }else if(typeBail.equals("4")){
            bailName ="BAIL COMMERCIAL" ;
        }else if(typeBail.equals("5")){
            bailName ="BAIL PROFESSIONNEL" ;
        }else if(typeBail.equals("6")){
            bailName ="BAIL GARAGE" ;
        }*/
        RectangleBuilder rectangle = cmp.rectangle();
        StyleBuilder shippingStyle = stl.style(Templates.boldStyle);
        HorizontalListBuilder list = cmp.horizontalList();//.setBaseStyle(stl.style().setTopBorder(stl.pen1Point()).setLeftPadding(10));
        addCustomerAttributeTitle2(list, "QUITTANCE DE LOYER DU MOIS DE "+reportData.get(0).getName().toUpperCase()+"" +
        " "+reportData.get(0).getDateTransient().split("-")[2]);

        return cmp.centerHorizontal(list).setStyle(Templates.boldStyle).setBackgroundComponent(rectangle);
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

    private void addCustomerAttributeTitles(HorizontalListBuilder list, String label) {
        StyleBuilder shippingStyle = stl.style(Templates.boldStyle).setHorizontalAlignment(HorizontalAlignment.RIGHT);
        if (label != null) {
            list.add(cmp.text(label + "").setStyle(shippingStyle)).newRow();
//            list.add(cmp.text(label + "").setFixedColumns(12).setStyle(Templates.boldStyle), cmp.text(value)).newRow();
        }
    }



    private void addCustomerAttributeTitle2(HorizontalListBuilder list, String label) {
        StyleBuilder shippingStyle = stl.style(Templates.bold12CenteredStyle).setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.TOP).setForegroundColor(Color.blue);
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
