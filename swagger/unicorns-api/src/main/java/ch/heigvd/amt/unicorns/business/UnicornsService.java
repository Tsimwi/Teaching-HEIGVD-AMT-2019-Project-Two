package ch.heigvd.amt.unicorns.business;

import ch.heigvd.amt.unicorns.api.exceptions.ApiException;
import ch.heigvd.amt.unicorns.api.model.Unicorn;
import ch.heigvd.amt.unicorns.entities.UnicornEntity;
import ch.heigvd.amt.unicorns.repositories.UnicornRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UnicornsService {

    @Autowired
    UnicornRepository unicornRepository;

    public ResponseEntity<Void> addUnicorn(Unicorn unicorn) throws ApiException {
        UnicornEntity newUnicornEntity = toUnicornEntity(unicorn);
        if (!unicornRepository.existsByName(unicorn.getName())) {
            unicornRepository.save(newUnicornEntity);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } else {
            throw new ApiException(HttpStatus.CONFLICT.value(), "");
        }
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
