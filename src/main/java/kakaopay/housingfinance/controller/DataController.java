package kakaopay.housingfinance.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kakaopay.housingfinance.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;


@RestController
@Api(value = "DataController")
public class DataController {
    private static Logger logger = LoggerFactory.getLogger(DataController.class);
    private static final HttpHeaders httpHeaders = new HttpHeaders();
    private final DataService dataService;

    public DataController(DataService dataService) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        this.dataService = dataService;
    }

    @PutMapping("/apis/file")
    @ApiOperation(value="Update File API", notes="(예정) 첨부파일로 csv 파일을 업로드하면 DB 를 업데이트 해주는 API.")
    public ResponseEntity updateFile(
            @RequestBody String payload) {
        return new ResponseEntity<>(Collections.singletonMap("error","첨부파일 업데이트 기능 예정"), httpHeaders, HttpStatus.OK);
    }

}
