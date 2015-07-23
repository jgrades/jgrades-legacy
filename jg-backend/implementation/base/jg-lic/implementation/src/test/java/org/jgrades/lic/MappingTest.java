package org.jgrades.lic;

import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class MappingTest extends BaseTest {
    @Autowired
    private Mapper mapper;

    @Before
    public void setUp() throws Exception {
        assertThat(mapper).isNotNull();
    }

    @Test
    public void shouldMapLicenceToEntity() throws Exception {
        //TODO
    }

    @Test
    public void shouldMapEntityToLicence() throws Exception {
        //TODO
    }
}
