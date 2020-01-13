package ch.heigvd.amt.unicorns.business;

import ch.heigvd.amt.unicorns.api.exceptions.ApiException;
import ch.heigvd.amt.unicorns.api.model.Magic;
import ch.heigvd.amt.unicorns.api.model.SimpleUnicorn;
import ch.heigvd.amt.unicorns.api.model.Unicorn;
import ch.heigvd.amt.unicorns.api.util.PayloadVerification;
import ch.heigvd.amt.unicorns.entities.MagicEntity;
import ch.heigvd.amt.unicorns.entities.UnicornEntity;
import ch.heigvd.amt.unicorns.repositories.UnicornRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class UnicornsService {

    @Autowired
    UnicornRepository unicornRepository;

    @Autowired
    PayloadVerification payloadVerification;

    /**
     * Add a new unicorn in the database
     *
     * @param unicorn The unicorn to add
     * @param creator The user creating the unicorn
     * @return A response code related to the result
     * @throws ApiException An exception in case of error during the process
     */
    public ResponseEntity<Void> addUnicorn(SimpleUnicorn unicorn, String creator) throws ApiException {
        UnicornEntity newUnicornEntity = toUnicornEntity(unicorn, creator);

        if (payloadVerification.checkPayloadIsValid(SimpleUnicorn.class, unicorn)) {
            if (!unicornRepository.existsByName(unicorn.getName())) {
                unicornRepository.save(newUnicornEntity);
                return new ResponseEntity<>(null, HttpStatus.CREATED);
            } else {
                throw new ApiException(HttpStatus.CONFLICT.value(), "");
            }
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST.value(), "");
        }

    }

    /**
     * Get the list of unicorns owned by the token bearer
     *
     * @param owner         The user that created the unicorns
     * @param pageNumber    The request current page number
     * @param numberPerPage The requested number of results per page
     * @return The result and the response code related to the result
     * @throws ApiException An exception in case of error during the process
     */
    public ResponseEntity<List<SimpleUnicorn>> getUnicorns(String owner, Integer pageNumber, Integer numberPerPage) throws ApiException {
        long numberOfUnicornsEntity = unicornRepository.countByEntityCreator(owner);
        List<UnicornEntity> unicorns = unicornRepository.getUnicornEntitiesByEntityCreator(owner, PageRequest.of(pageNumber - 1, numberPerPage, Sort.by("name").ascending()));
        List<SimpleUnicorn> simpleUnicorns = new ArrayList<>();

        for (UnicornEntity unicornEntity : unicorns) {
            simpleUnicorns.add(toSimpleUnicorn(unicornEntity));
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Pagination-NumberOfItems", String.valueOf(numberOfUnicornsEntity));
        if (numberOfUnicornsEntity > numberPerPage) {
            headers.add("Pagination-Next", "/unicorns?numberPerPage=10&pageNumber=" + (pageNumber + 1));
        }

        return new ResponseEntity<>(simpleUnicorns, headers, HttpStatus.OK);
    }

    /**
     * Get a unicorn by its name
     *
     * @param name          The name of the unicorn
     * @param owner         The owner of the unicorn
     * @param fullView      A boolean to specify if we want to see all the magics related to the unicorn or not
     * @param pageNumber    The request current page number
     * @param numberPerPage The requested number of results per page
     * @return The result and the response code related to the result
     * @throws ApiException An exception in case of error during the process
     */
    public ResponseEntity<Unicorn> getUnicornByName(String name, String owner, boolean fullView, Integer pageNumber, Integer numberPerPage) throws ApiException {
        //TODO utiliser les int
        UnicornEntity unicornEntity = unicornRepository.getUnicornEntityByName(name);
        if (unicornEntity != null) {
            if (unicornEntity.getEntityCreator().equals(owner)) {
                Unicorn fetchedUnicorn;
                if (fullView) {
                    fetchedUnicorn = toUnicorn(unicornEntity, unicornEntity.getMagicEntities());
                } else {
                    fetchedUnicorn = toUnicorn(unicornEntity, null);
                }
                return new ResponseEntity<>(fetchedUnicorn, HttpStatus.OK);

            } else {
                throw new ApiException(HttpStatus.FORBIDDEN.value(), "");
            }
        } else {
            throw new ApiException(HttpStatus.NOT_FOUND.value(), "");
        }
    }

    /**
     * Update an existing unicorn
     *
     * @param name    The name of the unicorn
     * @param unicorn The new unicorn object
     * @param owner   The owner of the unicorn
     * @return A response code related to the result
     * @throws ApiException An exception in case of error during the process
     */
    public ResponseEntity<Void> updateUnicorn(String name, SimpleUnicorn unicorn, String owner) throws ApiException {

        if (payloadVerification.checkPayloadIsValid(SimpleUnicorn.class, unicorn)) {
            UnicornEntity unicornEntity = unicornRepository.getUnicornEntityByName(name);

            if (unicornEntity != null) {
                if (unicornEntity.getEntityCreator().equals(owner)) {
                    unicornEntity.setColor(unicorn.getColor());
                    unicornEntity.setHasWings(unicorn.getHasWings());
                    unicornEntity.setSpeed(unicorn.getSpeed());
                    unicornRepository.save(unicornEntity);
                    return new ResponseEntity<>(null, HttpStatus.OK);
                } else {
                    throw new ApiException(HttpStatus.FORBIDDEN.value(), "");
                }
            } else {
                throw new ApiException(HttpStatus.NOT_FOUND.value(), "");
            }
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST.value(), "");
        }

    }

    /**
     * Delete a unicorn
     *
     * @param name  The name of the unicorn
     * @param owner The owner of the unicorn
     * @return A response code related to the result
     * @throws ApiException An exception in case of error during the process
     */
    public ResponseEntity<Void> deleteUnicorn(String name, String owner) throws ApiException {
        UnicornEntity unicornEntity = unicornRepository.getUnicornEntityByName(name);

        if (unicornEntity != null) {
            if (unicornEntity.getEntityCreator().equals(owner)) {
                unicornRepository.deleteByName(name);
                return new ResponseEntity<>(null, HttpStatus.OK);
            } else {
                throw new ApiException(HttpStatus.FORBIDDEN.value(), "");
            }
        } else {
            throw new ApiException(HttpStatus.NOT_FOUND.value(), "");
        }
    }


    /**
     * Convert a simple unicorn into a unicorn entity
     *
     * @param unicorn The simple unicorn
     * @param creator The creator of the unicorn
     * @return A unicorn entity
     */
    private UnicornEntity toUnicornEntity(SimpleUnicorn unicorn, String creator) {
        UnicornEntity entity = new UnicornEntity();
        entity.setName(unicorn.getName());
        entity.setColor(unicorn.getColor());
        entity.setHasWings(unicorn.getHasWings());
        entity.setSpeed(unicorn.getSpeed());
        entity.setEntityCreator(creator);
        return entity;
    }

    /**
     * Convert a unicorn entity into a unicorn
     *
     * @param entity        The unicorn entity
     * @param magicEntities The magic entities related to the unicorn
     * @return A unicorn object
     */
    private Unicorn toUnicorn(UnicornEntity entity, List<MagicEntity> magicEntities) {
        Unicorn unicorn = new Unicorn();
        unicorn.setName(entity.getName());
        unicorn.setColor(entity.getColor());
        unicorn.setHasWings(entity.getHasWings());
        unicorn.setSpeed(entity.getSpeed());

        if (magicEntities != null) {
            List<Magic> magics = new ArrayList<>();
            for (MagicEntity magicEntity : magicEntities) {
                Magic magic = new Magic();
                magic.setName(magicEntity.getName());
                magic.setPower(magicEntity.getPower());
                magic.setSpell(magicEntity.getSpell());
                magics.add(magic);
            }
            unicorn.setMagics(magics);
        }
        return unicorn;
    }

    /**
     * Convert a unicorn entity into a simple unicorn
     *
     * @param entity The unicorn entity
     * @return A simple unicorn object
     */
    private SimpleUnicorn toSimpleUnicorn(UnicornEntity entity) {
        SimpleUnicorn unicorn = new SimpleUnicorn();
        unicorn.setName(entity.getName());
        unicorn.setColor(entity.getColor());
        unicorn.setHasWings(entity.getHasWings());
        unicorn.setSpeed(entity.getSpeed());
        return unicorn;
    }
}
