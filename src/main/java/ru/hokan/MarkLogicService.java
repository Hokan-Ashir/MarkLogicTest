package ru.hokan;

import com.marklogic.xcc.ContentSource;
import com.marklogic.xcc.ContentSourceFactory;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;
import com.marklogic.xcc.exceptions.XccConfigException;
import org.apache.log4j.Logger;
import ru.hokan.modules.*;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public enum  MarkLogicService {
    INSTANCE;

    private static final Logger LOGGER = Logger.getLogger(MarkLogicService.class);

    private static final String DEFAULT_USER_NAME = "admin";
    private static final String DEFAULT_PASSWORD = "admin";
    private static final String DEFAULT_HOST_IP = "10.16.9.49";
    private static final String DEFAULT_PORT = "9999";
    private static final String DEFAULT_DATABASE_NAME = "ML-TEST";

    private final InsertContentModule insertContentModule;
    private final DeleteContentModule deleteContentModule;
    private final UpdateContentModule updateContentModule;
    private final ReadContentModule readContentModule;
    private final SearchContentModule searchContentModule;

    private String hostIp;
    private String port;
    private String databaseName;
    private String userName;
    private String password;

    MarkLogicService() {
        this.hostIp = DEFAULT_HOST_IP;
        this.port = DEFAULT_PORT;
        this.databaseName = DEFAULT_DATABASE_NAME;
        this.userName = DEFAULT_USER_NAME;
        this.password = DEFAULT_PASSWORD;

        insertContentModule = new InsertContentModule();
        deleteContentModule = new DeleteContentModule();
        updateContentModule = new UpdateContentModule();
        readContentModule = new ReadContentModule();
        searchContentModule = new SearchContentModule();
    }

    private Session createSession() {
        URI uri;
        try {
            String connectionString = "xcc://" + userName + ":" + password + "@" + hostIp + ":" + port + "/" + databaseName;
            uri = new URI(connectionString);
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

    public List<String> getDocumentURIsContainingValue(String value) {
        List<String> documentURIsContainingValue = Collections.emptyList();
        try (Session session = createSession()) {
            searchContentModule.setSession(session);
            try {
                documentURIsContainingValue = searchContentModule.getDocumentURIsContainingValue(value);
            } catch (RequestException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        return documentURIsContainingValue;
    }

    public List<String> getDocumentsContentContainingValue(String value) {
        List<String> documentsContainingValue = Collections.emptyList();
        try (Session session = createSession()) {
            searchContentModule.setSession(session);
            try {
                documentsContainingValue = searchContentModule.getDocumentsContainingValue(value);
            } catch (RequestException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        return documentsContainingValue;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
