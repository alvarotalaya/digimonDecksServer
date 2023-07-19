package net.ausiamarch.digimondecksSB.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.jsonwebtoken.io.IOException;
import net.ausiamarch.digimondecksSB.entity.ImageEntity;
import net.ausiamarch.digimondecksSB.helper.ValidationHelper;
import net.ausiamarch.digimondecksSB.repository.ImageRepository;
import net.ausiamarch.digimondecksSB.repository.CardRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
public class ImageService {
    
    @Autowired
    ImageRepository oImageRepository;

    @Autowired
    CardRepository oCardRepository;

    @Autowired
    AuthService oAuthService;

      public ResponseEntity<String> saveImage(String imageUrl, Long cardNumber) {
        try {
            // Descargar la imagen desde la URL
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<byte[]> response = restTemplate.getForEntity(imageUrl, byte[].class);
            byte[] imageBytes = response.getBody();

            // Guardar la imagen en la base de datos
            ImageEntity image = new ImageEntity();
            image.setData(imageBytes);
            image.setCard(oCardRepository.getById(cardNumber));
            oImageRepository.save(image);

            return ResponseEntity.ok("Imagen guardada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la imagen");
        }
    }

    public ImageEntity get(Long id){
        return oImageRepository.getById(id);
    }

    public Page<ImageEntity> getPage(Pageable oPageable, Long id_usertype) {
        ValidationHelper.validateRPP(oPageable.getPageSize());
            if (id_usertype == null) {
                return oImageRepository.findAll(oPageable);
            } else {
                return oImageRepository.findByCardId(id_usertype, oPageable);
            }
       
    }

    
}

