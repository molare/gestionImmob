package com.immo.controller;

import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.Contrat;
import com.immo.service.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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


    @RequestMapping(value = "/listContrat", method = RequestMethod.GET)
    public ResponseData getAllContrat(){
        List<Contrat> newComList = new ArrayList<Contrat>();
        SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy");
        List<Contrat> listCom = contratService.getAll();
        for(Contrat co : listCom){
            Contrat c = new Contrat();
            c.setId(co.getId());
            c.setName(co.getName());
            c.setDateTransient(sdf.format(co.getDate()));
            c.setAmount(co.getAmount());
            c.setFirstQuittance(co.getFirstQuittance());
            c.setMonthNber(co.getMonthNber());
            c.setStatutPayTransient(co.getStatutPay().getName());
            c.setMoyenPayTransient(co.getMoyenPay().getName());
            c.setLocaterTransient(co.getLocater().getFirstName()+" "+co.getLocater().getLastName());
            c.setLocativeTransient(co.getLocative().getDesignation());
            c.setAgenceMonth(co.getAgenceMonth());
            c.setAdvanceMonth(co.getAdvanceMonth());
            c.setCommentary(co.getCommentary());
            c.setBailDateTransient(sdf.format(co.getBailDate()));
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


    @RequestMapping(value = "/saveContrat", method = RequestMethod.POST,headers="Accept=*/*")
    public ResponseData addContrat(Locale locale,@ModelAttribute Contrat contrat, BindingResult result,@RequestParam("picture")MultipartFile file,HttpServletRequest request)throws Exception{
        ResponseData json=null;
        SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy");
        try {
            if (file.getSize() > 0) {
                String fileName = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                contrat.setImageName(fileName);
                contrat.setImage(bytes);
            }

        contrat.setName(request.getParameter("name").toUpperCase());
        contrat.setAmount(Double.parseDouble(request.getParameter("amount")));
        contrat.setAdvanceMonth(Integer.parseInt(request.getParameter("advance")));
        contrat.setAgenceMonth(Integer.parseInt(request.getParameter("agence")));
        contrat.setMonthNber(Integer.parseInt(request.getParameter("monthNber")));
        contrat.setFirstQuittance(Double.parseDouble(request.getParameter("firstQuittance")));
        contrat.setBailDate(sdf.parse(request.getParameter("bailDate")));
        contrat.setLocater(locaterService.findById(Integer.parseInt(request.getParameter("locater"))));
        contrat.setLocative(locativeService.findById(Integer.parseInt(request.getParameter("locative"))));
        contrat.setMoyenPay(moyenPayService.findById(Integer.parseInt(request.getParameter("moyen"))));
        contrat.setStatutPay(statutPayService.findById(Integer.parseInt(request.getParameter("statut"))));
        contrat.setCommentary(request.getParameter("commentary"));

         Contrat c =  contratService.add(contrat);
            json = new ResponseData(true, c);
        }catch (Exception ex){
            json = new ResponseData(false,"une valeur a été dupliquée ou erroné",ex.getCause());
        }
        return json;
    }

    @RequestMapping(value = "/updateContrat/{id}", method = RequestMethod.POST,headers="Accept=*/*")
    public ResponseData updateContrat(Locale locale,@ModelAttribute Contrat contrat,@PathVariable int id, BindingResult result,@RequestParam("editPicture")MultipartFile file,HttpServletRequest request){
        ResponseData json=null;
        try {
            if(file.getSize()>0 && !file.isEmpty()){
                String fileName = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                contrat.setImageName(fileName);
                contrat.setImage(bytes);
            }else{
                contrat.setImage(contratService.findById(id).getImage());
                contrat.setImageName(contratService.findById(id).getImageName());
            }
            contrat.setName(request.getParameter("name").toUpperCase());
            contrat.setAmount(Double.parseDouble(request.getParameter("amount")));
            contrat.setAdvanceMonth(Integer.parseInt(request.getParameter("advance")));
            contrat.setAgenceMonth(Integer.parseInt(request.getParameter("agence")));
            contrat.setFirstQuittance(Double.parseDouble(request.getParameter("firstQuittance")));
            contrat.setBailDate(new Date());
            contrat.setLocater(locaterService.findById(Integer.parseInt(request.getParameter("locater"))));
            contrat.setLocative(locativeService.findById(Integer.parseInt(request.getParameter("locative"))));
            contrat.setMoyenPay(moyenPayService.findById(Integer.parseInt(request.getParameter("moyen"))));
            contrat.setStatutPay(statutPayService.findById(Integer.parseInt(request.getParameter("statut"))));
            contrat.setCommentary(request.getParameter("commentary"));

            Contrat c =  contratService.update(contrat);
            json = new ResponseData(true, c);
        }catch (Exception ex){
            json = new ResponseData(false,"une valeur a été dupliquée ou erroné",ex.getCause());
        }
        return json;
    }

    @RequestMapping(value = "/findContrat/{id}", method = RequestMethod.GET)
    public ResponseData findContrat(Locale locale,@ModelAttribute Contrat contrat,@PathVariable int id, BindingResult result,HttpServletRequest request){
        Contrat ci = contratService.findById(id);
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
        contratService.delete(typeId);
            json = new ResponseData(true, null);
        }catch (Exception ex){
            json = new ResponseData(false,"Impossible de supprimer cette donnée car elle est liée ailleurs",ex.getCause());
        }
        return json;
    }
}
