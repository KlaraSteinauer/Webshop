package com.webshop.webshop.service;

import com.webshop.webshop.repository.KimUserRepository;

public class KimUserService {
    private KimUserRepository kimUserRepository;

    public KimUserService(KimUserRepository kimUserRepository) {
        this.kimUserRepository = kimUserRepository;
    }
}
