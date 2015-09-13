package org.jgrades.rest.sec.config;

import org.jgrades.data.api.dao.UserRepository;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.model.roles.JgRole;
import org.jgrades.data.api.model.roles.Roles;
import org.jgrades.data.config.DataConfig;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.rest.sec.model.UserDetailsImpl;
import org.jgrades.security.api.dao.PasswordDataRepository;
import org.jgrades.security.api.dao.PasswordPolicyRepository;
import org.jgrades.security.api.entities.PasswordData;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final static JgLogger logger = JgLoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordDataRepository passwordDataRepository;

    @Autowired
    private PasswordPolicyRepository passwordPolicyRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        User user = userRepository.findFirstByLogin(login);
        UserDetailsImpl userDetails = new UserDetailsImpl();
        if( user != null) {

            userDetails.setUsername(login);
            userDetails.setPassword(getUserPassword(user));
            userDetails.setEnabled(user.isActive());
            userDetails.setAccountNonLocked(true);
            userDetails.setAccountNonExpired(true);
            userDetails.setCredentialsNonExpired(isCredentialsNotExpired(user));

            return userDetails;
        }
        else {
            logger.info(" Cannot find user with {} login",login);
            return null;
        }

    }

    private boolean isCredentialsNotExpired(User user) {

        int expirationDaysForRole = getExpirationDays(getRoleWithHighestPriority(user.getRoles()));

        long lastUserLogin = user.getLastVisit().getMillis();
        long lastPasswordChange = getLastPasswordChangeTime(user);

        return lastUserLogin - lastPasswordChange > expirationDaysForRole ? false : true;
    }

    private long getLastPasswordChangeTime(User user) {
        return passwordDataRepository.getPasswordDataWithUser(user).getLastChange().getMillis();
    }

    private int getExpirationDays(JgRole roleWithShortestPasswordPolicy) {
        return passwordPolicyRepository.findOne(roleWithShortestPasswordPolicy).getExpirationDays();
    }

    private JgRole getRoleWithHighestPriority(Roles roles) {
        return roles.getRoleMap().keySet().iterator().next();
    }

    private String getUserPassword(User user){
        PasswordData passwordData = passwordDataRepository.getPasswordDataWithUser(user);
        return passwordData.getPassword();
    }



}
