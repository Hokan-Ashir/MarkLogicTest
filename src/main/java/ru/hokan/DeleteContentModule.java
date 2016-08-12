package ru.hokan;

import com.marklogic.xcc.AdhocQuery;
import com.marklogic.xcc.RequestOptions;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;
import org.apache.log4j.Logger;

public class DeleteContentModule {

    private static final Logger LOGGER = Logger.getLogger(DeleteContentModule.class);

    private Session session;

    public void deleteDocument(String documentURI) throws RequestException {
        LOGGER.info("Trying to delete document with URI: " + documentURI);

        AdhocQuery adhocQuery = session.newAdhocQuery("xdmp:document-delete('" + documentURI + "')");
        RequestOptions options = new RequestOptions();
        options.setQueryLanguage("XQuery");
        adhocQuery.setOptions(options);
        session.submitRequest(adhocQuery);

        LOGGER.info("Deleted document with URI: " + documentURI);
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
