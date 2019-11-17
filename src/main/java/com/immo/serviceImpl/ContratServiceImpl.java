package com.immo.serviceImpl;


import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.Contrat;
import com.immo.entities.City;
import com.immo.entities.PayRoll;
import com.immo.entities.Property;
import com.immo.repositories.ContratRepository;
import com.immo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
@Service("contratService")
public class ContratServiceImpl implements ContratService {
    @Autowired
    private ContratRepository contratRepository;

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

    @Override
    public List<Contrat> getAll() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat add(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Override
    public Contrat update(Contrat contrat) {
        if(contrat.getId() ==0){
            return contratRepository.save(contrat);
        }
        return contratRepository.saveAndFlush(contrat);
    }

    @Override
    public Contrat findById(int id) {
        return contratRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        contratRepository.deleteById(id);
    }



    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public ResponseData addContrat(Contrat contrat, BindingResult result, MultipartFile file, HttpServletRequest request) {
        ResponseData json=null;
        SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {


          /*  long monthsBetween = ChronoUnit.MONTHS.between(
            LocalDate.parse(sf.format(sdf.parse(request.getParameter("startBailDate")))).withDayOfMonth(6),
            LocalDate.parse(sf.format(sdf.parse(request.getParameter("endBailDate")))).withDayOfMonth(1));*/
            //System.out.println("semestriel "+LocalDate.parse(sf.format(sdf.parse(request.getParameter("startBailDate")))).withMonth(6)); //3


            if (file.getSize() > 0) {
                String fileName = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                contrat.setImageName(fileName);
                contrat.setImage(bytes);
            }

            contrat.setName(request.getParameter("name").toUpperCase());
            contrat.setAmount(Double.parseDouble(request.getParameter("amount")));
            contrat.setAdvanceMonth(Integer.parseInt(request.getParameter("advance")));
            contrat.setAgenceMonth(Integer.parseInt(request.getParameter("agence")));
            contrat.setMonthNber(Integer.parseInt(request.getParameter("monthNber")));
            contrat.setStatusContrat(request.getParameter("statusContrat"));
            contrat.setFirstQuittance(Double.parseDouble(request.getParameter("firstQuittance")));
            contrat.setStartBailDate(sdf.parse(request.getParameter("startBailDate")));
           // contrat.setEndBailDate(sdf.parse(request.getParameter("endBailDate")));
            contrat.setLocater(locaterService.findById(Integer.parseInt(request.getParameter("locater"))));
            contrat.setLocative(locativeService.findById(Integer.parseInt(request.getParameter("locative"))));
            contrat.setMoyenPay(moyenPayService.findById(Integer.parseInt(request.getParameter("moyen"))));
            contrat.setStatutPay(statutPayService.findById(Integer.parseInt(request.getParameter("statut"))));
            contrat.setCommentary(request.getParameter("commentary"));
            contrat.setRestCaution(contrat.getAmount()-(contrat.getAdvanceMonth()+contrat.getAgenceMonth())*(contrat.getLocative().getAmount()+contrat.getLocative().getCharge()));


            Contrat c = add(contrat);




           if(c!=null){
               String[] monthAbbrev = {"Janvier","Fevrier","Mars","Avril","Mai","Juin","Juillet","Août","Septembre","Octobre","Novembre","Decembre"};

               for(int i= 0; i < contrat.getAdvanceMonth(); i++){
                    PayRoll payRollScaleDetail = new PayRoll();

                    payRollScaleDetail.setContrat(c);
                   String[] startDateTab = request.getParameter("startBailDate").split("-");

                   //DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                   Calendar calendarStart=Calendar.getInstance();
                   calendarStart.set(Calendar.YEAR,Integer.parseInt(startDateTab[2]));
                   calendarStart.set(Calendar.MONTH,Integer.parseInt(startDateTab[1])-1);
                   calendarStart.set(Calendar.DAY_OF_MONTH,Integer.parseInt(startDateTab[0]));

                   Calendar calendarEnd=Calendar.getInstance();
                   calendarEnd.setTime(calendarStart.getTime());
                   calendarStart.set(Calendar.YEAR,Integer.parseInt(startDateTab[2]));
                   calendarEnd.set(Calendar.MONTH,Integer.parseInt(startDateTab[1])+i-1);;
                   calendarEnd.set(Calendar.DAY_OF_MONTH,Integer.parseInt(startDateTab[0]));

                   String monthId = df.format(calendarEnd.getTime());
                   payRollScaleDetail.setName(monthAbbrev[Integer.parseInt(monthId.split("-")[1])-1]);
                  //  payRollScaleDetail.setStartDate(calendarStart.getTime());
                    payRollScaleDetail.setEndDate(calendarEnd.getTime());
                   payRollScaleDetail.setAmount(c.getLocative().getAmount()+c.getLocative().getCharge());
                   payRollScaleDetail.setStatusId(1);
                   //payRollScaleDetail.setCaution(c.getAmount()-(contrat.getAdvanceMonth()+contrat.getAgenceMonth())*(c.getLocative().getAmount()+c.getLocative().getCharge()));
                   payRollScaleDetail.setCautionStatut(1);
                   PayRoll p = payRollService.add(payRollScaleDetail);
                }

               json = new ResponseData(true, c);
            }

        }catch (Exception ex){

            json = new ResponseData(false,"une valeur a été dupliquée ou erroné",ex.getCause());
        }
        return json;
    }

    @Override
    public List<Contrat> export(int cpt, HttpServletRequest request) {
        List<Contrat> cusList = new ArrayList<Contrat>();
        Contrat cus = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //DecimalFormat dft = new DecimalFormat("#.00");

        for(int i=0; i<cpt; i++){

            cus = contratRepository.findById(Integer.parseInt(request.getParameter("keyid"+i)));

            cus.setDateTransient(df.format(cus.getDate()));
            //System.out.println("users "+cus);

            cusList.add(cus);
        }
        return cusList;
    }

}
