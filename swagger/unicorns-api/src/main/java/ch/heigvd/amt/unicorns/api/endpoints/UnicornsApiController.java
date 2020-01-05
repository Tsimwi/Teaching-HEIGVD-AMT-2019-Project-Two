package ch.heigvd.amt.unicorns.api.endpoints;

import ch.heigvd.amt.unicorns.api.exceptions.ApiException;
import ch.heigvd.amt.unicorns.business.UnicornsService;
import ch.heigvd.amt.unicorns.entities.UnicornEntity;
import ch.heigvd.amt.unicorns.api.UnicornsApi;
import ch.heigvd.amt.unicorns.api.model.Unicorn;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class UnicornsApiController implements UnicornsApi {

    @Autowired
    UnicornsService unicornsService;

    public ResponseEntity<Void> addUnicorn(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Unicorn unicorn) {
        try {
            return unicornsService.addUnicorn(unicorn);
        } catch (ApiException exception) {
            return new ResponseEntity<>(HttpStatus.valueOf(exception.getCode()));
        }
    }


    public ResponseEntity<List<Unicorn>> getUnicorns() {

    }

    public ResponseEntity<Unicorn> getUnicornByName(@ApiParam(value = "",required=true) @PathVariable("name") String name,@ApiParam(value = "", defaultValue = "false") @Valid @RequestParam(value = "fullView", required = false, defaultValue="false") Boolean fullView) {
        return null;
    }

    public ResponseEntity<Void> updateUnicorn(@ApiParam(value = "",required=true) @PathVariable("name") String name,@ApiParam(value = "" ,required=true )  @Valid @RequestBody Unicorn unicorn) {
        return null;
    }

    public ResponseEntity<Void> deleteUnicorn(@ApiParam(value = "",required=true) @PathVariable("name") String name) {
        return null;
    }
}
