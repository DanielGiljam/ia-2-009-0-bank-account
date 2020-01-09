package com.danielgiljam.ia_2_009_0_bank_account.resources;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.PrintStream;

/**
 * Event handler class to provide to the {@link javax.xml.parsers.SAXParser} for parsing the resource XML-files.
 */
class ResourceHandler extends DefaultHandler {

    private final ResourceDistributor rd;
    private final PrintStream ips;
    private final PrintStream eps;

    private static final String parseExceptionInfo = "URI=%s Line=%s: %s";
    private static final String startDocument = "Started parsing document \"%s\"";
    private static final String endDocument = "Finished parsing document \"%s\"";

    private String pathToFile = "";

    /**
     * Constructor used once only by {@link ResourceParser}.
     * @param rd A reference to the {@link ResourceDistributor} for dispatching the resources to it once they are extracted.
     */
    ResourceHandler(ResourceDistributor rd) {
        this.rd = rd;
        this.ips = System.out;
        this.eps = System.err;
    }

    // TODO: finish this class

    void providePathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    private String getParseExceptionInfo(SAXParseException e) {

        return String.format(parseExceptionInfo,    ((e.getSystemId() == null) ? "null" : e.getSystemId()),
                                                    e.getLineNumber(),
                                                    e.getMessage());
    }

    @Override
    public void startDocument() {
        ips.println(String.format(startDocument, pathToFile));
    }

    @Override
    public void endDocument() {
        ips.println(String.format(endDocument, pathToFile));
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
    }

    @Override
    public void warning(SAXParseException e) {
        eps.println("Warning: " + getParseExceptionInfo(e));
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        throw new SAXException("Error: " + getParseExceptionInfo(e));
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        throw new SAXException("Fatal error: " + getParseExceptionInfo(e));
    }
}
