package com.example.demo.entity.xioami;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Model {
    public String containerClass;
    public boolean isCatalog;
    public String title;
    public ArrayList<Product> products;
    public String targetUrl;
    public boolean selectedHasChild;
    public String emptyCatalogLink;
    public ArrayList<String> reqiredCss;
    public ArrayList<String> requiredJs;
    public String googleTagParams;
    public String actionPayRetargetingData;
    public boolean needAdultProtection;
    public boolean promoMenuV2;
}
