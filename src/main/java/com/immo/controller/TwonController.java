package com.immo.controller;

import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.Twon;
import com.immo.service.CountryService;
import com.immo.service.TwonService;
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
public class TwonController {

    @Autowired
    private TwonService twonService;

    @Autowired
    private CountryService countryService;

    @RequestMapping(value = "/listTwon", method = RequestMethod.GET)
    public ResponseData getAllTwon(){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy : HH:mm");
        List<Twon> newComList = new ArrayList<Twon>();
        List<Twon> listCom = twonService.getAll();
        for(Twon co : listCom){
            Twon c = new Twon();
            c.setId(co.getId());
            c.setName(co.getName());
            c.setDescription(co.getDescription());
            c.setCreatedDate(co.getCreatedDate());
            c.setDateTransient(df.format(co.getCreatedDate()));
            c.setCountryTransient(co.getCountry().getName());
            String act="<td>\n" +
                    //"<button  class=\"btn btn-success btn-xs m-r-5\"  data-toggle=\"modal\" data-target=\"#editTwonModal\" onclick=\"editTwon("+c.getId()+") data-original-title=\"Edit\"><i class=\"fa fa-pencil font-14\"></i></button>\n"+
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#editTwonModal\" class=\"link-underlined margin-right-50 btn btn-success\" data-original-title=\"Editer\" onclick=\"editTwon("+c.getId()+")\"><i class=\"fa fa-pencil font-14\"><!-- --></i></a>\n" +
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#removeTwonModal\" class=\"link-underlined btn btn-danger\" data-original-title=\"Supprimer\" onclick=\"removeTwon("+c.getId()+")\"><i class=\"fa fa-trash font-14\"><!-- --></i></a>\n" +
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


    @RequestMapping(value = "/saveTwon", method = RequestMethod.POST)
    public ResponseData addTwon(Locale locale,@ModelAttribute Twon twon, BindingResult result,HttpServletRequest request){
        twon.setName(request.getParameter("name").toUpperCase());
        twon.setDescription(request.getParameter("description"));
        String country =request.getParameter("country");
        twon.setCountry(countryService.findById(Integer.parseInt(country)));
        Twon c =  twonService.add(twon);
        return new ResponseData(true, c);
    }

    @RequestMapping(value = "/updateTwon/{id}", method = RequestMethod.POST)
    public ResponseData updateTwon(Locale locale,@ModelAttribute Twon twon,@PathVariable int id, BindingResult result,HttpServletRequest request){
        Twon ci = twonService.findById(id);
        ci.setName(request.getParameter("name").toUpperCase());
        ci.setDescription(request.getParameter("description"));
        String country =request.getParameter("country");
        ci.setCountry(countryService.findById(Integer.parseInt(country)));
        Twon c =  twonService.add(ci);
        return new ResponseData(true, c);
    }

    @RequestMapping(value = "/findTwon/{id}", method = RequestMethod.GET)
    public ResponseData findTwon(Locale locale,@ModelAttribute Twon twon,@PathVariable int id, BindingResult result,HttpServletRequest request){
        Twon ci = twonService.findById(id);
        ci.setCountryTransient(ci.getCountry().getName());
       return new ResponseData(true, ci);
    }

    @RequestMapping(value = "/deleteTwon/{twonId}", method = RequestMethod.DELETE)
    public ResponseData deleteTwon(@PathVariable int twonId,HttpServletRequest request){
        ResponseData json=null;
        try {
            twonService.delete(twonId);
            json = new ResponseData(true, null);
        }catch (Exception ex){
            json = new ResponseData(false,"Impossible de supprimer cette donnée car elle est liée ailleurs",ex.getCause());
        }
        return json;
    }
}
