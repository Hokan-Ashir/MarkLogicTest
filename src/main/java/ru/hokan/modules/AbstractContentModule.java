package ru.hokan.modules;

import com.marklogic.xcc.AdhocQuery;
import com.marklogic.xcc.RequestOptions;
import com.marklogic.xcc.ResultSequence;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;
import org.apache.log4j.Logger;

public class AbstractContentModule {

    private final Logger logger = Logger.getLogger(AbstractContentModule.class);
    private Session session;

    public Logger getLogger() {
        return logger;
    }

    public ResultSequence executeQuery(String query) throws RequestException {
        AdhocQuery adhocQuery = getSession().newAdhocQuery(query);
        RequestOptions options = new RequestOptions();
        options.setQueryLanguage("XQuery");
        adhocQuery.setOptions(options);
        return getSession().submitRequest(adhocQuery);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
