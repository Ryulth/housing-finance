package kakaopay.housingfinance.controller;

import kakaopay.housingfinance.service.FinanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController("/finance")
public class FinanceController {
    private static Logger logger = LoggerFactory.getLogger(FinanceController.class);
    private static final HttpHeaders httpHeaders = new HttpHeaders();
    private final FinanceService financeService;

    public FinanceController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String,Object>> getFinanceStatus(){
        return new ResponseEntity<>(financeService.getFinanceStatus(),httpHeaders, HttpStatus.OK);
    }
}
