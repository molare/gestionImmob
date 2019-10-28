package com.immo.controller;

import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.TypeBien;
import com.immo.service.TypeBienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by olivier on 02/10/2019.
 */
@RestController
public class TypeBienController {

    @Autowired
    private TypeBienService typeBienService;
    
    @RequestMapping(value = "/listTypeBien", method = RequestMethod.GET)
    public ResponseData getAllTypebien(){
        List<TypeBien> newComList = new ArrayList<TypeBien>();
        List<TypeBien> listCom = typeBienService.getAll();
        for(TypeBien co : listCom){
            TypeBien c = new TypeBien();
            c.setId(co.getId());
            c.setName(co.getName());
            c.setDescription(co.getDescription());
            c.setCreatedDate(co.getCreatedDate());
            String act="<td>\n" +
                    //"<button  class=\"btn btn-success btn-xs m-r-5\"  data-toggle=\"modal\" data-target=\"#editTypebienModal\" onclick=\"editTypebien("+c.getId()+") data-original-title=\"Edit\"><i class=\"fa fa-pencil font-14\"></i></button>\n"+
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#editTypebienModal\" class=\"link-underlined margin-right-50 btn btn-success\" data-original-title=\"Editer\" onclick=\"editTypebien("+c.getId()+")\"><i class=\"fa fa-pencil font-14\"><!-- --></i></a>\n" +
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#removeTypebienModal\" class=\"link-underlined btn btn-danger\" data-original-title=\"Supprimer\" onclick=\"removeTypebien("+c.getId()+")\"><i class=\"fa fa-trash font-14\"><!-- --></i></a>\n" +
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


    @RequestMapping(value = "/saveTypebien", method = RequestMethod.POST)
    public ResponseData addTypebien(Locale locale,@ModelAttribute TypeBien devis, BindingResult result,HttpServletRequest request){
        devis.setName(request.getParameter("name").toUpperCase());
        devis.setDescription(request.getParameter("description"));
        TypeBien c =  typeBienService.add(devis);
        return new ResponseData(true, c);
    }

    @RequestMapping(value = "/updateTypebien/{id}", method = RequestMethod.POST)
    public ResponseData updateTypebien(Locale locale,@ModelAttribute TypeBien devis,@PathVariable int id, BindingResult result,HttpServletRequest request){
        TypeBien ci = typeBienService.findById(id);
        ci.setName(request.getParameter("name").toUpperCase());
        ci.setDescription(request.getParameter("description"));
        TypeBien c =  typeBienService.add(ci);
        return new ResponseData(true, c);
    }

    @RequestMapping(value = "/findTypebien/{id}", method = RequestMethod.GET)
    public ResponseData findTypebien(Locale locale,@ModelAttribute TypeBien devis,@PathVariable int id, BindingResult result,HttpServletRequest request){
        TypeBien ci = typeBienService.findById(id);
       return new ResponseData(true, ci);
    }

    @RequestMapping(value = "/deleteTypebien/{typeId}", method = RequestMethod.DELETE)
    public ResponseData deleteTypebien(@PathVariable int typeId,HttpServletRequest request){
        ResponseData json=null;
        try {
            typeBienService.delete(typeId);
            json = new ResponseData(true, null);
        }catch (Exception ex){
            json = new ResponseData(false,"Impossible de supprimer cette donnée car elle est liée ailleurs",ex.getCause());
        }
        return json;
    }
}
