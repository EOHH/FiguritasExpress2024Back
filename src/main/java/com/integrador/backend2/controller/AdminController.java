package com.integrador.backend2.controller;

import com.integrador.backend2.domain.AdminDTO;
import com.integrador.backend2.domain.LoginDTO;
import com.integrador.backend2.model.Admin;
import com.integrador.backend2.repository.AdminRepository;
import com.integrador.backend2.response.LoginResponse;
import com.integrador.backend2.service.AdminService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api_int_2024/admins")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminService adminService; // Asegúrate de que estás inyectando AdminService correctamente

    @PostMapping("/store")
    public Admin store(@RequestBody AdminDTO adminDTO) {
        Admin admin = new Admin(
                adminDTO.getUsername(),
                adminDTO.getEmail(),
                passwordEncoder.encode(adminDTO.getPassword())
        );
        return adminRepository.save(admin);
    }

    @GetMapping("/getAll")
    public List<Admin> getAll() {
        return adminRepository.findAll();
    }

    @PutMapping("update/{id}")
    public Admin actualizar(@RequestBody Admin admin, @PathVariable("id") Integer id) {
        Admin adminDB = adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El administrador con id: " + id + " no fue encontrado."));
        return adminRepository.save(adminDB);
    }

    @DeleteMapping("/delete/{id}")
    public boolean eliminar(@PathVariable("id") Integer id) {
        try {
            adminRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    @PostMapping(path = "/save")
    public String saveAdmin(@RequestBody AdminDTO adminDTO) {
        return adminService.addAdmin(adminDTO);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginAdmin(@RequestBody LoginDTO loginDTO) {
        LoginResponse loginResponse = adminService.loginAdmin(loginDTO); // Asegúrate de que se está llamando al método correcto
        return ResponseEntity.ok(loginResponse);
    }
}
