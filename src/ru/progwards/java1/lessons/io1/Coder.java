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
                    System.out.println(temp.charAt(q));
                    System.out.println((int) temp.charAt(q));
                    shifr = code[(int) temp.charAt(q)];
                    str = str + shifr;
                }
                // запись в файл
                    try {
                        FileWriter writer = new FileWriter(outFileName, true);
                        try {
                            writer.write(str + "\n");
                        } finally {
                            writer.close();
                        }
                    } catch (Exception e) {
                        //logFile(e.getMessage(), logName);
                        try {
                            FileWriter log = new FileWriter(logName);
                            try {
                                log.write(e.getMessage());
                            }
                            finally {
                                log.close();
                            }
                        }  catch (Exception ee) {
                            System.out.println("Всё пропало " + ee.getMessage());
                        }
                    }
                }
            }finally {
                scanner.close();
                reader.close();
            }
        } catch (Exception e1) {
  //          logFile(e.getMessage(), logName);
            try {
                FileWriter log = new FileWriter(logName);
                try {
                    log.write(e1.getMessage());
                }
                finally {
                    log.close();
                }
            }  catch (Exception e) {
                System.out.println("Всё пропало " + e.getMessage());
            }
        }
    }

    /*public static void logFile (String exc, String logName) {
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

    }*/

    public static void main(String[] args) {
        char[] code = new char [2000];
        int j=1999;
        for (int i=0; i<2000; i++) {
            code[i] = (char) j;
            j-=i;
        }
        codeFile("tmp.txt", "out.txt", code, "log.txt");
    }
}
