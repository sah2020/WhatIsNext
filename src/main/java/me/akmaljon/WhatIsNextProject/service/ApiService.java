package me.akmaljon.WhatIsNextProject.service;

import me.akmaljon.WhatIsNextProject.entity.RequestModel;
import me.akmaljon.WhatIsNextProject.entity.ResponseModel;
import org.springframework.stereotype.Service;

@Service
public interface ApiService {
    public ResponseModel getAnswer(RequestModel requestModel);
}
