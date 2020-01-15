package ch.heigvd.amt.unicorns.business;

import ch.heigvd.amt.unicorns.api.exceptions.ApiException;
import ch.heigvd.amt.unicorns.api.model.Magicorn;
import ch.heigvd.amt.unicorns.api.util.PayloadVerification;
import ch.heigvd.amt.unicorns.entities.MagicEntity;
import ch.heigvd.amt.unicorns.entities.UnicornEntity;
import ch.heigvd.amt.unicorns.repositories.MagicRepository;
import ch.heigvd.amt.unicorns.repositories.UnicornRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class MagicornService {

    @Autowired
    UnicornRepository unicornRepository;

    @Autowired
    MagicRepository magicRepository;

    @Autowired
    PayloadVerification payloadVerification;

    public ResponseEntity<Void> changeMagicorn(Magicorn magicorn, String owner) throws ApiException {
        if (payloadVerification.checkPayloadIsValid(Magicorn.class, magicorn)) {
            UnicornEntity unicornEntity = unicornRepository.getUnicornEntityByName(magicorn.getUnicorn());
            MagicEntity magicEntity = magicRepository.getMagicEntityByName(magicorn.getMagic());

            if (unicornEntity != null && magicEntity != null) {
                if (unicornEntity.getEntityCreator().equals(owner) && magicEntity.getEntityCreator().equals(owner)) {
                    switch (magicorn.getOperation().toLowerCase()) {
                        case "add":
                            if (!unicornEntity.getMagicEntities().contains(magicEntity)
                            && !magicEntity.getUnicornEntities().contains(unicornEntity)) {
                                unicornEntity.getMagicEntities().add(magicEntity);
                                magicEntity.getUnicornEntities().add(unicornEntity);
                                unicornRepository.save(unicornEntity);
                                magicRepository.save(magicEntity);
                                return new ResponseEntity<>(null, HttpStatus.CREATED);
                            } else {
                                throw new ApiException(HttpStatus.BAD_REQUEST.value(), "");
                            }
                        case "remove":
                            if (unicornEntity.getMagicEntities().contains(magicEntity)
                            && magicEntity.getUnicornEntities().contains(unicornEntity)) {
                                unicornEntity.getMagicEntities().remove(magicEntity);
                                magicEntity.getUnicornEntities().remove(unicornEntity);
                                unicornRepository.save(unicornEntity);
                                magicRepository.save(magicEntity);
                                return new ResponseEntity<>(null, HttpStatus.OK);
                            } else {
                                throw new ApiException(HttpStatus.BAD_REQUEST.value(), "");
                            }
                        default:
                            throw new ApiException(HttpStatus.BAD_REQUEST.value(), "");
                    }
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
}
