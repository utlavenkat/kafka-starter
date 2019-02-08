package org.venkat.kafka.properties;


import org.junit.Test;
import static org.junit.Assert.*;

public class PropertiesLoaderTest {

    @Test
    public void getPropertyValue() {
        assertNotNull(PropertiesLoader.getPropertyValue("bootstrap.server"));
    }
}
