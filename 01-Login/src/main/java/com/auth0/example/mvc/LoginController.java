package com.auth0.example.mvc;

import com.auth0.AuthenticationController;
import com.auth0.example.security.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unused")
@Controller
public class LoginController {

    @Autowired
    private AuthenticationController controller;
    @Autowired
    private AppConfig appConfig;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    protected String login(final HttpServletRequest req) {
        logger.debug("Performing login");
        String redirectUri = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/callback";
        String authorizeUrl = controller.buildAuthorizeUrl(req, redirectUri)
                .withAudience("http://localhost:3000/api/v1/customer/")
                //.withAudience("itbit.auth0.com/userinfo")
                .withParameter("prompt", "consent")
                .withScope("openid profile email read:eth_address read:withdrawal_accounts read:transactions")
                .build();
        return "redirect:" + authorizeUrl;
    }

}
