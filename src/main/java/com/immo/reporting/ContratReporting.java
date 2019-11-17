package com.immo.reporting;

import com.immo.entities.Contrat;
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
import java.util.*;
import java.util.List;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

/**
 * Created by olivier on 08/11/2019.
 */
public class ContratReporting {


/*    private String userName;
    private static ImageBuilder logoImg;
    URL image = Templates.class.getResource("/static/images/integration-pivotal.png");*/
    private List<Contrat> reportData;

    public ContratReporting(List<Contrat> reportData) {
        this.reportData = reportData;
    }

    public JasperReportBuilder build(HttpServletRequest request) throws DRException {
        JasperReportBuilder subreport = report();


        subreport
                .setPageMargin(margin(40))
                .setTemplate(Templates.reportTemplate)
                .summary(cmp.verticalGap(20),
                        cmp.text("Siege social : ABIDJAN - COTE D'IVOIRE, BOULEVARD VALERY GISCARD D'ESTAING IMMEUBLE GANAMET").setStyle(stl.style().setFontSize(8).setHorizontalAlignment(HorizontalAlignment.CENTER)),
                        cmp.text("Email: infos@pivot.ci   -   (225) 21 28 27 72 / 21 22 00 50").setStyle(stl.style().setFontSize(8).setHorizontalAlignment(HorizontalAlignment.CENTER)))
                .setPageFormat(PageType.A3, PageOrientation.PORTRAIT)
                .title(
                        //  logoImg,
                        cmp.horizontalFlowList(createInfosTitleComponent()).setStyle(stl.style(10)).setGap(10),
                        cmp.horizontalFlowList(createInfosTitleComponentLocater()).setStyle(Templates.boldStyle)
                                .setBaseGap(50),
                        cmp.horizontalFlowList(createTitreComponent()),
                                cmp.horizontalGap(50),
                        cmp.centerHorizontal(createInfosTitleComponentMessage()).setStyle(Templates.boldStyle)
                                .setGap(60),
                        cmp.horizontalFlowList(cmp.text("ARTICLE 1: ").setStyle(stl.style(Templates.boldStyle).setForegroundColor(Color.blue).setUnderline(true)),
                                /*cmp.verticalGap(5),*/ cmp.text("Destination")).setGap(10),
                        cmp.horizontalFlowList(cmp.text("HABITATION EXCLUSSIVEMENT")).setGap(10),
                        cmp.text("Le logement est destine a l'usage definie tout en haut en titre, dans le cas de l exercice d un autre activite " +
                                "le present contrat ne sera plus valable"),

                        cmp.horizontalFlowList(cmp.text("ARTICLE 2: ").setStyle(stl.style(Templates.boldStyle).setForegroundColor(Color.blue).setUnderline(true)),
                                /*cmp.verticalGap(5),*/ cmp.text("Loyer")).setGap(10),
                        cmp.horizontalFlowList(cmp.text("HABITATION EXCLUSSIVEMENT")).setGap(10),
                        cmp.text("Le Present bail est concenti a accepte moyennement un loyer brut mensuel de " +
                                reportData.get(0).getLocative().getAmount() + " payable mensuellement"),

                        cmp.text("Apres le premier versement de " +
                                reportData.get(0).getMonthNber() + " mois le loyer est verse de maniere mensuel et en espece")


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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy a HH:mm");
        Date dateJour = new Date();
        String d = sdf.format(dateJour);

        //addCustomerAttributeTitle(list, " Edite le : "+d+" par "+userName);
           addCustomerAttributeTitle(list,"Proprietaire : "+reportData.get(0).getLocative().getBien().getProperty()
           .getCivility().getName()+" "+reportData.get(0).getLocative().getBien().getProperty().getFirstName()+" "+
           reportData.get(0).getLocative().getBien().getProperty().getLastName());
            addCustomerAttributeTitle(list, "Telephone : "+reportData.get(0).getLocative().getBien().getProperty().getPhone());
            addCustomerAttributeTitle(list, "Email : "+reportData.get(0).getLocative().getBien().getProperty().getEmail());
        return cmp.centerVertical(list);
    }

    private ComponentBuilder<?, ?> createInfosTitleComponentLocater() {
        RectangleBuilder rectangle = cmp.rectangle();
        HorizontalListBuilder list = cmp.horizontalList();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy a HH:mm");
        Date dateJour = new Date();
        String d = sdf.format(dateJour);

        addCustomerAttributeTitles(list,"Locataire : "+reportData.get(0).getLocater().getCivility().getName()
                + " " +reportData.get(0).getLocater().getFirstName() + " " +
                reportData.get(0).getLocater().getLastName());
        addCustomerAttributeTitles(list,"Telephone : " + reportData.get(0).getLocater().getPhone());
        addCustomerAttributeTitles(list,"Email : " + reportData.get(0).getLocater().getEmail());
        addCustomerAttributeTitles(list,"Fait le : " +d);
        return cmp.horizontalList(list);
    }

    private ComponentBuilder<?, ?> createInfosTitleComponentMessage() {
        RectangleBuilder rectangle = cmp.rectangle();
        HorizontalListBuilder list = cmp.horizontalList();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy a HH:mm");
        Date dateJour = new Date();
        String d = sdf.format(dateJour);



        addCustomerAttributeTitleMessage(list, "Je soussigne(e)," + reportData.get(0).getLocative().getBien().getProperty()
                .getCivility().getName() + " " + reportData.get(0).getLocative().getBien().getProperty().getFirstName() + " " +
                reportData.get(0).getLocative().getBien().getProperty().getLastName() + " , proprietaire(s) / representant du Bailleur " +
                "pour la gestion du bien " + reportData.get(0).getLocative().getBien().getDesignation() + ", donne en location la locative " + reportData.get(0).getLocative().getDesignation() + " (" + reportData.get(0).getLocative().getTypeLocative().getName() + ") a " + reportData.get(0).getLocater().getCivility().getName()
                + " " + reportData.get(0).getLocater().getFirstName() + " " +
                reportData.get(0).getLocater().getLastName() + ", locataire(s) declare avoir recu de celui-ci / ceux-ci et au titre de la periode mentionnee");
                addCustomerAttributeTitleMessage(list, "ci-dessus, les sommes suivantes :");
                addCustomerAttributeTitleMessage(list, "* Caution : " + reportData.get(0).getAmount() + " " + reportData.get(0).getLocative().getDevis().getName() + " dont " + reportData.get(0).getAdvanceMonth() + " mois d avance et " + reportData.get(0).getAgenceMonth() + " mois agence(s)");
                addCustomerAttributeTitleMessage(list, "* Montant mensuel du Loyer : " + reportData.get(0).getLocative().getAmount() + " " + reportData.get(0).getLocative().getDevis().getName());
                addCustomerAttributeTitleMessage(list, "* Provision / Forfait pour charges : " + reportData.get(0).getLocative().getCharge() + " " + reportData.get(0).getLocative().getDevis().getName());
                addCustomerAttributeTitleMessage(list, "Cette quittance annule tous recus eventuellement remis a titre d acompte en cas paiement partiel ");
                addCustomerAttributeTitleMessage(list, "du montant du present terme. Cette quittance ne vaut que pour la periode susmentionnee et ne presume");
                addCustomerAttributeTitleMessage(list, "en aucun cas du paiement des termes precedents ni ne constitue une renonciation du bailleur a ses");
                addCustomerAttributeTitleMessage(list, "droits et actions eventuelles.");
                addCustomerAttributeTitleMessage(list, "En cas de conge precedemment donne, cette quittance ne saurait etre consideree comme un titre de location.");
                addCustomerAttributeTitleMessage(list, "Je vous prie d agreer, Madame, Monsieur, mes salutations distinguees. ");

        return cmp.horizontalList(list);
    }

    private ComponentBuilder<?, ?> createTitreComponent() {


        String bailName="";
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
        }
        RectangleBuilder rectangle = cmp.rectangle();
        StyleBuilder shippingStyle = stl.style(Templates.boldStyle);
        HorizontalListBuilder list = cmp.horizontalList();//.setBaseStyle(stl.style().setTopBorder(stl.pen1Point()).setLeftPadding(10));
        addCustomerAttributeTitle2(list, "CONTRAT DE BAIL - "+bailName);

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

    private void addCustomerAttributeTitleMessage(HorizontalListBuilder list, String label) {
        StyleBuilder shippingStyle = stl.style().setFontSize(10).setHorizontalAlignment(HorizontalAlignment.LEFT);
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
