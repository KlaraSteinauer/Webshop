package com.webshop.webshop.controller;

import com.webshop.webshop.repository.ProductRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductApiController {
    private ProductRepository productRepository;
    public ProductApiController(ProductRepository productRepository){this.productRepository = productRepository;}
//    @PostMapping("/register")
//    public UserModel register(@Valid UserRequestDTO request, BindingResult bindingResult) {
//        if(bindingResult.hasErrors()){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request was invalid");
//        } else {
//            UserModel user = new UserModel(
//                    request.getFirstname(), request.getLastname(), request.getEmail()
//            );
//            this.userRepository.save(user);
//            return user;
//        }
//    }
//
//    @GetMapping("/user")
//    public Iterable<UserModel> userList(){
//        return this.userRepository.findAll();
//    }


}
