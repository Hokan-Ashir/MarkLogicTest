package ru.hokan.modules;

import com.marklogic.xcc.exceptions.RequestException;

public class DeleteContentModule extends AbstractContentModule {

    public void deleteDocument(String documentURI) throws RequestException {
        getLogger().info("Trying to delete document with URI: " + documentURI);

        String query = "xdmp:document-delete('" + documentURI + "')";
        executeQuery(query);

        getLogger().info("Deleted document with URI: " + documentURI);
    }
}
