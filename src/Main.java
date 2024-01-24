import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class Main {

    private static final boolean SHOW_MESSAGE = true;

    static String writerName1 = "writerName1";
    static boolean isWriter1 = false;

    static String writerName2 = "writerName2";
    static boolean isWriter2 = false;

    static String tegText = "<div class=\"text\">";
    static boolean isText = false;

    static String tegName = "<div class=\"from_name\">";
    static boolean isName = false;

    static int numberMessage1 = 0;
    static int numberMessage2 = 0;

    static float numberChars1 = 0;
    static float numberChars2 = 0;

    static int numberNames = 0;


    public static void main(String[] args) {
        // Зазначте шлях до вашої папки
        String folderPath = "src/TelegramChatExportHtml";

        // Створіть об'єкт File для папки
        File folder = new File(folderPath);

        // Отримайте список файлів у папці
        File[] files = folder.listFiles();

        // Перевірте, чи папка не порожня
        if (files != null) {
            // Пройдіться по кожному файлу
            for (File file : files) {
                // Перевірте, чи це файл (не папка)
                if (file.isFile()) {
                    System.out.println("Читаю з файлу: " + file.getName());

                    // Відкрийте BufferedReader для читання з файлу
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;

                        // Читайте рядки з файлу, доки є дані
                        while ((line = reader.readLine()) != null) {
                            getName(line);
                            calc(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        } else {
            System.out.println("Папка порожня або не існує.");
        }

        showResult();


    }

    private static void showResult() {
        System.out.println("--------");
        System.out.println(writerName1 + ": write chars = " + (int) numberChars1 + " = " + percent(numberChars1) + " % ");
        System.out.println(writerName2 + ": write chars = " + (int) numberChars2 + " = " + percent(numberChars2) + " % ");
        System.out.println("--------");
    }

    private static void getName(String line) {

        if (numberNames < 2) {

            if (isName && numberNames == 0) {
                writerName1 = line;
                numberNames++;
                isName = false;
               // System.out.println("2 " + writerName1 + " " + writerName2 + " " + numberNames);
            }

            if (numberNames == 1 && isName && !writerName1.equals(line)) {
                writerName2 = line;
                numberNames++;
                isName = false;
              //  System.out.println("3 " + writerName1 + " " + writerName2 + " " + numberNames);
            }

            if (line.contains(tegName)) {
                isName = true;
            }
        }
    }


    private static float percent(float numberChars) {
        float sumChars = numberChars1 + numberChars2;
        return (numberChars / sumChars * 100);
    }

    static void calc(String line) {
        checkWriter(line);
        calcChars(line);
    }

    private static void checkWriter(String line) {
        if (writerName1 != null) {
            if (line.contains(writerName1)) {
                isWriter1 = true;
                isWriter2 = false;
            }
        }

        if (writerName2 != null) {
            if (line.contains(writerName2)) {
                isWriter2 = true;
                isWriter1 = false;
            }
        }
    }

    private static void calcChars(String line) {

        if (isText) {

            if (isWriter1) {
                numberChars1 += line.length();
                if (SHOW_MESSAGE) {
                    System.out.println(writerName1 + " = " + (int) numberChars1 + " chars; " + line);
                }
            }

            if (isWriter2) {
                numberChars2 += line.length();
                if (SHOW_MESSAGE) {
                    System.out.println(writerName2 + " = " + (int) numberChars2 + " chars; " + line);
                }
            }
        }


        isText = line.contains(tegText);
    }


}
