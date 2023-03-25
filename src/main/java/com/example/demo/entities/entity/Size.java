package com.example.demo.entities.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Size{
    public String name;
    public String origName;
    public int rank;
    public int optionId;
    public int wh;
    public String sign;
}