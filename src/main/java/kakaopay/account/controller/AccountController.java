package kakaopay.account.controller;

import kakaopay.account.dto.UserDto;
import kakaopay.account.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityExistsException;

@RestController
public class AccountController {
    private static Logger logger = LoggerFactory.getLogger(AccountController.class);
    private static final HttpHeaders httpHeaders = new HttpHeaders();
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/signup")
    public ResponseEntity signUp(
            @RequestBody UserDto userDto) {
        try{
            return new ResponseEntity<>(accountService.signUp(userDto), httpHeaders, HttpStatus.OK);
        }
        catch (EntityExistsException e){
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            logger.error(e.toString());
            return new ResponseEntity<>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
