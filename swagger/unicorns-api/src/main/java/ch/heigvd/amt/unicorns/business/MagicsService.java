package ch.heigvd.amt.unicorns.business;

import ch.heigvd.amt.unicorns.api.exceptions.ApiException;
import ch.heigvd.amt.unicorns.api.model.Magic;
import ch.heigvd.amt.unicorns.api.model.SimpleMagic;
import ch.heigvd.amt.unicorns.api.model.SimpleUnicorn;
import ch.heigvd.amt.unicorns.api.model.Unicorn;
import ch.heigvd.amt.unicorns.entities.MagicEntity;
import ch.heigvd.amt.unicorns.entities.UnicornEntity;
import ch.heigvd.amt.unicorns.repositories.MagicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MagicsService {

    @Autowired
    MagicRepository magicRepository;

    public ResponseEntity<Void> addUnicorn(SimpleUnicorn unicorn, String creator) throws ApiException {
        return null;
    }

    public ResponseEntity<List<SimpleUnicorn>> getUnicorns(String owner) throws ApiException {
        return null;
    }

    public ResponseEntity<Unicorn> getUnicornByName(String name, String owner, boolean fullView) throws ApiException {
        return null;
    }


    private MagicEntity toMagicEntity(SimpleMagic magic, String creator) {
        MagicEntity entity = new MagicEntity();
        entity.setName(magic.getName());
        entity.setPower(magic.getPower());
        entity.setSpell(magic.getSpell());
        entity.setEntityCreator(creator);
        return entity;
    }

    private Magic toMagic(MagicEntity entity, List<UnicornEntity> unicornEntities) {
        Magic magic = new Magic();
        magic.setName(entity.getName());
        magic.setPower(entity.getPower());
        magic.setSpell(entity.getSpell());

        List<Unicorn> unicorns = new ArrayList<>();
        for (UnicornEntity unicornEntity : unicornEntities) {
            Unicorn unicorn = new Unicorn();
            unicorn.setName(unicornEntity.getName());
            unicorn.setColor(unicornEntity.getColor());
            unicorn.setSpeed(unicornEntity.getSpeed());
            unicorn.setHasWings(unicornEntity.getHasWings());
            unicorn.setMagics(null);
            unicorns.add(unicorn);
        }
        magic.setUnicorns(unicorns);
        return magic;
    }

    private SimpleMagic toSimpleMagic(MagicEntity entity) {
        SimpleMagic magic = new SimpleMagic();
        magic.setName(entity.getName());
        magic.setPower(entity.getPower());
        magic.setSpell(entity.getSpell());
        return magic;
    }
}
