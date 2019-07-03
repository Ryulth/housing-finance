package kakaopay.housingfinance.controller;

import kakaopay.housingfinance.service.BankService;
import kakaopay.housingfinance.service.FinanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HousingFinanceController {
    private static Logger logger = LoggerFactory.getLogger(HousingFinanceController.class);
    private static final HttpHeaders httpHeaders = new HttpHeaders();
    private final FinanceService financeService;
    private final BankService bankService;

    public HousingFinanceController(FinanceService financeService, BankService bankService) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        this.financeService = financeService;
        this.bankService = bankService;
    }
    @GetMapping("/banks")
    public ResponseEntity<Map<String,Object>> getAllBanks(){
        return new ResponseEntity<>(bankService.getAllBanks(),httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/finance/status")
    public ResponseEntity<Map<String, Object>> getFinanceStatus() {
        return new ResponseEntity<>(financeService.getFinanceStatus(), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/finance/highest")
    public ResponseEntity<Map<String, Object>> getHighestBank() {
        return new ResponseEntity<>(financeService.getHighestBank(), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/finance/minmax")
    public ResponseEntity<Map<String, Object>> getMinMaxAmount(
            @RequestParam(value = "name") String bankName
    ) {
        return new ResponseEntity<>(financeService.getMinMaxAmount(bankName), httpHeaders, HttpStatus.OK);
    }
}
