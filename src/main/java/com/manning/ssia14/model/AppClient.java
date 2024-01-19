package com.manning.ssia14.model;

import jakarta.persistence.*;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Entity
@Table(name = "client", uniqueConstraints = {@UniqueConstraint(columnNames = {"client_id"})})
public class AppClient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "client_id")
    private String clientId;
    @Column
    private Instant clientIdIssuedAt;
    @Column
    private String clientSecret;
    @Column
    private Instant clientSecretExpiresAt;
    @Column
    private String clientName;
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<AppClientAuthenticationMethod> clientAuthenticationMethods;
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<GrantType> grantTypes;
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<RedirectUri> redirectUris;
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<RedirectUri> postLogoutRedirectUris;
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<AppScope> scopes;
    @OneToOne
    private AppClientSettings clientSettings;
    @OneToOne
    private AppTokenSettings tokenSettings;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Instant getClientIdIssuedAt() {
        return clientIdIssuedAt;
    }

    public void setClientIdIssuedAt(Instant clientIdIssuedAt) {
        this.clientIdIssuedAt = clientIdIssuedAt;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Instant getClientSecretExpiresAt() {
        return clientSecretExpiresAt;
    }

    public void setClientSecretExpiresAt(Instant clientSecretExpiresAt) {
        this.clientSecretExpiresAt = clientSecretExpiresAt;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Set<AppClientAuthenticationMethod> getClientAuthenticationMethods() {
        return clientAuthenticationMethods;
    }

    public void setClientAuthenticationMethods(Set<AppClientAuthenticationMethod> clientAuthenticationMethods) {
        this.clientAuthenticationMethods = clientAuthenticationMethods;
    }

    public Set<GrantType> getGrantTypes() {
        return grantTypes;
    }

    public void setGrantTypes(Set<GrantType> grantTypes) {
        this.grantTypes = grantTypes;
    }

    public Set<RedirectUri> getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(Set<RedirectUri> redirectUris) {
        this.redirectUris = redirectUris;
    }

    public Set<RedirectUri> getPostLogoutRedirectUris() {
        return postLogoutRedirectUris;
    }

    public void setPostLogoutRedirectUris(Set<RedirectUri> postLogoutRedirectUris) {
        this.postLogoutRedirectUris = postLogoutRedirectUris;
    }

    public Set<AppScope> getScopes() {
        return scopes;
    }


    public void setScopes(Set<AppScope> scopes) {
        this.scopes = scopes;
    }

    public AppClientSettings getClientSettings() {
        return clientSettings;
    }

    public void setClientSettings(AppClientSettings clientSettings) {
        this.clientSettings = clientSettings;
    }

    public AppTokenSettings getTokenSettings() {
        return tokenSettings;
    }

    public void setTokenSettings(AppTokenSettings tokenSettings) {
        this.tokenSettings = tokenSettings;
    }




    public static RegisteredClient fromClient(AppClient client){

        return RegisteredClient.withId(client.getClientId())
                .clientIdIssuedAt(client.getClientIdIssuedAt())
                .clientSecret(client.getClientSecret())
                .clientSecretExpiresAt(client.getClientSecretExpiresAt())
                .clientName(client.getClientName())
                .clientAuthenticationMethods(AppClient.clientAuthenticationMethods(client.getClientAuthenticationMethods()))
                .authorizationGrantTypes(AppClient.clientGrantTypes(client.getGrantTypes()))
                .redirectUris(AppClient.redirectUris(client.getRedirectUris()))
                .postLogoutRedirectUris(AppClient.redirectUris(client.getPostLogoutRedirectUris()))
                .scopes(AppClient.scopes(client.getScopes()))
                .clientSettings(AppClientSettings.fromAppClientSettings(client.getClientSettings()))
                .tokenSettings(AppTokenSettings.fromAppTokenSettings(client.getTokenSettings()))
                .build();
    }

    public static AppClient toClient(RegisteredClient rc){
        AppClient c = new AppClient();

        c.setClientId(rc.getClientId());
        c.setClientIdIssuedAt(rc.getClientIdIssuedAt());
        c.setClientSecret(rc.getClientSecret());
        c.setClientSecretExpiresAt(rc.getClientSecretExpiresAt());
        c.setClientName(rc.getClientName());
        c.setClientAuthenticationMethods(
                rc.getClientAuthenticationMethods().stream()
                        .map(am -> new AppClientAuthenticationMethod(am.getValue(), c)).collect(Collectors.toSet())
        );
        c.setGrantTypes(
                rc.getAuthorizationGrantTypes().stream()
                        .map(agt -> new GrantType(agt.getValue(), c))
                        .collect(Collectors.toSet())
        );
        c.setRedirectUris(
                rc.getRedirectUris().stream()
                        .map(rdu -> new RedirectUri(rdu, false, c))
                        .collect(Collectors.toSet())
        );
        c.setPostLogoutRedirectUris(
                rc.getPostLogoutRedirectUris().stream()
                        .map(plr -> new RedirectUri(plr, true, c))
                        .collect(Collectors.toSet())
        );
        c.setScopes(
                rc.getScopes().stream()
                        .map(s -> new AppScope(s,c))
//                        .map(AppScope::new)
                        .collect(Collectors.toSet())
        );
        c.setClientSettings(AppClientSettings.toAppClientSettings(rc.getClientSettings(), c));
        c.setTokenSettings(AppTokenSettings.toAppTokenSettings(rc.getTokenSettings(), c));


        return c;
    }

    private static Consumer<Set<ClientAuthenticationMethod>> clientAuthenticationMethods(
            Set<AppClientAuthenticationMethod> authenticationMethods
    ){
        return m -> {
            for (AppClientAuthenticationMethod a : authenticationMethods){
                m.add(new ClientAuthenticationMethod(a.getClientAuthenticationMethod()));
            }
        };
    }

    private static Consumer<Set<AuthorizationGrantType>> clientGrantTypes(
            Set<GrantType> grantTypes
    ){
        return m -> {
            for (GrantType g : grantTypes){
                m.add(new AuthorizationGrantType(g.getGrantType()));
            }
        };
    }

    private static Consumer<Set<String>> scopes(
            Set<AppScope> scopes
    ){
        return s -> {
            for(AppScope sc: scopes){
                s.add(sc.getScope());
            }
        };
    }

    private static Consumer<Set<String>> redirectUris(
            Set<RedirectUri> redirectUrls
    ){
        return r -> {
            for(RedirectUri ru: redirectUrls){
                r.add(ru.getRedirectUri());
            }
        };
    }




}
