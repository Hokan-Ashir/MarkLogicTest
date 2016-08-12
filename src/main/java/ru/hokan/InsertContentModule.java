package ru.hokan;

import com.marklogic.xcc.Content;
import com.marklogic.xcc.ContentCreateOptions;
import com.marklogic.xcc.ContentFactory;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;

import java.io.File;
import java.util.UUID;

public class InsertContentModule {

    private ContentCreateOptions options;
    private Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    public void setOptions(ContentCreateOptions options) {
        this.options = options;
    }

    private void load(String[] uris, File[] files) throws RequestException {
        Content[] contents = new Content[files.length];

        for(int i = 0; i < files.length; ++i) {
            contents[i] = ContentFactory.newContent(uris[i], files[i], this.options);
        }

        this.session.insertContent(contents);
    }

    public void load(File[] files) throws RequestException {
        String[] uris = new String[files.length];

        for(int i = 0; i < files.length; ++i) {
            uris[i] = UUID.randomUUID().toString();
        }

        this.load(uris, files);
    }

}
