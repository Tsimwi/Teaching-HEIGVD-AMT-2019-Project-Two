package ch.heigvd.amt.unicorns.business;

import ch.heigvd.amt.unicorns.api.model.Magicorn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ch.heigvd.amt.unicorns.repositories.MagicornRepository;

@Component
public class MagicornService {

    @Autowired
    MagicornRepository magicornRepository;

    public ResponseEntity<Void> changeMagicorn(Magicorn operation, String owner) {
        return null;
    }
}
