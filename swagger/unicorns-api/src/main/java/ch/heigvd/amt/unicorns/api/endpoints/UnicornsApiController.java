package ch.heigvd.amt.unicorns.api.endpoints;

import ch.heigvd.amt.unicorns.entities.UnicornEntity;
import ch.heigvd.amt.unicorns.repositories.UnicornRepository;
import ch.heigvd.amt.unicorns.api.UnicornsApi;
import ch.heigvd.amt.unicorns.api.model.Unicorn;
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
public class UnicornsApiController implements UnicornsApi {

    @Autowired
    UnicornRepository unicornRepository;

    public ResponseEntity<Object> addUnicorn(@ApiParam(value = "", required = true) @Valid @RequestBody Unicorn unicorn) {
        UnicornEntity newUnicornEntity = toUnicornEntity(unicorn);
        unicornRepository.save(newUnicornEntity);
        Long id = newUnicornEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newUnicornEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    public ResponseEntity<List<Unicorn>> getUnicorns() {
        List<Unicorn> unicorns = new ArrayList<>();
        for (UnicornEntity unicornEntity : unicornRepository.findAll()) {
            unicorns.add(toUnicorn(unicornEntity));
        }
        /*
        Unicorn staticUnicorn = new Unicorn();
        staticUnicorn.setColour("red");
        staticUnicorn.setKind("banana");
        staticUnicorn.setSize("medium");
        List<Unicorn> unicorns = new ArrayList<>();
        unicorns.add(staticUnicorn);
        */
        return ResponseEntity.ok(unicorns);
    }

    public ResponseEntity<Unicorn> getUnicornByName(@ApiParam(value = "", required = true) @PathVariable("name") String name, @ApiParam(value = "", defaultValue = "false") @Valid @RequestParam(value = "fullView", required = false, defaultValue = "false") Boolean fullView) {
        return null;
    }

    public ResponseEntity<Void> updateUnicorn(@ApiParam(value = "", required = true) @PathVariable("name") String name, @ApiParam(value = "", required = true) @Valid @RequestBody Unicorn unicorn) {
        return null;
    }

    public ResponseEntity<Void> deleteUnicorn(@ApiParam(value = "", required = true) @PathVariable("name") String name) {
        return null;
    }


    private UnicornEntity toUnicornEntity(Unicorn unicorn) {
        UnicornEntity entity = new UnicornEntity();
        entity.setName(unicorn.getName());
        entity.setColor(unicorn.getColor());
        entity.setHasWings(unicorn.getHasWings());
        entity.setSpeed(unicorn.getSpeed());
        return entity;
    }

    private Unicorn toUnicorn(UnicornEntity entity) {
        Unicorn unicorn = new Unicorn();
        unicorn.setName(entity.getName());
        unicorn.setColor(entity.getColor());
        unicorn.setHasWings(entity.getHasWings());
        unicorn.setSpeed(entity.getSpeed());
        return unicorn;
    }

}
