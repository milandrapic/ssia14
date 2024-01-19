package com.manning.ssia14.model;

import jakarta.persistence.*;

@Entity
@Table(name = "redirect_uris")
public class RedirectUri {

    public RedirectUri(String redirectUri, boolean postLogout, AppClient client) {
        this.redirectUri = redirectUri;
        this.postLogout = postLogout;
        this.client = client;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "redirect_uri")
    private String redirectUri;

    @ManyToOne
    private AppClient client;

    @Column(name = "post_logout")
    private boolean postLogout;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public AppClient getClient() {
        return client;
    }

    public void setClient(AppClient client) {
        this.client = client;
    }

    public boolean isPostLogout() {
        return postLogout;
    }

    public void setPostLogout(boolean postLogout) {
        this.postLogout = postLogout;
    }
}
