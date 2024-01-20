package com.manning.ssia14.service;

import com.manning.ssia14.model.AppUser;
import com.manning.ssia14.model.Role;
import com.manning.ssia14.model.UserPrincipal;
import com.manning.ssia14.repository.AppUserJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Transactional
@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserJpaRepository appUserJpaRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> appUser = appUserJpaRepository.findByUsername(username);
        return new UserPrincipal(appUser.orElse(null));
    }

    @Transactional
    public AppUser register(AppUser appUser) throws IllegalArgumentException{
        String username = appUser.getUsername();
        Optional<AppUser> userOptional = appUserJpaRepository.findByUsername(username);
        AppUser user = userOptional.orElse(null);
//        AppUser user = userOptional.orElse(new AppUser());

        if(user != null){
            throw new IllegalArgumentException(String.format("%s is already a registered user.", username));
        }
        appUser.setPassword(bcrypt.encode(appUser.getPassword()));
        appUser.setRole(Role.ROLE_USER);
        AppUser newUser = appUserJpaRepository.save(appUser);

    // TODO: DETERMINE WHICH USER SHOULD BE RETURNED
        return newUser;
    }

    public AppUser update(AppUser appUser){
        return appUserJpaRepository.save(appUser);
    }


}
