package continuum.cucumber.PageKafkaConfigUtilities;


import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public class MavenProperties {
    public static final String MAVEN_PROPERTIES = "/maven.properties";
    private static final String ENVIRONMENT_PROPERTY = "ENV_NAME";
    private static Properties properties;
    private static AtomicBoolean isLoaded = new AtomicBoolean(false);

    public static Properties getMavenProperties() {
        if (!isLoaded.get()) {
            loadProperties();
            isLoaded.set(true);
        }
        return properties;
    }
    public static Environments getCurrentEnvironment() {
        return Environments.fromString(getMavenProperties().getProperty(ENVIRONMENT_PROPERTY));
    }

    private static void loadProperties() {
        properties = ConfigLoader.loadProps(MAVEN_PROPERTIES, true);
    }

    public static void replaceSystemProperties() {
        properties = ConfigLoader.replaceSystemProperties(properties);
    }

}
