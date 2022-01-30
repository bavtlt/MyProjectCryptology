import java.io.IOException;
import java.util.Scanner;

public class СryptographFiles {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZабвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ .,!:;?%0123456789";

    static void encryptFileWithKey() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите полный путь до файла:");
        String filePath = scanner.nextLine();
        String fileText = GeneralFiles.getFileText(filePath);

        System.out.println("Введите ключ от 0 до 20:");
        int key = scanner.nextInt();

        System.out.println("Зашифровываю файл...");
        String newFilePath = GeneralFiles.getNewFilePath(filePath, "-encrypted");
        try {
            GeneralFiles.writeNewTextToFile(encryptText(fileText, key), newFilePath);
            System.out.println("Файл зашифрован. Путь до файла: \n" + newFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    static String encryptText(String fileText, int key) {

        StringBuilder stringBuilder = new StringBuilder();

        if (fileText != null) {
            for (int i = 0; i < fileText.length(); i++) {
                char symbolText = fileText.charAt(i);
                int alphabetSymbolIndex = ALPHABET.indexOf(symbolText);
                //вычисляем новый индекс с учетом что если введут ключ больше длины алфавита
                int newAlphabetSymbolIndex = (alphabetSymbolIndex + key) % ALPHABET.length();
                char encryptedSymbol = ALPHABET.charAt(newAlphabetSymbolIndex);
                stringBuilder.append(encryptedSymbol);
            }
        }
        return stringBuilder.toString();
    }

    static void decryptFileWithKey() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите полный путь до файла:");
        String filePath = scanner.nextLine();
        String fileText = GeneralFiles.getFileText(filePath);

        System.out.println("Введите ключ:");
        int key = scanner.nextInt();

        System.out.println("Расшифровываю файл...");
        String newFilePath = GeneralFiles.getNewFilePath(filePath, "-decrypted");
        try {
            GeneralFiles.writeNewTextToFile(encryptText(fileText, key), newFilePath);
            System.out.println("Файл расшифрован. Путь до файла: \n" + newFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    static void decryptFileStatisticalAnalysis() {
    }

    static void decryptFileBruteForce() {
    }


}
