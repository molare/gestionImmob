package com.immo.controller;

import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.EnterpriseType;
import com.immo.service.EnterpriseTypeService;
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
public class EnterpriseTypeController {

    @Autowired
    private EnterpriseTypeService enterpriseTypeService;
    @RequestMapping(value = "/listEnterpriseType", method = RequestMethod.GET)
    public ResponseData getAllEnterpriseType(){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy : HH:mm");
        List<EnterpriseType> newComList = new ArrayList<EnterpriseType>();
        List<EnterpriseType> listCom = enterpriseTypeService.getAll();
        for(EnterpriseType co : listCom){
            EnterpriseType c = new EnterpriseType();
            c.setId(co.getId());
            c.setName(co.getName());
            c.setDescription(co.getDescription());
            c.setCreatedDate(co.getCreatedDate());
            c.setDateTransient(df.format(co.getCreatedDate()));
            String act="<td>\n" +
                    //"<button  class=\"btn btn-success btn-xs m-r-5\"  data-toggle=\"modal\" data-target=\"#editEnterpriseTypeModal\" onclick=\"editEnterpriseType("+c.getId()+") data-original-title=\"Edit\"><i class=\"fa fa-pencil font-14\"></i></button>\n"+
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#editEnterpriseTypeModal\" class=\"link-underlined margin-right-50 btn btn-success\" data-original-title=\"Editer\" onclick=\"editEnterpriseType("+c.getId()+")\"><i class=\"fa fa-pencil font-14\"><!-- --></i></a>\n" +
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#removeEnterpriseTypeModal\" class=\"link-underlined btn btn-danger\" data-original-title=\"Supprimer\" onclick=\"removeEnterpriseType("+c.getId()+")\"><i class=\"fa fa-trash font-14\"><!-- --></i></a>\n" +
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


    @RequestMapping(value = "/saveEnterpriseType", method = RequestMethod.POST)
    public ResponseData addEnterpriseType(Locale locale,@ModelAttribute EnterpriseType devis, BindingResult result,HttpServletRequest request){
        devis.setName(request.getParameter("name").toUpperCase());
        devis.setDescription(request.getParameter("description"));
        EnterpriseType c =  enterpriseTypeService.add(devis);
        return new ResponseData(true, c);
    }

    @RequestMapping(value = "/updateEnterpriseType/{id}", method = RequestMethod.POST)
    public ResponseData updateEnterpriseType(Locale locale,@ModelAttribute EnterpriseType devis,@PathVariable int id, BindingResult result,HttpServletRequest request){
        EnterpriseType ci = enterpriseTypeService.findById(id);
        ci.setName(request.getParameter("name").toUpperCase());
        ci.setDescription(request.getParameter("description"));
        EnterpriseType c =  enterpriseTypeService.add(ci);
        return new ResponseData(true, c);
    }

    @RequestMapping(value = "/findEnterpriseType/{id}", method = RequestMethod.GET)
    public ResponseData findEnterpriseType(Locale locale,@ModelAttribute EnterpriseType devis,@PathVariable int id, BindingResult result,HttpServletRequest request){
        EnterpriseType ci = enterpriseTypeService.findById(id);
       return new ResponseData(true, ci);
    }

    @RequestMapping(value = "/deleteEnterpriseType/{devisId}", method = RequestMethod.DELETE)
    public ResponseData deleteEnterpriseType(@PathVariable int devisId,HttpServletRequest request){
        ResponseData json=null;
        try {
            enterpriseTypeService.delete(devisId);
            json = new ResponseData(true, null);
        }catch (Exception ex){
            json = new ResponseData(false,"Impossible de supprimer cette donnée car elle est liée ailleurs",ex.getCause());
        }
        return json;
    }
}
