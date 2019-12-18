package ch.heigvd.amt.unicorns.api.endpoints;

import ch.heigvd.amt.unicorns.api.MagicsApi;
import ch.heigvd.amt.unicorns.api.model.Magic;
import ch.heigvd.amt.unicorns.entities.MagicEntity;
import ch.heigvd.amt.unicorns.repositories.MagicRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class MagicsApiController implements MagicsApi {

    @Autowired
    MagicRepository magicRepository;

    public ResponseEntity<Object> addMagic(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Magic magic) {        MagicEntity newMagicEntity = toMagicEntity(magic);
        magicRepository.save(newMagicEntity);
        Long id = newMagicEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newMagicEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    public ResponseEntity<List<Magic>> getMagics() {
        List<Magic> magics = new ArrayList<>();
        for (MagicEntity magicEntity : magicRepository.findAll()) {
            magics.add(toMagic(magicEntity));
        }
        /*
        Magic staticMagic = new Magic();
        staticMagic.setColour("red");
        staticMagic.setKind("banana");
        staticMagic.setSize("medium");
        List<Magic> magics = new ArrayList<>();
        magics.add(staticMagic);
        */
        return ResponseEntity.ok(magics);
    }

    public ResponseEntity<Magic> getMagicByName(@ApiParam(value = "",required=true) @PathVariable("name") String name, @ApiParam(value = "", defaultValue = "false") @Valid @RequestParam(value = "fullView", required = false, defaultValue="false") Boolean fullView) {
        return null;
    }

    public ResponseEntity<Void> deleteMagic(@ApiParam(value = "",required=true) @PathVariable("name") String name) {
        return null;
    }

    public ResponseEntity<Void> updateMagic(@ApiParam(value = "",required=true) @PathVariable("name") String name,@ApiParam(value = "" ,required=true )  @Valid @RequestBody Magic magic) {
        return null;
    }

    private MagicEntity toMagicEntity(Magic magic) {
        MagicEntity entity = new MagicEntity();
        entity.setName(magic.getName());
        entity.setPower(magic.getPower());
        entity.setSpell(magic.getSpell());
        return entity;
    }

    private Magic toMagic(MagicEntity entity) {
        Magic magic = new Magic();
        magic.setName(entity.getName());
        magic.setPower(entity.getPower());
        magic.setSpell(entity.getSpell());
        return magic;
    }

}