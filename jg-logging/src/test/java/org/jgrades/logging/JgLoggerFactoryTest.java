package org.jgrades.logging;

import com.project.StrangerClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.StrictAssertions.assertThat;

public class JgLoggerFactoryTest {
    @Test
    public void shouldSetJGradesModuleName_whenConstructWithJGradesClass() throws Exception {
        // given
        Class clazz = JgLoggerTest.class;

        // when
        JgLogger jgLogger = JgLoggerFactory.getLogger(clazz);

        // then
        String moduleName = (String) ReflectionTestUtils.getField(jgLogger, "moduleName");
        assertThat(moduleName).isEqualTo("logging");
    }

    @Test
    public void shouldSetExternalLibModuleName_whenConstructNotWithJGradesClass() throws Exception {
        // given
        Class clazz = StrangerClass.class;

        // when
        JgLogger jgLogger = JgLoggerFactory.getLogger(clazz);

        // then
        String moduleName = (String) ReflectionTestUtils.getField(jgLogger, "moduleName");
        assertThat(moduleName).isEqualTo("external-lib");
    }

}
