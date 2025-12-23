package com.example.coltros.Exception;

import com.example.coltros.DTO.filter.TransporteurFilterDTO;
import com.example.coltros.DTO.Request.TransporteurRequest;
import com.example.coltros.DTO.Request.TransporteurUpdateRequest;
import com.example.coltros.DTO.Reponse.TransporteurResponse;
import com.example.coltros.Mapper.TransporteurMapper;
import com.example.coltros.enums.StatutColis;
import com.example.coltros.entity.Transporteur;
import com.example.coltros.entity.User;
import com.example.coltros.Repository.ColisRepository;
import com.example.coltros.Repository.TransporteurRepository;
import com.example.coltros.Repository.UserRepository;
import com.example.coltros.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TransporteurRepository transporteurRepository;
    private final ColisRepository colisRepository;
    private final TransporteurMapper transporteurMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Object findAllTransporteurs(TransporteurFilterDTO filter) {
        Pageable pageable = filter.toPageable();

        if (filter.getSpecialite() != null) {
            return transporteurRepository.findBySpecialite(filter.getSpecialite(), pageable)
                    .map(this::enrichTransporteurDTO);
        }

        return userRepository.findAllTransporteurs(pageable)
                .map(user -> enrichTransporteurDTO((Transporteur) user));
    }

    private TransporteurResponse enrichTransporteurDTO(Transporteur transporteur) {
        TransporteurResponse dto = transporteurMapper.toDTO(transporteur);
        Long nombreColis = colisRepository.countByTransporteurIdAndStatuts(
                transporteur.getId(), StatutColis.EN_TRANSIT);
        dto.setNombreColisEnLivraison(nombreColis);
        return dto;
    }

    @Override
    public Page<TransporteurResponse> findTransporteursBySpecialite(String specialite, Pageable pageable) {
        return transporteurRepository.findBySpecialite(
                        com.example.coltros.enums.Specialite.valueOf(specialite), pageable)
                .map(this::enrichTransporteurDTO);
    }

    @Override
    @Transactional
    public TransporteurResponse createTransporteur(TransporteurRequest dto) {
        if (userRepository.existsByLogin(dto.getLogin())) {
            throw new ValidationException("Le login existe déjà");
        }

        Transporteur transporteur = transporteurMapper.toEntity(dto);
        transporteur.setPassword(passwordEncoder.encode(dto.getPassword()));

        Transporteur saved = transporteurRepository.save(transporteur);
        return enrichTransporteurDTO(saved);
    }

    @Override
    @Transactional
    public TransporteurResponse updateTransporteur(String id, TransporteurUpdateRequest dto) {
        Transporteur transporteur = transporteurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transporteur non trouvé"));

        transporteurMapper.updateEntity(dto, transporteur);

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            transporteur.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getActive() != null) {
            transporteur.setActive(dto.getActive());
        }

        Transporteur updated = transporteurRepository.save(transporteur);
        return enrichTransporteurDTO(updated);
    }

    @Override
    @Transactional
    public void deleteTransporteur(String id) {
        Transporteur transporteur = transporteurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transporteur non trouvé"));

        // Vérifier si le transporteur a des colis en cours
        Long colisEnCours = colisRepository.countByTransporteurIdAndStatuts(
                id, StatutColis.EN_TRANSIT);

        if (colisEnCours > 0) {
            throw new ValidationException(
                    "Impossible de supprimer le transporteur, il a des colis en cours de livraison"
            );
        }

        transporteurRepository.delete(transporteur);
    }

    @Override
    @Transactional
    public TransporteurResponse reactiverTransporteur(String id) {
        Transporteur transporteur = transporteurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transporteur non trouvé"));

        transporteur.setActive(true);
        Transporteur updated = transporteurRepository.save(transporteur);

        return enrichTransporteurDTO(updated);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
    }

    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}