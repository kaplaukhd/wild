package com.example.demo.entity.xioami.subData;

import java.util.ArrayList;

@lombok.Data
public class Data {
    private int imt_id;
    private int nm_id;
    private String imt_name;
    private String subj_name;
    private String subj_root_name;
    private String vendor_code;
    private String description;
    private ArrayList<GroupedOption> grouped_options;
    private ArrayList<Option> options;
    private String nm_colors_names;
    private ArrayList<Integer> colors;
    private String contents;
}