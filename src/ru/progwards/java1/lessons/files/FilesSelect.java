package ru.progwards.java1.lessons.files;

import java.io.File;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class FilesSelect {

    public void selectFiles(String inFolder, String outFolder, List<String> keys) {
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.txt");
        try {
            Files.walkFileTree(Paths.get(inFolder), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    if (pathMatcher.matches(path)) {
                        try {
                            String str = Files.readString(path);
                            for (String keysName : keys) {
                                if (str.contains(keysName)) {
                                    Path path1 = Paths.get(outFolder);
                                    Path newDir = path1.resolve(keysName);
                                    File file = new File(newDir.toString());
                                    if (!file.exists()) {
                                        Files.createDirectory(newDir);
                                    }
                                    Files.copy(path, newDir.resolve(path.getFileName()));
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    public static void main(String[] args) {
        FilesSelect filesSelect = new FilesSelect();
        List <String> prod = List.of("сок", "хлеб", "вода");
        filesSelect.selectFiles("./каталог", "./сортировка", prod);
    }
}
