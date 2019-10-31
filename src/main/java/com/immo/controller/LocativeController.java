package com.immo.controller;

import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.Locative;
import com.immo.service.BienService;
import com.immo.service.LocativeService;
import com.immo.service.TypeLocativeService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by olivier on 02/10/2019.
 */
@RestController
public class LocativeController {

    @Autowired
    private LocativeService locativeService;


    @Autowired
    private TypeLocativeService typeLocativeService;

    @Autowired
    private BienService bienService;


    @RequestMapping(value = "/listLocative", method = RequestMethod.GET)
    public ResponseData getAllLocative(){
        List<Locative> newComList = new ArrayList<Locative>();
        List<Locative> listCom = locativeService.getAll();
        for(Locative co : listCom){
            Locative c = new Locative();
            c.setId(co.getId());
            c.setDesignation(co.getDesignation());
            c.setAmount(co.getAmount());
            c.setTypeTransient(co.getTypeLocative().getName());
            c.setNberRoom(co.getNberRoom());
            c.setMeubleLocative(co.getMeubleLocative());
            c.setCharge(co.getCharge());
            c.setSuperficy(co.getSuperficy());
            c.setDate(co.getDate());
            c.setBienTransient(co.getBien().getDesignation());
          Locative locat =  locativeService.findByContrat(co.getId());
            if(locat != null){
                c.setStatutTransient("<td>\n" +
                        "<span class=\"badge badge-danger\"><h7>Occupée</h7></span>\n" +
                        "  </td>");
            }else {
                c.setStatutTransient("<td>\n" +
                        "<span class=\"badge badge-success\"><h7>Disponible</h7></span>\n" +
                        "  </td>");
            }
            String act="<td>\n" +
                    //"<button  class=\"btn btn-success btn-xs m-r-5\"  data-toggle=\"modal\" data-target=\"#editLocativeModal\" onclick=\"editLocative("+c.getId()+") data-original-title=\"Edit\"><i class=\"fa fa-pencil font-14\"></i></button>\n"+
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#editLocativeModal\" class=\"link-underlined margin-right-50 btn btn-success\" data-original-title=\"Editer\" onclick=\"editLocative("+c.getId()+")\"><i class=\"fa fa-pencil font-14\"><!-- --></i></a>\n" +
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#removeLocativeModal\" class=\"link-underlined btn btn-danger\" data-original-title=\"Supprimer\" onclick=\"removeLocative("+c.getId()+")\"><i class=\"fa fa-trash font-14\"><!-- --></i></a>\n" +
                    "</td>";
            c.setAction(act);
            String checkboxes ="<input name=\"select_id\" id=\"tabId\" value=\""+c.getId()+"\" type=\"checkbox\">";
            c.setCheckboxe(checkboxes);

            if(co.getImage() != null){
                byte[] encodeBase64 = Base64.encodeBase64(co.getImage());
                String base64Encoded = null;
                try {
                    base64Encoded = new String(encodeBase64, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //String img = "<img width=\"64\" height=\"64\" alt=\"img\" src=\"data:image/jpeg;base64,"+base64Encoded+"\"/>";
                String img = "<img style=\"width:60px\" alt=\"img\" src=\"data:image/jpeg;base64,"+base64Encoded+"\"/>";
                c.setImageTransient(img);
            }else{
                // String img = "<img class=\"user_picture_small\" style=\"width:200px\" alt=\"Image\" src=\"../assets/common/img/upload_img.png\">";
                c.setImageTransient(null);
            }
            newComList.add(c);
        }



        return new ResponseData(true,newComList);
    }


    @RequestMapping(value = "/saveLocative", method = RequestMethod.POST,headers="Accept=*/*")
    public ResponseData addLocative(Locale locale, @ModelAttribute Locative locative,BindingResult result,@RequestParam("picture")MultipartFile file,HttpServletRequest request)throws Exception{
        ResponseData json=null;

        try {
            if(file.getSize()>0){
                String fileName = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                locative.setImageName(fileName);
                locative.setImage(bytes);
            }
            //locative.setCode(request.getParameter("code"));
            locative.setDesignation(request.getParameter("designation"));
            locative.setMeubleLocative(request.getParameter("meubleLocative"));
            locative.setCharge(Double.parseDouble(request.getParameter("charge")));
            locative.setAmount(Double.parseDouble(request.getParameter("amount")));
            locative.setNberRoom(Integer.parseInt(request.getParameter("nberRoom")));
            locative.setTypeLocative(typeLocativeService.findById(Integer.parseInt(request.getParameter("typeLocative"))));
            locative.setBien(bienService.findById(Integer.parseInt(request.getParameter("bien"))));
            locative.setSuperficy(Double.parseDouble(request.getParameter("superficy")));
            locative.setCommentary(request.getParameter("commentary"));
            Locative c =  locativeService.add(locative);
            json = new ResponseData(true, c);

        }catch (Exception ex){
            System.out.println("error1 "+ex.getMessage());
            json = new ResponseData(false,"une valeur a été dupliquée ou erronée",ex.getCause());
        }
        return json;
    }


    @RequestMapping(value = "/updateLocative/{id}", method = RequestMethod.POST,headers="Accept=*/*")
    public ResponseData updateLocative(Locale locale,@ModelAttribute Locative locative,@PathVariable int id, BindingResult result,@RequestParam("editPicture")MultipartFile file,HttpServletRequest request){
        ResponseData json=null;
        try {
            if(file.getSize()>0 && !file.isEmpty()){
                String fileName = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                locative.setImageName(fileName);
                locative.setImage(bytes);
            }else{
                locative.setImage(locativeService.findById(id).getImage());
                locative.setImageName(locativeService.findById(id).getImageName());
            }

            locative.setDesignation(request.getParameter("designation"));
            locative.setMeubleLocative(request.getParameter("meubleLocative"));
            locative.setCharge(Double.parseDouble(request.getParameter("charge")));
            locative.setAmount(Double.parseDouble(request.getParameter("amount")));
            locative.setNberRoom(Integer.parseInt(request.getParameter("nberRoom")));
            locative.setTypeLocative(typeLocativeService.findById(Integer.parseInt(request.getParameter("typeLocative"))));
            locative.setBien(bienService.findById(Integer.parseInt(request.getParameter("bien"))));
            locative.setSuperficy(Double.parseDouble(request.getParameter("superficy")));
            locative.setCommentary(request.getParameter("commentary"));
            Locative c =  locativeService.update(locative);
            json = new ResponseData(true, c);
        }catch (Exception ex){

            json = new ResponseData(false,"une valeur a été dupliquée ou erroné",ex.getCause());
        }
        return json;
    }

    @RequestMapping(value = "/findLocative/{id}", method = RequestMethod.GET)
    public ResponseData findLocative(Locale locale,@ModelAttribute Locative locative,@PathVariable int id, BindingResult result,HttpServletRequest request){
        Locative ci = locativeService.findById(id);
        if(ci.getImage() != null){
            byte[] encodeBase64 = Base64.encodeBase64(ci.getImage());
            String base64Encoded = null;
            try {
                base64Encoded = new String(encodeBase64, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //String img = "<img width=\"64\" height=\"64\" alt=\"img\" src=\"data:image/jpeg;base64,"+base64Encoded+"\"/>";
            String img = "<img style=\"width:200px\" alt=\"img\" src=\"data:image/jpeg;base64,"+base64Encoded+"\"/>";
            ci.setImageTransient(img);
        }else{
            // String img = "<img class=\"user_picture_small\" style=\"width:200px\" alt=\"Image\" src=\"../assets/common/img/upload_img.png\">";
            ci.setImageTransient(null);
        }
       return new ResponseData(true, ci);
    }

    @RequestMapping(value = "/deleteLocative/{typeId}", method = RequestMethod.DELETE)
    public ResponseData deleteLocative(@PathVariable int typeId,HttpServletRequest request){
        ResponseData json=null;
        try {
            locativeService.delete(typeId);
            json = new ResponseData(true, null);
        }catch (Exception ex){
            json = new ResponseData(false,"Impossible de supprimer cette donnée car elle est liée ailleurs",ex.getCause());
        }
        return json;
    }

    @RequestMapping(value = "/getGaranty/{locativeId}", method = RequestMethod.POST)
    public ResponseData getGaranty(@PathVariable int locativeId,HttpServletRequest request){
        ResponseData json=null;
        try {
            double garantie=  locativeService.garanty(locativeId);
            json = new ResponseData(true, garantie);
        }catch (Exception ex){
            json = new ResponseData(false,"erreur",ex.getCause());
        }
        return json;
    }

}
