package com.danielgiljam.resource_distributor;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Sets up and executes the parsing of the resource XML-files.
 */
class ResourceParser {

    private final SAXParserFactory parserFactory;

    private static final String schemaLanguage = "http://www.w3.org/2001/XMLSchema";
    private static final String XML_SCHEMA_PATH = "out/production/ia-2-009-0-bank-account/console-dialogue.xsd";

    /**
     * Constructor used once only by {@link ResourceDistributor}.
     */
    ResourceParser() {

        parserFactory = SAXParserFactory.newInstance();
        parserFactory.setNamespaceAware(true);

        SchemaFactory schemaFactory = SchemaFactory.newInstance(schemaLanguage);

        try {
            parserFactory.setSchema(schemaFactory.newSchema(new File(XML_SCHEMA_PATH)));
        } catch (SAXException e) {
            System.err.println("Failed in setting schema on parser factory.");
            e.printStackTrace();
        }
    }

    /**
     * Executes the parsing.
     *
     * @param xmlFilesDirectory URL to the resource XML-file.
     * @param rd A reference to the {@link ResourceDistributor} for dispatching the resources to it once they are extracted.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    ResourceDistributor parse(Path xmlFilesDirectory, ResourceDistributor rd) throws ParserConfigurationException, SAXException, IOException {

        final SAXParser parser = parserFactory.newSAXParser();

        if (Files.isDirectory(xmlFilesDirectory)) {

            List<File> xmlFiles;

            try (final Stream<Path> xmlFilesStream = Files.walk(xmlFilesDirectory)) {
                xmlFiles = xmlFilesStream.filter(ResourceParser::xmlFilesFilter)
                                .map(Path::toFile)
                                .collect(Collectors.toList());
            }

            final ResourceHandler rh = new ResourceHandler(rd);

            for (File xmlFile : xmlFiles) {
                rh.providePathToFile(xmlFile.getPath());
                parser.parse(xmlFile, rh);
            }

            return rd;

        } else throw new DirectoryNotFoundException(xmlFilesDirectory.toString());
    }

    /**
     * Tests .xml -files.
     * @param path A {@link Path} pointing a potential .xml -file.
     * @return True or false.
     */
    private static boolean xmlFilesFilter(Path path) {
        return Files.isRegularFile(path) && Pattern.matches("^.*\\.xml$", path.toString());
    }

    /**
     * Signals that a specified pathname doesn't point to a directory, when it's expected should do so.
     */
    private static class DirectoryNotFoundException extends FileNotFoundException {

        private static final String messageTemplate = "The path \"%s\" was not a path to a directory.";

        DirectoryNotFoundException(String s) {
            super(String.format(messageTemplate, s));
        }
    }
}
