package net.ausiamarch.digimondecksSB.repository;

import net.ausiamarch.digimondecksSB.entity.PlayerEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    PlayerEntity findByEmailAndPassword(String email, String password); //oAuth

    boolean existsByEmail(String email);

    Page<PlayerEntity> findByUsertypeId(Long id_usertype, Pageable oPageable);

    Page<PlayerEntity> findByNameIgnoreCase(String strFilterName, Pageable oPageable);

    Page<PlayerEntity> findByNameIgnoreCaseAndUsertypeId(String strFiletName, Long id, Pageable oPageable);
    
}
