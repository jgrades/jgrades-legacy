/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.logging.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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

public class LogbackXmlEditor {
    private static Document documentUnderEdit;

    private synchronized static void forgetDocumentUnderEdit() {
        documentUnderEdit = null;
    }

    private Document getConfigDocument() {
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

    public NodeList getFileNamePatternNodes() {
        return getNodes(".//fileNamePattern");
    }

    public NodeList getMaxFileSizeNodes() {
        return getNodes(".//maxFileSize");
    }

    public NodeList getMaxDays() {
        return getNodes(".//maxHistory");
    }

    public String getLevelNameFromAppenderName(Node fileNamePatternNode) {
        String appenderName = getAppenderName(fileNamePatternNode);
        return appenderName.substring(appenderName.lastIndexOf("-") + 1);
    }

    public String getLogTypeFromAppenderName(Node fileNamePatternNode) {
        String appenderName = getAppenderName(fileNamePatternNode);
        String appenderNameWithoutLevel = appenderName.substring(0, appenderName.lastIndexOf("-"));
        return appenderNameWithoutLevel.substring(appenderNameWithoutLevel.lastIndexOf("-") + 1);
    }

    private String getAppenderName(Node fileNamePatternNode) {
        return ((Element) fileNamePatternNode.getParentNode().getParentNode()).getAttribute("name");
    }

    public Node getLevelNode() {
        return getNodes(".//root/@level").item(0);
    }

    private NodeList getNodes(String xpathExpression) {
        if (documentUnderEdit == null) {
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
            forgetDocumentUnderEdit();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public boolean isXmlExists() {
        return new File(InternalProperties.XML_FILE).exists();
    }
}
