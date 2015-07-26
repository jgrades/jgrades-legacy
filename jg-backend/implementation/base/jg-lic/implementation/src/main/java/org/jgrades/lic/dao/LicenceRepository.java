package org.jgrades.lic.dao;

import org.jgrades.lic.entities.LicenceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenceRepository extends CrudRepository<LicenceEntity, Long> {
    @Query("select l from LicenceEntity l where l.product.name = ?1")
    List<LicenceEntity> findByProductName(String name);
}
