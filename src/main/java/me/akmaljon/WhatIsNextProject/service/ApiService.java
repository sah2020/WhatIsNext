package me.akmaljon.WhatIsNextProject.service;

import me.akmaljon.WhatIsNextProject.entity.RequestModel;
import me.akmaljon.WhatIsNextProject.entity.ResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApiService {
    ResponseModel getAnswer(RequestModel requestModel);

    List<String> getShopTypesList();
}
