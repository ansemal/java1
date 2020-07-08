package ru.progwards.java2.lessons.annotation;

/**
 * Исключение при работе с АВЛ-деревом
 */
public class TreeException extends Exception {
    public TreeException(String message) {
        super(message);
    }
}
