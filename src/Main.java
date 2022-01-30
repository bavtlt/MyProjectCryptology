import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try(Scanner scanner = new Scanner(System.in)) {

            String firstMenuPage = "Выберите действие, введя его номер:\n" +
                                   "1. Зашифровать текст в файле с помощью ключа.\n" +
                                   "2. Расшифровать текст в файле с помощью ключа.\n" +
                                   "3. Расшифровать текст в файле методом перебора (брут-форс).\n" +
                                   "4. Расшифровать текст в файле методом статического анализа.\n";
            System.out.println(firstMenuPage);

            while (true) {

                int menuItem = scanner.nextInt();

                if (menuItem == 1) {
                    CryptographFiles.encryptFileWithKey();
                    break;
                } else if (menuItem == 2) {
                    CryptographFiles.decryptFileWithKey();
                    break;
                } else if (menuItem == 3) {
                    CryptographFiles.decryptFileBruteForce();
                    break;
                } else if (menuItem == 4) {
                    CryptographFiles.decryptFileStatisticalAnalysis();
                    break;
                } else if (menuItem == 5) {
                    System.exit(0);
                    break;
                } else {
                    System.out.println("Такого номера нет в меню.");
                }
            }
        }
    }





}
