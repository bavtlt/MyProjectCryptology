import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Cryptology {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZабвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ .,!:;?%0123456789";
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {

        String firstMenuPage = "Выберите действие, введя его номер:\n" +
                               "1. Зашифровать текст в файле с помощью ключа.\n" +
                               "2. Расшифровать текст в файле с помощью ключа.\n" +
                               "3. Расшифровать текст в файле методом перебора (брут-форс).\n" +
                               "4. Расшифровать текст в файле методом статического анализа.\n";
        System.out.println(firstMenuPage);

        while (true){

            int menuItem = SCANNER.nextInt();

            if(menuItem == 1){
                encryptFileWithKey();
                break;
            }
            else if(menuItem == 2){
                decipherFileWithKey();
                break;
            }
            else if(menuItem == 3){
                decipherFileBruteForce();
                break;
            }
            else if(menuItem == 4){
                decipherFileStatisticalAnalysis();
                break;
            }
            else if(menuItem == 5) {
                System.exit(0);
                break;
            }
            else {
                System.out.println("Такого номера нет в меню.");
            }
        }
    }


    private static void encryptFileWithKey() {
        System.out.println("Введите полный путь до файла:");
        String filePath = SCANNER.nextLine();
        String fileText = getFileText(filePath);
        System.out.println("Введите ключ от 0 до 20:");
        int key = SCANNER.nextInt();
        StringBuilder stringBuilder = new StringBuilder();
        if (fileText != null) {
            for (int i = 0; i < fileText.length(); i++) {
                char symbolText = fileText.charAt(i);
                int alphabetSymbolIndex = ALPHABET.indexOf(symbolText);
                //вычисляем новый индекс с учетом что если введут ключ больше длины алфавита
                int newAlphabetSymbolIndex = (alphabetSymbolIndex + key)%ALPHABET.length();
                char encryptedSymbol = ALPHABET.charAt(newAlphabetSymbolIndex);
                stringBuilder.append(encryptedSymbol);
            }
        }
        writeNewTextToFile(stringBuilder.toString(), filePath);
    }

    private static String getFileText(String filePath) {
        try {
            return new String(Files.readAllBytes(Path.of(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void writeNewTextToFile(String text, String filePath) {
        int dotIndex = filePath.lastIndexOf(".");
        String pathBeforeDot = filePath.substring(0, dotIndex);
        String pathAfterDot = filePath.substring(dotIndex);
        String newFilePath = pathBeforeDot + "-encrypted" + pathAfterDot;
        try {
            Files.writeString(Path.of(newFilePath), text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        private static void decipherFileStatisticalAnalysis() {
    }

    private static void decipherFileBruteForce() {
    }

    private static void decipherFileWithKey() {
    }


}
