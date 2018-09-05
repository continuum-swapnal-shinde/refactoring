package continuum.cucumber.PageKafkaExecutorsHelpers;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.aspectj.util.FileUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileManager {

    public static void replaceFile(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom); // convert from String to Path
        Path to = Paths.get(pathTo);
        try {
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void replaceFile(InputStream inputStream, Path pathTo) {
        try {
            Files.copy(inputStream, pathTo, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
        	System.err.println(e.getMessage());
        }
    }

    /**
     * Relocate file from resources to real location
     * @param classWithResources
     * @param resourceFilePath
     * @param realPath
     * @return
     */
    public static String relocateFile(Class classWithResources, String resourceFilePath, String realPath){
        try (InputStream inputStream = classWithResources.getResourceAsStream(resourceFilePath)) {
            String fileName = FilenameUtils.getName(resourceFilePath);
            File targetFile = getCreatedFile(realPath, fileName);
            replaceFile(inputStream, targetFile.toPath());
            return targetFile.getAbsolutePath();
        } catch (IOException e) {
        	System.err.println(e.getMessage());
        }
        return "";
    }

    /**
     * Relocate file from resources to real location
     * @param classWithResources
     * @param resourceFilePath
     * @return
     */
    public static String readFileFromResources(Class classWithResources, String resourceFilePath){
        try (InputStream inputStream = classWithResources.getResourceAsStream(resourceFilePath)) {
            return IOUtils.toString(inputStream, "UTF-8");
        } catch (IOException e) {
        	System.err.println(e.getMessage());
        }
        return "";
    }

    public static void createDirectory(String dirPath){
        File directory = new File(dirPath);
        if (! directory.exists()){
        	System.out.println("Create new directory: " + dirPath);
            directory.mkdir();
        }
    }

    public static void createFile(String filePath){
    	System.out.println("Create new file: " + filePath);
        try {
            new File(filePath).createNewFile();
        } catch (IOException e) {
        	System.err.println(e.getMessage());
        }
    }

    public static void removeFile(String filePath){
    	System.out.println("Remove file: {}"+ filePath);
        try {
            FileUtils.forceDelete(new File(filePath));
        } catch (IOException e) {
        	System.err.println(e.getMessage());
        }
    }

    public static void removeDirectory(String folderPath){
    	System.out.println("Remove directory: {}"+ folderPath);
        try {
            FileUtils.deleteDirectory(new File(folderPath));
        } catch (IOException e) {
        	System.err.println(e.getMessage());
        }
    }

    private static File getCreatedFile(String realPath, String fileName){
        File targetFile = new File(realPath + "/" + fileName);
        targetFile.mkdirs();
        return targetFile;
    }

    public static String loadFileAsString(String path) {
        String fileConеents=null;
        try (InputStream inputStream = FileUtil.class.getResourceAsStream(path)) {
            fileConеents = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileConеents;
    }

    /**
     * get resource by specified path. if resource was not found, assume path variable is absolute already.
     */
    public static String getAbsPath(String path) {
        return FileUtil.class.getResource(path) != null?
                FileUtil.class.getResource(path).getPath() : path;
    }
}
