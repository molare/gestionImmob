package com.immo.service;

import com.immo.dataTableResponse.ResponseData;
import com.immo.entities.Contrat;
import com.immo.entities.City;
import com.immo.entities.Property;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

/**
 * Created by olivier on 02/10/2019.
 */
public interface ContratService {
    public List<Contrat> getAll();
    public Contrat add(Contrat contrat);
    public Contrat update(Contrat contrat);
    public Contrat findById(int id);
    public void delete(int id);
    public ResponseData addContrat(Contrat contrat, BindingResult result,MultipartFile file,HttpServletRequest request);
    public List<Contrat> export(int cpt, HttpServletRequest request);
    public ResponseData updateContrat(Locale locale, Contrat cont,BindingResult result, int idContrat,MultipartFile file,HttpServletRequest request);

    }
