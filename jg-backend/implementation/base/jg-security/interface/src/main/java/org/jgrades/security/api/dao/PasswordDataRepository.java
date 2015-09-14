package org.jgrades.security.api.dao;

import org.jgrades.data.api.entities.User;
import org.jgrades.security.api.entities.PasswordData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordDataRepository extends CrudRepository<PasswordData, Long> {
    @Modifying
    @Query("update PasswordData pd set pd.password = ?1 where pd.user = ?2")
    void setPasswordForUser(String password, User user);

    @Query("select pd from PasswordData pd where pd.user = ?1")
    PasswordData getPasswordDataWithUser(User user);
}
