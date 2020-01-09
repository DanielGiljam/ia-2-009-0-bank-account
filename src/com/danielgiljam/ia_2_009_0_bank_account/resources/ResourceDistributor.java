package com.danielgiljam.ia_2_009_0_bank_account.resources;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Paths;

public class ResourceDistributor {

    private static final String XML_RESOURCES_PATH = "out/production/ia-2-009-0-bank-account";

    public static ResourceDistributor initialize() throws IOException, SAXException, ParserConfigurationException {
        ResourceParser rp = new ResourceParser();
        return rp.parse(Paths.get(XML_RESOURCES_PATH), new ResourceDistributor());
    }
}
