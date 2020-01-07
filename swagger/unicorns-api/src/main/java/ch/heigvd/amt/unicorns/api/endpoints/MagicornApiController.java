package ch.heigvd.amt.unicorns.api.endpoints;

import ch.heigvd.amt.unicorns.api.MagicornApi;
import ch.heigvd.amt.unicorns.api.model.Magicorn;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class MagicornApiController implements MagicornApi {

    public ResponseEntity<Void> changeMagicorn(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Magicorn operation) {
        return null;
    }
}
