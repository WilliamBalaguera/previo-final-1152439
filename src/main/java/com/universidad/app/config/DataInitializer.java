package com.universidad.app.config;

import com.universidad.app.security.Usuario;
import com.universidad.app.security.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(Usuario.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .rol(Usuario.Rol.ADMIN)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .username("usuario1")
                    .password(passwordEncoder.encode("user123"))
                    .rol(Usuario.Rol.USER)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .username("usuario2")
                    .password(passwordEncoder.encode("user123"))
                    .rol(Usuario.Rol.USER)
                    .build());

            System.out.println("✅ Usuarios de prueba creados: admin/admin123, usuario1/user123, usuario2/user123");
        }
    }
}
