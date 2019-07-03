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

import javax.persistence.EntityNotFoundException;

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
    public ResponseEntity getAllBanks() {
        try {
            return new ResponseEntity<>(bankService.getAllBanks(), httpHeaders, HttpStatus.OK);
        } catch (Exception e){
            logger.error(e.toString());
            return new ResponseEntity<>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/finances")
    public ResponseEntity getFinanceStatus() {
        try {
            return new ResponseEntity<>(financeService.getFinanceStatus(), httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/finances/year/highest")
    public ResponseEntity getHighestBank() {
        try {
            return new ResponseEntity<>(financeService.getHighestBank(), httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/finances/year/minmax")
    public ResponseEntity getMinMaxAmount(
            @RequestParam(value = "name") String bankName
    ) {
        try {
            return new ResponseEntity<>(financeService.getMinMaxAmount(bankName), httpHeaders, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
