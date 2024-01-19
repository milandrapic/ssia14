package com.manning.ssia14.service;

import com.manning.ssia14.model.AppClient;
import com.manning.ssia14.repository.AppClientJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AppRegisteredClientRepository implements RegisteredClientRepository {

    @Autowired
    private AppClientJpaRepository appClientRepository;

    @Transactional
    @Override
    public void save(RegisteredClient registeredClient) {
        AppClient appClient = loadAppClientByClientId(registeredClient.getClientId());
        if(appClient == null){
            appClientRepository.save(AppClient.toClient(registeredClient));
        }
    }

    public AppClient loadAppClientByClientId(String clientId){
        Optional<AppClient> appClientOptional = appClientRepository.findByClientId(clientId);
        return appClientOptional.orElse(null);
    }

    @Override
    public RegisteredClient findById(String id) {
        Optional<AppClient> appClientOptional = appClientRepository.findById(Integer.parseInt(id));
        AppClient appClient = appClientOptional.orElse(null);
        if(appClient == null) return null;
        return AppClient.fromClient(appClient);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Optional<AppClient> appClientOptional = appClientRepository.findByClientId(clientId);
        AppClient appClient = appClientOptional.orElse(null);
        if(appClient == null) return null;
        return AppClient.fromClient(appClient);
    }
}
