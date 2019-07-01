package kakaopay.housingfinance.controller;

import kakaopay.housingfinance.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class BankController {
    private static Logger logger = LoggerFactory.getLogger(BankController.class);
    private static final HttpHeaders httpHeaders = new HttpHeaders();
    private final BankService bankService;

    public BankController(BankService bankService) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        this.bankService = bankService;
    }

    @GetMapping("/banks")
    public ResponseEntity<Map<String,Object>> getAllBanks(){
        return new ResponseEntity<>(bankService.getAllBanks(),httpHeaders, HttpStatus.OK);
    }
}
