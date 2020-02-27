package ru.progwards.java1.lessons.io1;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Coder {
    public static void codeFile(String inFileName, String outFileName, char[] code, String logName) {
        try {
            // чтение из файла
            FileReader reader = new FileReader(inFileName);
            Scanner scanner = new Scanner(reader);
            // шифрование символов
            try {
                String temp;
                char shifr;
                while (scanner.hasNextLine()) {
                    String str = "";
                    temp = scanner.nextLine();
                for (int q = 0; q<temp.length(); q++) {
                    shifr = code[(int)temp.charAt(q)];
                    str = str + shifr;
                }
                // запись в файл
                    try {
                        FileWriter writer = new FileWriter(outFileName, true);
                        try {
                            if (scanner.hasNextLine())
                                writer.write(str + code[(int) '\n']);
                            else
                                writer.write(str);
                        } finally {
                            writer.close();
                        }
                    } catch (Exception e) {
                        logFile(e.getMessage(), logName);
                    }
                }
            } catch (Exception e) {
               logFile(e.getMessage(), logName);
            }  finally {
                scanner.close();
                reader.close();
            }
        } catch (Exception e) {
            logFile(e.getMessage(), logName);
        }
    }

    public static void logFile (String exc, String logName) {
        //запись исключений в логфайл
        try {
            FileWriter log = new FileWriter(logName);
            try {
                log.write(exc);
            }
            finally {
                log.close();
            }
        }  catch (Exception e) {
            System.out.println("Всё пропало " + e.getMessage());
        }

    }

    public static void main(String[] args) {
        char[] code = new char [100];
        int j=99;
        for (int i=0; i<100; i++) {
            code[i] = (char) j;
            j-=i;
        }
        codeFile("tmp.txt", "out.txt", code, "log.txt");
    }
}
