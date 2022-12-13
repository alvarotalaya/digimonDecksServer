package net.ausiamarch.digimondecksSB.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import net.ausiamarch.digimondecksSB.entity.PlayerEntity;
import net.ausiamarch.digimondecksSB.service.PlayerService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    PlayerService oPlayerService;

    @GetMapping("/{id}")
    public ResponseEntity<PlayerEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<PlayerEntity>(oPlayerService.get(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<PlayerEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter,
            @RequestParam(value = "usertype", required = false) Long id_usertype) {
        return new ResponseEntity<>(oPlayerService.getPage(oPageable, strFilter, id_usertype), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oPlayerService.count(), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Long> update(@RequestBody PlayerEntity oPlayerEntity) {
        return new ResponseEntity<Long>(oPlayerService.update(oPlayerEntity), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody PlayerEntity oNewPlayerEntity) {
        return new ResponseEntity<Long>(oPlayerService.create(oNewPlayerEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oPlayerService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/generate")
    public ResponseEntity<PlayerEntity> generateOne() {
        return new ResponseEntity<>(oPlayerService.generateOne(), HttpStatus.OK);
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<Long> generateSome(@PathVariable Long amount) {
        return new ResponseEntity<>(oPlayerService.generateSome(amount), HttpStatus.OK);
    }
    
}
