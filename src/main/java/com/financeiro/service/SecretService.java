package com.financeiro.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.crypto.MacProvider;
import io.jsonwebtoken.lang.Assert;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;


public interface SecretService {

    void setup();

    SigningKeyResolver getSigningKeyResolver();

    Map<String, String> getSecrets();
    
    void setSecrets(Map<String, String> secrets);

    byte[] getHS256SecretBytes();

    byte[] getHS384SecretBytes();

    byte[] getHS512SecretBytes();

    Map<String, String> refreshSecrets();
}
