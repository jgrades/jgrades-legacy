package org.jgrades.logging.utils;

import org.junit.Test;

public class ModuleNameResolverTest {
    @Test
    public void shouldReturnExternalLib_whenNull() throws Exception {

    }

    @Test
    public void shouldReturnExternalLib_whenEmpty() throws Exception {

    }

    @Test
    public void shouldReturnExternalLib_whenClassOutsideJGradesPackage1() throws Exception {
        //TODO test for org.springframework.data.auditing.AuditingHandler
    }

    @Test
    public void shouldReturnExternalLib_whenClassOutsideJGradesPackage2() throws Exception {
        //TODO test for com.jgrades.foo.Clazz
    }

    @Test
    public void shouldReturnExternalLib_whenClassOutsideJGradesPackage3() throws Exception {
        //TODO test for com.org.jgrades.foo.Clazz
    }

    @Test
    public void shouldReturnLic_when_OrgJgradesLic() throws Exception {
        //TODO test for org.jgrades.lic.Clazz
    }

    @Test
    public void shouldReturnRest_when_OrgJgradesRest() throws Exception {
        //TODO test for org.jgrades.rest.org.jgrades.Clazz
    }

    @Test
    public void shouldReturnApp_when_OrgJgradesClazz() throws Exception {
        //TODO test for org.jgrades.Clazz
    }
}
