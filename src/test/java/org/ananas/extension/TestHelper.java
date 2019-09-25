package org.ananas.extension;

import java.net.URL;

public class TestHelper {
    public static URL getResource(String relativePath) {
        ClassLoader classLoader = TestHelper.class.getClassLoader();
        URL resource = classLoader.getResource(relativePath);

        return resource;
    }
}
