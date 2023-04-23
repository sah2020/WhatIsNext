package me.akmaljon.WhatIsNextProject.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseModel {
    private List<ResponseItem> items;
    private String comments;

    public ResponseModel(String comments) {
        this.comments = comments;
    }

    public ResponseModel() {
    }
}
