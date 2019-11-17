package com.immo.controller;

import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.TypePay;
import com.immo.service.TypePayService;
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
public class TypePayController {

    @Autowired
    private TypePayService devisService;
    @RequestMapping(value = "/listTypePay", method = RequestMethod.GET)
    public ResponseData getAllTypePay(){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        List<TypePay> newComList = new ArrayList<TypePay>();
        List<TypePay> listCom = devisService.getAll();
        for(TypePay co : listCom){
            TypePay c = new TypePay();
            c.setId(co.getId());
            c.setName(co.getName());
            c.setDescription(co.getDescription());
            c.setCreatedDate(co.getCreatedDate());
            c.setDateTransient(df.format(co.getCreatedDate()));
            String act="<td>\n" +
                    //"<button  class=\"btn btn-success btn-xs m-r-5\"  data-toggle=\"modal\" data-target=\"#editTypePayModal\" onclick=\"editTypePay("+c.getId()+") data-original-title=\"Edit\"><i class=\"fa fa-pencil font-14\"></i></button>\n"+
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#editTypePayModal\" class=\"link-underlined margin-right-50 btn btn-success\" data-original-title=\"Editer\" onclick=\"editTypePay("+c.getId()+")\"><i class=\"fa fa-pencil font-14\"><!-- --></i></a>\n" +
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#removeTypePayModal\" class=\"link-underlined btn btn-danger\" data-original-title=\"Supprimer\" onclick=\"removeTypePay("+c.getId()+")\"><i class=\"fa fa-trash font-14\"><!-- --></i></a>\n" +
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


    @RequestMapping(value = "/saveTypePay", method = RequestMethod.POST)
    public ResponseData addTypePay(Locale locale,@ModelAttribute TypePay devis, BindingResult result,HttpServletRequest request){
        devis.setName(request.getParameter("name").toUpperCase());
        devis.setDescription(request.getParameter("description"));
        TypePay c =  devisService.add(devis);
        return new ResponseData(true, c);
    }

    @RequestMapping(value = "/updateTypePay/{id}", method = RequestMethod.POST)
    public ResponseData updateTypePay(Locale locale,@ModelAttribute TypePay devis,@PathVariable int id, BindingResult result,HttpServletRequest request){
        TypePay ci = devisService.findById(id);
        ci.setName(request.getParameter("name").toUpperCase());
        ci.setDescription(request.getParameter("description"));
        TypePay c =  devisService.add(ci);
        return new ResponseData(true, c);
    }

    @RequestMapping(value = "/findTypePay/{id}", method = RequestMethod.GET)
    public ResponseData findTypePay(Locale locale,@ModelAttribute TypePay devis,@PathVariable int id, BindingResult result,HttpServletRequest request){
        TypePay ci = devisService.findById(id);
       return new ResponseData(true, ci);
    }

    @RequestMapping(value = "/deleteTypePay/{devisId}", method = RequestMethod.DELETE)
    public ResponseData deleteTypePay(@PathVariable int devisId,HttpServletRequest request){
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
