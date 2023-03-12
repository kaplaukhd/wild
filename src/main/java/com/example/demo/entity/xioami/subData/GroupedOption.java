package com.example.demo.entity.xioami.subData;

import lombok.Data;

import java.util.ArrayList;

@Data
public class GroupedOption {
    private String group_name;
    private ArrayList<Option> options;
}
