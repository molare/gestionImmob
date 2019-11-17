package com.immo.serviceImpl;

import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.Contrat;
import com.immo.entities.PayRoll;
import com.immo.repositories.PayRollRepository;
import com.immo.service.ContratService;
import com.immo.service.PayRollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by olivier on 02/10/2019.
 */
@Service("payRollService")
public class PayRollServiceImpl implements PayRollService {
    @Autowired
    private PayRollRepository payRollRepository;
    @Autowired
    private ContratService contratService;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<PayRoll> getAll() {
        return payRollRepository.findAll();
    }

    @Override
    public PayRoll add(PayRoll payRoll) {
        return payRollRepository.save(payRoll);
    }

    @Override
    public PayRoll update(PayRoll payRoll) {
        if(payRoll.getId() ==0){
            return payRollRepository.save(payRoll);
        }
        return payRollRepository.saveAndFlush(payRoll);
    }

    @Override
    public PayRoll findById(int id) {
        return payRollRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        payRollRepository.deleteById(id);
    }

    @Override
    public List<PayRoll> findByContrat(Contrat contrat) {
        return payRollRepository.findByContrat(contrat);
    }

    @Override
    public List<PayRoll> findByContratByOrderByIdAsc(int contratId) {
        SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat dft = new DecimalFormat("#");


        String sql="SELECT pr.id AS idpayroll, pr.created_date, pr.amount AS amount, pr.end_date AS end_date, \n" +
                "pr.name AS month_name, pr.status_val, c.name AS contrat_name, c.id as idcontrat, d.name AS devise, pr.caution_statut AS caution\n" +
                "FROM pay_roll pr, contrat c, locative lo, devis d\n" +
                "WHERE pr.contrat_id = c.id\n" +
                "AND c.locative_id = lo.id\n" +
                "AND lo.devis_id = d.id\n" +
                "AND c.id ="+contratId+"\n" +
                "ORDER BY pr.id ASC";
        Query query = em.createNativeQuery(sql);
        List<Object[]> result = query.getResultList();
        List<PayRoll> payRollList = new ArrayList<PayRoll>();
        for(Object[] rs: result){
            PayRoll c = new PayRoll();
            c.setId(Integer.parseInt(rs[0]+""));
            if(Integer.parseInt(rs[9]+"")==2){
                c.setName("R&egrave;glement du mois de " + rs[4] +" &agrave &eacute;t&eacute; deduit de la garantie");
            }else {
                c.setName("R&egrave;glement du mois de " + rs[4]);
            }
            c.setDateTransient(sdf.format(rs[1]));

            c.setAmountTransient(dft.format(rs[2])+" "+ rs[8]+"");
            c.setEndDateTransient(sdf.format(rs[3]));
            c.setStatusId(Integer.parseInt(rs[5] + ""));
            Date d = new Date();
            if((Integer.parseInt(rs[5]+""))==1) {
                c.setStatutTransient("\n" +
                        "<span class=\"badge badge-success\"><h7>Pay&eacute;</h7></span>");

            }else try {
                if((Integer.parseInt(rs[5]+""))==0 && d.after(sf.parse(rs[3]+"")) || (Integer.parseInt(rs[5]+""))==2){
                    c.setStatutTransient("\n" +
                            "<span class=\"badge badge-danger\"><h7>Impay&eacute;</h7></span>");

                }else{
                    c.setStatutTransient("\n" +
                            "<span class=\"badge badge-dark\"><h7>A venir</h7></span>");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


            String act="\n" +
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#editPayrollModal\" class=\"link-underlined margin-right-50 badge badge-warning\" data-original-title=\"Editer\" onclick=\"editPayroll("+c.getId()+")\"><i class=\"fa fa-pencil font-14\"><!-- --></i></a>\n" +
                    "";
            c.setAction(act);
            String checkboxes ="<input name=\"select_id\" id=\"tabId\" value=\""+c.getId()+"\" type=\"checkbox\">";
            c.setCheckboxe(checkboxes);

            String report="\n" +
                    "	<a href=\"javascript: void(0);\" data-toggle=\"modal\" data-target=\"#\" class=\"link-underlined margin-right-50 badge badge-info\" data-original-title=\"Impr\" onclick=\"editPayroll("+c.getId()+")\"><i class=\"fa fa-upload font-14\"><!-- --></i></a>\n" +
                    "";
            c.setReporting(report);


            payRollList.add(c);

        }

        return payRollList;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public ResponseData addPayRoll(Locale locale, PayRoll payRoll, BindingResult result, HttpServletRequest request) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        ResponseData json=null;
        boolean test=true;
        try {
            String[] monthAbbrev = {"Janvier","Fevrier","Mars","Avril","Mai","Juin","Juillet","Août","Septembre","Octobre","Novembre","Decembre"};
            Contrat contrat = contratService.findById(Integer.parseInt(request.getParameter("contrat")));
            String[] startDateTab = request.getParameter("startBailDate").split("-");
            List<PayRoll> listPayRoll = findByContratByOrderByIdAsc(Integer.parseInt(request.getParameter("contrat")));

            if(!listPayRoll.isEmpty()){
                for (PayRoll p : listPayRoll) {
                    String[] stringDate = p.getEndDateTransient().split("-");
                    if (Integer.parseInt(startDateTab[1]) == Integer.parseInt(stringDate[1])&&
                            Integer.parseInt(startDateTab[2]) == Integer.parseInt(stringDate[2])) {
                        test=false;

                    }
                }
            }

            if(test==true) {
                Calendar calendarStart = Calendar.getInstance();
                calendarStart.set(Calendar.YEAR, Integer.parseInt(startDateTab[2]));
                calendarStart.set(Calendar.MONTH, Integer.parseInt(startDateTab[1]) - 1);
                calendarStart.set(Calendar.DAY_OF_MONTH, Integer.parseInt(startDateTab[0]));

                Calendar calendarEnd = Calendar.getInstance();
                calendarEnd.setTime(calendarStart.getTime());
                calendarStart.set(Calendar.YEAR, Integer.parseInt(startDateTab[2]));
                calendarEnd.set(Calendar.MONTH, Integer.parseInt(startDateTab[1]) - 1);
                ;
                calendarEnd.set(Calendar.DAY_OF_MONTH, Integer.parseInt(startDateTab[0]));

                String monthId = df.format(calendarEnd.getTime());
                payRoll.setName(monthAbbrev[Integer.parseInt(monthId.split("-")[1]) - 1]);
                payRoll.setEndDate(calendarEnd.getTime());
                payRoll.setContrat(contrat);
                payRoll.setAmount(contrat.getLocative().getAmount() + contrat.getLocative().getCharge());
                payRoll.setStatusId(Integer.parseInt(request.getParameter("statut")));
                payRoll.setCautionStatut(Integer.parseInt(request.getParameter("caution")));
                if (Integer.parseInt(request.getParameter("caution")) != 1 && contrat.getRestCaution() >= payRoll.getAmount()) {
                    contrat.setRestCaution(contrat.getRestCaution() - payRoll.getAmount());
                    //contrat.setAdvanceMonth(contrat.getAdvanceMonth()-1);
                    Contrat c = contratService.update(contrat);
                }
                PayRoll pr = add(payRoll);
                json = new ResponseData(true, pr);
            }else{
                json = new ResponseData(false,"cette date contient deja une quittance",null);
            }
        }catch (Exception ex){
            json = new ResponseData(false,"une valeur a été dupliquée",ex.getCause());
        }
        return json;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public ResponseData updatePayRoll(Locale locale, PayRoll p, int id, BindingResult result, HttpServletRequest request) {
        PayRoll payRoll = findById(id);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        ResponseData json=null;
        try {
            String[] monthAbbrev = {"Janvier","Fevrier","Mars","Avril","Mai","Juin","Juillet","Août","Septembre","Octobre","Novembre","Decembre"};

            String[] startDateTab = request.getParameter("startBailDate").split("-");

            Calendar calendarStart=Calendar.getInstance();
            calendarStart.set(Calendar.YEAR,Integer.parseInt(startDateTab[2]));
            calendarStart.set(Calendar.MONTH,Integer.parseInt(startDateTab[1])-1);
            calendarStart.set(Calendar.DAY_OF_MONTH,Integer.parseInt(startDateTab[0]));

            Calendar calendarEnd=Calendar.getInstance();
            calendarEnd.setTime(calendarStart.getTime());
            calendarStart.set(Calendar.YEAR,Integer.parseInt(startDateTab[2]));
            calendarEnd.set(Calendar.MONTH,Integer.parseInt(startDateTab[1])-1);;
            calendarEnd.set(Calendar.DAY_OF_MONTH,Integer.parseInt(startDateTab[0]));

            String monthId = df.format(calendarEnd.getTime());
            payRoll.setName(monthAbbrev[Integer.parseInt(monthId.split("-")[1])-1]);
            payRoll.setEndDate(calendarEnd.getTime());
            Contrat contrat = contratService.findById(Integer.parseInt(request.getParameter("contrat")));
            payRoll.setContrat(contrat);
            payRoll.setAmount(contrat.getLocative().getAmount()+contrat.getLocative().getCharge());
            payRoll.setStatusId(Integer.parseInt(request.getParameter("statut")));
            //payRoll.setCaution(contrat.getAmount()-(contrat.getAdvanceMonth()+contrat.getAgenceMonth())*(contrat.getLocative().getAmount()+contrat.getLocative().getCharge()));
            payRoll.setCautionStatut(Integer.parseInt(request.getParameter("caution")));

            if(Integer.parseInt(request.getParameter("caution"))!=1 && contrat.getRestCaution()>=payRoll.getAmount()){
                contrat.setRestCaution(contrat.getRestCaution()-payRoll.getAmount());
                Contrat c = contratService.update(contrat);
            }

            PayRoll pr = update(payRoll);
            json = new ResponseData(true, pr);
        }catch (Exception ex){
            json = new ResponseData(false,"une valeur a été dupliquée",ex.getCause());
        }
        return json;
    }

    @Override
    public double sumPayRoll() {
        String sql="SELECT COALESCE(SUM(pr.amount)+SUM(DISTINCT c.rest_caution),0) AS total FROM pay_roll pr, contrat c\n" +
                "WHERE pr.contrat_id = c.id AND pr.status_val =1";
        Query query = em.createNativeQuery(sql);
        try{
            return Double.parseDouble(query.getSingleResult()+"");
        }catch (NoResultException ex){
            return 0;
        }
    }


}
