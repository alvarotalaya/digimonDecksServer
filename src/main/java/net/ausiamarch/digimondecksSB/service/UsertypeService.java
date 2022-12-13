package net.ausiamarch.digimondecksSB.service;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import net.ausiamarch.digimondecksSB.exception.ResourceNotFoundException;
import net.ausiamarch.digimondecksSB.exception.ResourceNotModifiedException;
import net.ausiamarch.digimondecksSB.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiamarch.digimondecksSB.exception.CannotPerformOperationException;
import net.ausiamarch.digimondecksSB.helper.RandomHelper;
import net.ausiamarch.digimondecksSB.helper.ValidationHelper;
import net.ausiamarch.digimondecksSB.repository.UsertypeRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class UsertypeService {

    UsertypeRepository oUsertypeRepository;

    @Autowired
    UsertypeService oUsertypeService;

    public void validate(Long id) {
        if (!oUsertypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }
}
