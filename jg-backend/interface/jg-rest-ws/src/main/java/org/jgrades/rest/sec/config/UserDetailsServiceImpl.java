package org.jgrades.rest.sec.config;

import org.jgrades.data.api.dao.UserRepository;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.config.DataConfig;
import org.jgrades.rest.sec.model.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        UserDetailsImpl userDetails = new UserDetailsImpl();
        User user = userRepository.findFirstByLogin(login);

        if( user != null) {

            userDetails.setAccountNonExpired(false);
            userDetails.setAccountNonLocked(false);
            userDetails.setEnabled(false);
            userDetails.setAuthorities(null);
            userDetails.setPassword(null);
            userDetails.setUsername(null);


            return userDetails;
        }
        else {
            return null;
        }

    }


}
