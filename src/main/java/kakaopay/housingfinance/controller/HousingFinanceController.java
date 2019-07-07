package kakaopay.housingfinance.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.util.Collections;

@RestController
@Api(value = "HousingFinanceController")
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

    @GetMapping("/apis/banks")
    @ApiOperation(value="Bank List API", notes="전체 은행 목록을 반환하는 API.")
    public ResponseEntity getAllBanks() {
        try {
            return new ResponseEntity<>(bankService.getAllBanks(), httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>(Collections.singletonMap("error","INTERNAL SERVER ERROR"),httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/apis/finances")
    @ApiOperation(value="Finances List API", notes="전체 지원 금액 현황 목록을 반환하는 API.")
    public ResponseEntity getAllFinances() {
        try {
            return new ResponseEntity<>(financeService.getAllFinances(), httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>(Collections.singletonMap("error","INTERNAL SERVER ERROR"),httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/apis/finances/highest/year")
    @ApiOperation(value="HighestYear Bank API", notes="1년에 가장 많이 지원을 한 은행을 반환하는 API.")
    public ResponseEntity getHighestYearBank() {
        try {
            return new ResponseEntity<>(financeService.getHighestYearBank(), httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>(Collections.singletonMap("error","INTERNAL SERVER ERROR"),httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/apis/finances/minmax/year")
    @ApiOperation(value="MinMaxYear Bank ByName API", notes="특정 은행의 지원금액에 대해 최대,최소 년을 반환하는 API.")
    public ResponseEntity getMinMaxYearBankByName(
            @RequestParam(value = "name") String bankName
    ) {
        try {
            return new ResponseEntity<>(financeService.getMinMaxYearBank(bankName), httpHeaders, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(Collections.singletonMap("error","Cannot Find Bank Name"),httpHeaders, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>(Collections.singletonMap("error","INTERNAL SERVER ERROR"),httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/apis/finances/predict")
    @ApiOperation(value="Predict Amount ByMonth API", notes="특정 은행의 2018년 특정 달의 지원금액을 예측하는 API.")
    public ResponseEntity getPredictAmountByMonth(
            @RequestParam(value = "name") String bankName,
            @RequestParam(value = "month") int month) {
        if(month<1||month>12){
            return new ResponseEntity<>(Collections.singletonMap("error","Please Check Month"),httpHeaders, HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(financeService.getPredictAmountByMonth(bankName, month), httpHeaders, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(Collections.singletonMap("error","Cannot Find Bank Name"),httpHeaders, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>(Collections.singletonMap("error","INTERNAL SERVER ERROR"),httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
