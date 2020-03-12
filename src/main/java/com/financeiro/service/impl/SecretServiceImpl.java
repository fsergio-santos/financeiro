package com.financeiro.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.crypto.MacProvider;
import io.jsonwebtoken.lang.Assert;
import org.springframework.stereotype.Service;

import com.financeiro.service.SecretService;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;

@Service
public class SecretServiceImpl implements SecretService {

    private Map<String, String> secrets = new HashMap<>();

    private SigningKeyResolver signingKeyResolver = new SigningKeyResolverAdapter() {
        @Override
        public byte[] resolveSigningKeyBytes(JwsHeader header, Claims claims) {
            return TextCodec.BASE64.decode(secrets.get(header.getAlgorithm()));
        }
    };

    @Override
    @PostConstruct
    public void setup() {
        refreshSecrets();
    }

    @Override
    public SigningKeyResolver getSigningKeyResolver() {
        return signingKeyResolver;
    }

    @Override
    public Map<String, String> getSecrets() {
        return secrets;
    }

    @Override
    public void setSecrets(Map<String, String> secrets) {
        Assert.notNull(secrets);
        Assert.hasText(secrets.get(SignatureAlgorithm.HS256.getValue()));
        Assert.hasText(secrets.get(SignatureAlgorithm.HS384.getValue()));
        Assert.hasText(secrets.get(SignatureAlgorithm.HS512.getValue()));

        this.secrets = secrets;
    }

    @Override
    public byte[] getHS256SecretBytes() {
        return TextCodec.BASE64.decode(secrets.get(SignatureAlgorithm.HS256.getValue()));
    }

    @Override
    public byte[] getHS384SecretBytes() {
        return TextCodec.BASE64.decode(secrets.get(SignatureAlgorithm.HS384.getValue()));
    }

    @Override
    public byte[] getHS512SecretBytes() {
        return TextCodec.BASE64.decode(secrets.get(SignatureAlgorithm.HS384.getValue()));
    }


    @Override
    public Map<String, String> refreshSecrets() {
        SecretKey key = MacProvider.generateKey(SignatureAlgorithm.HS256);
        secrets.put(SignatureAlgorithm.HS256.getValue(), TextCodec.BASE64.encode(key.getEncoded()));
        key = MacProvider.generateKey(SignatureAlgorithm.HS384);
        secrets.put(SignatureAlgorithm.HS384.getValue(), TextCodec.BASE64.encode(key.getEncoded()));
        key = MacProvider.generateKey(SignatureAlgorithm.HS512);
        secrets.put(SignatureAlgorithm.HS512.getValue(), TextCodec.BASE64.encode(key.getEncoded()));
        return secrets;
    }
}
