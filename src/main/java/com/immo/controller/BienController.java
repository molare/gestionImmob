package com.immo.controller;

import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.Bien;
import com.immo.service.CityService;
import com.immo.service.BienService;
import com.immo.service.PropertyService;
import com.immo.service.TypeBienService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by olivier on 02/10/2019.
 */
@RestController
public class BienController {

    @Autowired
    private BienService bienService;

    @Autowired
    private CityService cityService;

    @Autowired
    private TypeBienService typeBienService;

    @Autowired
    private PropertyService propertyService;

    @RequestMapping(value = "/listBien", method = RequestMethod.GET)
    public ResponseData getAllBien(){
        List<Bien> newComList = new ArrayList<Bien>();
        List<Bien> listCom = bienService.getAll();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        for(Bien co : listCom){
            Bien c = new Bien();
            c.setId(co.getId());
            c.setCode(co.getCode());
            c.setDesignation(co.getDesignation());
            c.setAcquisitionCost(co.getAcquisitionCost());
            c.setCityTransient(co.getCity().getName());
            c.setPropertyTransient(co.getProperty().getFirstName());
            c.setTypeTransient(co.getTypeBien().getName());
            c.setNberIlot(co.getNberIlot());
            c.setNberlot(co.getNberlot());
            c.setSuperficy(co.getSuperficy());
            c.setDate(co.getDate());
            c.setDateTransient(df.format(co.getDate()));
            c.setCommuneTransient(co.getCity().getCommune().getName());
            String act="<td>\n" +
                    //"<button  class=\"btn btn-success btn-xs m-r-5\"  data-toggle=\"modal\" data-target=\"#editBienModal\" onclick=\"editBien("+c.getId()+") data-original-title=\"Edit\"><i class=\"fa fa-pencil font-14\"></i></button>\n"+
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#editBienModal\" class=\"link-underlined margin-right-50 btn btn-success\" data-original-title=\"Editer\" onclick=\"editBien("+c.getId()+")\"><i class=\"fa fa-pencil font-14\"><!-- --></i></a>\n" +
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#removeBienModal\" class=\"link-underlined btn btn-danger\" data-original-title=\"Supprimer\" onclick=\"removeBien("+c.getId()+")\"><i class=\"fa fa-trash font-14\"><!-- --></i></a>\n" +
                    "</td>";
            c.setAction(act);
            /*String checkboxes ="<input name=\"select_id\" id=\"tabId\" value=\""+c.getId()+"\" type=\"checkbox\">";
            c.setCheckboxe(checkboxes);*/

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

                String checkboxes="<a href=\"javascript: void(0);\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#ModalBien"+c.getId()+"\"><i class=\"fa fa-eye\"></i></a>\n" +
                        "<!-- Modal -->\n" +
                        "  <div class=\"modal fade\" id=\"ModalBien"+c.getId()+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabelts\" aria-hidden=\"true\">\n" +
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
                c.setCheckboxe(checkboxes);
            }else{
                // String img = "<img class=\"user_picture_small\" style=\"width:200px\" alt=\"Image\" src=\"../assets/common/img/upload_img.png\">";
                c.setImageTransient(null);
                c.setCheckboxe(null);
            }
            newComList.add(c);
        }



        return new ResponseData(true,newComList);
    }


    @RequestMapping(value = "/saveBien", method = RequestMethod.POST,headers="Accept=*/*")
    public ResponseData addBien(Locale locale,@ModelAttribute Bien bien, BindingResult result,@RequestParam("picture")MultipartFile file,HttpServletRequest request)throws Exception{
        ResponseData json=null;
        try {
            if (file.getSize() > 0) {
                String fileName = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                bien.setImageName(fileName);
                bien.setImage(bytes);
            }

        bien.setDesignation(request.getParameter("designation").toUpperCase());
            bien.setNberFoncier(request.getParameter("nberFoncier"));
        bien.setAcquisitionCost(Double.parseDouble(request.getParameter("cost")));
        bien.setCity(cityService.findById(Integer.parseInt(request.getParameter("city"))));
        bien.setProperty(propertyService.findById(Integer.parseInt(request.getParameter("property"))));
        bien.setTypeBien(typeBienService.findById(Integer.parseInt(request.getParameter("typeBien"))));
        bien.setNberIlot(request.getParameter("nberIlot"));
        bien.setNberlot(request.getParameter("nberLot"));
        bien.setSuperficy(Double.parseDouble(request.getParameter("superficy")));
            bien.setCommentary(request.getParameter("commentary"));
         Bien c =  bienService.add(bien);
            json = new ResponseData(true, c);
        }catch (Exception ex){
            json = new ResponseData(false,"une valeur a été dupliquée ou erroné",ex.getCause());
        }
        return json;
    }

    @RequestMapping(value = "/updateBien/{id}", method = RequestMethod.POST,headers="Accept=*/*")
    public ResponseData updateBien(Locale locale,@ModelAttribute Bien bien,@PathVariable int id, BindingResult result,@RequestParam("editPicture")MultipartFile file,HttpServletRequest request){
        ResponseData json=null;
        try {
            if(file.getSize()>0 && !file.isEmpty()){
                String fileName = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                bien.setImageName(fileName);
                bien.setImage(bytes);
            }else{
                bien.setImage(bienService.findById(id).getImage());
                bien.setImageName(bienService.findById(id).getImageName());
            }

            bien.setDesignation(request.getParameter("designation").toUpperCase());
            bien.setNberFoncier(request.getParameter("nberFoncier"));
            bien.setAcquisitionCost(Double.parseDouble(request.getParameter("cost")));
            bien.setCity(cityService.findById(Integer.parseInt(request.getParameter("city"))));
            bien.setProperty(propertyService.findById(Integer.parseInt(request.getParameter("property"))));
            bien.setTypeBien(typeBienService.findById(Integer.parseInt(request.getParameter("typeBien"))));
            bien.setNberIlot(request.getParameter("nberIlot"));
            bien.setNberlot(request.getParameter("nberLot"));
            bien.setSuperficy(Double.parseDouble(request.getParameter("superficy")));
            bien.setCommentary(request.getParameter("commentary"));
            Bien c =  bienService.update(bien);
            json = new ResponseData(true, c);
        }catch (Exception ex){
            json = new ResponseData(false,"une valeur a été dupliquée ou erroné",ex.getCause());
        }
        return json;
    }

    @RequestMapping(value = "/findBien/{id}", method = RequestMethod.GET)
    public ResponseData findBien(Locale locale,@ModelAttribute Bien bien,@PathVariable int id, BindingResult result,HttpServletRequest request){
        Bien ci = bienService.findById(id);
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

    @RequestMapping(value = "/deleteBien/{typeId}", method = RequestMethod.DELETE)
    public ResponseData deleteBien(@PathVariable int typeId,HttpServletRequest request){
        ResponseData json=null;
        try {
        bienService.delete(typeId);
            json = new ResponseData(true, null);
        }catch (Exception ex){
            json = new ResponseData(false,"Impossible de supprimer cette donnée car elle est liée ailleurs",ex.getCause());
        }
        return json;
    }

    @RequestMapping(value = "/countBien", method = RequestMethod.POST)
    public ResponseData countBien(HttpServletRequest request){
        ResponseData json=null;
        try {
            int count = bienService.countBien();
            json = new ResponseData(true, count);
        }catch (Exception ex){
            json = new ResponseData(false,"erreur serveur",ex.getCause());
        }
        return json;
    }
}
