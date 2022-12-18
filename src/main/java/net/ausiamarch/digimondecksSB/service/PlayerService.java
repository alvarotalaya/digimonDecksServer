package net.ausiamarch.digimondecksSB.service;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import net.ausiamarch.digimondecksSB.exception.ResourceNotFoundException;
import net.ausiamarch.digimondecksSB.exception.ResourceNotModifiedException;
import net.ausiamarch.digimondecksSB.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiamarch.digimondecksSB.entity.PlayerEntity;
import net.ausiamarch.digimondecksSB.exception.CannotPerformOperationException;
import net.ausiamarch.digimondecksSB.helper.RandomHelper;
import net.ausiamarch.digimondecksSB.helper.ValidationHelper;
import net.ausiamarch.digimondecksSB.repository.PlayerRepository;
import net.ausiamarch.digimondecksSB.repository.UsertypeRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class PlayerService {
    
    @Autowired
    PlayerRepository oPlayerRepository;

    @Autowired
    UsertypeRepository oUsertypeRepository;

    @Autowired
    UsertypeService oUsertypeService;

    @Autowired
    AuthService oAuthService;

    String DIGIMON_DEFAULT_PASSWORD = "DIGIMON_DECKS";
    private final List<String> names = List.of("Ainhoa", "Kevin", "Estefania", "Cristina",
    "Jose Maria", "Lucas Ezequiel", "Carlos", "Elliot", "Alexis", "Ruben", "Luis Fernando", "Karim", "Luis",
    "Jose David", "Nerea", "Ximo", "Iris", "Alvaro", "Mario", "Raimon");

    public void validate(PlayerEntity oPlayerEntity) {
        ValidationHelper.validateStringLength(oPlayerEntity.getName(), 2, 50, "campo name de Player(el campo debe tener longitud de 2 a 50 caracteres)");
        ValidationHelper.validateEmail(oPlayerEntity.getEmail(), "campo email de Player");
        if (oPlayerRepository.existsByEmail(oPlayerEntity.getEmail())) {
            throw new ValidationException("este email ya se esta usando en otra cuenta");
        }
        oUsertypeService.validate(oPlayerEntity.getUsertype().getId());
    }

    public void validate(Long id) {
        if (!oPlayerRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public PlayerEntity get(Long id) {
        oAuthService.OnlyAdminsOrOwnUsersData(id);
        validate(id);
        return oPlayerRepository.getById(id);
    }

    public Page<PlayerEntity> getPage(Pageable oPageable, String strFilter, Long id_usertype) {
        oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        if (strFilter == null || strFilter.length() == 0) {
            if (id_usertype == null) {
                return oPlayerRepository.findAll(oPageable);
            } else {
                return oPlayerRepository.findByUsertypeId(id_usertype, oPageable);
            }
        } else {
            if (id_usertype == null) {
                return oPlayerRepository.findByNameIgnoreCase(strFilter, oPageable);
            } else {
                return oPlayerRepository.findByNameIgnoreCaseAndUsertypeId(strFilter, id_usertype, oPageable);
            }
        }
     }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oPlayerRepository.count();
    }

    public Long update(PlayerEntity oPlayerEntity) {
        validate(oPlayerEntity.getId());
        oAuthService.OnlyAdmins();
        PlayerEntity oOldPlayerEntity=oPlayerRepository.getById(oPlayerEntity.getId());
        oPlayerEntity.setPassword(oOldPlayerEntity.getPassword());
        oPlayerEntity.setUsertype(oOldPlayerEntity.getUsertype());
        return oPlayerRepository.save(oPlayerEntity).getId();
    }

    public Long create(PlayerEntity oNewPlayerEntity) {
        oAuthService.OnlyAdmins();
        validate(oNewPlayerEntity);
        oNewPlayerEntity.setId(0L);
        oNewPlayerEntity.setPassword(DIGIMON_DEFAULT_PASSWORD);

        return oPlayerRepository.save(oNewPlayerEntity).getId();
    }

    public Long delete(Long id) {
        oAuthService.OnlyAdmins();
        validate(id);
        oPlayerRepository.deleteById(id);
        if (oPlayerRepository.existsById(id)) {
            throw new ResourceNotModifiedException("can't remove register " + id);
        } else {
            return id;
        }
    }

    private PlayerEntity generatePlayer() {
        PlayerEntity oPlayerEntity = new PlayerEntity();

        oPlayerEntity.setName(names.get(RandomHelper.getRandomInt(0, names.size() - 1)));
        oPlayerEntity.setPassword(DIGIMON_DEFAULT_PASSWORD);
        oPlayerEntity.setEmail(RandomHelper.getRandomInt(0, 999999) + "@test.com");
        int totalUsertypes = (int) oUsertypeRepository.count();
        int randomUserTypeId = RandomHelper.getRandomInt(1, totalUsertypes);
        oUsertypeRepository.findById((long) randomUserTypeId)
        .ifPresent(oPlayerEntity::setUsertype);

        return oPlayerEntity;
    }

    public PlayerEntity generateOne() {
        oAuthService.OnlyAdmins();
        return oPlayerRepository.save(generatePlayer());
    }

    public Long generateSome(Long amount) {
        oAuthService.OnlyAdmins();
        List<PlayerEntity> PlayerToSave = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            PlayerToSave.add(generatePlayer());
        }
        oPlayerRepository.saveAll(PlayerToSave);
        return oPlayerRepository.count();
    }
}

