import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Cryptology {
    private static final String ALPHABET = "";
    private static final String Key = "";

    public static void main(String[] args) throws IOException {

        String menuPage1 = "Выберите действие, введя его номер:\n" +
                "1. Зашифровать текст в файле с помощью ключа.\n" +
                "2. Расшифровать текст в файле с помощью ключа.\n" +
                "3. Расшифровать текст в файле методом перебора (брут-форс).\n" +
                "4. Расшифровать текст в файле методом статического анализа.\n";
        System.out.println(menuPage1);

        while (true){

            Scanner scanner = new Scanner(System.in);
            int menuItem = scanner.nextInt();

            try {
                if(menuItem == 1){
                    encryptFile();
                    System.out.println("Файл успешно зашифрован.");
                    break;
                }
                else if(menuItem >= 2 || menuItem <= 4){
                    decipherFile();
                    System.out.println("Файл успешно расшифрован.");
                    break;
                }
                else {
                    System.out.println("Такого номера нет в меню.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static void encryptFile() throws IOException {
        encryptFileWithKey();
    }

    private static void decipherFile() throws IOException {
        decipherFileStatisticalAnalysis();
        decipherFileBruteForce();
        decipherFileWithKey();
    }

    private static void decipherFileStatisticalAnalysis() {
    }

    private static void decipherFileBruteForce() {
    }

    private static void decipherFileWithKey() {
    }

    private static void encryptFileWithKey() {
    }

}
