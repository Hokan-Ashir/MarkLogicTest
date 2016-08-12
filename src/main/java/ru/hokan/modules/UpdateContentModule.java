package ru.hokan.modules;

import com.marklogic.xcc.exceptions.RequestException;

public class UpdateContentModule extends AbstractContentModule {

    public void updateDocumentContent(String documentURI, String newDocumentContent) throws RequestException {
        getLogger().info("Trying to update document with URI: " + documentURI + " with:\n" + newDocumentContent);

        String query = "xdmp:document-insert('" + documentURI + "', " + newDocumentContent + ")";
        executeQuery(query);

        getLogger().info("Updated document with URI: " + documentURI + " with\n" + newDocumentContent);
    }

    public void updateDocumentNodeValue(String documentURI, String nodeName, String newNodeValue) throws RequestException {
        getLogger().info("Trying to update document with URI: " + documentURI + " with: " + newNodeValue + " for node " + nodeName);

        String query = "xdmp:node-replace(doc('" + documentURI + "')//" + nodeName + ", <" + nodeName + ">" + newNodeValue + "</" + nodeName + ">)";
        executeQuery(query);

        getLogger().info("Updated document with URI: " + documentURI + " with: " + newNodeValue + " for node " + nodeName);
    }
}
