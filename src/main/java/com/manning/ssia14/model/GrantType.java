package com.manning.ssia14.model;

import jakarta.persistence.*;

@Entity
@Table(name = "grant_type")
public class GrantType {

    public GrantType(String grantType, AppClient client) {
        this.grantType = grantType;
        this.client = client;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "grant_type")
    private String grantType;

    @ManyToOne
    private AppClient client;

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public AppClient getClient() {
        return client;
    }

    public void setClient(AppClient client) {
        this.client = client;
    }
}
