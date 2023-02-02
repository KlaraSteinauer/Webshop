package com.webshop.webshop.service;

import com.webshop.webshop.repository.OperatorRepository;

public class OperatorService {

    private OperatorRepository operatorRepository;

    public OperatorService(OperatorRepository operatorRepository) {
        this.operatorRepository = operatorRepository;
    }
}
