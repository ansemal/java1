package ru.progwards.java1.archiver;

import java.io.*;
import java.util.*;

public class Archiver {
    Map<Character, Integer> charCount = new HashMap<>();
    PriorityQueue<Node> priorTree = new PriorityQueue<>();
    Map<String, Character> charFromArc = new HashMap<>();
    int zeroLastByte;  // количество дополнительных нулей в последнем байте


    // подсчет частоты символов в строке
    public boolean count (String inFile) {
        boolean isEmty = true;
        try (FileInputStream inputStream = new FileInputStream(inFile); BufferedInputStream bufferedIStream = new BufferedInputStream(inputStream, 800)) {
            int i;
            if (bufferedIStream.available()>0) {
                isEmty = false;
                while ((i = bufferedIStream.read()) != -1) {
                    Integer oldVal = charCount.putIfAbsent((char) i, 1);
                    if (oldVal != null)
                        charCount.replace((char) i, charCount.get((char) i) + 1);
                }
            } else
                return isEmty;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return isEmty;
    }

    // создание взаимосвязей - дерева
    public Map<Character,String> createNode () {
        if (charCount.size() > 1) {
            for (Map.Entry<Character, Integer> entry : charCount.entrySet()) {
                priorTree.offer(new Node(entry.getKey(), entry.getValue()));
            }
        } else {   //  если в файле встречается всего один символ
            for (Map.Entry<Character, Integer> entry : charCount.entrySet()) {
                priorTree.offer(new Node(entry.getKey(), entry.getValue(), "0"));
            }
        }
        while (priorTree.size() > 1) {
            priorTree.offer(new Node(priorTree.poll(), priorTree.poll()));
        }
        return priorTree.poll().charTable;
    }

    public void toArchivByte (String inFile) {

        boolean emptyFile = count(inFile);

        if (emptyFile) {
            System.out.println("Файл пустой");
            return;
        }

        Map<Character, String> table = createNode();

        try (FileInputStream inputStream = new FileInputStream(inFile); BufferedInputStream bufferedIStream = new BufferedInputStream(inputStream, 800);
                FileOutputStream outputStream = new FileOutputStream(inFile + ".arh",false);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, 800)) {

            // составление строки из новых кодов
            int i;
            StringBuilder str = new StringBuilder();
            while ((i=bufferedIStream.read()) != -1)
                str.append(table.get((char) i));

            // заполняем последний байт лидирующими нулями
            zeroLastByte = 0;
            if (str.length() == 0)
                str.append(0);
            while ((str.length() % 8) != 0) {
                str.insert((str.length()-(str.length()%8)), "0");
                zeroLastByte++;
            }

            // запись количества строк в кодировочной таблице
            int sizeTable = table.size();
            outputStream.write(String.valueOf(sizeTable).getBytes());
            outputStream.write('\n');

            // запись нулей в байте
            outputStream.write(zeroLastByte);
            outputStream.write('\n');


            //  запись таблицы кодировки в файл
            for (Map.Entry<Character, String> entry: table.entrySet()) {
                outputStream.write(entry.getKey());
                outputStream.write(entry.getValue().getBytes());
                outputStream.write('\n');
            }

            // разбиение строки на байты и запись в файл
            System.out.println();
            for (String s : str.toString().split("(?<=\\G\\d{8})"))
                outputStream.write(Integer.parseInt(s,2));
            bufferedOutputStream.flush();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public void fromArchiv (String inFile) {

        String name = "new" + inFile.substring(0,inFile.length()-4);

        try (FileInputStream inputStream = new FileInputStream(inFile); BufferedInputStream bufferedIStream = new BufferedInputStream(inputStream, 800);
             FileOutputStream outputStream = new FileOutputStream(name,false);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, 800);) {

            //   определение количества символов в таблице
            int i;
            String allSymb = "";
            while ((i=inputStream.read()) != '\n') {
                allSymb += (char)i;
            }
            int allCode = Integer.parseInt(allSymb);

            //   определение количества нулей в последнем байте
            while ((i=inputStream.read()) != '\n')
                zeroLastByte = i;

            // создание кодировочного словаря
            while (allCode != 0) {
                Character ch = (char)inputStream.read();
                String str = "";
                while ((i=inputStream.read()) != '\n') {
                    str += (char)i;
                }
                charFromArc.put(str,ch);
                allCode--;
            }

            // чтение символов для разархивации из файла
            StringBuilder code = new StringBuilder();
            while ((i=bufferedIStream.read()) != -1) {
                StringBuilder codeTemp = new StringBuilder(8);
                codeTemp.append(Integer.toBinaryString(i));
                int codeSize = codeTemp.length();
                for (int z = 0; z < 8 - codeSize; z++) {
                    codeTemp.insert(0, "0");
                }
                code.append(codeTemp);
            }

            //приведение последнего байта к нужному виду
            code.delete(code.length()-8, code.length() - 8 + zeroLastByte);

            // перевод символов по кодировочному словарю
            String strTemp = "";
            while (code.length() != 0){
                strTemp += code.substring(0,1);
                code.delete(0,1);
                if (charFromArc.containsKey(strTemp)) {
                   bufferedOutputStream.write(charFromArc.get(strTemp));
                    strTemp = "";
                }
            }
            bufferedOutputStream.flush();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Archiver archiver = new Archiver();
        archiver.toArchivByte("tmp2.txt");
        archiver.fromArchiv("tmp2.txt.arh");
    }
}
