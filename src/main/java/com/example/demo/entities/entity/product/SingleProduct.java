package com.example.demo.entities.entity.product;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class SingleProduct {
    public int imt_id;
    public int nm_id;
    public String imt_name;
    public String subj_name;
    public String subj_root_name;
    public String description;
    public List<Option> options;
    public String nm_colors_names;
    public String contents;
    public Media media;
    @Setter
    public List<String> images;
}

