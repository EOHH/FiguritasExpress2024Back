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
        // Verifica si la imagen de perfil es nula o vacía antes de asignarla
        Admin admin = new Admin(
                adminDTO.getUsername(),
                adminDTO.getEmail(),
                this.passwordEncoder.encode(adminDTO.getPassword())
        );

        // Si se proporciona una imagen, asignarla al admin
        if (adminDTO.getProfileImage() != null && !adminDTO.getProfileImage().isEmpty()) {
            admin.setProfileImage(adminDTO.getProfileImage());
        }

        // Guardar el admin en la base de datos
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
                // Se agrega el campo email y profileImage al constructor de LoginResponse
                return new LoginResponse(
                        true,
                        "Login Success",
                        "token_de_ejemplo", // Aquí deberías generar un token JWT real
                        true,
                        admin.getIdAdmin().longValue(),
                        admin.getUsername(),
                        admin.getEmail(),
                        admin.getProfileImage() // Aquí se agrega la imagen de perfil
                );
            } else {
                return new LoginResponse(false, "Password Not Match", null, true, null, null, null, null);
            }
        } else {
            return new LoginResponse(false, "Email not exists", null, true, null, null, null, null);
        }
    }

}
