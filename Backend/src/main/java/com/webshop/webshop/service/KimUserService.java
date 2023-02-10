package com.webshop.webshop.service;

import com.webshop.webshop.repository.KimUserRepository;

public class KimUserService {
    private KimUserRepository operatorRepository;

    public KimUserService(KimUserRepository operatorRepository) {
        this.operatorRepository = operatorRepository;
    }
}
