package ru.progwards.java1.lessons.io2;

public class Translator {
    private String[] inLang;
    private String[] outLang;

    Translator(String[] inLang, String[] outLang) {
        this.inLang = inLang;
        this.outLang = outLang;
    }

    public String translate(String sentence) {
        String [] arrIn = sentence.split(" ");
        String znak, temp;
        String transOut = "";
        int up;
        for (int i = 0; i < arrIn.length; i++) {
            znak = "";
            up = 0;
            // определение и отделение пукнтуационного знака
            if (!Character.isAlphabetic(arrIn[i].charAt(arrIn[i].length()-1)) && arrIn[i].length()>1) {
                znak = Character.toString(arrIn[i].charAt(arrIn[i].length()-1));
                arrIn[i] = arrIn[i].substring(0, arrIn[i].length()-1);
            }
            // определение заглавной буквы и переведение в нижний регистр
            if (Character.isUpperCase(arrIn[i].charAt(0))) {
                up = 1;
                arrIn[i] = arrIn[i].toLowerCase();
            }
            for (int j = 0; j<inLang.length; j++) {
                if (arrIn[i].equals(inLang[j])) {
                    if (up == 1) {
                        temp = Character.toUpperCase(outLang[j].charAt(0)) + outLang[j].substring(1);
                    } else {
                        temp = outLang[j];
                    }
                    if (i != arrIn.length - 1) {
                        transOut = transOut + temp + znak + " ";
                    } else {
                        transOut = transOut + temp + znak;
                    }
                    break;
                } else if (j == inLang.length-1) {
                        if (up == 1) {
                            temp = Character.toUpperCase(arrIn[i].charAt(0)) + arrIn[i].substring(1);
                        } else {
                            temp = arrIn[i];
                        }
                        if (i != arrIn.length - 1) {
                            transOut = transOut + temp + znak + " ";
                        } else {
                            transOut = transOut + temp + znak;
                        }
                }
            }
        }
        return transOut;
    }

    public static void main(String[] args) {
        String [] inLang = {"hello", "world"};
        String [] outLang = {"привет", "мир"};
        Translator s = new Translator(inLang, outLang);
        System.out.println(s.translate("Hello! All world."));
    }
}
