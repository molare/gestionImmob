package com.immo.controller;

import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.Locater;
import com.immo.service.CivilityService;
import com.immo.service.LocaterService;
import com.immo.service.TypePropertyService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by olivier on 09/10/2019.
 */
@RestController
public class LocaterController {
    @Autowired
    private LocaterService locaterService;
  /*  @Autowired
    private TypePropertyService typePropertyService;*/

    @Autowired
    private CivilityService civilityService;

    @RequestMapping(value = "/listLocater", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public ResponseData getAllProperty(){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        List<Locater> prds = new ArrayList<Locater>();
        List<Locater> listProperty = locaterService.getAll();
        for(Locater p: listProperty){
            Locater pro = new Locater();
            pro.setId(p.getId());
            pro.setCode(p.getCode());
            pro.setDate(p.getDate());
            pro.setFirstName(p.getFirstName());
            pro.setLastName(p.getLastName());
            pro.setEmail(p.getEmail());
            pro.setPhone(p.getPhone());
            pro.setCivilityTransient(p.getCivility().getName());
            String act="<td>\n" +
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#editLocaterModal\" class=\"link-underlined margin-right-50 btn btn-success\" onclick=\"editLocater("+pro.getId()+")\"><i class=\"fa fa-edit\"><!-- --></i></a>\n" +
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#removeLocaterModal\" class=\"link-underlined btn btn-danger\" onclick=\"removeLocater("+pro.getId()+")\"><i class=\"fa fa-trash-o\"><!-- --></i></a>\n" +
                    "</td>";

            if(p.getImage() != null){
                byte[] encodeBase64 = Base64.encodeBase64(p.getImage());
                String base64Encoded = null;
                try {
                    base64Encoded = new String(encodeBase64, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //String img = "<img width=\"64\" height=\"64\" alt=\"img\" src=\"data:image/jpeg;base64,"+base64Encoded+"\"/>";
                String img = "<img style=\"width:60px\" alt=\"img\" src=\"data:image/jpeg;base64,"+base64Encoded+"\"/>";
                pro.setImageTransient(img);
            }else{
                // String img = "<img class=\"user_picture_small\" style=\"width:200px\" alt=\"Image\" src=\"../assets/common/img/upload_img.png\">";
                pro.setImageTransient(null);
            }

            pro.setAction(act);

            String checkboxes ="<input name=\"select_id\" id=\"tabId\" value=\""+pro.getId()+"\" type=\"checkbox\">";
            pro.setCheckboxe(checkboxes);
            prds.add(pro);
        }
        return new ResponseData(true, prds);
    }

    @RequestMapping(value = "/saveLocater", method = RequestMethod.POST,headers="Accept=*/*")
    public ResponseData addProperty(Locale locale, @ModelAttribute Locater locater,BindingResult result,@RequestParam("picture")MultipartFile file,HttpServletRequest request)throws Exception{
        ResponseData json=null;
        SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy");
        try {
            if(file.getSize()>0){
                String fileName = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                locater.setImageName(fileName);
                locater.setImage(bytes);
            }
            String code = request.getParameter("code");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String profession = request.getParameter("profession");
            String nationality = request.getParameter("nationality");
            String civility = request.getParameter("civility");
            locater.setCode(code);
            locater.setFirstName(firstName);
            locater.setLastName(lastName);
            if(email.isEmpty()){
                locater.setEmail(firstName+"."+lastName+"@gmail.com");
            }else {
                locater.setEmail(email);
            }

            locater.setPhone(phone);
            locater.setProfession(profession);
            locater.setNationality(nationality);
            locater.setCivility(civilityService.findById(Integer.parseInt(civility)));
            Locater p = locaterService.add(locater);
            json = new ResponseData(true, p);

        }catch (Exception ex){
            json = new ResponseData(false,"une valeur a été dupliquée",ex.getCause());
        }
        return json;
    }

    @RequestMapping(value = "/updateLocater/{idLocater}", method = RequestMethod.POST,headers="Accept=*/*")
    public ResponseData updateProperty(Locale locale, @ModelAttribute Locater locater,BindingResult result, @PathVariable int idLocater,@RequestParam("editPicture")MultipartFile file,HttpServletRequest request)throws Exception{
        ResponseData json=null;
        SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy");
        locater.setId(idLocater);
        try {
            if(file.getSize()>0 && !file.isEmpty()){
                String fileName = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                locater.setImageName(fileName);
                locater.setImage(bytes);
            }else{
                locater.setImage(locaterService.findById(idLocater).getImage());
                locater.setImageName(locaterService.findById(idLocater).getImageName());
            }
            String code = request.getParameter("code");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String profession = request.getParameter("profession");
            String nationality = request.getParameter("nationality");
            String civility = request.getParameter("civility");
            locater.setCode(code);
            locater.setFirstName(firstName);
            locater.setLastName(lastName);
            if(email.isEmpty()){
                locater.setEmail(firstName+"."+lastName+"@gmail.com");
            }else {
                locater.setEmail(email);
            }
            locater.setPhone(phone);
            locater.setProfession(profession);
            locater.setNationality(nationality);
            locater.setCivility(civilityService.findById(Integer.parseInt(civility)));
            Locater p = locaterService.update(locater);
            json = new ResponseData(true, p);
        }catch (Exception ex){
            json = new ResponseData(false,"une valeur a été dupliquée",null);
        }
        return json;
    }

    @RequestMapping(value = "/locater/{id}", method = RequestMethod.GET)
    public ResponseData getProperty(@PathVariable int id){
        Locater p = locaterService.findById(id);
        SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy");

        if(p.getImage() != null){
            byte[] encodeBase64 = Base64.encodeBase64(p.getImage());
            String base64Encoded = null;
            try {
                base64Encoded = new String(encodeBase64, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //String img = "<img width=\"64\" height=\"64\" alt=\"img\" src=\"data:image/jpeg;base64,"+base64Encoded+"\"/>";
            String img = "<img style=\"width:200px\" alt=\"img\" src=\"data:image/jpeg;base64,"+base64Encoded+"\"/>";
            p.setImageTransient(img);
        }else{
            // String img = "<img class=\"user_picture_small\" style=\"width:200px\" alt=\"Image\" src=\"../assets/common/img/upload_img.png\">";
            p.setImageTransient(null);
        }
        return new ResponseData(true, p);
    }

    //methode daffichage d'image pour la vente

/*    @RequestMapping(value = "/propertys/sales/{id}", method = RequestMethod.GET)
    public ResponseData getPropertyInSale(@PathVariable int id){
        Locater p = locaterService.findById(id);
        if(p.getImage() != null){
            byte[] encodeBase64 = Base64.encodeBase64(p.getImage());
            String base64Encoded = null;
            try {
                base64Encoded = new String(encodeBase64, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //String img = "<img width=\"64\" height=\"64\" alt=\"img\" src=\"data:image/jpeg;base64,"+base64Encoded+"\"/>";
            String img = "<img style=\"width:100px\" alt=\"img\" src=\"data:image/jpeg;base64,"+base64Encoded+"\"/>";
            p.setImageTransient(img);
        }else{
            // String img = "<img class=\"user_picture_small\" style=\"width:200px\" alt=\"Image\" src=\"../assets/common/img/upload_img.png\">";
            p.setImageTransient(null);
        }
        return new ResponseData(true, p);
    }*/

    @RequestMapping(value = "/deleteLocater/{id}", method = RequestMethod.DELETE)
    public ResponseData deleteProperty(@PathVariable int id){
        ResponseData json;
        try {
            locaterService.delete(id);
            json = new ResponseData(true, null);
        }catch (Exception ex){
            json = new ResponseData(false,"Impossible de supprimer car cette donnée est utilisée ailleurs",null);
        }
        return json;
    }
}
