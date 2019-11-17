package com.immo.controller;

import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.TypeLocative;
import com.immo.service.TypeLocativeService;
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
public class TypeLocativeController {

    @Autowired
    private TypeLocativeService typeLocativeService;
    
    @RequestMapping(value = "/listTypeLocative", method = RequestMethod.GET)
    public ResponseData getAllTypelocative(){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        List<TypeLocative> newComList = new ArrayList<TypeLocative>();
        List<TypeLocative> listCom = typeLocativeService.getAll();
        for(TypeLocative co : listCom){
            TypeLocative c = new TypeLocative();
            c.setId(co.getId());
            c.setName(co.getName());
            c.setDescription(co.getDescription());
            c.setCreatedDate(co.getCreatedDate());
            c.setDateTransient(df.format(co.getCreatedDate()));

            String act="<td>\n" +
                    //"<button  class=\"btn btn-success btn-xs m-r-5\"  data-toggle=\"modal\" data-target=\"#editTypelocativeModal\" onclick=\"editTypelocative("+c.getId()+") data-original-title=\"Edit\"><i class=\"fa fa-pencil font-14\"></i></button>\n"+
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#editTypelocativeModal\" class=\"link-underlined margin-right-50 btn btn-success\" data-original-title=\"Editer\" onclick=\"editTypelocative("+c.getId()+")\"><i class=\"fa fa-pencil font-14\"><!-- --></i></a>\n" +
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#removeTypelocativeModal\" class=\"link-underlined btn btn-danger\" data-original-title=\"Supprimer\" onclick=\"removeTypelocative("+c.getId()+")\"><i class=\"fa fa-trash font-14\"><!-- --></i></a>\n" +
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


    @RequestMapping(value = "/saveTypeLocative", method = RequestMethod.POST)
    public ResponseData addTypelocative(Locale locale,@ModelAttribute TypeLocative devis, BindingResult result,HttpServletRequest request){
        devis.setName(request.getParameter("name").toUpperCase());
        devis.setDescription(request.getParameter("description"));
        TypeLocative c =  typeLocativeService.add(devis);
        return new ResponseData(true, c);
    }

    @RequestMapping(value = "/updateTypeLocative/{id}", method = RequestMethod.POST)
    public ResponseData updateTypelocative(Locale locale,@ModelAttribute TypeLocative devis,@PathVariable int id, BindingResult result,HttpServletRequest request){
        TypeLocative ci = typeLocativeService.findById(id);
        ci.setName(request.getParameter("name").toUpperCase());
        ci.setDescription(request.getParameter("description"));
        TypeLocative c =  typeLocativeService.add(ci);
        return new ResponseData(true, c);
    }

    @RequestMapping(value = "/findTypeLocative/{id}", method = RequestMethod.GET)
    public ResponseData findTypelocative(Locale locale,@ModelAttribute TypeLocative devis,@PathVariable int id, BindingResult result,HttpServletRequest request){
        TypeLocative ci = typeLocativeService.findById(id);
       return new ResponseData(true, ci);
    }

    @RequestMapping(value = "/deleteTypeLocative/{typeId}", method = RequestMethod.DELETE)
    public ResponseData deleteTypelocative(@PathVariable int typeId,HttpServletRequest request){
        ResponseData json=null;
        try {
            typeLocativeService.delete(typeId);
            json = new ResponseData(true, null);
        }catch (Exception ex){
            json = new ResponseData(false,"Impossible de supprimer cette donnée car elle est liée ailleurs",ex.getCause());
        }
        return json;

    }
}
