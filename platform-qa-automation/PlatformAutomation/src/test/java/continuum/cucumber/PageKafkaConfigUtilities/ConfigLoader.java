package continuum.cucumber.PageKafkaConfigUtilities;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class ConfigLoader {

    /**
     * This method is used to Load config into class variables. Class variables should be static
     *
     * @param configClass
     * @param file
     */
    public static void load(Class<?> configClass, String file) {
        try {
            Properties systemProperties = System.getProperties();
            Properties props = new Properties();

            try (InputStream inputStream = configClass.getResourceAsStream(file)) {
                props.load(inputStream);
            }

            for (Field field : configClass.getDeclaredFields())
                if (Modifier.isStatic(field.getModifiers()) && (props.getProperty(field.getName())!=null || systemProperties.getProperty(field.getName())!=null)) {
                    //override property if defined
                    if (systemProperties.getProperty(field.getName())!=null) {
                        props.setProperty(field.getName(), systemProperties.getProperty(field.getName()));
                    }
                    field.set(null, getValue(props, field.getName(), field.getType()));
                }
        } catch (Exception e) {
            throw new RuntimeException("Error loading configuration: " + e, e);
        }
    }

    /**
     * This method is used to Load config into class public/private/protected/static/non-static/ variables.
     *
     * @param object
     * @param file
     */
    public static void load(Object object, String file) {
        try {
            Properties systemProperties = System.getProperties();
            Properties props = loadConfigProperties(object.getClass(), file);
            List<Field> fields = new ArrayList<>();
            Collections.addAll(fields, object.getClass().getDeclaredFields());
            Collections.addAll(fields, object.getClass().getSuperclass().getDeclaredFields());
            for (Field field : fields)
                if (props.getProperty(field.getName()) != null || systemProperties.getProperty(field.getName()) != null) {
                    //override property if defined
                    if (systemProperties.getProperty(field.getName()) != null) {
                        props.setProperty(field.getName(), systemProperties.getProperty(field.getName()));
                    }
                    field.setAccessible(true);
                    field.set(object, getValue(props, field.getName(), field.getType()));
                }
        } catch (Exception e) {
            throw new RuntimeException("Error loading configuration: " + e, e);
        }
    }

    /**
     * This method is used for the value from properties and convert to its proper type
     *
     * @param props
     * @param name
     * @param type
     * @return
     */
    private static Object getValue(Properties props, String name, Class<?> type) {
        String value = props.getProperty(name);
        if (value.equals("") && type != String.class)
            return null;
        if (value == null)
            return null;
        if (type == String.class)
            return value;
        if (type == boolean.class || type == Boolean.class)
            return Boolean.parseBoolean(value);
        if (type == int.class || type == Integer.class)
            return Integer.parseInt(value);
        if (type == float.class || type == Float.class)
            return Float.parseFloat(value);
        if (type == long.class || type == Long.class)
            return Long.parseLong(value);
        if (type == List.class || type == Arrays.class)
            return Arrays.asList(value);
        throw new IllegalArgumentException("Unknown configuration value type: " + type.getName());
    }

    /**
     * This method is used to load config properties into class
     * 
     * @param configClass
     * @param file
     * @return
     */
    public static Properties loadConfigProperties(Class<?> configClass, String file) {
        Properties props = new Properties();
        try (InputStream inputStream = configClass.getResourceAsStream(file)) {
            props.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Error loading configuration: " + e, e);
        }
        return props;
    }

    /**
     * This method is used to load properties from file.
     * 
     *
     * @param file
     * @return Properties
     */
    public static Properties loadProps(String file, boolean replaceSystemProperties) {
        try {
            Properties props = loadConfigProperties(ConfigLoader.class, file);
            if (replaceSystemProperties){
                props = ConfigLoader.replaceSystemProperties(props);
            }
            return props;
        } catch (Exception e) {
            throw new RuntimeException("Error loading configuration: " + e, e);
        }
    }

    /**
     * This method is used to Replace system properties by input properties
     *
     * @param properties
     */
    public static Properties replaceSystemProperties(Properties properties) {
        Properties toReplace = new Properties();
        Properties systemProperties = System.getProperties();
        properties.forEach((x, y) -> {
            if (systemProperties.getProperty(x.toString()) != null) {
                toReplace.setProperty(x.toString(), systemProperties.getProperty(x.toString()));
            }
        });
        toReplace.forEach((x, y) -> {
            properties.setProperty(x.toString(), y.toString());
        });

        return properties;
    }

    public static Object getProperty(Properties props, String key) {
        boolean found = false;
        List<String> vals = new ArrayList<>();
        for (Map.Entry<Object, Object> entry: props.entrySet()) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            } else if (entry.getKey().toString().contains(key)) {
                vals.add((String) entry.getKey());
            }

        }
        String max = Collections.max(vals, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
        return props.getProperty(max);
    }

    public static Properties loadProps(String file) {
        try {
            Properties systemProperties = System.getProperties();
            Properties props = new Properties();
            try (InputStream inputStream = ConfigLoader.class.getResourceAsStream(file)) {
                props.load(inputStream);

                systemProperties.forEach((x,y)->{
                    props.setProperty(x.toString(), y.toString());
                });
            }catch (Exception e) {
                throw new RuntimeException("Error loading configuration: " + e, e);
            }
            return props;

        } catch (Exception e) {
            throw new RuntimeException("Error loading configuration: " + e, e);
        }
    }

    public String getAbsPath(String filename){
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        return classloader.getResource(filename).getFile();
    }

    public static void overrideObjectBySystemProperties(Object object) {
        Properties systemProperties = System.getProperties();
        try {
            List<Field> fields = new ArrayList<>();
            Collections.addAll(fields, object.getClass().getDeclaredFields());
            Collections.addAll(fields, object.getClass().getSuperclass().getDeclaredFields());
            for (Field field : fields) {
                String propertyName;
                JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
                if (jsonProperty != null) {
                    propertyName = jsonProperty.value();
                } else {
                    propertyName = field.getName();
                }
                if (systemProperties.getProperty(propertyName) != null) {
                    field.setAccessible(true);
                    field.set(object, getValue(systemProperties, propertyName, field.getType()));
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error transforming pojo to properties: " + e, e);
        }
    }
}
