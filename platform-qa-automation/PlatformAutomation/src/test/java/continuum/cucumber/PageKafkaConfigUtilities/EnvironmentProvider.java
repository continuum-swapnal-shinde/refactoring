package continuum.cucumber.PageKafkaConfigUtilities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import continuum.cucumber.PageObjectMapper.DtoConvert;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static continuum.cucumber.PageKafkaConfigUtilities.MavenProperties.getCurrentEnvironment;

public class EnvironmentProvider {

    private static final String FILE_NAME = "environment.json";
    private static final String RESOURCE = "/environments";

    private static AtomicBoolean isLoaded = new AtomicBoolean(false);

    private static final ThreadLocal<EnvironmentProvider> providerThreadLocal = new ThreadLocal<>();

    @JsonProperty("environments")
    private Map<String, Object> environments;

    @JsonCreator
    private EnvironmentProvider(Map<String, Object> environments) {
        this.environments = environments;
    }

    private EnvironmentProvider(){
    }

    private Map<String, Object> getEnvironmentSettings() {
        return environments;
    }

    private Map<String, Object> getEnvironemtns() {
        return environments;
    }

    public static EnvironmentProvider provideEnvironment() {
        if (providerThreadLocal.get() == null) {
            loadEnvironmentJson();
        }
        return providerThreadLocal.get();
    }

    private static void loadEnvironmentJson() {
        String path = RESOURCE + "/" + getCurrentEnvironment().getName() + "/" + FILE_NAME;
        EnvironmentProvider environmentProvider = new EnvironmentProvider();
        environmentProvider.environments = ((EnvironmentProvider)DtoConvert
                .jsonFileToDto(
                        EnvironmentProvider.class,
                        path, false)).getEnvironmentSettings();
        providerThreadLocal.set(environmentProvider);
    }

    public Object getSettings(String settingName) {
        return this.getEnvironemtns().get(settingName);
    }

}
