package com.example.demo.entities.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Root{
    public int state;
    public int version;
    public Params params;
    public Data data;
}
