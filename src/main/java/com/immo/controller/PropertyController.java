package com.immo.controller;

import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.Property;
import com.immo.service.CivilityService;
import com.immo.service.PropertyService;
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
public class PropertyController {
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private TypePropertyService typePropertyService;

    @Autowired
    private CivilityService civilityService;

    @RequestMapping(value = "/listProperty", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public ResponseData getAllProperty(){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        List<Property> prds = new ArrayList<Property>();
        List<Property> listProperty = propertyService.getAll();
        for(Property p: listProperty){
            Property pro = new Property();
            pro.setId(p.getId());
            pro.setCode(p.getCode());
            pro.setDate(p.getDate());
            pro.setFirstName(p.getFirstName());
            pro.setLastName(p.getLastName());
            pro.setEmail(p.getEmail());
            pro.setPhone(p.getPhone());
            pro.setCountry(p.getCountry());
            pro.setDateTransient(sdf.format(p.getBirthDate()));
            pro.setNberPiece(p.getNberPiece());
            pro.setDomicil(p.getDomicil());
            pro.setNaturePiece(p.getNaturePiece());
            pro.setLieuNais(p.getLieuNais());
            pro.setTypeTransient(p.getTypeProperty().getName());
            pro.setCivilityTransient(p.getCivility().getName());
            String act="<td>\n" +
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#editPropertyModal\" class=\"link-underlined margin-right-50 btn btn-success\" onclick=\"editProperty("+pro.getId()+")\"><i class=\"fa fa-edit\"><!-- --></i></a>\n" +
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#removePropertyModal\" class=\"link-underlined btn btn-danger\" onclick=\"removeProperty("+pro.getId()+")\"><i class=\"fa fa-trash-o\"><!-- --></i></a>\n" +
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

    @RequestMapping(value = "/saveProperty", method = RequestMethod.POST,headers="Accept=*/*")
    public ResponseData addProperty(Locale locale, @ModelAttribute Property property,BindingResult result,@RequestParam("picture")MultipartFile file,HttpServletRequest request)throws Exception{
        ResponseData json=null;
        SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy");
        try {
            if(file.getSize()>0){
                String fileName = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                property.setImageName(fileName);
                property.setImage(bytes);
            }
            String code = request.getParameter("code");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String profession = request.getParameter("profession");
            String nationality = request.getParameter("nationality");
            String lieuNais = request.getParameter("lieuNais");
            String cpteContribu = request.getParameter("cpteContribu");
            String domicil = request.getParameter("domicil");
            String country = request.getParameter("country");
            String birthDate = request.getParameter("birthDate");
            String nberPiece = request.getParameter("nberPiece");
            String naturePiece = request.getParameter("naturePiece");
            String typeProperty = request.getParameter("typeProperty");
            String civility = request.getParameter("civility");
            property.setCode(code);
            property.setFirstName(firstName);
            property.setLastName(lastName);
            property.setEmail(email);
            property.setPhone(phone);
            property.setProfession(profession);
            property.setNationality(nationality);
            property.setLieuNais(lieuNais);
            property.setCpteContribu(cpteContribu);
            property.setBirthDate(sdf.parse(birthDate));
            property.setCountry(country);
            property.setDomicil(domicil);
            property.setNberPiece(nberPiece);
            property.setNaturePiece(naturePiece);
            property.setTypeProperty(typePropertyService.findById(Integer.parseInt(typeProperty)));
            property.setCivility(civilityService.findById(Integer.parseInt(civility)));
            Property p = propertyService.add(property);
            json = new ResponseData(true, p);

        }catch (Exception ex){
            json = new ResponseData(false,"une valeur a été dupliquée",ex.getCause());
        }
        return json;
    }

    @RequestMapping(value = "/updateProperty/{idProperty}", method = RequestMethod.POST,headers="Accept=*/*")
    public ResponseData updateProperty(Locale locale, @ModelAttribute Property property,BindingResult result, @PathVariable int idProperty,@RequestParam("editPicture")MultipartFile file,HttpServletRequest request)throws Exception{
        ResponseData json=null;
        SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy");
        property.setId(idProperty);
        try {
            if(file.getSize()>0 && !file.isEmpty()){
                String fileName = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                property.setImageName(fileName);
                property.setImage(bytes);
            }else{
                property.setImage(propertyService.findById(idProperty).getImage());
                property.setImageName(propertyService.findById(idProperty).getImageName());
            }
            String code = request.getParameter("code");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String profession = request.getParameter("profession");
            String nationality = request.getParameter("nationality");
            String lieuNais = request.getParameter("lieuNais");
            String cpteContribu = request.getParameter("cpteContribu");
            String domicil = request.getParameter("domicil");
            String country = request.getParameter("country");
            String birthDate = request.getParameter("birthDate");
            String nberPiece = request.getParameter("nberPiece");
            String naturePiece = request.getParameter("naturePiece");
            String typeProperty = request.getParameter("typeProperty");
            String civility = request.getParameter("civility");
            property.setCode(code);
            property.setFirstName(firstName);
            property.setLastName(lastName);
            property.setEmail(email);
            property.setPhone(phone);
            property.setProfession(profession);
            property.setNationality(nationality);
            property.setLieuNais(lieuNais);
            property.setCpteContribu(cpteContribu);
            property.setBirthDate(sdf.parse(birthDate));
            property.setCountry(country);
            property.setDomicil(domicil);
            property.setNberPiece(nberPiece);
            property.setNaturePiece(naturePiece);
            property.setTypeProperty(typePropertyService.findById(Integer.parseInt(typeProperty)));
            property.setCivility(civilityService.findById(Integer.parseInt(civility)));
            Property p = propertyService.update(property);
            json = new ResponseData(true, p);
        }catch (Exception ex){
            json = new ResponseData(false,"une valeur a été dupliquée",null);
        }
        return json;
    }

    @RequestMapping(value = "/property/{id}", method = RequestMethod.GET)
    public ResponseData getProperty(@PathVariable int id){
        Property p = propertyService.findById(id);
        SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy");
        p.setDateTransient(sdf.format(p.getBirthDate()));
        if(p.getCountry().equals("1")){
            p.setCountryTransient("Cote d'ivoire");
        }else if(p.getCountry().equals("2")){
            p.setCountryTransient("France");
        }else{
            p.setCountryTransient("Belgique");
        }

        if(p.getNaturePiece().equals("1")){
            p.setNatureTransient("CNI");
        }else if(p.getNaturePiece().equals("2")){
            p.setNatureTransient("Attestation");
        }else{
            p.setNatureTransient("Passeport");
        }


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
        Property p = propertyService.findById(id);
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

    @RequestMapping(value = "/deleteProperty/{id}", method = RequestMethod.DELETE)
    public ResponseData deleteProperty(@PathVariable int id){
        ResponseData json;
        try {
            propertyService.delete(id);
            json = new ResponseData(true, null);
        }catch (Exception ex){
            json = new ResponseData(false,"Impossible de supprimer car cette donnée est utilisée ailleurs",null);
        }
        return json;
    }
}
