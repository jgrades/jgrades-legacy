package org.jgrades.lic.service;

import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import org.apache.commons.lang3.StringUtils;
import org.jgrades.lic.api.exception.ViolationOfLicenceConditionException;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.LicenceProperty;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

class MacRule implements ValidationRule {
    private static final String MAC_PROPERTY_NAME = "mac";

    private NetworkInterface networkInterface;

    public MacRule() {
    }

    public MacRule(NetworkInterface networkInterface) {
        this.networkInterface = networkInterface;
    }

    @Override
    public boolean isValid(Licence licence) {
        List<LicenceProperty> properties = licence.getProperties();
        UnmodifiableIterator<LicenceProperty> macPropertyIterator = Iterators.filter(properties.iterator(), licenceProperty -> {
            return licenceProperty.getName().equals(MAC_PROPERTY_NAME);
        });
        LicenceProperty property = null;
        if (macPropertyIterator.hasNext()) {
            property = macPropertyIterator.next();
        }

        if (Optional.ofNullable(property).isPresent()) {
            String requestedMac = property.getValue();
            String getCurrentMac = null;
            try {
                getCurrentMac = getCurrentMac();
            } catch (UnknownHostException | SocketException e) {
                throw new ViolationOfLicenceConditionException(e);
            }
            return requestedMac.equalsIgnoreCase(getCurrentMac);
        }

        return true;
    }

    protected String getCurrentMac() throws UnknownHostException, SocketException {
        if (!Optional.ofNullable(networkInterface).isPresent()) {
            prepareNetworkInterfaceOfLocalhost();
        }
        byte[] macBytes = networkInterface.getHardwareAddress();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < macBytes.length; i++) {
            sb.append(String.format("%02X%s", macBytes[i], (i < macBytes.length - 1) ? ":" : StringUtils.EMPTY));
        }
        return sb.toString();
    }

    private void prepareNetworkInterfaceOfLocalhost() throws UnknownHostException, SocketException {
        networkInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
    }
}
