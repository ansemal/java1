public class Task2 {
    public static double printJava(double num) {
        return num %= 1;
        //String str1 = d1.toString();
        //static String toString (double);
        //int i1 = Integer.parseInt(str1);
        //return d1 - i1;
    }

    public static void main(String[] args) {
        double z = printJava(2.685);
        System.out.println("дробная часть = " + z);
    }

}