import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class СryptographFiles {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZабвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ .,!:;?%0123456789";

    public static void encryptFileWithKey() {

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

    public static String encryptText(String fileText, int key) {

        StringBuilder stringBuilder = new StringBuilder();

        if (fileText != null) {
            for (int i = 0; i < fileText.length(); i++) {
                char symbolText = fileText.charAt(i);
                int alphabetSymbolIndex = ALPHABET.indexOf(symbolText);
                int newAlphabetSymbolIndex = (alphabetSymbolIndex + key) % ALPHABET.length();
                char encryptedSymbol = ALPHABET.charAt(newAlphabetSymbolIndex);
                stringBuilder.append(encryptedSymbol);
            }
        }
        return stringBuilder.toString();
    }

    public static void decryptFileWithKey() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите полный путь до файла:");
        String filePath = scanner.nextLine();
        String fileText = GeneralFiles.getFileText(filePath);

        System.out.println("Введите ключ:");
        int key = scanner.nextInt();

        System.out.println("Расшифровываю файл...");
        String newFilePath = GeneralFiles.getNewFilePath(filePath, "-decrypted");
        try {
            GeneralFiles.writeNewTextToFile(decryptText(fileText, key), newFilePath);
            System.out.println("Файл расшифрован. Путь до файла: \n" + newFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    public static void decryptFileBruteForce() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите полный путь до файла:");
        String filePath = scanner.nextLine();
        String fileText = GeneralFiles.getFileText(filePath);
        for (int i = 0; i < ALPHABET.length(); i++) {
            String decryptedText = decryptText(fileText, i);
            boolean isValidText = isValidText(decryptedText);
            if (isValidText) {
                try {
                    System.out.println("Ключ: " + i);
                    String newFilePath = GeneralFiles.getNewFilePath(filePath, "-decryptedBrut");
                    GeneralFiles.writeNewTextToFile(decryptedText, newFilePath);
                    System.out.println("Файл расшифрован. Путь до файла: \n" + newFilePath);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    scanner.close();
                    break;
                }
            }
        }
    }

    private static boolean isValidText(String text) {

        String[] strings = text.split(" ");
        for (String string : strings) {
            if (string.length() > 24) {
                return false;
            }
        }

        int stringStart = new Random().nextInt(text.length() / 2);
        int stringEnd = stringStart + 300;
        System.out.println("Понятен ли Вам этот текст?\n"
                           + text.substring(stringStart, stringEnd) + "\n"
                           + "Введите 1 - если \"Текст понятен\", для продолжения подбора ключа нажмите Enter:\n");
        try (Scanner scanner = new Scanner(System.in)) {
            if (scanner.nextLine() == "1") {
                return true;
            } else {
                return false;
            }
        }
    }

    public static void decryptFileStatisticalAnalysis() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите полный путь до файла:");
        String filePath = scanner.nextLine();
        System.out.println("Введите полный путь до файла статистики:");
        String statFilePath = scanner.nextLine();
        String decryptedFileText = GeneralFiles.getFileText(filePath);
        String statFileText = GeneralFiles.getFileText(statFilePath);
        HashMap<Character, Integer> decryptedFileStatistics = getCharacterStatistics(decryptedFileText);
        HashMap<Character, Integer> statFileStatistics = getCharacterStatistics(decryptedFileText);
    }

    public static HashMap<Character, Integer> getCharacterStatistics(String text){
        HashMap<Character, Integer> resultAbsolut = new HashMap<>();
        HashMap<Character, Integer> result = new HashMap<>();
        for (int i = 0; i < text.length(); i++) {
            char symbolText = text.charAt(i);
            Integer symbolAmount = resultAbsolut.get(symbolText);
            if (symbolAmount == null) {
                resultAbsolut.put(symbolText, 1);
            }else {
                symbolAmount++;
                resultAbsolut.put(symbolText, symbolAmount);
            }
        }
        for (Character character : resultAbsolut.keySet()) {
            Integer symbolCount = resultAbsolut.get(character);
            int relativeAmount = symbolCount * 10_000 / text.length();
            result.put(character, relativeAmount);
        }

        return result;
    }

    public static String decryptText(String encryptedText, int key){
        StringBuilder stringBuilder = new StringBuilder();

        if (encryptedText != null) {
            for (int i = 0; i < encryptedText.length(); i++) {
                char symbolText = encryptedText.charAt(i);
                int alphabetSymbolIndex = ALPHABET.indexOf(symbolText);
                int newAlphabetSymbolIndex =  (ALPHABET.length()+(alphabetSymbolIndex - key)) % ALPHABET.length();
                char encryptedSymbol = ALPHABET.charAt(newAlphabetSymbolIndex);
                stringBuilder.append(encryptedSymbol);
            }
        }

        return stringBuilder.toString();
    }
}
