package com.immo.service;

import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.Contrat;
import com.immo.entities.PayRoll;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

/**
 * Created by olivier on 02/10/2019.
 */
public interface PayRollService {
    public List<PayRoll> getAll();
    public PayRoll add(PayRoll payRoll);
    public PayRoll update(PayRoll payRoll);
    public PayRoll findById(int id);
    public void delete(int id);
    public List<PayRoll> findByContrat(Contrat contrat);
    public List<PayRoll> findByContratByOrderByIdAsc(int contratId);
    public ResponseData addPayRoll(Locale locale,PayRoll payRoll, BindingResult result,HttpServletRequest request);
    public ResponseData updatePayRoll(Locale locale, PayRoll payRoll,int id, BindingResult result,HttpServletRequest request);
    public double sumPayRoll();
    public List<PayRoll> export(int cpt, HttpServletRequest request);
    List<Object> firstYearPayRollChart();
    List<Object> secondYearPayRollChart();
    List<Object> threeYearPayRollChart();
    public List<PayRoll> statePayRollReporting(String startDate, String endDate,int bienId, int locativeId, int locaterId);


}
