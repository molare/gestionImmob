package com.immo.controller;

import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.City;
import com.immo.entities.Commune;
import com.immo.service.CityService;
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
public class CityController {
    @Autowired
    private CityService cityService;

    @Autowired
    private CommuneService communeService;

    @RequestMapping(value = "/listCity", method = RequestMethod.GET)
    public ResponseData getAllCity() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy : HH:mm");
        List<City> newComList = new ArrayList<City>();
        List<City> listCom = cityService.getAll();
        for (City co : listCom) {
            City c = new City();
            c.setId(co.getId());
            c.setName(co.getName());
            c.setDescription(co.getDescription());
            c.setCreatedDate(co.getCreatedDate());
            c.setDateTransient(df.format(co.getCreatedDate()));
            c.setCommuneTransient(co.getCommune().getName());
            String act = "<td>\n" +
                    //"<button  class=\"btn btn-success btn-xs m-r-5\"  data-toggle=\"modal\" data-target=\"#editCommuneModal\" onclick=\"editCommune("+c.getId()+") data-original-title=\"Edit\"><i class=\"fa fa-pencil font-14\"></i></button>\n"+
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#editCityModal\" class=\"link-underlined margin-right-50 btn btn-success\" data-original-title=\"Editer\" onclick=\"editCity(" + c.getId() + ")\"><i class=\"fa fa-pencil font-14\"><!-- --></i></a>\n" +
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#removeCityModal\" class=\"link-underlined btn btn-danger\" data-original-title=\"Supprimer\" onclick=\"removeCity(" + c.getId() + ")\"><i class=\"fa fa-trash font-14\"><!-- --></i></a>\n" +
                    "</td>";
            c.setAction(act);
            String checkboxes ="<input name=\"select_id\" id=\"tabId\" value=\""+c.getId()+"\" type=\"checkbox\">";
            c.setCheckboxe(checkboxes);
            newComList.add(c);
        }
        return new ResponseData(true,newComList);
    }

    @RequestMapping(value = "/saveCity", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    public ResponseData addCity(Locale locale,@ModelAttribute City city, BindingResult result,HttpServletRequest request){
        city.setName(request.getParameter("name").toUpperCase());
        city.setDescription(request.getParameter("description"));
        city.setCommune(communeService.findById(Integer.parseInt(request.getParameter("commune"))));
        City c =  cityService.add(city);
        return new ResponseData(true,c);
    }

    @RequestMapping(value = "/updateCity/{idCity}", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
       public ResponseData updateCity(Locale locale,@ModelAttribute City city,@PathVariable int idCity, BindingResult result,HttpServletRequest request){
        City ci = cityService.findById(idCity);
        ci.setName(request.getParameter("name").toUpperCase());
        ci.setDescription(request.getParameter("description"));
        ci.setCommune(communeService.findById(Integer.parseInt(request.getParameter("commune"))));
        City c =  cityService.update(ci);
        return new ResponseData(true,c);
    }

    @RequestMapping(value = "/findCity/{idCity}", method = RequestMethod.GET)
    public ResponseData findCity(@PathVariable int idCity,HttpServletRequest request){
        City ci = cityService.findById(idCity);
        return new ResponseData(true,ci);
    }

    @RequestMapping(value = "/deleteCity/{id}", method = RequestMethod.DELETE)
    public ResponseData deleteCity(@PathVariable int id,HttpServletRequest request){
        ResponseData json=null;
        try {
            cityService.delete(id);
            json = new ResponseData(true, null);
        }catch (Exception ex){
            json = new ResponseData(false,"Impossible de supprimer cette donnée car elle est liée ailleurs",ex.getCause());
        }
        return json;
    }
}
