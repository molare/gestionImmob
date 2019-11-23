package com.immo.controller;

import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.Contrat;
import com.immo.entities.PayRoll;
import com.immo.reporting.ContratReporting;
import com.immo.reporting.PayRollReporting;
import com.immo.reporting.StatePayRollReporting;
import com.immo.service.*;
import net.sf.dynamicreports.report.exception.DRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    @RequestMapping(value = "/savePayRoll", method = RequestMethod.POST,headers="Accept=*/*",produces="application/json;charset=UTF-8")
    public ResponseData savePayRoll(Locale locale,@ModelAttribute PayRoll payRoll, BindingResult result,HttpServletRequest request){
        ResponseData json=null;
        json = payRollService.addPayRoll(locale,payRoll,result,request);
        return json;
    }


    @RequestMapping(value = "/updatePayRoll/{id}", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
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

    @RequestMapping(value = "/payRoll/export", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    public void exportCustomer(HttpServletRequest request, HttpServletResponse response,HttpSession session){
        response.setContentType("application/pdf;charset=UTF-8");

        int id =Integer.parseInt(request.getParameter("cpt"));
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<PayRoll> pr = payRollService.export(id, request);
        try {
            OutputStream out = response.getOutputStream();
            PayRollReporting p = new PayRollReporting(pr);
            // response.setHeader("Content-disposition", "attachment; filename="+ ExportFileName.CONTRAT+".pdf");
            p.build(request).toPdf(out);
        } catch (IOException ex) {
            Logger.getLogger(ContratController.class.getName()).log(Level.SEVERE, null, ex);
        }  catch (DRException ex) {
            Logger.getLogger(ContratController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @RequestMapping(value = "/payRoll/charts", method = RequestMethod.POST)
    public ResponseData saleCharts(){
        List<Object>  firstPayRollCharts = payRollService.firstYearPayRollChart();
        List<Object>  secondPayRollCharts = payRollService.secondYearPayRollChart();
        List<Object>  threePayRollCharts = payRollService.threeYearPayRollChart();
        return new ResponseData(true,firstPayRollCharts,secondPayRollCharts,threePayRollCharts);
    }


    @RequestMapping(value = "/statePayRoll/reporting", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    public void statePayRollReporting(HttpServletRequest request, HttpServletResponse response,HttpSession session){
        response.setContentType("application/pdf;charset=UTF-8");

        String startDate =request.getParameter("startDate");
        String endDate =request.getParameter("endDate");

        int bienId =Integer.parseInt(request.getParameter("bien"));
        int locativeId =Integer.parseInt(request.getParameter("locative"));
        int locaterId =Integer.parseInt(request.getParameter("locater"));
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<PayRoll> pr = payRollService.statePayRollReporting(startDate,endDate,bienId,locativeId,locaterId);
        try {
            OutputStream out = response.getOutputStream();
            StatePayRollReporting p = new StatePayRollReporting(pr,authentication.getName());
            // response.setHeader("Content-disposition", "attachment; filename="+ ExportFileName.CONTRAT+".pdf");
            p.build(request).toPdf(out);
        } catch (IOException ex) {
            Logger.getLogger(ContratController.class.getName()).log(Level.SEVERE, null, ex);
        }  catch (DRException ex) {
            Logger.getLogger(ContratController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
