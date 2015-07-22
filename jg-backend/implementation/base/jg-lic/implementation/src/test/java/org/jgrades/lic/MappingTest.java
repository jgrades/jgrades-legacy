package org.jgrades.lic;

import org.dozer.Mapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class MappingTest extends BaseTest {
    @Autowired
    private Mapper mapper;

    @Test
    public void testName() throws Exception {
        assertThat(mapper).isNotNull();

    }
}
