package ch.heigvd.amt.unicorns.api.endpoints;

import ch.heigvd.amt.unicorns.api.MagicornApi;
import ch.heigvd.amt.unicorns.api.exceptions.ApiException;
import ch.heigvd.amt.unicorns.api.model.Magicorn;
import ch.heigvd.amt.unicorns.business.MagicornService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class MagicornApiController implements MagicornApi {

    @Autowired
    MagicornService magicornService;

    @Autowired
    HttpServletRequest httpServletRequest;

    public ResponseEntity<Void> changeMagicorn(@ApiParam(value = "", required = true) @Valid @RequestBody Magicorn operation) {
        try {
            return magicornService.changeMagicorn(operation, (String) httpServletRequest.getAttribute("email"));
        } catch (ApiException exception) {
            return new ResponseEntity<>(HttpStatus.valueOf(exception.getCode()));
        }
    }
}
