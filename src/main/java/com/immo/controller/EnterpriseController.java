package com.immo.controller;

import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.Enterprise;
import com.immo.service.CivilityService;
import com.immo.service.CountryService;
import com.immo.service.EnterpriseService;
import com.immo.service.EnterpriseTypeService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by olivier on 09/10/2019.
 */
@RestController
public class EnterpriseController {
    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private EnterpriseTypeService enterpriseTypeService;

    //Save the uploaded file to this folder
   // private static String UPLOADED_FOLDER ="C:/Users/olivier/IdeaProjects/gestionImmob/src/main/resources/static/";

    @RequestMapping(value = "/listEnterprise", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public ResponseData getAllProperty(){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy : HH:mm");
        List<Enterprise> prds = new ArrayList<Enterprise>();
        List<Enterprise> listProperty = enterpriseService.getAll();
        for(Enterprise p: listProperty){
            Enterprise pro = new Enterprise();
            pro.setId(p.getId());
            pro.setEnterpriseTypeTransient(p.getEnterpriseType().getName());
            pro.setCreatedDate(p.getCreatedDate());
            pro.setCapital(p.getCapital());
            pro.setName(p.getName());
            pro.setEmail(p.getEmail());
            pro.setPhone(p.getPhone());
            pro.setFixePhone(p.getFixePhone());
            pro.setDateTransient(df.format(p.getCreatedDate()));
            pro.setCountryTransient(p.getCountry().getName());
            String act="<td>\n" +
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#editEnterpriseModal\" class=\"link-underlined margin-right-50 btn btn-success\" onclick=\"editEnterprise("+pro.getId()+")\"><i class=\"fa fa-edit\"><!-- --></i></a>\n" +
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#removeEnterpriseModal\" class=\"link-underlined btn btn-danger\" onclick=\"removeEnterprise("+pro.getId()+")\"><i class=\"fa fa-trash-o\"><!-- --></i></a>\n" +
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

                String checkboxes="<a href=\"javascript: void(0);\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#Modal-lightbox"+pro.getId()+"\"><i class=\"fa fa-eye\"></i></a>\n" +
                        "<!-- Modal -->\n" +
                        "  <div class=\"modal fade\" id=\"Modal-lightbox"+pro.getId()+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">\n" +
                        " <div class=\"modal-dialog\" role=\"document\">\n" +
                        " <div class=\"modal-content\">\n" +
                        "  <div class=\"modal-body\">\n" +
                        "  <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">\n" +
                        " <span aria-hidden=\"true\">&times;</span>\n" +
                        "  </button>\n" +
                        //" class="img img-fluid" <img src=\"..\\files\\assets\\images\\modal\\overflow.jpg\" alt=\"\" class=\"img img-fluid\">\n" +
                        "<img style=\"width:900px\" alt=\"img\" class=\"img img-fluid\" src=\"data:image/jpeg;base64,"+base64Encoded+"\"/>\n"+
                        "  </div>\n" +
                        "  </div>\n" +
                        " </div>\n" +
                        " </div>";
                pro.setCheckboxe(checkboxes);
            }else{
                // String img = "<img class=\"user_picture_small\" style=\"width:200px\" alt=\"Image\" src=\"../assets/common/img/upload_img.png\">";
                pro.setImageTransient(null);
            }

            pro.setAction(act);

           // String checkboxes ="<input name=\"select_id\" id=\"tabId\" value=\""+pro.getId()+"\" type=\"checkbox\">";

            prds.add(pro);
        }
        return new ResponseData(true, prds);
    }

