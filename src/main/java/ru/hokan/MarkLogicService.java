package ru.hokan;

import com.marklogic.xcc.ContentSource;
import com.marklogic.xcc.ContentSourceFactory;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;
import com.marklogic.xcc.exceptions.XccConfigException;
import org.apache.log4j.Logger;

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

    MarkLogicService() {
        insertContentModule = new InsertContentModule();
        deleteContentModule = new DeleteContentModule();
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
}
