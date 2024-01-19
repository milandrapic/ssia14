package com.manning.ssia14.model;

import jakarta.persistence.*;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

@Entity
@Table(name = "app_client_settings")
public class AppClientSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "require_proof_key")
    private boolean requireProofKey;
    @Column(name = "require_authorization_consent")
    private boolean requireAuthorizationConsent;
    @Column(name = "jwk_set_url")
    private String jwkSetUrl;
    @Column(name = "authentication_signing_algorithm")
    private String authenticationSigningAlgorithm;
    @OneToOne
    private AppClient client;

    public boolean isRequireProofKey() {
        return requireProofKey;
    }

    public void setRequireProofKey(boolean requireProofKey) {
        this.requireProofKey = requireProofKey;
    }

    public boolean isRequireAuthorizationConsent() {
        return requireAuthorizationConsent;
    }

    public void setRequireAuthorizationConsent(boolean requireAuthorizationConsent) {
        this.requireAuthorizationConsent = requireAuthorizationConsent;
    }

    public String getJwkSetUrl() {
        return jwkSetUrl;
    }

    public void setJwkSetUrl(String jwkSetUrl) {
        this.jwkSetUrl = jwkSetUrl;
    }

    public String getAuthenticationSigningAlgorithm() {
        return authenticationSigningAlgorithm;
    }

    public void setAuthenticationSigningAlgorithm(String authenticationSigningAlgorithm) {
        this.authenticationSigningAlgorithm = authenticationSigningAlgorithm;
    }

    public AppClient getClient() {
        return client;
    }

    public void setClient(AppClient client) {
        this.client = client;
    }

    // TODO: create method to convert this object to a ClientSettings Object
    public static ClientSettings fromAppClientSettings(AppClientSettings appClientSettings){
        JwsAlgorithm jwsAlgorithm;
        jwsAlgorithm = SignatureAlgorithm.from(appClientSettings.getAuthenticationSigningAlgorithm());

        if(jwsAlgorithm == null) jwsAlgorithm = MacAlgorithm.from(appClientSettings.getAuthenticationSigningAlgorithm());

        return ClientSettings.builder().requireProofKey(appClientSettings.isRequireProofKey())
                .requireAuthorizationConsent(appClientSettings.isRequireAuthorizationConsent())
                .jwkSetUrl(appClientSettings.getJwkSetUrl())
                .tokenEndpointAuthenticationSigningAlgorithm(jwsAlgorithm)
                .build();
    }

    public static AppClientSettings toAppClientSettings(ClientSettings clientSettings, AppClient client){
        AppClientSettings appClientSettings = new AppClientSettings();
        appClientSettings.setRequireProofKey(clientSettings.isRequireProofKey());
        appClientSettings.setJwkSetUrl(clientSettings.getJwkSetUrl());
        appClientSettings.setAuthenticationSigningAlgorithm(clientSettings.getTokenEndpointAuthenticationSigningAlgorithm().getName());
        appClientSettings.setRequireAuthorizationConsent(clientSettings.isRequireAuthorizationConsent());
        appClientSettings.setClient(client);

        return appClientSettings;
    }
}
