package me.akmaljon.WhatIsNextProject.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestModel {
    private ShopTypeEnum shopType;
    private List<String> items;
}
