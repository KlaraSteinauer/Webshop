package com.webshop.webshop.controller;

import com.webshop.webshop.service.KimUserService;

public class KimUserController {

    private KimUserService kimUserService;

    private KimUserController(KimUserService kimUserService) {
        this.kimUserService = kimUserService;
    }

    /*
    - neuen User mit DTO erstellen
    - user in DB speichern
    - user kann sich einloggen
    - schauen ob user admin ist

    USER
    - user kann produkte zum shoppingcart hinzufügen
    - user kann produkte zum shoppingcart löschen

    ADMIN
    - kann produkte


     */
}
