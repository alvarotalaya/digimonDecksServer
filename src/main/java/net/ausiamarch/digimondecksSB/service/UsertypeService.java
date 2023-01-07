package net.ausiamarch.digimondecksSB.service;

import net.ausiamarch.digimondecksSB.entity.UsertypeEntity;
import net.ausiamarch.digimondecksSB.exception.ResourceNotFoundException;
import net.ausiamarch.digimondecksSB.exception.ResourceNotModifiedException;
import net.ausiamarch.digimondecksSB.helper.ValidationHelper;
import net.ausiamarch.digimondecksSB.exception.ValidationException;
import net.ausiamarch.digimondecksSB.repository.UsertypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsertypeService {

    private final UsertypeRepository oUsertypeRepository;
    private final AuthService oAuthService;

    public UsertypeService(UsertypeRepository oUsertypeRepository, AuthService oAuthService) {
        this.oUsertypeRepository = oUsertypeRepository;
        this.oAuthService = oAuthService;
    }

    public void validate(UsertypeEntity oUsertypeEntity) {
        ValidationHelper.validateStringLength(oUsertypeEntity.getType(), 2, 50, "campo name de Usertype(el campo debe tener longitud de 2 a 50 caracteres)");
        if (oUsertypeRepository.existsByType(oUsertypeEntity.getType())) {
            throw new ValidationException("tthis type already exists");
        }
    }

    public UsertypeEntity get(Long id) {
        oAuthService.OnlyAdmins();
        return oUsertypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserType with id: " + id + " not found"));
    }

    public Page<UsertypeEntity> getPage(Pageable oPageable, String strFilter) {
        oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        if (strFilter == null || strFilter.length() == 0) {
                return oUsertypeRepository.findAll(oPageable);
            } else {
                return oUsertypeRepository.findByType(strFilter, oPageable);
            }
        }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oUsertypeRepository.count();
    }

    public Page<UsertypeEntity> getPage(int page, int size) {
        oAuthService.OnlyAdmins();
        Pageable oPageable = PageRequest.of(page, size);
        
            return oUsertypeRepository.findAll(oPageable);
    }

    public Long update(UsertypeEntity oUsertypeEntity) {
        oAuthService.OnlyAdmins();
        validate(oUsertypeEntity.getId());
        oUsertypeRepository.save(oUsertypeEntity);
        return oUsertypeEntity.getId();
    }

    public Long create(UsertypeEntity oNewUsertypeEntity) {
        oAuthService.OnlyAdmins();
        validate(oNewUsertypeEntity);
        oNewUsertypeEntity.setId(0L);

        return oUsertypeRepository.save(oNewUsertypeEntity).getId();
    }

    public Long delete(Long id) {
        oAuthService.OnlyAdmins();
        validate(id);
        oUsertypeRepository.deleteById(id);
        if (oUsertypeRepository.existsById(id)) {
            throw new ResourceNotModifiedException("can't remove register " + id);
        } else {
            return id;
        }
    }
    
    public void validate(Long id) {
        if (!oUsertypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }
}
