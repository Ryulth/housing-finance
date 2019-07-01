package kakaopay.housingfinance.controller;


import kakaopay.housingfinance.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DataController {
    private static Logger logger = LoggerFactory.getLogger(DataController.class);
    private static final HttpHeaders httpHeaders = new HttpHeaders();
    private final DataService dataService;
    public DataController(DataService dataService) {
        this.dataService = dataService;
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
    }

    @PutMapping("/file")
    public ResponseEntity<String> updateFile(
            @RequestBody String payload) {
        System.out.println(payload);
        return new ResponseEntity<>(payload, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/file")
    public ResponseEntity<String> convertFile(
            ) {
        dataService.ConvertFile();
        return new ResponseEntity<>("convert", httpHeaders, HttpStatus.OK);
    }
}
