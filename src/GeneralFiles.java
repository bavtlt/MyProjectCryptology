import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GeneralFiles {

    public static String getNewFilePath(String filePath, String filenameEnding){

        int dotIndex = filePath.lastIndexOf(".");
        String pathBeforeDot = filePath.substring(0, dotIndex);
        String pathAfterDot = filePath.substring(dotIndex);
        String newFilePath = pathBeforeDot + filenameEnding + pathAfterDot;

        return newFilePath;
    }

    public static void writeNewTextToFile(String text, String newFilePath) throws IOException {
        Files.writeString(Path.of(newFilePath), text);
    }

    public static String getFileText(String filePath) {

        try {
            return new String(Files.readAllBytes(Path.of(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
