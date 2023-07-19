package net.ausiamarch.digimondecksSB.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import net.ausiamarch.digimondecksSB.entity.ImageEntity;
import net.ausiamarch.digimondecksSB.service.ImageService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ImageService oImageService;

    @GetMapping("/{id}")
    public ResponseEntity<ImageEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<ImageEntity>(oImageService.get(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ImageEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(value = "id_card", required = false) Long id_card) {
        return new ResponseEntity<>(oImageService.getPage(oPageable, id_card), HttpStatus.OK);
    }
}
