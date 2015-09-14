package org.jgrades.security.service;

import org.jgrades.data.api.entities.User;
import org.jgrades.security.api.dao.PasswordDataRepository;
import org.jgrades.security.api.entities.PasswordData;
import org.jgrades.security.api.service.PasswordMgntService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class PasswordMgntServiceImpl implements PasswordMgntService {
    @Autowired
    private PasswordDataRepository passwordDataRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void setPassword(String password, User user) {
        PasswordData pswdData = passwordDataRepository.findOne(user.getId());
        if (pswdData == null) {
            PasswordData passwordData = new PasswordData();
            passwordData.setUserId(user.getId());
            passwordData.setUser(user);
            passwordData.setPassword(password);
            passwordData.setLastChange(DateTime.now());

            passwordDataRepository.save(passwordData);
        } else {
            passwordDataRepository.getPasswordDataWithUser(user);
        }
    }

    @Override
    public String getPassword(User user) {
        return passwordDataRepository.getPasswordDataWithUser(user).getPassword();
    }

    @Override
    public boolean isPasswordExpired(User user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getLogin());
        return !userDetails.isCredentialsNonExpired();
    }
}
