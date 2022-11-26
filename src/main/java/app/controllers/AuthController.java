package app.controllers;

import app.config.security.jwt.domain.JwtRequest;
import app.config.security.jwt.domain.JwtResponse;
import app.config.security.jwt.domain.RefreshJwtRequest;
import app.config.security.jwt.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.message.AuthException;

@Api("Authentication API")
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ApiOperation(value = "Login and get tokens")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Got tokens"),
            @ApiResponse(code = 500, message = "Authentication exception")
    })
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) throws AuthException {
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/token")
    @ApiOperation(value = "Get a new access token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Got an access token"),
            @ApiResponse(code = 401, message = "Mismatch refresh token")
    })
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        if (token.getAccessToken() != null) {
            log.info("getNewAccessToken: access token was generated");
            return ResponseEntity.ok(token);
        } else {
            log.info("getNewAccessToken: this refresh token doesn't exist");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/refresh")
    @ApiOperation(value = "Get new tokens")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Got tokens"),
            @ApiResponse(code = 401, message = "Mismatch refresh token")
    })
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = authService.refresh(request.getRefreshToken());
        if (token.getAccessToken() != null) {
            log.info("getNewRefreshToken: new tokens were generated");
            return ResponseEntity.ok(token);
        } else {
            log.info("getNewRefreshToken: this refresh token doesn't exist");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