    @RequestMapping(value = "/saveEnterprise", method = RequestMethod.POST,headers="Accept=*/*",produces="application/json;charset=UTF-8")
    public ResponseData addProperty(Locale locale, @ModelAttribute Enterprise enterprise,BindingResult result,@RequestParam("picture")MultipartFile file,HttpServletRequest request)throws Exception{
        ResponseData json=null;
        SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy");

        try {
            if(file.getSize()>0){
                String fileName = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                enterprise.setImageName(fileName);
                enterprise.setImage(bytes);

                //envoie d un fichier vers un dossier specifique
               /* Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);*/
            }
            String name = request.getParameter("name");
            String capital = request.getParameter("capital");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String fixePhone = request.getParameter("fixePhone");
            String country = request.getParameter("country");
            String enterpriseType = request.getParameter("enterpriseType");
            String saleRegistre = request.getParameter("saleRegistre");

            enterprise.setName(name.toUpperCase());
            enterprise.setCapital(Double.parseDouble(capital));
            enterprise.setPhone(phone);
            enterprise.setFixePhone(fixePhone);
            enterprise.setSaleRegistre(saleRegistre);
            enterprise.setCountry(countryService.findById(Integer.parseInt(country)));
            enterprise.setEnterpriseType(enterpriseTypeService.findById(Integer.parseInt(enterpriseType)));

            if(email.isEmpty()){
                enterprise.setEmail(name+"@gmail.com");
            }else {
                enterprise.setEmail(email);
            }

            Enterprise p = enterpriseService.add(enterprise);
            json = new ResponseData(true, p);

        }catch (Exception ex){
            json = new ResponseData(false,"une valeur a &eacute;t&eacute; dupliqu&eacute;e ou erron&eacute;e",ex.getCause());
        }
        return json;
    }

    @RequestMapping(value = "/updateEnterprise/{idEnterprise}", method = RequestMethod.POST,headers="Accept=*/*",produces="application/json;charset=UTF-8")
    public ResponseData updateProperty(Locale locale, @ModelAttribute Enterprise enterprise,BindingResult result, @PathVariable int idEnterprise,@RequestParam("editPicture")MultipartFile file,HttpServletRequest request)throws Exception{
        ResponseData json=null;
        SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy");
        enterprise.setId(idEnterprise);
        try {
            if(file.getSize()>0 && !file.isEmpty()){
                String fileName = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                enterprise.setImageName(fileName);
                enterprise.setImage(bytes);
            }else{
                enterprise.setImage(enterpriseService.findById(idEnterprise).getImage());
                enterprise.setImageName(enterpriseService.findById(idEnterprise).getImageName());
            }
            String name = request.getParameter("name");
            String capital = request.getParameter("capital");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String fixePhone = request.getParameter("fixePhone");
            String country = request.getParameter("country");
            String enterpriseType = request.getParameter("enterpriseType");
            String saleRegistre = request.getParameter("saleRegistre");

            enterprise.setName(name.toUpperCase());
            enterprise.setCapital(Double.parseDouble(capital));
            enterprise.setPhone(phone);
            enterprise.setFixePhone(fixePhone);
            enterprise.setSaleRegistre(saleRegistre);
            enterprise.setCountry(countryService.findById(Integer.parseInt(country)));
            enterprise.setEnterpriseType(enterpriseTypeService.findById(Integer.parseInt(enterpriseType)));

            if(email.isEmpty()){
                enterprise.setEmail(name+"@gmail.com");
            }else {
                enterprise.setEmail(email);
            }

            Enterprise p = enterpriseService.update(enterprise);
            json = new ResponseData(true, p);
        }catch (Exception ex){
            json = new ResponseData(false,"une valeur a &eacute;t&eacute; dupliqu&eacute;e ou erron&eacute;e",ex.getCause());
        }
        return json;
    }

    @RequestMapping(value = "/findEnterprise/{id}", method = RequestMethod.GET)
    public ResponseData getProperty(@PathVariable int id){
        Enterprise p = enterpriseService.findById(id);
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


    @RequestMapping(value = "/deleteEnterprise/{id}", method = RequestMethod.DELETE ,produces="application/json;charset=UTF-8")
    public ResponseData deleteProperty(@PathVariable int id){
        ResponseData json;
        try {
            enterpriseService.delete(id);
            json = new ResponseData(true, null);
        }catch (Exception ex){
            json = new ResponseData(false,"Impossible de supprimer car cette donn&eacute;e est utilis&eacute;e ailleurs",null);
        }
        return json;
    }


}
