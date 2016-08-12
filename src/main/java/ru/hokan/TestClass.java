package ru.hokan;

import org.apache.log4j.Logger;

public class TestClass {

    private static final Logger LOGGER = Logger.getLogger(TestClass.class);
    private static final String JANE_DOE_XML_FILENAME = "examples/jane_doe.xml";
    private static final String JOHN_DOE_XML_FILENAME = "examples/john_doe.xml";

    public void run() {
        testDocumentDeletion();
        testUpdatingDocumentContent();
        testUpdatingDocumentNodeValue();
        testGettingDocumentContent();
    }

    private void testDocumentDeletion() {
        String johnDoeURI = MarkLogicService.INSTANCE.insertResourceFile(JOHN_DOE_XML_FILENAME);
        MarkLogicService.INSTANCE.deleteDocument(johnDoeURI);
    }

    private void testUpdatingDocumentContent() {
        String janeDoeURI = MarkLogicService.INSTANCE.insertResourceFile(JANE_DOE_XML_FILENAME);

        String newJaneDoeDocumentContent = "<Human>\n" +
                "    <Name>Jane</Name>\n" +
                "</Human>";
        MarkLogicService.INSTANCE.updateDocumentContent(janeDoeURI, newJaneDoeDocumentContent);
    }

    private void testUpdatingDocumentNodeValue() {
        String anotherJaneDoeURI = MarkLogicService.INSTANCE.insertResourceFile(JANE_DOE_XML_FILENAME);
        String nodeName = "Active";
        String newNodeValue = "UnHuman";
        MarkLogicService.INSTANCE.updateDocumentNodeValue(anotherJaneDoeURI, nodeName, newNodeValue);
    }

    private void testGettingDocumentContent() {
        String anotherJohnDoeURI = MarkLogicService.INSTANCE.insertResourceFile(JOHN_DOE_XML_FILENAME);
        String johnDoeFileContent = MarkLogicService.INSTANCE.getDocumentContent(anotherJohnDoeURI);
        LOGGER.info(johnDoeFileContent);
    }
}
