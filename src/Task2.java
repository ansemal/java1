public class Task2 {
    public static double printJava(double num) {
        return num %= 1;
    }
    static int addAsStrings(int n1, int n2) {
        return Integer.valueOf(""+n1+n2+"");
    }
    static String textGrade(int grade) {
        String str;
        if (grade <0 || grade >100) str ="не определено";
        else if (grade == 0) str = "не оценено";
        else if (grade <=20) str = "очень плохо";
        else  if (grade <=40) str = "плохо";
        else if (grade <=60) str = "удовлетворительно";
        else if (grade <=80) str = "хорошо";
        else str = "отлично";
        return str;
    }
    static long factorial(long n) {
        long a =1;
        long f = 1;
        if (n==0) return 1;
        else
            while (a<=n) {
                f *= a;
                a++;
            }
         return f;
     }

    public static void main(String[] args) {
        double z = printJava(2.685);
        System.out.println("дробная часть = " + z);
        System.out.println(addAsStrings(1,2));
        System.out.println(textGrade(38));
        System.out.println(factorial(4));
    }

}