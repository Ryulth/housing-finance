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
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/finances")
    public ResponseEntity getAllFinances() {
        try {
            return new ResponseEntity<>(financeService.getAllFinances(), httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/finances/highest/year")
    public ResponseEntity getHighestYearBank() {
        try {
            return new ResponseEntity<>(financeService.getHighestYearBank(), httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/finances/minmax/year")
    public ResponseEntity getMinMaxYearBank(
            @RequestParam(value = "name") String bankName
    ) {
        try {
            return new ResponseEntity<>(financeService.getMinMaxYearBank(bankName), httpHeaders, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/finances/predict")
    public ResponseEntity getPredictAmountByMonth(
            @RequestParam(value = "name") String bankName,
            @RequestParam(value = "month") Integer month) {
        try {
            return new ResponseEntity<>(financeService.getPredictAmountByMonth(bankName, month), httpHeaders, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
        }
    }
}
