package com.example.coltros.Service;

import com.example.coltros.DTO.filter.TransporteurFilterDTO;
import com.example.coltros.DTO.Request.TransporteurRequest;
import com.example.coltros.DTO.Request.TransporteurUpdateRequest;
import com.example.coltros.DTO.Reponse.TransporteurResponse;
import com.example.coltros.entity.Transporteur;
import com.example.coltros.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {

    // Transporteurs
    Page<TransporteurResponse> findAllTransporteurs(TransporteurFilterDTO filter);

//    TransporteurResponse findTransporteurById(String id);

    TransporteurResponse createTransporteur(TransporteurRequest dto);

    TransporteurResponse updateTransporteur(String id, TransporteurUpdateRequest dto);

    void deleteTransporteur(String id);

    TransporteurResponse reactiverTransporteur(String id);

    // Users
    User findByLogin(String login);

    User findById(String id);

//    Page<User> findAllUsers(int page, int size);

    // Utilitaires
//    Transporteur getTransporteurEntityById(String id);

//    void validerTransporteurDisponible(Transporteur transporteur);
}