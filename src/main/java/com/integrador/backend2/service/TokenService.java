package com.integrador.backend2.service;

import com.integrador.backend2.model.User;

public interface TokenService {
    String generateToken(User user);
}