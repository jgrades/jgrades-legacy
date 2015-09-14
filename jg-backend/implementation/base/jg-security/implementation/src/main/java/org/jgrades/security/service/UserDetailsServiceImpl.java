package org.jgrades.security.service;

import org.jgrades.data.api.dao.UserRepository;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.model.roles.JgRole;
import org.jgrades.data.api.model.roles.Roles;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.security.api.dao.PasswordDataRepository;
import org.jgrades.security.api.dao.PasswordPolicyRepository;
import org.jgrades.security.api.entities.PasswordData;
import org.jgrades.security.utils.UserDetailsImpl;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final static JgLogger LOGGER = JgLoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordDataRepository passwordDataRepository;

    @Autowired
    private PasswordPolicyRepository passwordPolicyRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        User user = userRepository.findFirstByLogin(login);
        UserDetailsImpl userDetails = new UserDetailsImpl(login, getUserPassword(user), null);
        if( user != null) {
            userDetails = new UserDetailsImpl(
                    login, getUserPassword(user), user.isActive(),
                    true, isCredentialsNotExpired(user), true,
                    null);
        } else {
            LOGGER.info(" Cannot find user with {} login", login);
        }
        return userDetails;
    }

    private boolean isCredentialsNotExpired(User user) {
        int expirationDaysForRole = getExpirationDays(getRoleWithHighestPriority(user.getRoles()));
        DateTime lastPasswordChangeTime = passwordDataRepository.getPasswordDataWithUser(user).getLastChange();

        Duration duration = new Duration(lastPasswordChangeTime, DateTime.now());
        return duration.isShorterThan(Duration.standardDays(expirationDaysForRole));
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
