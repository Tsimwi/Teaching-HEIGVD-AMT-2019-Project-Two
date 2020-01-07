package ch.heigvd.amt.unicorns.api.endpoints;

import ch.heigvd.amt.unicorns.api.MagicsApi;
import ch.heigvd.amt.unicorns.api.model.Magic;
import ch.heigvd.amt.unicorns.api.model.SimpleMagic;
import ch.heigvd.amt.unicorns.entities.MagicEntity;
import ch.heigvd.amt.unicorns.repositories.MagicRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class MagicsApiController implements MagicsApi {

    @Autowired
    MagicRepository magicRepository;

    public ResponseEntity<Void> addMagic(@ApiParam(value = "" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization, @ApiParam(value = "" ,required=true )  @Valid @RequestBody Magic magic) {
        MagicEntity newMagicEntity = toMagicEntity(magic);
        magicRepository.save(newMagicEntity);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }


    public ResponseEntity<List<SimpleMagic>> getMagics(@ApiParam(value = "", defaultValue = "1.0d") @Valid @RequestParam(value = "pageNumber", required = false, defaultValue="1.0d") BigDecimal pageNumber, @ApiParam(value = "", defaultValue = "10.0d") @Valid @RequestParam(value = "numberPerPage", required = false, defaultValue="10.0d") BigDecimal numberPerPage) {
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
        return null;
//        return ResponseEntity.ok(magics);
    }

    public ResponseEntity<Magic> getMagicByName(@ApiParam(value = "",required=true) @PathVariable("name") String name, @ApiParam(value = "", defaultValue = "false") @Valid @RequestParam(value = "fullView", required = false, defaultValue="false") Boolean fullView, @ApiParam(value = "", defaultValue = "1.0d") @Valid @RequestParam(value = "pageNumber", required = false, defaultValue="1.0d") BigDecimal pageNumber, @ApiParam(value = "", defaultValue = "10.0d") @Valid @RequestParam(value = "numberPerPage", required = false, defaultValue="10.0d") BigDecimal numberPerPage) {
        return null;
    }

    public ResponseEntity<Void> deleteMagic(@ApiParam(value = "" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "",required=true) @PathVariable("name") String name) {
        return null;
    }

    public ResponseEntity<Void> updateMagic(@ApiParam(value = "" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "",required=true) @PathVariable("name") String name,@ApiParam(value = "" ,required=true )  @Valid @RequestBody Magic magic) {
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
