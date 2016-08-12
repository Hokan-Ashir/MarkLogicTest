package ru.hokan.modules;

import com.marklogic.xcc.ResultItem;
import com.marklogic.xcc.ResultSequence;
import com.marklogic.xcc.exceptions.RequestException;

import java.util.ArrayList;
import java.util.List;

public class SearchContentModule extends AbstractContentModule {

    public List<String> getDocumentURIsContainingValue(String value) throws RequestException {
        List<String> resultURIs = new ArrayList<>();

        getLogger().info("Trying to get all document's URIs that contain '" + value + "'");

        String query = "cts:search(/, cts:word-query(\"" + value + "\"))";
        ResultSequence resultSequence = executeQuery(query);
        while (resultSequence.hasNext()) {
            ResultItem next = resultSequence.next();
            String documentURI = next.getDocumentURI();
            resultURIs.add(documentURI);
        }

        getLogger().info("Successfully got all document's URIs that contain '" + value + "'");
        return resultURIs;
    }

    public List<String> getDocumentsContainingValue(String value) throws RequestException {
        List<String> resultDocumentContents = new ArrayList<>();

        getLogger().info("Trying to get all documents that contain '" + value + "'");

        String query = "cts:search(/, cts:word-query(\"" + value + "\"))";
        ResultSequence resultSequence = executeQuery(query);
        while (resultSequence.hasNext()) {
            ResultItem next = resultSequence.next();
            String documentContent = next.getItem().asString();
            resultDocumentContents.add(documentContent);
        }

        getLogger().info("Successfully got all documents that contain '" + value + "'");
        return resultDocumentContents;
    }
}
