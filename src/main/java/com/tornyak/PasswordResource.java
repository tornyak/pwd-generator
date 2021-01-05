package com.tornyak;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.security.SecureRandom;

@Path("/passwords")
public class PasswordResource {

    @Inject
    PasswordService passwordService;

    @POST
    public Password generatePassword(PasswordPolicy passwordPolicy) {
        String password = passwordService.generatePassword(passwordPolicy);
        return new Password(password);
    }
}

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class Password {
    private String text;

    public Password() {
    }

    public Password(String text) {
        this.text = text;
    }
}

@ApplicationScoped
class Producers {
    @javax.enterprise.inject.Produces
    SecureRandom rng = new SecureRandom();
}