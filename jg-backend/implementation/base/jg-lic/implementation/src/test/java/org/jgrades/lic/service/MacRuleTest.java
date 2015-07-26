package org.jgrades.lic.service;

import com.google.common.collect.Lists;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.LicenceProperty;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.verifyZeroInteractions;

@RunWith(PowerMockRunner.class)
@PrepareForTest({NetworkInterface.class})
public class MacRuleTest {
    private MacRule macRule;

    private NetworkInterface networkInterface;

    private Licence licence;

    @Before
    public void setUp() throws Exception {
        macRule = new MacRule();
        licence = new Licence();

        assertThat(macRule).isNotNull();
    }

    @Test
    public void shouldValid_whenMacPropertyIsAbsent() throws Exception {
        // given
        networkInterface = PowerMockito.mock(NetworkInterface.class);
        macRule = new MacRule(networkInterface);
        licence.setProperties(Lists.newArrayList());

        // when
        boolean isValid = macRule.isValid(licence);

        // then
        assertThat(isValid).isTrue();
        verifyZeroInteractions(networkInterface);
    }

    @Test
    public void shouldValid_whenMacPropertyIsPresent_andMatched() throws Exception {
        // given
        licence.setProperties(getPropertyWithMac(macRule.getCurrentMac()));

        // when
        boolean isValid = macRule.isValid(licence);

        // then
        assertThat(isValid).isTrue();
    }

    @Test
    public void shouldNotValid_whenMacPropertyIsPresent_andNotMatched() throws Exception {
        // given
        licence.setProperties(getPropertyWithMac("00:00:00:00:00:0A"));

        // when
        boolean isValid = macRule.isValid(licence);

        // then
        assertThat(isValid).isFalse();
    }

    @Ignore("Some issue with PowerMock")
    @Test(expected = SocketException.class)
    public void shouldThrowException_whenMacPropertyIsPresent_andWasIssueDueToInetAddrRetrieved() throws Exception {
        // given
        networkInterface = PowerMockito.spy(NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));
        PowerMockito.doThrow(new SocketException()).when(networkInterface).getHardwareAddress();
        macRule = new MacRule(networkInterface);

        // when
        macRule.getCurrentMac();

        // then
        // should throw SocketException
    }

    private List<LicenceProperty> getPropertyWithMac(String mac) {
        List<LicenceProperty> properties = Lists.newArrayList();
        LicenceProperty property = new LicenceProperty();
        property.setName("mac");
        property.setValue(mac);
        properties.add(property);
        return properties;
    }
}
