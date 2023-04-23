package me.akmaljon.WhatIsNextProject.controller;

import me.akmaljon.WhatIsNextProject.entity.RequestModel;
import me.akmaljon.WhatIsNextProject.entity.ResponseModel;
import me.akmaljon.WhatIsNextProject.service.ApiService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @PostMapping(value = "/api",
            consumes= MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel> generate(@RequestBody RequestModel requestModel) {
        ResponseModel answer = apiService.getAnswer(requestModel);
        return ResponseEntity.ok(answer);
    }
}
