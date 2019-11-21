package com.immo.controller;

import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.Commune;
import com.immo.service.CommuneService;
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
public class CommuneController {

    @Autowired
    private CommuneService communeService;
    @RequestMapping(value = "/listCommune", method = RequestMethod.GET)
    public ResponseData getAllCommune(){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy : HH:mm");
        List<Commune> newComList = new ArrayList<Commune>();
        List<Commune> listCom = communeService.getAll();
        for(Commune co : listCom){
            Commune c = new Commune();
            c.setId(co.getId());
            c.setName(co.getName());
            c.setDescription(co.getDescription());
            c.setCreatedDate(co.getCreatedDate());
            c.setDateTransient(df.format(co.getCreatedDate()));
            String act="<td>\n" +
                    //"<button  class=\"btn btn-success btn-xs m-r-5\"  data-toggle=\"modal\" data-target=\"#editCommuneModal\" onclick=\"editCommune("+c.getId()+") data-original-title=\"Edit\"><i class=\"fa fa-pencil font-14\"></i></button>\n"+
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#editCommuneModal\" class=\"link-underlined margin-right-50 btn btn-success\" data-original-title=\"Editer\" onclick=\"editCommune("+c.getId()+")\"><i class=\"fa fa-pencil font-14\"><!-- --></i></a>\n" +
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#removeCommuneModal\" class=\"link-underlined btn btn-danger\" data-original-title=\"Supprimer\" onclick=\"removeCommune("+c.getId()+")\"><i class=\"fa fa-trash font-14\"><!-- --></i></a>\n" +
                    "</td>";
            c.setAction(act);
            String checkboxes ="<input name=\"select_id\" id=\"tabId\" value=\""+c.getId()+"\" type=\"checkbox\">";
            c.setCheckboxe(checkboxes);
            newComList.add(c);
        }



        return new ResponseData(true,newComList);
    }


    @RequestMapping(value = "/saveCommune", method = RequestMethod.POST)
    public ResponseData addCommune(Locale locale,@ModelAttribute Commune commune, BindingResult result,HttpServletRequest request){
        commune.setName(request.getParameter("name").toUpperCase());
        commune.setDescription(request.getParameter("description"));
        Commune c =  communeService.add(commune);
        return new ResponseData(true, c);
    }

    @RequestMapping(value = "/updateCommune/{id}", method = RequestMethod.POST)
    public ResponseData updateCommune(Locale locale,@ModelAttribute Commune commune,@PathVariable int id, BindingResult result,HttpServletRequest request){
        Commune ci = communeService.findById(id);
        ci.setName(request.getParameter("name").toUpperCase());

        ci.setDescription(request.getParameter("description"));
        Commune c =  communeService.add(ci);
        return new ResponseData(true, c);
    }

    @RequestMapping(value = "/findCommune/{id}", method = RequestMethod.GET)
    public ResponseData findCommune(Locale locale,@ModelAttribute Commune commune,@PathVariable int id, BindingResult result,HttpServletRequest request){
        Commune ci = communeService.findById(id);
       return new ResponseData(true, ci);
    }

    @RequestMapping(value = "/deleteCommune/{communeId}", method = RequestMethod.DELETE)
    public ResponseData deleteCommune(@PathVariable int communeId,HttpServletRequest request){
        ResponseData json=null;
        try {
            communeService.delete(communeId);
            json = new ResponseData(true, null);
        }catch (Exception ex){
            json = new ResponseData(false,"Impossible de supprimer cette donnée car elle est liée ailleurs",ex.getCause());
        }
        return json;
    }
}
