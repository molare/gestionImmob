package com.immo.controller;

import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.Contrat;
import com.immo.entities.PayRoll;
import com.immo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by olivier on 02/10/2019.
 */
@RestController
public class PayRollController {

    @Autowired
    private ContratService contratService;

    @Autowired
    private LocativeService locativeService;

    @Autowired
    private LocaterService locaterService;

    @Autowired
    private MoyenPayService moyenPayService;

    @Autowired
    private StatutPayService statutPayService;

    @Autowired
    private PayRollService payRollService;



    @RequestMapping(value = "/payRoll/findByContrat/{id}", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    public ResponseData findContrat(Locale locale,@ModelAttribute PayRoll payRoll,@PathVariable int id, BindingResult result,HttpServletRequest request){
        SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy");
        //List<PayRoll> payRollList = new ArrayList<PayRoll>();
        //Contrat ci = contratService.findById(id);
        //List<PayRoll> pList = payRollService.findByContrat(ci);
        List<PayRoll> pList = payRollService.findByContratByOrderByIdAsc(id);



        return new ResponseData(true, pList);
    }

    @RequestMapping(value = "/savePayRoll", method = RequestMethod.POST,headers="Accept=*/*")
    public ResponseData savePayRoll(Locale locale,@ModelAttribute PayRoll payRoll, BindingResult result,HttpServletRequest request){
        ResponseData json=null;
        json = payRollService.addPayRoll(locale,payRoll,result,request);
        return json;
    }


    @RequestMapping(value = "/updatePayRoll/{id}", method = RequestMethod.POST)
    public ResponseData updatePayRoll(Locale locale,@ModelAttribute PayRoll p, @PathVariable int id, BindingResult result,HttpServletRequest request){
        ResponseData json=null;
        json = payRollService.updatePayRoll(locale,p,id,result,request);
        return json;
    }

    @RequestMapping(value = "/findPayRoll/{id}", method = RequestMethod.GET)
    public ResponseData findPayRoll(Locale locale,@ModelAttribute PayRoll payRoll,@PathVariable int id, BindingResult result,HttpServletRequest request){
        SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy");
        PayRoll p = payRollService.findById(id);
        p.setEndDateTransient(sdf.format(p.getEndDate()));
        return new ResponseData(true, p);
    }

    @RequestMapping(value = "/sumPayRoll", method = RequestMethod.POST)
    public ResponseData countBien(HttpServletRequest request){
        ResponseData json=null;
        try {
            double sum = payRollService.sumPayRoll();
            json = new ResponseData(true, sum);
        }catch (Exception ex){
            json = new ResponseData(false,"erreur serveur",ex.getCause());
        }
        return json;
    }

}
