package ch.heigvd.amt.unicorns.api.endpoints;

import ch.heigvd.amt.unicorns.api.model.Magicorn;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class MagicornApiController {

    public ResponseEntity<Void> changeMagicorn(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Magicorn operation) {
        return null;
    }
}
