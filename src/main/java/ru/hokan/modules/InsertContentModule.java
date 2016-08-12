package ru.hokan.modules;

import com.marklogic.xcc.Content;
import com.marklogic.xcc.ContentCreateOptions;
import com.marklogic.xcc.ContentFactory;
import com.marklogic.xcc.exceptions.RequestException;

import java.io.File;
import java.util.UUID;

public class InsertContentModule extends AbstractContentModule {

    private static final String XML_FILE_EXTENSION = ".xml";

    private ContentCreateOptions options;

    public String insertFile(File file) throws RequestException {
        String[] uris = insertFiles(new File[]{file});
        return uris[0];
    }

    public String[] insertFiles(File[] files) throws RequestException {
        String[] uris = new String[files.length];

        for (int i = 0; i < files.length; ++i) {
            uris[i] = UUID.randomUUID().toString() + XML_FILE_EXTENSION;
        }

        this.insertFiles(uris, files);

        return uris;
    }

    private void insertFiles(String[] uris, File[] files) throws RequestException {
        Content[] contents = new Content[files.length];
        for(int i = 0; i < files.length; ++i) {
            contents[i] = ContentFactory.newContent(uris[i], files[i], this.options);
        }

        logInsertedContent("Trying to insert:", files, contents);
        getSession().insertContent(contents);
        logInsertedContent("Inserted:", files, contents);
    }

    private void logInsertedContent(String intent, File[] files, Content[] contents) {
        StringBuilder builder = new StringBuilder(intent + "\n");
        for (int i = 0; i < files.length; ++i) {
            builder.append("File: ");
            builder.append(files[i].getAbsolutePath());
            builder.append(" with URI: ");
            builder.append(contents[i].getUri());
            builder.append("\n");
        }
        getLogger().info(builder.toString());
    }

    public void setOptions(ContentCreateOptions options) {
        this.options = options;
    }
}
