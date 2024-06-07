package com.company.reverselog.domain.usuario.controller;
import com.company.reverselog.domain.usuario.dto.AuthenticationDto;
import com.company.reverselog.domain.usuario.entity.Usuario;
import com.company.reverselog.infra.security.TokenJwtData;
import com.company.reverselog.infra.security.TokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor//faz injeção de dependência
public class AutenticacaoController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity authenticate(@RequestBody @Valid AuthenticationDto data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.senha());// dto do spring
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.generateToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenJwtData(tokenJWT));
    }
}