package ru.hokan.modules;

import com.marklogic.xcc.ResultSequence;
import com.marklogic.xcc.exceptions.RequestException;

public class ReadContentModule extends AbstractContentModule {

    public String getDocumentContent(String documentURI) throws RequestException {
        getLogger().info("Trying to get content of document with URI: " + documentURI);

        String query = "doc('" + documentURI + "')";
        ResultSequence resultSequence = executeQuery(query);
        String documentContent = resultSequence.next().getItem().asString();

        getLogger().info("Successfully got content of document with URI: " + documentURI);

        return documentContent;
    }
}
