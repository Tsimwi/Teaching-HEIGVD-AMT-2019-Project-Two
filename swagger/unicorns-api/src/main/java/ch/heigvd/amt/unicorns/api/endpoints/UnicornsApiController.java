package ch.heigvd.amt.unicorns.api.endpoints;

import ch.heigvd.amt.unicorns.api.exceptions.ApiException;
import ch.heigvd.amt.unicorns.api.model.SimpleUnicorn;
import ch.heigvd.amt.unicorns.api.model.UpdateUnicorn;
import ch.heigvd.amt.unicorns.business.UnicornsService;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class UnicornsApiController implements UnicornsApi {

    @Autowired
    UnicornsService unicornsService;

    @Autowired
    HttpServletRequest httpServletRequest;

    public ResponseEntity<Void> addUnicorn(@ApiParam(value = "", required = true) @Valid @RequestBody SimpleUnicorn unicorn) {
        try {
            return unicornsService.addUnicorn(unicorn, (String) httpServletRequest.getAttribute("email"));
        } catch (ApiException exception) {
            return new ResponseEntity<>(HttpStatus.valueOf(exception.getCode()));
        }
    }

    public ResponseEntity<List<SimpleUnicorn>> getUnicorns(@Min(1) @ApiParam(value = "", defaultValue = "1") @Valid @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber, @Min(1) @Max(50) @ApiParam(value = "", defaultValue = "10") @Valid @RequestParam(value = "numberPerPage", required = false, defaultValue = "10") Integer numberPerPage) {
        try {
            return unicornsService.getUnicorns((String) httpServletRequest.getAttribute("email"), pageNumber, numberPerPage);
        } catch (ApiException exception) {
            return new ResponseEntity<>(HttpStatus.valueOf(exception.getCode()));
        }
    }


    public ResponseEntity<Unicorn> getUnicornByName(@ApiParam(value = "", required = true) @PathVariable("name") String name, @ApiParam(value = "Display magics related to this unicorns", defaultValue = "false") @Valid @RequestParam(value = "fullView", required = false, defaultValue = "false") Boolean fullView) {
        try {
            return unicornsService.getUnicornByName(name, (String) httpServletRequest.getAttribute("email"), fullView);
        } catch (ApiException exception) {
            return new ResponseEntity<>(HttpStatus.valueOf(exception.getCode()));
        }
    }

    public ResponseEntity<Void> updateUnicorn(@ApiParam(value = "",required=true) @PathVariable("name") String name,@ApiParam(value = "" ,required=true )  @Valid @RequestBody UpdateUnicorn unicorn) {
        try {
            return unicornsService.updateUnicorn(name, unicorn, (String) httpServletRequest.getAttribute("email"));
        } catch (ApiException exception) {
            return new ResponseEntity<>(HttpStatus.valueOf(exception.getCode()));
        }
    }

    public ResponseEntity<Void> deleteUnicorn(@ApiParam(value = "", required = true) @PathVariable("name") String name) {
        try {
            return unicornsService.deleteUnicorn(name, (String) httpServletRequest.getAttribute("email"));
        } catch (ApiException exception) {
            return new ResponseEntity<>(HttpStatus.valueOf(exception.getCode()));
        }
    }
}
