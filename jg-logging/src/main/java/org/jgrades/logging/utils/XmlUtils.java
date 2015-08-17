package org.jgrades.logging.utils;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;

public final class XmlUtils {
    private static Document doc;
    private XmlUtils() { }

    private static Document getConfigDocument(){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            return docBuilder.parse(LoggerInternalProperties.XML_FILE);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            return null;
        }
    }

    private static XPathExpression getXPathExpression(String xpathExpression) throws XPathExpressionException {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        return xpath.compile(xpathExpression);
    }

    public static NodeList getNodeList(String xpathExpression) throws XPathExpressionException {
        if(doc == null){
            doc = getConfigDocument();
        }
        return (NodeList) getXPathExpression(xpathExpression).evaluate(doc, XPathConstants.NODESET);
    }


    public static void saveUpdated() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(LoggerInternalProperties.XML_FILE));
            transformer.transform(source, result);
            doc = null;
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
