package org.jgrades.lic.dao;

import com.google.common.collect.Lists;
import org.jgrades.lic.BaseTest;
import org.jgrades.lic.entities.LicenceEntity;
import org.jgrades.lic.entities.ProductEntity;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LicenceRepositoryTest extends BaseTest {
    @Autowired
    LicenceRepository licenceRepository;

    @Before
    public void setUp() throws Exception {
        assertThat(licenceRepository).isNotNull();
        licenceRepository.deleteAll();
    }

    @Test
    public void shouldSaveLicence() throws Exception {
        // given
        LicenceEntity licenceEntity = new LicenceEntity();
        licenceEntity.setUid(1L);
        licenceEntity.setLicenceFilePath("lic.enc");

        // when
        licenceRepository.save(licenceEntity);
        Iterable<LicenceEntity> licences = licenceRepository.findAll();
        Iterator<LicenceEntity> iterator = licences.iterator();
        LicenceEntity licence = iterator.next();

        // then
        assertThat(licence).isEqualTo(licenceEntity);
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    public void shouldReturnLicencesForGivenProduct() throws Exception {
        // given
        LicenceEntity licenceBase1 = new LicenceEntity();
        licenceBase1.setUid(100L);
        ProductEntity product1 = new ProductEntity();
        product1.setName("JG-BASE");
        licenceBase1.setProduct(product1);

        LicenceEntity licenceBase2 = new LicenceEntity();
        licenceBase2.setUid(101L);
        ProductEntity product2 = new ProductEntity();
        product2.setName("JG-BASE");
        licenceBase2.setProduct(product2);

        LicenceEntity licenceRaporting3 = new LicenceEntity();
        licenceRaporting3.setUid(102L);
        ProductEntity product3 = new ProductEntity();
        product3.setName("JG-RAPORTING");
        licenceRaporting3.setProduct(product3);

        licenceRepository.save(licenceBase1);
        licenceRepository.save(licenceBase2);
        licenceRepository.save(licenceRaporting3);

        // when
        List<LicenceEntity> baseLicences = licenceRepository.findByProductName("JG-BASE");

        // then
        assertThat(baseLicences.size()).isEqualTo(2);
        assertThat(baseLicences).isEqualTo(Lists.newArrayList(licenceBase1, licenceBase2));

    }
}
