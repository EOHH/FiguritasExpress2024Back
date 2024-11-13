package com.integrador.backend2.service.Impl;

import com.integrador.backend2.domain.LoginDTO;
import com.integrador.backend2.domain.AdminDTO;
import com.integrador.backend2.model.Admin;
import com.integrador.backend2.repository.AdminRepository;
import com.integrador.backend2.response.LoginResponse;
import com.integrador.backend2.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String addAdmin(AdminDTO adminDTO) {
        Admin admin = new Admin(
                adminDTO.getUsername(),
                adminDTO.getEmail(),
                this.passwordEncoder.encode(adminDTO.getPassword())
        );
        adminRepository.save(admin);
        return admin.getUsername();
    }

    @Override
    public LoginResponse loginAdmin(LoginDTO loginDTO) {
        Admin admin = adminRepository.findByEmail(loginDTO.getEmail());
        if (admin != null) {
            String password = loginDTO.getPassword();
            String storedPassword = admin.getPassword();

            // Comparación simple sin encriptar
            if (password.equals(storedPassword)) {
                return new LoginResponse(true, "Login Success", "token_de_ejemplo", true, admin.getIdAdmin().longValue(), admin.getUsername());
            } else {
                return new LoginResponse(false, "Password Not Match", null, true, null, null);
            }
        } else {
            return new LoginResponse(false, "Email not exists", null, true, null, null);
        }
    }

}
