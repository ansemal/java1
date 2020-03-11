package ru.progwards.java1.lessons.io2;

public class PhoneNumber {

    public static String format(String phone) {
        String telFormat = "";
        String sem = "+7(";
        for (int i=0; i < phone.length(); i++) {
            if (Character.isDigit(phone.charAt(i))) {
                telFormat += phone.charAt(i);
            }
        }
        if (telFormat.length() == 10) {
            telFormat = sem.concat(telFormat);
        } else if (telFormat.length() == 11) {
            telFormat = sem.concat(telFormat.substring(1));
        }else {
            throw new RuntimeException ("Неправильное количество цифр в номере " + phone);
        }
        return telFormat.substring(0,6) + ")" + telFormat.substring(6,9) + "-" + telFormat.substring(9);
    }

    public static void main(String[] args) {
        try {
            System.out.println(format("8(989)567-45-21"));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
