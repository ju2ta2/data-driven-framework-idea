package com.nostromo.rough;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class TestProperties {
    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir"));
        Properties config = new Properties();
        Properties OR = new Properties();

        FileInputStream fis = new FileInputStream(Paths.get("src/test/resources/properties/Config.properties").toAbsolutePath().toString());
        config.load(fis);

        fis = new FileInputStream(Paths.get("src/test/resources/properties/OR.properties").toAbsolutePath().toString());
        OR.load(fis);

        System.out.println(config.getProperty("browser"));
        System.out.println(config.getProperty("OS"));
        System.out.println(OR.getProperty("bankMngLogBtn"));
    }
}
