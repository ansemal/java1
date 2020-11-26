package ru.progwards.java2.lessons.synchro;

public class InvalidPointerException extends RuntimeException {
    private int wrongMark;

    InvalidPointerException(int wrongMark) {
        this.wrongMark = wrongMark;
    }

    @Override
    public String toString() {
        return "InvalidPointerException{" + "Неправильный указатель = " + wrongMark + '}';
    }
}
