package com.example.coltros.init;

import com.example.coltros.entity.Admin;
import com.example.coltros.entity.Transporteur;
import com.example.coltros.enums.Specialite;
import com.example.coltros.Repository.AdminRepository;
import com.example.coltros.Repository.TransporteurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final TransporteurRepository transporteurRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Créer un admin par défaut
        if (adminRepository.count() == 0) {
            Admin admin = new Admin("admin", passwordEncoder.encode("admin123"));
            adminRepository.save(admin);
        }

        // Créer des transporteurs de test
        if (transporteurRepository.count() == 0) {
            createTransporteur("transporteur1", "trans123", Specialite.STANDARD);
            createTransporteur("transporteur2", "trans123", Specialite.FRAGILE);
            createTransporteur("transporteur3", "trans123", Specialite.FRIGO);
        }
    }

    private void createTransporteur(String login, String password, Specialite specialite) {
        Transporteur transporteur = new Transporteur(login, passwordEncoder.encode(password), specialite);
        transporteurRepository.save(transporteur);
    }
}