package ch.heigvd.amt.unicorns.business;

import ch.heigvd.amt.unicorns.api.exceptions.ApiException;
import ch.heigvd.amt.unicorns.api.model.Magic;
import ch.heigvd.amt.unicorns.api.model.SimpleMagic;
import ch.heigvd.amt.unicorns.api.model.Unicorn;
import ch.heigvd.amt.unicorns.entities.MagicEntity;
import ch.heigvd.amt.unicorns.entities.UnicornEntity;
import ch.heigvd.amt.unicorns.repositories.MagicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MagicsService {

    @Autowired
    MagicRepository magicRepository;

    /**
     * Add a new magic in the database
     * @param magic The magic to add
     * @param creator The user creating the magic
     * @return A response code related to the result
     * @throws ApiException An exception in case of error during the process
     */
    public ResponseEntity<Void> addMagic(SimpleMagic magic, String creator) throws ApiException {
        MagicEntity newMagicEntity = toMagicEntity(magic, creator);

        if (!magicRepository.existsByName(magic.getName())) {
            magicRepository.save(newMagicEntity);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } else {
            throw new ApiException(HttpStatus.CONFLICT.value(), "");
        }
    }

    /**
     * Get the list of magics owned by the token bearer
     * @param owner The user that created the magics
     * @param pageNumber
     * @param numberPerPage
     * @return The result and the response code related to the result
     * @throws ApiException An exception in case of error during the process
     */
    public ResponseEntity<List<SimpleMagic>> getMagics(String owner, Integer pageNumber, Integer numberPerPage) throws ApiException {
        //TODO utiliser les int
        List<MagicEntity> magics = magicRepository.getMagicEntitiesByEntityCreator(owner);
        List<SimpleMagic> simpleMagics = new ArrayList<>();

        for (MagicEntity magicEntity : magics) {
            simpleMagics.add(toSimpleMagic(magicEntity));
        }

        return new ResponseEntity<>(simpleMagics, HttpStatus.OK);
    }

    /**
     * Get a magic by its name
     * @param name The name of the magic
     * @param owner The owner of the magic
     * @param fullView A boolean to specify if we want to see all the unicorns related to the magic or not
     * @param pageNumber The request current page number
     * @param numberPerPage The requested number of results per page
     * @return The result and the response code related to the result
     * @throws ApiException An exception in case of error during the process
     */
    public ResponseEntity<Magic> getMagicByName(String name, String owner, boolean fullView, Integer pageNumber, Integer numberPerPage) throws ApiException {
        //TODO utiliser les int
        MagicEntity magicEntity = magicRepository.getMagicEntityByName(name);
        if (magicEntity != null) {
            if (magicEntity.getEntityCreator().equals(owner)) {
                Magic fetchedMagic;
                if (fullView) {
                    fetchedMagic = toMagic(magicEntity, magicEntity.getUnicornEntities());
                } else {
                    fetchedMagic = toMagic(magicEntity, null);
                }
                return new ResponseEntity<>(fetchedMagic, HttpStatus.OK);

            } else {
                throw new ApiException(HttpStatus.FORBIDDEN.value(), "");
            }
        } else {
            throw new ApiException(HttpStatus.NOT_FOUND.value(), "");
        }
    }

    /**
     * Update an existing magic
     * @param name The name of the magic
     * @param magic The new magic object
     * @param owner The owner of the magic
     * @return A response code related to the result
     * @throws ApiException An exception in case of error during the process
     */
    public ResponseEntity<Void> updateMagic(String name, SimpleMagic magic, String owner) throws ApiException {
        MagicEntity magicEntity = magicRepository.getMagicEntityByName(name);
        if (magicEntity != null) {
            if (magicEntity.getEntityCreator().equals(owner)) {
                magicEntity.setPower(magic.getPower());
                magicEntity.setSpell(magic.getSpell());
                magicRepository.save(magicEntity);
                return new ResponseEntity<>(null, HttpStatus.CREATED);
            } else {
                throw new ApiException(HttpStatus.FORBIDDEN.value(), "");
            }
        } else {
            throw new ApiException(HttpStatus.NOT_FOUND.value(), "");
        }
    }


    /**
     * Convert a simple magic into a magic entity
     * @param magic The simple magic
     * @param creator The creator of the magic
     * @return A magic entity
     */
    private MagicEntity toMagicEntity(SimpleMagic magic, String creator) {
        MagicEntity entity = new MagicEntity();
        entity.setName(magic.getName());
        entity.setPower(magic.getPower());
        entity.setSpell(magic.getSpell());
        entity.setEntityCreator(creator);
        return entity;
    }

    /**
     * Convert a magic entity into a magic
     * @param entity The magic entity
     * @param unicornEntities The unicorn entities related to the magic
     * @return A magic object
     */
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

    /**
     * Convert a magic entity into a simple magic
     * @param entity The magic entity
     * @return A simple magic object
     */
    private SimpleMagic toSimpleMagic(MagicEntity entity) {
        SimpleMagic magic = new SimpleMagic();
        magic.setName(entity.getName());
        magic.setPower(entity.getPower());
        magic.setSpell(entity.getSpell());
        return magic;
    }
}
