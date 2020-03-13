package ru.progwards.java1.lessons.io2;

import java.util.Arrays;

public class Translator {
    private String[] inLang;
    private String[] outLang;

    Translator(String[] inLang, String[] outLang) {
        this.inLang = inLang;
        System.out.println("inlang = " + Arrays.toString(inLang));
        this.outLang = outLang;
        System.out.println("outlang = " + Arrays.toString(outLang));

    }

    public String translate(String sentence) {
        String [] arrIn = sentence.split(" ");
        System.out.println("arrIn = " + Arrays.toString(arrIn));
        String znak, temp;
        String transOut = "";
        int up;
        for (int i = 0; i < arrIn.length; i++) {
            znak = "";
            up = 0;
            // определение и отделение пукнтуационного знака
            if (!Character.isAlphabetic(arrIn[i].charAt(arrIn[i].length()-1))) {
                znak = Character.toString(arrIn[i].charAt(arrIn[i].length()-1));
                System.out.println("znak !Character.isAlphabetic(arrIn[i].charAt(arrIn[i].length()-1)) = " + znak);
                arrIn[i] = arrIn[i].substring(0, arrIn[i].length()-1);
                System.out.println("arrIn[i] !Character.isAlphabetic(arrIn[i].charAt(arrIn[i].length()-1)) = " + arrIn[i]);
            }
            // определение заглавной буквы и переведение в нижний регистр
            if (Character.isUpperCase(arrIn[i].charAt(0))) {
                up = 1;
                arrIn[i] = arrIn[i].toLowerCase();
                System.out.println("arrIn[i] Character.isUpperCase(arrIn[i].charAt(0)) = " + arrIn[i]);
            }
            for (int j = 0; j<inLang.length; j++) {
                if (arrIn[i].equals(inLang[j])) {
                    if (up == 1) {
                        temp = Character.toUpperCase(outLang[j].charAt(0)) + outLang[j].substring(1);
                        System.out.println("temp arrIn[i].equals(inLang[j]) up == 1 = " + temp);
                    } else {
                        temp = outLang[j];
                        System.out.println("temp arrIn[i].equals(inLang[j]) up == 1 else = " + temp);
                    }
                    if (i != arrIn.length - 1) {
                        transOut = transOut + temp + znak + " ";
                        System.out.println("transOut arrIn[i].equals(inLang[j]) i != arrIn.length - 1 = " + transOut);
                    } else {
                        transOut = transOut + temp + znak;
                        System.out.println("transOut arrIn[i].equals(inLang[j]) i != arrIn.length - 1 else = " + transOut);
                    }
                    break;
                } else if (j == inLang.length-1) {
                        if (up == 1) {
                            temp = Character.toUpperCase(arrIn[i].charAt(0)) + arrIn[i].substring(1);
                            System.out.println("temp j == inLang.length-1 up == 1 = " + temp);
                        } else {
                            temp = arrIn[i];
                            System.out.println("temp j == inLang.length-1 up == 1 else = " + temp);
                        }
                        if (i != arrIn.length - 1) {
                            transOut = transOut + temp + znak + " ";
                            System.out.println("transout inLang.length-1 = " + transOut);
                        } else {
                            transOut = transOut + temp + znak;
                            System.out.println("transout inLang.length-1 else = " + transOut);
                        }
                }
            }
        }
        return transOut;
    }

    public static void main(String[] args) {
        String [] inLang = {"make", "love", "not", "war"};
        String [] outLang = {"твори", "любовь", "не", "войну"};
        Translator s = new Translator(inLang, outLang);
        System.out.println(s.translate("make love not war."));
    }
}
