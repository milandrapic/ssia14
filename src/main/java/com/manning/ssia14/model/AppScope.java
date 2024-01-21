package com.manning.ssia14.model;

import jakarta.persistence.*;

@Entity
@Table(name = "scope")
public class AppScope {

    public AppScope(String scope, AppClient client) {
        this.scope = scope;
        this.client = client;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column
    private String scope;

    @ManyToOne
    private AppClient client;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }


    public AppClient getClient() {
        return client;
    }

    public void setClient(AppClient client) {
        this.client = client;
    }
}
