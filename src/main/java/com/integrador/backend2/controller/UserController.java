package com.integrador.backend2.controller;

import com.integrador.backend2.domain.LoginDTO;
import com.integrador.backend2.domain.UserDTO;
import com.integrador.backend2.model.User;
import com.integrador.backend2.repository.UserRepository;
import com.integrador.backend2.response.LoginResponse;
import com.integrador.backend2.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.integrador.backend2.service.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api_int_2024/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Value("${google.clientId}")
    private String clientId;

    // Registro tradicional con formulario
    @PostMapping(value = "store", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> store(@RequestBody UserDTO userDTO) {
        // Verificar si el usuario ya existe
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo electrónico ya está registrado.");
        }
        // Crear el usuario con los datos proporcionados
        User user = new User(
                userDTO.getUsername(),
                userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword())
        );
        userRepository.save(user);

        // Generar un token de autenticación (JWT)
        String token = tokenService.generateToken(user); // Usa un servicio para generar el token

        // Crear una respuesta con el token y otros datos necesarios
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Registro exitoso");
        response.put("token", token);
        response.put("username", user.getUsername());
        response.put("idUser", user.getIdUser()); // Agrega el idUser aquí

        // Retornar la respuesta con el token
        return ResponseEntity.ok(response);
    }


    // Obtener todos los usuarios
    @GetMapping("/getAll")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<User> obtenerUsuarioPorId(@PathVariable Integer id) {
        User usuario = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El usuario con id: " + id + " no fue encontrado."));
        return ResponseEntity.ok(usuario);
    }

    // Actualizar usuario
    @PutMapping("update/{id}")
    public User actualizar(@RequestBody User user, @PathVariable("id") Integer id) {
        User userDB = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El usuario con id: " + id + " no fue encontrado."));
        return userRepository.save(userDB);
    }

    // Eliminar usuario
    @DeleteMapping("/delete/{id}")
    public boolean eliminar(@PathVariable("id") Integer id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    // Guardar usuario
    @PostMapping(path = "/save")
    public String saveUser(@RequestBody UserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    // Inicio de sesión con credenciales
    @PostMapping(path = "/login")
    public ResponseEntity<?> loginEmployee(@RequestBody LoginDTO loginDTO) {
        LoginResponse loginResponse = userService.loginUser(loginDTO);
        return ResponseEntity.ok(loginResponse);
    }

    // Registro e inicio de sesión con Google
    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        if (token == null || token.isEmpty()) {
            System.out.println("Token no proporcionado");
            return ResponseEntity.status(400).body("Token no proporcionado");
        }

        try {
            System.out.println("Iniciando verificación del token de Google");
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                    .setAudience(Collections.singletonList(clientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                System.out.println("Token de Google verificado exitosamente");
                GoogleIdToken.Payload googlePayload = idToken.getPayload();

                String googleId = googlePayload.getSubject();
                String email = googlePayload.getEmail();
                String username = (String) googlePayload.get("name");

                System.out.println("Datos del usuario de Google: " + email + ", " + username);

                Optional<User> existingUser = userRepository.findByEmail(email);
                User user;

                if (existingUser.isPresent()) {
                    user = existingUser.get();
                    user.setGoogleId(googleId);
                    user.setUsername(username);
                } else {
                    user = new User();
                    user.setGoogleId(googleId);
                    user.setUsername(username);
                    user.setEmail(email);
                    userRepository.save(user);
                }

                String jwtToken = tokenService.generateToken(user);

                Map<String, Object> response = new HashMap<>();
                response.put("message", "Registro e inicio de sesión exitoso con Google");
                response.put("token", jwtToken);
                response.put("username", user.getUsername());
                response.put("idUser", user.getIdUser()); // Agrega el idUser aquí

                System.out.println("Respuesta de éxito preparada, con token JWT incluido");
                return ResponseEntity.ok(response);
            } else {
                System.out.println("Token inválido");
                return ResponseEntity.status(401).body("Token inválido");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en la autenticación con Google");
            return ResponseEntity.status(500).body("Error en la autenticación con Google");
        }
    }
}
