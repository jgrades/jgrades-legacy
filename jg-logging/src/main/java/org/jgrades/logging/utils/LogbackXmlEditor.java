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

public final class LogbackXmlEditor {
    private static Document documentUnderEdit;

    private Document getConfigDocument(){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            return docBuilder.parse(InternalProperties.XML_FILE);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            return null;
        }
    }

    private XPathExpression getXPathExpression(String xpathExpression) throws XPathExpressionException {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        return xpath.compile(xpathExpression);
    }

    public NodeList getNodes(String xpathExpression)  {
        if(documentUnderEdit == null){
            documentUnderEdit = getConfigDocument();
        }
        try {
            return (NodeList) getXPathExpression(xpathExpression).evaluate(documentUnderEdit, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            return null;
        }
    }
    
    public void saveWithChanges() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(documentUnderEdit);
            StreamResult result = new StreamResult(new File(InternalProperties.XML_FILE));
            transformer.transform(source, result);
            documentUnderEdit = null;
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public boolean isXmlExists() {
        return new File(InternalProperties.XML_FILE).exists();
    }
}
