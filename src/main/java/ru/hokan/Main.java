package ru.hokan;

public class Main {
    public static void main(String[] args) {
        String documentURI = MarkLogicService.INSTANCE.insertResourceFile("examples/john_doe.xml");
        MarkLogicService.INSTANCE.deleteDocument(documentURI);
    }
}
