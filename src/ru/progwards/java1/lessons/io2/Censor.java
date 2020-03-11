package ru.progwards.java1.lessons.io2;

import java.io.RandomAccessFile;

public class Censor {

    public static class CensorException extends Exception{
        String msg;
        String fileName;

        CensorException (String msg, String fileName) {
            this.msg = msg;
            this.fileName = fileName;
        }

        @Override
        public String toString () {
            return fileName + " : " + msg;
        }
    }

    public static void censorFile(String inoutFileName, String[] obscene) throws Exception{
        try (RandomAccessFile change = new RandomAccessFile(inoutFileName, "rw")) {
            int indX, start, current = 0;
             String temp;
             while (change.getFilePointer() < change.length()) {
                 String str = change.readLine();
                for (String s : obscene) {
                    if (str.contains(s)) {
                        temp = "";
                        start = 0;
                        while (start < str.length()) {              // есть ли еще данные в строке
                            indX = str.indexOf(s, start);           // поиск слова
                            if (indX == -1)
                                break;
                            for (int q = 0; q < s.length(); q++) {  // заполнение ** шаблона слова
                                temp += "*";
                            }
                            str = str.replaceFirst(s, temp);        // замена слова
                            start = indX + s.length();
                        }
                    }
                }
                // перезапись слова
                    change.seek(current);
                    change.writeBytes(str);
                    current += (change.getFilePointer() + 1);
                    change.seek(current);
            }
        } catch (Exception e) {
            throw new CensorException(e.getMessage(),inoutFileName);
        }
    }

    public static void main(String[] args) {
        try {
            String[] obscene = {"day", "write", "count", "two", "storey"};
            censorFile("tmp.txt", obscene);
        }catch (Throwable e) {
            System.out.println(e.toString());
        }
    }
}
