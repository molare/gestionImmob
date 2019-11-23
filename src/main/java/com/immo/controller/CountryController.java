package com.immo.controller;

import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.Country;
import com.immo.service.CountryService;
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
public class CountryController {

    @Autowired
    private CountryService countryService;
    @RequestMapping(value = "/listCountry", method = RequestMethod.GET)
    public ResponseData getAllCountry(){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy : HH:mm");
        List<Country> newComList = new ArrayList<Country>();
        List<Country> listCom = countryService.getAll();
        for(Country co : listCom){
            Country c = new Country();
            c.setId(co.getId());
            c.setName(co.getName());
            c.setDescription(co.getDescription());
            c.setCreatedDate(co.getCreatedDate());
            c.setDateTransient(df.format(co.getCreatedDate()));
            String act="<td>\n" +
                    //"<button  class=\"btn btn-success btn-xs m-r-5\"  data-toggle=\"modal\" data-target=\"#editCountryModal\" onclick=\"editCountry("+c.getId()+") data-original-title=\"Edit\"><i class=\"fa fa-pencil font-14\"></i></button>\n"+
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#editCountryModal\" class=\"link-underlined margin-right-50 btn btn-success\" data-original-title=\"Editer\" onclick=\"editCountry("+c.getId()+")\"><i class=\"fa fa-pencil font-14\"><!-- --></i></a>\n" +
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#removeCountryModal\" class=\"link-underlined btn btn-danger\" data-original-title=\"Supprimer\" onclick=\"removeCountry("+c.getId()+")\"><i class=\"fa fa-trash font-14\"><!-- --></i></a>\n" +
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


    @RequestMapping(value = "/saveCountry", method = RequestMethod.POST)
    public ResponseData addCountry(Locale locale,@ModelAttribute Country country, BindingResult result,HttpServletRequest request){
        country.setName(request.getParameter("name").toUpperCase());
        country.setDescription(request.getParameter("description"));
        Country c =  countryService.add(country);
        return new ResponseData(true, c);
    }

    @RequestMapping(value = "/updateCountry/{id}", method = RequestMethod.POST)
    public ResponseData updateCountry(Locale locale,@ModelAttribute Country country,@PathVariable int id, BindingResult result,HttpServletRequest request){
        Country ci = countryService.findById(id);
        ci.setName(request.getParameter("name").toUpperCase());
        ci.setDescription(request.getParameter("description"));
        Country c =  countryService.add(ci);
        return new ResponseData(true, c);
    }

    @RequestMapping(value = "/findCountry/{id}", method = RequestMethod.GET)
    public ResponseData findCountry(Locale locale,@ModelAttribute Country country,@PathVariable int id, BindingResult result,HttpServletRequest request){
        Country ci = countryService.findById(id);
       return new ResponseData(true, ci);
    }

    @RequestMapping(value = "/deleteCountry/{countryId}", method = RequestMethod.DELETE)
    public ResponseData deleteCountry(@PathVariable int countryId,HttpServletRequest request){
        ResponseData json=null;
        try {
            countryService.delete(countryId);
            json = new ResponseData(true, null);
        }catch (Exception ex){
            json = new ResponseData(false,"Impossible de supprimer cette donnée car elle est liée ailleurs",ex.getCause());
        }
        return json;
    }
}
