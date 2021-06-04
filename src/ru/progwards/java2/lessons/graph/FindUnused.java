package ru.progwards.java2.lessons.graph;

import java.util.LinkedList;
import java.util.List;

public class FindUnused {

    static class CObject {

        public List<CObject> references = new LinkedList<>(); // ссылки на другие объекты
        int mark;  // пометка для алгоритма
        // 0 - не используется
        // 1 - посещен
        // 2 - используется
    }

    public static List<CObject> find(List<CObject> roots, List<CObject> objects) {
//        - List<CObject> roots -  список корневых объектов
//        - List<CObject> objects - список всех объектов системы
//        - возвращает список неиспользуемых объектов

        for (CObject root : roots) {
            if (root.mark == 0) {
                boolean isOK = dfs(root);
                if (!isOK) {
                    System.out.println("Что-то пошло не так");
                }
            }
        }

        LinkedList<CObject> unused = new LinkedList<>();
        for (CObject object : objects) {
            if (object.mark == 0) {
                unused.addLast(object);
            }
        }
        return unused;
    }

    private static boolean dfs(CObject cObject) {
        cObject.mark = 1;
        for (CObject ref : cObject.references) {
            if (ref.mark == 0) {
                dfs(ref);
            }
        }
        cObject.mark = 2;
        return true;
    }
}