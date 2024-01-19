package com.manning.ssia14.model;

import jakarta.persistence.*;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.ConfigurationSettingNames;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.Assert;

import java.time.Duration;

@Entity
@Table(name = "app_token_settings")
public class AppTokenSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    private Duration authorizationCodeTimeToLive;
    private Duration accessTokenTimeToLive;
    private String accessTokenFormat;
    private Duration deviceCodeTimeToLive;
    private boolean reuseRefreshTokens;
    private Duration refreshTokenTimeToLive;
    private String idTokenSignatureAlgorithm;
    @OneToOne
    private AppClient client;

    public Duration getAuthorizationCodeTimeToLive() {
        return authorizationCodeTimeToLive;
    }

    public void setAuthorizationCodeTimeToLive(Duration authorizationCodeTimeToLive) {
        this.authorizationCodeTimeToLive = authorizationCodeTimeToLive;
    }

    public Duration getAccessTokenTimeToLive() {
        return accessTokenTimeToLive;
    }

    public void setAccessTokenTimeToLive(Duration accessTokenTimeToLive) {
        this.accessTokenTimeToLive = accessTokenTimeToLive;
    }

    public String getAccessTokenFormat() {
        return accessTokenFormat;
    }

    public void setAccessTokenFormat(String accessTokenFormat) {
        this.accessTokenFormat = accessTokenFormat;
    }

    public Duration getDeviceCodeTimeToLive() {
        return deviceCodeTimeToLive;
    }

    public void setDeviceCodeTimeToLive(Duration deviceCodeTimeToLive) {
        this.deviceCodeTimeToLive = deviceCodeTimeToLive;
    }

    public boolean isReuseRefreshTokens() {
        return reuseRefreshTokens;
    }

    public void setReuseRefreshTokens(boolean reuseRefreshTokens) {
        this.reuseRefreshTokens = reuseRefreshTokens;
    }

    public Duration getRefreshTokenTimeToLive() {
        return refreshTokenTimeToLive;
    }

    public void setRefreshTokenTimeToLive(Duration refreshTokenTimeToLive) {
        this.refreshTokenTimeToLive = refreshTokenTimeToLive;
    }

    public String getIdTokenSignatureAlgorithm() {
        return idTokenSignatureAlgorithm;
    }

    public void setIdTokenSignatureAlgorithm(String idTokenSignatureAlgorithm) {
        this.idTokenSignatureAlgorithm = idTokenSignatureAlgorithm;
    }

    public AppClient getClient() {
        return client;
    }

    public void setClient(AppClient client) {
        this.client = client;
    }

    public static TokenSettings fromAppTokenSettings(AppTokenSettings appTokenSettings){
        return TokenSettings.builder()
                .authorizationCodeTimeToLive(appTokenSettings.getAuthorizationCodeTimeToLive())
                .accessTokenTimeToLive(appTokenSettings.accessTokenTimeToLive)
                .accessTokenFormat(new OAuth2TokenFormat(appTokenSettings.getAccessTokenFormat()))
                .deviceCodeTimeToLive(appTokenSettings.getDeviceCodeTimeToLive())
                .reuseRefreshTokens(appTokenSettings.isReuseRefreshTokens())
                .refreshTokenTimeToLive(appTokenSettings.getRefreshTokenTimeToLive())
                .idTokenSignatureAlgorithm(SignatureAlgorithm.from(appTokenSettings.getIdTokenSignatureAlgorithm()))
                .build();
    }

    public static AppTokenSettings toAppTokenSettings(TokenSettings tokenSettings, AppClient client){
        AppTokenSettings appTokenSettings = new AppTokenSettings();
        appTokenSettings.setAuthorizationCodeTimeToLive(tokenSettings.getAuthorizationCodeTimeToLive());
        appTokenSettings.setAccessTokenTimeToLive(tokenSettings.getAccessTokenTimeToLive());
        appTokenSettings.setAccessTokenFormat(tokenSettings.getAccessTokenFormat().getValue());
        appTokenSettings.setDeviceCodeTimeToLive(tokenSettings.getDeviceCodeTimeToLive());
        appTokenSettings.setReuseRefreshTokens(tokenSettings.isReuseRefreshTokens());
        appTokenSettings.setRefreshTokenTimeToLive(tokenSettings.getRefreshTokenTimeToLive());
        appTokenSettings.setIdTokenSignatureAlgorithm(tokenSettings.getIdTokenSignatureAlgorithm().getName());
        appTokenSettings.setClient(client);

        return appTokenSettings;
    }
}
