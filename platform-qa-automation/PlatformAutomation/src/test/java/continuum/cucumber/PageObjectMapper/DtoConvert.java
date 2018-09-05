package continuum.cucumber.PageObjectMapper;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.List;
import java.util.Map;

public class DtoConvert {

    private DtoConvert() {
        throw new AssertionError();
    }

    @SuppressWarnings("unchecked")
    public static String dtoToJsonString(Object dtoClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {
        	mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.writeValueAsString(dtoClass);
        } catch (JsonProcessingException e) {
        	System.err.println(e.getMessage());
        }
        return "";
    }
    
    @SuppressWarnings("unchecked")
    public static String dtoToJsonStringNonEmpty(Object dtoClass,boolean inclusion) {
        ObjectMapper mapper = new ObjectMapper();
        try {
        	mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            if(inclusion){
            	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            }
            else{
            	mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            }
            
            return mapper.writeValueAsString(dtoClass);
        } catch (JsonProcessingException e) {
        	System.err.println(e.getMessage());
        }
        return "";
    }

    @SuppressWarnings("unchecked")
    public static Map<Object, Object> dtoToMap(Object dtoClass) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Map<Object, Object> map = mapper.convertValue(dtoClass, Map.class);
        return map;
    }

//    @SuppressWarnings("unchecked")
//    public static List<Map<String, Object>> dtoToListOfMaps(List dtoClass) {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
////        List<Map<String, Object>> map = mapper.convertValue(dtoClass, List.class<Map.class>);
//        TypeReference typeReference = new TypeReference<List<Map<String, Object>>>(){};
//        List<Map<String, Object>> map = mapper.convertValue(dtoClass, typeReference);
//        return map;
//    }

    @SuppressWarnings("unchecked")
    public static <T> T mapDtoObject(Object dtoClass, boolean includeNonNull) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        if (includeNonNull) {
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        Object obj = mapper.convertValue(dtoClass, Object.class);
        return (T)obj;
    }

    @SuppressWarnings("unchecked")
    public static <T> T jsonStringToDto(Object dtoClass, String content , boolean failOnUnknown) {
        ObjectMapper dtoObjectMapper = new ObjectMapper();
        dtoObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnknown);

//        dtoObjectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        try {
            if (content.startsWith("[")) {
                TypeFactory typeFactory = dtoObjectMapper.getTypeFactory();
                CollectionType collectionType = typeFactory.constructCollectionType(List.class, dtoClass.getClass());
                return (T) dtoObjectMapper.readValue(content, collectionType);
            } else if (content.startsWith("{")) {
                return (T) dtoObjectMapper.readValue(content, dtoClass.getClass());
            } else return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T jsonFileToMap(String filePath, Class<?> klass, boolean failOnUnknown) {
        ObjectMapper dtoObjectMapper = new ObjectMapper();
        dtoObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnknown);

        try (InputStream inputStream = this.getClass().getResourceAsStream(filePath)) {
            return (T) dtoObjectMapper.readValue(inputStream, klass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T jsonFileToDto(Class<?> configClass, String filePath, boolean failOnUnknown){
        ObjectMapper dtoObjectMapper = new ObjectMapper();
        dtoObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnknown);
        try (InputStream inputStream = configClass.getResourceAsStream(filePath)) {
            return (T) dtoObjectMapper.readValue(inputStream, configClass);
        } catch (IOException e) {
        	System.err.println(e);
        }
        return (T) new Object();
    }

    @SuppressWarnings("unchecked")
    public static <T> T stringToDto(Class<?> dtoClass, String content, boolean failOnUnknown) {
        ObjectMapper dtoObjectMapper = new ObjectMapper();
        dtoObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnknown);
        try {
            return (T) dtoObjectMapper.readValue(content, dtoClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T inputStreamToDto(Class<?> dtoClass, InputStream content, boolean failOnUnknown) {
        ObjectMapper dtoObjectMapper = new ObjectMapper();
        dtoObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnknown);
        //dtoObjectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        try {
            return (T) dtoObjectMapper.readValue(content, dtoClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T mapToDto(Class<?> dtoClass, Map<String, Object> content, boolean failOnUnknown) {
        ObjectMapper dtoObjectMapper = new ObjectMapper();
        dtoObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnknown);
//        dtoObjectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        try {
            return (T) dtoObjectMapper.convertValue(content, dtoClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T mapToDto(Object object, Map<String, Object> content, boolean failOnUnknown) {
        ObjectMapper dtoObjectMapper = new ObjectMapper();
        dtoObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnknown);
//        dtoObjectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        try {
            return (T) dtoObjectMapper.convertValue(content, object.getClass());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // convert InputStream to String
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
