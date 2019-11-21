package com.immo.controller;

import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.Contrat;
import com.immo.entities.PayRoll;
import com.immo.reporting.ContratReporting;
import com.immo.reporting.ExportFileName;
import com.immo.service.*;
import net.sf.dynamicreports.report.exception.DRException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by olivier on 02/10/2019.
 */
@RestController
public class ContratController {

    @Autowired
    private ContratService contratService;

    @Autowired
    private LocativeService locativeService;

    @Autowired
    private LocaterService locaterService;

    @Autowired
    private MoyenPayService moyenPayService;

    @Autowired
    private StatutPayService statutPayService;

    @Autowired
    private PayRollService payRollService;


    @RequestMapping(value = "/listContrat", method = RequestMethod.GET)
    public ResponseData getAllContrat(){
        List<Contrat> newComList = new ArrayList<Contrat>();
        SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy : HH:mm");
        DecimalFormat df = new DecimalFormat("#");
        List<Contrat> listCom = contratService.getAll();
        for(Contrat co : listCom){
            Contrat c = new Contrat();
            c.setId(co.getId());
            c.setName(co.getName());
            c.setDateTransient(sdf.format(co.getDate()));
            c.setAmount(co.getAmount());
            c.setFirstQuittance(co.getFirstQuittance());
            c.setMonthNber(co.getMonthNber());
            c.setStatusContratTransient(co.getStatusContrat());
            c.setStatutPayTransient("<td>\n" +
                    "<span class=\"badge badge-warning\"><h7>"+co.getStatutPay().getName()+"</h7></span>\n" +
                    "  </td>");
            if(co.getStatusContrat().equals("1")) {
                c.setStatusContrat("<td>\n" +
                        "<span class=\"badge badge-success\"><h7>En cours</h7></span>\n" +
                        "  </td>");
                c.setTextStatusContratTransient("En cours");
            }else{
                c.setStatusContrat("<td>\n" +
                        "<span class=\"badge badge-danger\"><h7>Annuler</h7></span>\n" +
                        "  </td>");
                c.setTextStatusContratTransient("Annuler");
            }
            c.setMoyenPayTransient(co.getMoyenPay().getName());
            c.setLocaterTransient(co.getLocater().getFirstName()+" "+co.getLocater().getLastName());
            c.setLocativeTransient(co.getLocative().getDesignation()+" ("+co.getLocative().getTypeLocative().getName()+")");
            c.setAgenceMonth(co.getAgenceMonth());
            c.setAdvanceMonth(co.getAdvanceMonth());
            c.setCommentary(co.getCommentary());
            c.setStartBailDateTransient(sdf.format(co.getStartBailDate()));
           // c.setEndBailDateTransient(sdf.format(co.getEndBailDate()));
            c.setBienTransient(co.getLocative().getBien().getDesignation());
            double rest = (co.getRestCaution()/(co.getLocative().getAmount()/*+co.getLocative().getCharge()*/));
            c.setRestCautionTransient(df.format(rest)+" mois soit "+df.format(co.getRestCaution())+" "+co.getLocative().getDevis().getName());
            String act="<td>\n" +
                    //"<button  class=\"btn btn-success btn-xs m-r-5\"  data-toggle=\"modal\" data-target=\"#editContratModal\" onclick=\"editContrat("+c.getId()+") data-original-title=\"Edit\"><i class=\"fa fa-pencil font-14\"></i></button>\n"+
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#editContratModal\" class=\"link-underlined margin-right-50 btn btn-success\" data-original-title=\"Editer\" onclick=\"editContrat("+c.getId()+")\"><i class=\"fa fa-pencil font-14\"><!-- --></i></a>\n" +
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#removeContratModal\" class=\"link-underlined btn btn-danger\" data-original-title=\"Supprimer\" onclick=\"removeContrat("+c.getId()+")\"><i class=\"fa fa-trash font-14\"><!-- --></i></a>\n" +
                    "</td>";
            c.setAction(act);
            String checkboxes ="<input name=\"select_id\" id=\"tabId\" value=\""+c.getId()+"\" type=\"checkbox\">";
            c.setCheckboxe(checkboxes);

            if(co.getImage() != null){
                byte[] encodeBase64 = Base64.encodeBase64(co.getImage());
                String base64Encoded = null;
                try {
                    base64Encoded = new String(encodeBase64, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //String img = "<img width=\"64\" height=\"64\" alt=\"img\" src=\"data:image/jpeg;base64,"+base64Encoded+"\"/>";
                String img = "<img style=\"width:60px\" alt=\"img\" src=\"data:image/jpeg;base64,"+base64Encoded+"\"/>";
                c.setImageTransient(img);
            }else{
                // String img = "<img class=\"user_picture_small\" style=\"width:200px\" alt=\"Image\" src=\"../assets/common/img/upload_img.png\">";
                c.setImageTransient(null);
            }
            newComList.add(c);
        }



        return new ResponseData(true,newComList);
    }


    @RequestMapping(value = "/saveContrat", method = RequestMethod.POST,headers="Accept=*/*",produces="application/json;charset=UTF-8")
    public ResponseData addContrat(Locale locale,@ModelAttribute Contrat contrat, BindingResult result,@RequestParam("picture")MultipartFile file,HttpServletRequest request)throws Exception{
        ResponseData json=null;
        try {
            json = contratService.addContrat(contrat,result,file,request);

        }catch (Exception ex){
            json = new ResponseData(false,"une valeur a été dupliquée ou erroné",ex.getCause());
        }
        return json;
    }

    @RequestMapping(value = "/updateContrat/{idContrat}", method = RequestMethod.POST,headers="Accept=*/*",produces="application/json;charset=UTF-8")
    public ResponseData updateContrats(Locale locale, @ModelAttribute Contrat cont,BindingResult result, @PathVariable int idContrat,@RequestParam("editPicture")MultipartFile file,HttpServletRequest request)throws Exception{

        ResponseData json=null;
        SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sf =new SimpleDateFormat("dd-mm-yyyy");
     try{

             json = contratService.updateContrat(locale,cont,result,idContrat,file,request);


        }catch (Exception ex){
         System.out.println("erreur "+ex.getMessage());
         json = new ResponseData(false,"une valeur a été dupliquée ou erronée",ex.getMessage());
        }
        return json;
    }

    @RequestMapping(value = "/findContrat/{id}", method = RequestMethod.GET)
    public ResponseData findContrat(Locale locale,@ModelAttribute Contrat contrat,@PathVariable int id, BindingResult result,HttpServletRequest request){
        SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy");

        Contrat ci = contratService.findById(id);
        ci.setStartBailDateTransient(sdf.format(ci.getStartBailDate()));
        //ci.setEndBailDateTransient(sdf.format(ci.getEndBailDate()));
        if(ci.getImage() != null){
            byte[] encodeBase64 = Base64.encodeBase64(ci.getImage());
            String base64Encoded = null;
            try {
                base64Encoded = new String(encodeBase64, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //String img = "<img width=\"64\" height=\"64\" alt=\"img\" src=\"data:image/jpeg;base64,"+base64Encoded+"\"/>";
            String img = "<img style=\"width:200px\" alt=\"img\" src=\"data:image/jpeg;base64,"+base64Encoded+"\"/>";
            ci.setImageTransient(img);
        }else{
            // String img = "<img class=\"user_picture_small\" style=\"width:200px\" alt=\"Image\" src=\"../assets/common/img/upload_img.png\">";
            ci.setImageTransient(null);
        }
       return new ResponseData(true, ci);
    }

    @RequestMapping(value = "/deleteContrat/{typeId}", method = RequestMethod.DELETE)
    public ResponseData deleteContrat(@PathVariable int typeId,HttpServletRequest request){
        ResponseData json=null;
        try {

            List<PayRoll> listPayRoll = payRollService.findByContrat(contratService.findById(typeId));
            if(!listPayRoll.isEmpty()){
               for(PayRoll  pr : listPayRoll){
                   payRollService.delete(pr.getId());
               }
            }
        contratService.delete(typeId);
            json = new ResponseData(true, null);
        }catch (Exception ex){
            json = new ResponseData(false,"Impossible de supprimer cette donnée car elle est liée ailleurs",ex.getCause());
        }
        return json;
    }


    @RequestMapping(value = "/contrat/export", method = RequestMethod.POST)
    public void exportCustomer(HttpServletRequest request, HttpServletResponse response,HttpSession session){
        response.setContentType("application/pdf");

        int id =Integer.parseInt(request.getParameter("cpt"));
       // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Contrat> contrats = contratService.export(id, request);
        try {
            OutputStream out = response.getOutputStream();
            ContratReporting p = new ContratReporting(contrats);
           // response.setHeader("Content-disposition", "attachment; filename="+ ExportFileName.CONTRAT+".pdf");
            p.build(request).toPdf(out);
        } catch (IOException ex) {
            Logger.getLogger(ContratController.class.getName()).log(Level.SEVERE, null, ex);
        }  catch (DRException ex) {
            Logger.getLogger(ContratController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
