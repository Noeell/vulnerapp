package ch.bbw.m183.vulnerapp.controller;

import java.util.Base64;

import ch.bbw.m183.vulnerapp.datamodel.UserEntity;
import ch.bbw.m183.vulnerapp.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final LoginService loginService;

	@GetMapping("/whoami")
	public UserEntity whoami(@AuthenticationPrincipal String user, HttpServletRequest request) {
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		log.info("Token: {}={}", token.getHeaderName(), token.getToken());

		return loginService.whoami(user);
	}
}
