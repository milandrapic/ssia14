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

    public AppTokenSettings(AppClient client) {
        this.client = client;
    }

    public AppTokenSettings(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    private Duration authorizationCodeTimeToLive = Duration.ofMinutes(5);
    private Duration accessTokenTimeToLive = Duration.ofMinutes(5);
    private String accessTokenFormat = OAuth2TokenFormat.SELF_CONTAINED.getValue();
    private Duration deviceCodeTimeToLive = Duration.ofMinutes(5);
    private boolean reuseRefreshTokens = true;
    private Duration refreshTokenTimeToLive = Duration.ofMinutes(5);
    private String idTokenSignatureAlgorithm = SignatureAlgorithm.RS256.getName();
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

    @Override
    public String toString() {
        return "AppTokenSettings{" +
                "id=" + id +
                ", authorizationCodeTimeToLive=" + authorizationCodeTimeToLive +
                ", accessTokenTimeToLive=" + accessTokenTimeToLive +
                ", accessTokenFormat='" + accessTokenFormat + '\'' +
                ", deviceCodeTimeToLive=" + deviceCodeTimeToLive +
                ", reuseRefreshTokens=" + reuseRefreshTokens +
                ", refreshTokenTimeToLive=" + refreshTokenTimeToLive +
                ", idTokenSignatureAlgorithm='" + idTokenSignatureAlgorithm + '\'' +
                ", client=" + client.getClientId() +
                '}';
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
        AppTokenSettings appTokenSettings = new AppTokenSettings(client);
        appTokenSettings.setAuthorizationCodeTimeToLive(tokenSettings.getAuthorizationCodeTimeToLive());
        appTokenSettings.setAccessTokenTimeToLive(tokenSettings.getAccessTokenTimeToLive());
        appTokenSettings.setAccessTokenFormat(tokenSettings.getAccessTokenFormat().getValue());
        appTokenSettings.setDeviceCodeTimeToLive(tokenSettings.getDeviceCodeTimeToLive());
        appTokenSettings.setReuseRefreshTokens(tokenSettings.isReuseRefreshTokens());
        appTokenSettings.setRefreshTokenTimeToLive(tokenSettings.getRefreshTokenTimeToLive());
        appTokenSettings.setIdTokenSignatureAlgorithm(tokenSettings.getIdTokenSignatureAlgorithm().getName());


        return appTokenSettings;
    }
}
