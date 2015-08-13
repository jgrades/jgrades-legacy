package org.jgrades.lic.api.service;

import org.jgrades.lic.api.model.Licence;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.helpers.DefaultValidationEventHandler;

public final class LicenceMarshallingFactory {
    private static JAXBContext jaxbContext;
    static {
        try {
            jaxbContext = JAXBContext.newInstance(Licence.class);
        } catch (JAXBException e) {
            e.printStackTrace();//TODO: use logger in future
        }
    }

    private LicenceMarshallingFactory() {
    }

    public static Marshaller getMarshaller() {
        Marshaller jaxbMarshaller = null;
        try {
            jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException e) {
            e.printStackTrace();//TODO: use logger in future
        }
        return jaxbMarshaller;
    }

    public static Unmarshaller getUnmarshaller() {
        Unmarshaller jaxbUnmarshaller = null;
        try {
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            jaxbUnmarshaller.setEventHandler(new DefaultValidationEventHandler());
        } catch (JAXBException e) {
            e.printStackTrace();//TODO: use logger in future
        }
        return jaxbUnmarshaller;
    }
}
