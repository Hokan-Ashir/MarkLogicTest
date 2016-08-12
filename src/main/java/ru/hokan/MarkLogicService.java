package ru.hokan;

import com.marklogic.xcc.ContentSource;
import com.marklogic.xcc.ContentSourceFactory;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;
import com.marklogic.xcc.exceptions.XccConfigException;
import org.apache.log4j.Logger;
import ru.hokan.modules.DeleteContentModule;
import ru.hokan.modules.InsertContentModule;
import ru.hokan.modules.ReadContentModule;
import ru.hokan.modules.UpdateContentModule;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public enum  MarkLogicService {
    INSTANCE;

    private static final Logger LOGGER = Logger.getLogger(MarkLogicService.class);

    private static final String USER_NAME = "admin";
    private static final String PASSWORD = "admin";
    private static final String HOST_IP = "10.16.9.49";
    private static final String PORT = "9999";
    private static final String DATABASE_NAME = "ML-TEST";
    private static final String MARK_LOGIC_XDBC_SERVER = "xcc://" + USER_NAME + ":" + PASSWORD + "@" + HOST_IP + ":" + PORT + "/" + DATABASE_NAME;

    private final InsertContentModule insertContentModule;
    private final DeleteContentModule deleteContentModule;
    private final UpdateContentModule updateContentModule;
    private final ReadContentModule readContentModule;

    MarkLogicService() {
        insertContentModule = new InsertContentModule();
        deleteContentModule = new DeleteContentModule();
        updateContentModule = new UpdateContentModule();
        readContentModule = new ReadContentModule();
    }

    private Session createSession() {
        URI uri;
        try {
            uri = new URI(MARK_LOGIC_XDBC_SERVER);
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        ContentSource contentSource;
        try {
            contentSource = ContentSourceFactory.newContentSource(uri);
        } catch (XccConfigException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }

        return contentSource.newSession();
    }

    public String insertResourceFile(String fileName) {
        URL resource = getClass().getClassLoader().getResource(fileName);
        File file;
        try {
            file = new File(resource.toURI());
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }

        String insertedFileURI = null;
        try (Session session = createSession()) {
            assert session != null;

            insertContentModule.setSession(session);
            try {
                insertedFileURI = insertContentModule.insertFile(file);
            } catch (RequestException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        return insertedFileURI;
    }

    public void deleteDocument(String documentURI) {
        try (Session session = createSession()) {
            deleteContentModule.setSession(session);
            try {
                deleteContentModule.deleteDocument(documentURI);
            } catch (RequestException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    public void updateDocumentContent(String documentURI, String newDocumentContent) {
        try (Session session = createSession()) {
            updateContentModule.setSession(session);
            try {
                updateContentModule.updateDocumentContent(documentURI, newDocumentContent);
            } catch (RequestException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    public void updateDocumentNodeValue(String documentURI, String nodeName, String newNodeValue) {
        try (Session session = createSession()) {
            updateContentModule.setSession(session);
            try {
                updateContentModule.updateDocumentNodeValue(documentURI, nodeName, newNodeValue);
            } catch (RequestException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    public String getDocumentContent(String documentURI) {
        String documentContent = "";
        try (Session session = createSession()) {
            readContentModule.setSession(session);
            try {
                documentContent = readContentModule.getDocumentContent(documentURI);
            } catch (RequestException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        return documentContent;
    }
}
