package ch.heigvd.amt.unicorns.business;

import ch.heigvd.amt.unicorns.api.exceptions.ApiException;
import ch.heigvd.amt.unicorns.api.model.SimpleUnicorn;
import ch.heigvd.amt.unicorns.entities.UnicornEntity;
import ch.heigvd.amt.unicorns.repositories.UnicornRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UnicornsService {

    @Autowired
    UnicornRepository unicornRepository;

    public ResponseEntity<Void> addUnicorn(SimpleUnicorn unicorn, String creator) throws ApiException {
        UnicornEntity newUnicornEntity = toUnicornEntity(unicorn, creator);

        if (!unicornRepository.existsByName(unicorn.getName())) {
            unicornRepository.save(newUnicornEntity);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } else {
            throw new ApiException(HttpStatus.CONFLICT.value(), "");
        }
    }

    public ResponseEntity<List<SimpleUnicorn>> getUnicorns(String owner) throws ApiException {
        List<UnicornEntity> unicorns = unicornRepository.getUnicornEntitiesByEntityCreator(owner);
        List <SimpleUnicorn> simpleUnicorns = new ArrayList<>();

        for (UnicornEntity unicornEntity : unicorns) {
            simpleUnicorns.add(toSimpleUnicorn(unicornEntity));
        }

        return new ResponseEntity<>(simpleUnicorns, HttpStatus.OK);
    }

    private UnicornEntity toUnicornEntity(SimpleUnicorn unicorn, String creator) {
        UnicornEntity entity = new UnicornEntity();
        entity.setName(unicorn.getName());
        entity.setColor(unicorn.getColor());
        entity.setHasWings(unicorn.getHasWings());
        entity.setSpeed(unicorn.getSpeed());
        entity.setEntityCreator(creator);
        return entity;
    }

    private SimpleUnicorn toSimpleUnicorn(UnicornEntity entity) {
        SimpleUnicorn unicorn = new SimpleUnicorn();
        unicorn.setName(entity.getName());
        unicorn.setColor(entity.getColor());
        unicorn.setHasWings(entity.getHasWings());
        unicorn.setSpeed(entity.getSpeed());
        return unicorn;
    }
}
