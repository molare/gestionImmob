/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.immo.reporting;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author karim.coulibaly
 */
public class ExportData {
    
    public static final int PDF = 1;
    public static final int XLS = 2;
    
    
    private String title;	
    
    private String lang;

    private LinkedHashMap <String, String> criteria;
    
    private List data;
       
    private LinkedHashMap <String, Properties> headerCols;
    
    private  LinkedHashMap<String, Properties> subTotals;
    
    public void ExportData(){
        
    }

    public ExportData(String title, String lang, LinkedHashMap<String, String> criteria, List data, LinkedHashMap<String, Properties> headerCols) {
        this.title = title;
        this.lang = lang;
        this.criteria = criteria;
        this.data = data;
        this.headerCols = headerCols;
        this.subTotals = null;
    }
    
    public ExportData(String title, String lang, LinkedHashMap<String, String> criteria, List data, LinkedHashMap<String, Properties> headerCols, LinkedHashMap<String, Properties> subTotals) {
        this.title = title;
        this.lang = lang;
        this.criteria = criteria;
        this.data = data;
        this.headerCols = headerCols;
        this.subTotals = subTotals;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }


    public LinkedHashMap<String, String> getCriteria() {
        return criteria;
    }

    public void setCriteria(LinkedHashMap<String, String> criteria) {
        this.criteria = criteria;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public LinkedHashMap<String, Properties> getHeaderCols() {
        return headerCols;
    }

    public void setHeaderCols(LinkedHashMap<String, Properties> headerCols) {
        this.headerCols = headerCols;
    }

    public LinkedHashMap<String, Properties> getSubTotals() {
        return subTotals;
    }

    public void setSubTotals(LinkedHashMap<String, Properties> subTotals) {
        this.subTotals = subTotals;
    }
    
    
    

    @Override
    public String toString() {
        return "ExportData{" + "title=" + title + ", lang=" + lang + ", criteria=" + criteria + ", data=" + data + ", headerCols=" + headerCols + '}';
    }


}
