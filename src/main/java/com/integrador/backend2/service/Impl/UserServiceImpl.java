package com.integrador.backend2.service.Impl;

import com.integrador.backend2.domain.LoginDTO;
import com.integrador.backend2.domain.UserDTO;
import com.integrador.backend2.model.User;
import com.integrador.backend2.repository.UserRepository;
import com.integrador.backend2.response.LoginResponse;
import com.integrador.backend2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String addUser(UserDTO userDTO) {
        // Guardar el nuevo usuario con el campo profileImage
        User user = new User(
                userDTO.getUsername(),
                userDTO.getEmail(),
                this.passwordEncoder.encode(userDTO.getPassword()),
                userDTO.getProfileImage() // Incluir el campo profileImage
        );
        userRepository.save(user);
        return user.getUsername();
    }

    @Override
    public User getUserById(Integer idUser) {
        return userRepository.findById(idUser).orElse(null);
    }

    @Override
    public LoginResponse loginUser(LoginDTO loginDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(loginDTO.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String password = loginDTO.getPassword();
            String encodedPassword = user.getPassword();
            boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                return new LoginResponse(
                        true,
                        "Login Success",
                        "token_de_ejemplo",
                        false,
                        user.getIdUser().longValue(), // Conversión explícita a Long
                        user.getUsername(),
                        user.getEmail(), // Nuevo campo email
                        user.getProfileImage()
                );
            } else {
                return new LoginResponse(false, "Password Not Match", null, false, null, null, null, null);
            }
        } else {
            return new LoginResponse(false, "Email not exists", null, false, null, null, null, null);
        }
    }

}
