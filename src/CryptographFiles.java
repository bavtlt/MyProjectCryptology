import java.io.IOException;
import java.util.*;

public class CryptographFiles {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZабвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ .,!:;?%0123456789";
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void encryptFileWithKey() {

        String fileText = null;
        String filePath = "";

        while (fileText == null) {
            System.out.println("Введите полный путь до файла:");
            filePath = SCANNER.nextLine();
            fileText = GeneralFiles.getFileText(filePath);
        }

        System.out.println("Введите ключ от 0 до 20:");
        int key = SCANNER.nextInt();

        System.out.println("Зашифровываю файл...");
        String newFilePath = GeneralFiles.getNewFilePath(filePath, "-encrypted");
        try {
            GeneralFiles.writeNewTextToFile(encryptText(fileText, key), newFilePath);
            System.out.println("Файл зашифрован. Путь до файла: \n" + newFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String encryptText(String fileText, int key) {

        StringBuilder stringBuilder = new StringBuilder();

        if (fileText != null) {
            char encryptedSymbol;
            for (int i = 0; i < fileText.length(); i++) {
                char symbolText = fileText.charAt(i);
                int alphabetSymbolIndex = ALPHABET.indexOf(symbolText);
                if(alphabetSymbolIndex == -1){
                    encryptedSymbol = symbolText;
                }else {
                    int newAlphabetSymbolIndex = (alphabetSymbolIndex + key) % ALPHABET.length();
                    encryptedSymbol = ALPHABET.charAt(newAlphabetSymbolIndex);
                }
                stringBuilder.append(encryptedSymbol);
            }
        }
        return stringBuilder.toString();
    }

    public static void decryptFileWithKey() {

        String fileText = null;
        String filePath = "";

        while (fileText == null) {
            System.out.println("Введите полный путь до файла:");
            filePath = SCANNER.nextLine();
            fileText = GeneralFiles.getFileText(filePath);
        }

        System.out.println("Введите ключ:");
        int key = SCANNER.nextInt();

        System.out.println("Расшифровываю файл...");
        String newFilePath = GeneralFiles.getNewFilePath(filePath, "-decrypted");
        try {
            GeneralFiles.writeNewTextToFile(decryptText(fileText, key), newFilePath);
            System.out.println("Файл расшифрован. Путь до файла: \n" + newFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decryptFileBruteForce() {

        String fileText = null;
        String filePath = "";

        while (fileText == null) {
            System.out.println("Введите полный путь до файла:");
            filePath = SCANNER.nextLine();
            fileText = GeneralFiles.getFileText(filePath);
        }

        boolean isValidText = false;
        for (int i = 0; i < ALPHABET.length(); i++) {
            String decryptedText = decryptText(fileText, i);
            isValidText = isValidText(decryptedText);
            if (isValidText) {
                try {
                    System.out.println("Ключ: " + i);
                    String newFilePath = GeneralFiles.getNewFilePath(filePath, "-decryptedBrut");
                    GeneralFiles.writeNewTextToFile(decryptedText, newFilePath);
                    System.out.println("Файл расшифрован. Путь до файла: \n" + newFilePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        if(!isValidText) {
            System.out.println("Файл не удалось расшифровать.");
        }
        
    }

    private static boolean isValidText(String text) {
        int count = 0;
        String[] strings = text.split(" ");
        for (String string : strings) {
            if (string.length() > 28) {
                return false;
            }
            if (string.lastIndexOf(",") == string.length()-1){
                count++;
            }
        }
        int totalCharacters = text.split(",").length;
        if (totalCharacters-count > totalCharacters/2){
            return false;
        }

        int stringStart = new Random().nextInt(text.length() / 2);
        int stringEnd = stringStart + 300;
        System.out.println("Понятен ли Вам этот текст?\n"
                           + text.substring(stringStart, stringEnd) + "\n"
                           + "Введите 1 - если \"Текст понятен\", для продолжения подбора ключа нажмите Enter:\n");

        return SCANNER.nextLine().equals("1");

    }

    public static void decryptFileStatisticalAnalysis() {

        String decryptedFileText = null;
        String statFileText = null;
        String filePath = "";

        while (decryptedFileText == null) {
            System.out.println("Введите полный путь до файла:");
            filePath = SCANNER.nextLine();
            decryptedFileText = GeneralFiles.getFileText(filePath);
        }
        while (statFileText == null) {
            System.out.println("Введите полный путь до файла статистики:");
            String statFilePath = SCANNER.nextLine();
            statFileText = GeneralFiles.getFileText(statFilePath);
        }

        LinkedHashMap<Character, Integer> decryptedFileStatistics = getCharacterStatistics(decryptedFileText);
        LinkedHashMap<Character, Integer> statFileStatistics = getCharacterStatistics(statFileText);

        HashMap<Character, Character> characterStatistic = getMatchingKeysMap(decryptedFileStatistics, statFileStatistics);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < decryptedFileText.length(); i++) {
            Character encryptedChar = characterStatistic.get(decryptedFileText.charAt(i));
            stringBuilder.append(encryptedChar);
        }
        try {
            String newFilePath = GeneralFiles.getNewFilePath(filePath, "-decryptedStatistic");
            GeneralFiles.writeNewTextToFile(stringBuilder.toString(), newFilePath);
            System.out.println("Файл расшифрован. Путь до файла: \n" + newFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //get matching keys in order in LinkedHashMaps
    public static HashMap<Character, Character> getMatchingKeysMap(LinkedHashMap<Character, Integer> decryptedFileStatistics, LinkedHashMap<Character, Integer> statFileStatistics) {

        HashMap<Character, Character> characterHashMap = new HashMap<>();
        int i = 0;

        for (Character charStat : statFileStatistics.keySet()) {
            int j = 0;
            for (Character charDecrypted : decryptedFileStatistics.keySet()) {
                if (i == j) {
                    characterHashMap.put(charDecrypted, charStat);
                    break;
                }
                j++;
            }
            i++;
        }
        return characterHashMap;
    }

    public static String decryptText(String encryptedText, int key){
        StringBuilder stringBuilder = new StringBuilder();

        if (encryptedText != null) {
            char decryptedSymbol;
            for (int i = 0; i < encryptedText.length(); i++) {
                char symbolText = encryptedText.charAt(i);
                int alphabetSymbolIndex = ALPHABET.indexOf(symbolText);

                if(alphabetSymbolIndex == -1){
                    decryptedSymbol = symbolText;
                }else {
                    int newAlphabetSymbolIndex = (ALPHABET.length() + (alphabetSymbolIndex - key)) % ALPHABET.length();
                    decryptedSymbol = ALPHABET.charAt(newAlphabetSymbolIndex);
                }
                stringBuilder.append(decryptedSymbol);
            }
        }

        return stringBuilder.toString();
    }

    public static LinkedHashMap<Character, Integer> getCharacterStatistics(String text){
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

        return sortedHashMapDesc(result);
    }

    public static LinkedHashMap<Character, Integer> sortedHashMapDesc(HashMap<Character, Integer> map){
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(LinkedHashMap::new,
                        (m, c) -> m.put(c.getKey(), c.getValue()),
                        LinkedHashMap::putAll);
    }

}
