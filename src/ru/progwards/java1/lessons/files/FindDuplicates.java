package ru.progwards.java1.lessons.files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

public class FindDuplicates {
    LinkedList<List<String>> fileIdent = new LinkedList<>();
    LinkedList<String> allFilesString = new LinkedList<>();
    String [][] allFiles;

    public List<List<String>> findDuplicates(String startPath){
        // проход по дереву каталогов и перенос нужных сведений о файле в строку и соответственно в список
        try {
            Files.walkFileTree(Paths.get(startPath), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    String str = path.getFileName() + "," + attrs.lastModifiedTime() + "," + attrs.size() + "," + path.toAbsolutePath().normalize();
                    allFilesString.add(str);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        // разбиение данных из строк в массив
        int i=0;
        allFiles = new String[allFilesString.size()][];
        while (!allFilesString.isEmpty()) {
            allFiles [i] = allFilesString.pop().split(",");
            i++;
        }
        // сравнение нужных данных о файлах и поиск одинаковых
        for (int z=0; z<allFiles.length-1; z++) {
            LinkedList<String> identTemp = new LinkedList<>();
            int sovpad = 0;
            for (int q=z+1; q<allFiles.length; q++) {
                // проверка по названию (и расширению), дате-времени последнего изменения, размеру
                if (allFiles[z][0].equals(allFiles[q][0]) && allFiles[z][1].equals(allFiles[q][1]) && allFiles[z][2].equals(allFiles[q][2])) {
                    try {
                        // проверка по содержимому
                    if (Files.readString(Paths.get(allFiles[z][3])).equals(Files.readString(Paths.get(allFiles[q][3])))) {
                            // занесение имени и пути одинаковых файлов в список по файлу
                            if (sovpad > 0) {
                                identTemp.push(allFiles[q][0] + " " + allFiles[q][3]);
                                System.out.println(identTemp);
                            } else {
                                identTemp.push(allFiles[z][0] + " " + allFiles[z][3]);
                                identTemp.push(allFiles[q][0] + " " + allFiles[q][3]);
                                sovpad = 1;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            // составление окончательного списка по всем файлам
            if (!identTemp.isEmpty())
                fileIdent.addFirst(identTemp);
        }
        return fileIdent;
    }

    public static void main(String[] args) {
        FindDuplicates findDuplicates = new FindDuplicates();
            System.out.println(findDuplicates.findDuplicates("./каталог"));
    }
}
