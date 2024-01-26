package com.manning.ssia14.model;

import jakarta.persistence.*;

@Entity
@Table(name = "client_authentication_method")
public class AppClientAuthenticationMethod {

    public AppClientAuthenticationMethod(String clientAuthenticationMethod, AppClient client) {
        this.clientAuthenticationMethod = clientAuthenticationMethod;
        this.client = client;
    }

    public AppClientAuthenticationMethod() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name = "client_authentication_method")
    private String clientAuthenticationMethod;

    @ManyToOne
    private AppClient client;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientAuthenticationMethod() {
        return clientAuthenticationMethod;
    }

    public void setClientAuthenticationMethod(String clientAuthenticationMethod) {
        this.clientAuthenticationMethod = clientAuthenticationMethod;
    }

    public AppClient getClient() {
        return client;
    }

    public void setClient(AppClient client) {
        this.client = client;
    }
}
