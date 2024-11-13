package com.integrador.backend2.service;

import com.integrador.backend2.domain.LoginDTO;
import com.integrador.backend2.domain.AdminDTO;
import com.integrador.backend2.response.LoginResponse;

public interface AdminService {

    String addAdmin(AdminDTO adminDTO);

    LoginResponse loginAdmin(LoginDTO loginDTO); // Método para el inicio de sesión
}
