package com.immo.controller;

import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.StatutPay;
import com.immo.service.StatutPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by olivier on 02/10/2019.
 */
@RestController
public class StatutPayController {

    @Autowired
    private StatutPayService devisService;
    @RequestMapping(value = "/listStatutPay", method = RequestMethod.GET)
    public ResponseData getAllStatutPay(){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        List<StatutPay> newComList = new ArrayList<StatutPay>();
        List<StatutPay> listCom = devisService.getAll();
        for(StatutPay co : listCom){
            StatutPay c = new StatutPay();
            c.setId(co.getId());
            c.setName(co.getName());
            c.setDescription(co.getDescription());
            c.setCreatedDate(co.getCreatedDate());
            c.setDateTransient(df.format(co.getCreatedDate()));
            String act="<td>\n" +
                    //"<button  class=\"btn btn-success btn-xs m-r-5\"  data-toggle=\"modal\" data-target=\"#editStatutPayModal\" onclick=\"editStatutPay("+c.getId()+") data-original-title=\"Edit\"><i class=\"fa fa-pencil font-14\"></i></button>\n"+
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#editStatutPayModal\" class=\"link-underlined margin-right-50 btn btn-success\" data-original-title=\"Editer\" onclick=\"editStatutPay("+c.getId()+")\"><i class=\"fa fa-pencil font-14\"><!-- --></i></a>\n" +
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#removeStatutPayModal\" class=\"link-underlined btn btn-danger\" data-original-title=\"Supprimer\" onclick=\"removeStatutPay("+c.getId()+")\"><i class=\"fa fa-trash font-14\"><!-- --></i></a>\n" +
                    "</td>";
            c.setAction(act);
      /*      String checkboxe ="  <label class=\"ui-checkbox\">\n" +
                    "  <input type=\"checkbox\" id=\""+c.getId()+"\">\n" +
                    "  <span class=\"input-span\"></span>\n" +
                    "  </label>";*/
            String checkboxe ="<input name=\"select_id\" id=\"tabId\" value=\""+c.getId()+"\" type=\"checkbox\">";
            c.setCheckboxe(checkboxe);
            newComList.add(c);
        }



        return new ResponseData(true,newComList);
    }


    @RequestMapping(value = "/saveStatutPay", method = RequestMethod.POST)
    public ResponseData addStatutPay(Locale locale,@ModelAttribute StatutPay devis, BindingResult result,HttpServletRequest request){
        devis.setName(request.getParameter("name").toUpperCase());
        devis.setDescription(request.getParameter("description"));
        StatutPay c =  devisService.add(devis);
        return new ResponseData(true, c);
    }

    @RequestMapping(value = "/updateStatutPay/{id}", method = RequestMethod.POST)
    public ResponseData updateStatutPay(Locale locale,@ModelAttribute StatutPay devis,@PathVariable int id, BindingResult result,HttpServletRequest request){
        StatutPay ci = devisService.findById(id);
        ci.setName(request.getParameter("name").toUpperCase());
        ci.setDescription(request.getParameter("description"));
        StatutPay c =  devisService.add(ci);
        return new ResponseData(true, c);
    }

    @RequestMapping(value = "/findStatutPay/{id}", method = RequestMethod.GET)
    public ResponseData findStatutPay(Locale locale,@ModelAttribute StatutPay devis,@PathVariable int id, BindingResult result,HttpServletRequest request){
        StatutPay ci = devisService.findById(id);
       return new ResponseData(true, ci);
    }

    @RequestMapping(value = "/deleteStatutPay/{devisId}", method = RequestMethod.DELETE)
    public ResponseData deleteStatutPay(@PathVariable int devisId,HttpServletRequest request){
        ResponseData json=null;
        try {
            devisService.delete(devisId);
            json = new ResponseData(true, null);
        }catch (Exception ex){
            json = new ResponseData(false,"Impossible de supprimer cette donnée car elle est liée ailleurs",ex.getCause());
        }
        return json;
    }
}
