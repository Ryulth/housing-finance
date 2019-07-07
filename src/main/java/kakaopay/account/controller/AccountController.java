package kakaopay.account.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kakaopay.account.dto.UserDto;
import kakaopay.account.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import java.util.Collections;

@RestController
@Api(value = "AccountController")
public class AccountController {
    private static Logger logger = LoggerFactory.getLogger(AccountController.class);
    private static final HttpHeaders httpHeaders = new HttpHeaders();
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/account/signup")
    @ApiOperation(value="SingUP API", notes="회원가입을 하면 토큰을 반환하는 API.")
    public ResponseEntity signUp(
            @RequestBody UserDto userDto) {
        try {
            return new ResponseEntity<>(accountService.signUp(userDto), httpHeaders, HttpStatus.OK);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), httpHeaders, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new ResponseEntity<>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/account/signin")
    @ApiOperation(value="SingIn API", notes="로그인을 하면 토큰을 반환하는 API.")
    public ResponseEntity signIn(
            @RequestBody UserDto userDto) {
        try {
            return new ResponseEntity<>(accountService.signIn(userDto), httpHeaders, HttpStatus.OK);
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), httpHeaders, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new ResponseEntity<>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/account/update")
    @ApiOperation(value="Update Access Token API", notes="refresh_token 을 보내면 access_token 을 반환해주는 API.")
    public ResponseEntity updateAccessToken(
            @RequestHeader("Authorization") String token ) {
        try {
            return new ResponseEntity<>(accountService.updateAccessToken(token), httpHeaders, HttpStatus.OK);
        } catch (IllegalAccessException| UsernameNotFoundException e) {
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), httpHeaders, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new ResponseEntity<>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
