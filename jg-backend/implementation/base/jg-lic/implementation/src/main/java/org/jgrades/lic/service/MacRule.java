package org.jgrades.lic.service;

import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import org.apache.commons.lang3.StringUtils;
import org.jgrades.lic.api.exception.ViolationOfLicenceConditionException;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.LicenceProperty;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class MacRule implements ValidationRule {
    private static final String MAC_PROPERTY_NAME = "mac";

    private List<NetworkInterface> networkInterfaces;

    public MacRule() {
    }

    public MacRule(List<NetworkInterface> networkInterfaces) {
        this.networkInterfaces = networkInterfaces;
    }

    @Override
    public boolean isValid(Licence licence) {
        List<LicenceProperty> properties = licence.getProperties();
        UnmodifiableIterator<LicenceProperty> macPropertyIterator = Iterators.filter(
                properties.iterator(), licenceProperty -> {
                    return licenceProperty.getName().equals(MAC_PROPERTY_NAME);
                });
        LicenceProperty property = null;
        if (macPropertyIterator.hasNext()) {
            property = macPropertyIterator.next();
        }

        if (Optional.ofNullable(property).isPresent()) {
            try {
                prepareNetworkInterfacesIfNeeded();
            } catch (SocketException e) {
                throw new ViolationOfLicenceConditionException(e);
            }
            String requestedMac = property.getValue();
            for (NetworkInterface networkInterface : networkInterfaces) {
                try {
                    String currentMac = getCurrentMac(networkInterface);
                    if (currentMac.equalsIgnoreCase(requestedMac)) {
                        return true;
                    }
                } catch (SocketException e) {
                    //TODO log
                }
            }
            return false;
        }
        return true;
    }

    private void prepareNetworkInterfacesIfNeeded() throws SocketException {
        if (networkInterfaces == null) {
            networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
        }
    }

    protected String getCurrentMac(NetworkInterface networkInterface) throws SocketException {
        byte[] macBytes = networkInterface.getHardwareAddress();
        if (macBytes == null) {
            return StringUtils.EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < macBytes.length; i++) {
            sb.append(String.format("%02X%s", macBytes[i], (i < macBytes.length - 1) ? ":" : StringUtils.EMPTY));
        }
        return sb.toString();
    }
}
