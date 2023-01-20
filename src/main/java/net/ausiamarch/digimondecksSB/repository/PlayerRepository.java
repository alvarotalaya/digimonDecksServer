package net.ausiamarch.digimondecksSB.repository;

import net.ausiamarch.digimondecksSB.entity.PlayerEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    PlayerEntity findByEmailAndPassword(String email, String password); //oAuth

    boolean existsByEmail(String email);

    PlayerEntity findByEmail(String email);

    Page<PlayerEntity> findByUsertypeId(Long id_usertype, Pageable oPageable);

    Page<PlayerEntity> findByNameIgnoreCaseContainingOrEmailIgnoreCaseContaining(String strFilterName, String strFilterEmail, Pageable oPageable);

    Page<PlayerEntity> findByNameIgnoreCaseContainingOrEmailIgnoreCaseContainingAndUsertypeId(String strFilterName, String strFilterEmail, Long id_usertype, Pageable oPageable);
    
}
