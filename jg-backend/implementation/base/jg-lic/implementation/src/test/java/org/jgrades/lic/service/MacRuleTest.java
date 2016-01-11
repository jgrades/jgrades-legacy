/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.service;

import com.google.common.collect.Lists;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.LicenceProperty;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.verifyZeroInteractions;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MacRule.class})
public class MacRuleTest {
    private MacRule macRule;

    private List<NetworkInterface> networkInterfaces;

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
        networkInterfaces = PowerMockito.mock(List.class);
        macRule = new MacRule(networkInterfaces);
        licence.setProperties(Lists.newArrayList());

        // when
        boolean isValid = macRule.isValid(licence);

        // then
        assertThat(isValid).isTrue();
        verifyZeroInteractions(networkInterfaces);
    }

    @Test
    public void shouldValid_whenMacPropertyIsPresent_andMatched() throws Exception {
        // given
        licence.setProperties(getPropertyWithMac(macRule.getCurrentMac(getExampleNetworkInterfaceWithHardwareAddress())));

        // when
        boolean isValid = macRule.isValid(licence);

        // then
        assertThat(isValid).isTrue();
    }

    @Test
    public void shouldNotValid_whenMacPropertyIsPresent_andNotMatched() throws Exception {
        // given
        licence.setProperties(getPropertyWithMac("00:00:11:00:00:0A"));

        // when
        boolean isValid = macRule.isValid(licence);

        // then
        assertThat(isValid).isFalse();
    }

    @Test(expected = SocketException.class)
    public void shouldThrowException_whenMacPropertyIsPresent_andWasIssueDueToInetAddrRetrieved() throws Exception {
        // given
        NetworkInterface networkInterface = PowerMockito.spy(getExampleNetworkInterfaceWithHardwareAddress());
        PowerMockito.doThrow(new SocketException()).when(networkInterface).getHardwareAddress();
        macRule = new MacRule(Lists.newArrayList(networkInterface));

        // when
        macRule.getCurrentMac(networkInterface);

        Mockito.verify(networkInterface).getHardwareAddress();

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

    private NetworkInterface getExampleNetworkInterfaceWithHardwareAddress() throws Exception {
        List<NetworkInterface> list = Collections.list(NetworkInterface.getNetworkInterfaces());
        for (NetworkInterface ni : list) {
            byte[] address = ni.getHardwareAddress();
            if (address != null) {
                return ni;
            }
        }
        return null;
    }
}
