package com.immo.controller;

import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.TypeProperty;
import com.immo.service.TypePropertyService;
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
public class TypePropertyController {

    @Autowired
    private TypePropertyService typePropertyService;
    @RequestMapping(value = "/listTypeProperty", method = RequestMethod.GET)
    public ResponseData getAllTypeProperty(){
        List<TypeProperty> newComList = new ArrayList<TypeProperty>();
        List<TypeProperty> listCom = typePropertyService.getAll();
        for(TypeProperty co : listCom){
            TypeProperty c = new TypeProperty();
            c.setId(co.getId());
            c.setName(co.getName());
            c.setDescription(co.getDescription());
            c.setCreatedDate(co.getCreatedDate());
            String act="<td>\n" +
                    //"<button  class=\"btn btn-success btn-xs m-r-5\"  data-toggle=\"modal\" data-target=\"#editTypepropertyModal\" onclick=\"editTypeproperty("+c.getId()+") data-original-title=\"Edit\"><i class=\"fa fa-pencil font-14\"></i></button>\n"+
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#editTypePropertyModal\" class=\"link-underlined margin-right-50 btn btn-success\" data-original-title=\"Editer\" onclick=\"editTypeProperty("+c.getId()+")\"><i class=\"fa fa-pencil font-14\"><!-- --></i></a>\n" +
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#removeTypePropertyModal\" class=\"link-underlined btn btn-danger\" data-original-title=\"Supprimer\" onclick=\"removeTypeProperty("+c.getId()+")\"><i class=\"fa fa-trash font-14\"><!-- --></i></a>\n" +
                    "</td>";
            c.setAction(act);
            String checkboxes ="<input name=\"select_id\" id=\"tabId\" value=\""+c.getId()+"\" type=\"checkbox\">";
            c.setCheckboxe(checkboxes);
            newComList.add(c);
        }



        return new ResponseData(true,newComList);
    }


    @RequestMapping(value = "/saveTypeProperty", method = RequestMethod.POST)
    public ResponseData addTypeproperty(Locale locale,@ModelAttribute TypeProperty typeProperty, BindingResult result,HttpServletRequest request){
        typeProperty.setName(request.getParameter("name"));
        typeProperty.setDescription(request.getParameter("description"));
        TypeProperty c =  typePropertyService.add(typeProperty);
        return new ResponseData(true, c);
    }

    @RequestMapping(value = "/updateTypeProperty/{id}", method = RequestMethod.POST)
    public ResponseData updateTypeproperty(Locale locale,@ModelAttribute TypeProperty typePropertyId,@PathVariable int id, BindingResult result,HttpServletRequest request){
        TypeProperty ci = typePropertyService.findById(id);
        ci.setName(request.getParameter("name"));
        ci.setDescription(request.getParameter("description"));
        TypeProperty c =  typePropertyService.add(ci);
        return new ResponseData(true, c);
    }

    @RequestMapping(value = "/findTypeProperty/{idType}", method = RequestMethod.GET)
    public ResponseData findTypeproperty(Locale locale,@ModelAttribute TypeProperty typePropertyId,@PathVariable int idType, BindingResult result,HttpServletRequest request){
        TypeProperty ci = typePropertyService.findById(idType);
       return new ResponseData(true, ci);
    }

    @RequestMapping(value = "/deleteTypeProperty/{typePropertyId}", method = RequestMethod.DELETE)
    public ResponseData deleteTypeproperty(@PathVariable int typePropertyId,HttpServletRequest request){
        typePropertyService.delete(typePropertyId);
        return new ResponseData(true, null);
    }
}
