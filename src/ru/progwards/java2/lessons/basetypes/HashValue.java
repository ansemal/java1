package ru.progwards.java2.lessons.basetypes;

public interface HashValue {
    String getKey();

    default int getHash() {
        int hash = 0;
        try {
            hash = Integer.parseInt(getKey());
        } catch (Exception ex) {
            // функция хэширования строк FAQ6
            for (int i = 0; i < getKey().length(); ++i) {
                hash += getKey().charAt(i);
                hash += (hash << 10);
                hash ^= (hash >> 6);
            }
            hash += (hash << 3);
            hash ^= (hash >> 11);
            hash += (hash << 15);
        }
        return hash;
    };
}
