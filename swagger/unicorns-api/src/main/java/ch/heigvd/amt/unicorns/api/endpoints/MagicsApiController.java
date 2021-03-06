package ch.heigvd.amt.unicorns.api.endpoints;

import ch.heigvd.amt.unicorns.api.MagicsApi;
import ch.heigvd.amt.unicorns.api.exceptions.ApiException;
import ch.heigvd.amt.unicorns.api.model.Magic;
import ch.heigvd.amt.unicorns.api.model.SimpleMagic;
import ch.heigvd.amt.unicorns.api.model.UpdateMagic;
import ch.heigvd.amt.unicorns.business.MagicsService;
import ch.heigvd.amt.unicorns.entities.MagicEntity;
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
public class MagicsApiController implements MagicsApi {

    @Autowired
    MagicsService magicsService;

    @Autowired
    HttpServletRequest httpServletRequest;

    public ResponseEntity<Void> addMagic(@ApiParam(value = "", required = true) @Valid @RequestBody SimpleMagic magic) {
        try {
            return magicsService.addMagic(magic, (String) httpServletRequest.getAttribute("email"));
        } catch (ApiException exception) {
            return new ResponseEntity<>(HttpStatus.valueOf(exception.getCode()));
        }
    }


    public ResponseEntity<List<SimpleMagic>> getMagics(@Min(1) @ApiParam(value = "", defaultValue = "1") @Valid @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber, @Min(1) @Max(50) @ApiParam(value = "", defaultValue = "10") @Valid @RequestParam(value = "numberPerPage", required = false, defaultValue = "10") Integer numberPerPage) {
        try {
            return magicsService.getMagics((String) httpServletRequest.getAttribute("email"), pageNumber, numberPerPage);
        } catch (ApiException exception) {
            return new ResponseEntity<>(HttpStatus.valueOf(exception.getCode()));
        }
    }

    public ResponseEntity<Magic> getMagicByName(@ApiParam(value = "", required = true) @PathVariable("name") String name, @ApiParam(value = "Display unicorns related to this magics", defaultValue = "false") @Valid @RequestParam(value = "fullView", required = false, defaultValue = "false") Boolean fullView) {
        try {
            return magicsService.getMagicByName(name, (String) httpServletRequest.getAttribute("email"), fullView);
        } catch (ApiException exception) {
            return new ResponseEntity<>(HttpStatus.valueOf(exception.getCode()));
        }
    }

    public ResponseEntity<Void> updateMagic(@ApiParam(value = "",required=true) @PathVariable("name") String name,@ApiParam(value = "" ,required=true )  @Valid @RequestBody UpdateMagic magic) {
        try {
            return magicsService.updateMagic(name, magic, (String) httpServletRequest.getAttribute("email"));
        } catch (ApiException exception) {
            return new ResponseEntity<>(HttpStatus.valueOf(exception.getCode()));
        }
    }

    public ResponseEntity<Void> deleteMagic(@ApiParam(value = "", required = true) @PathVariable("name") String name) {
        try {
            return magicsService.deleteMagic(name, (String) httpServletRequest.getAttribute("email"));
        } catch (ApiException exception) {
            return new ResponseEntity<>(HttpStatus.valueOf(exception.getCode()));
        }
    }
}
