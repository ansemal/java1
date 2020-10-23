public class SimpleCalculator implements ICalc {
    @Override
    public int sum(int val1, int val2) {
        long result = (long) val1 + val2;
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE)
            throw new ArithmeticException();
        return val1 + val2;
    }

    @Override
    public int diff(int val1, int val2) {
        long result = (long) val1 - val2;
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE)
            throw new ArithmeticException();
        return val1 - val2;
    }

    @Override
    public int mult(int val1, int val2) {
        long result = (long) val1 * val2;
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE)
            throw new ArithmeticException();
        return val1 * val2;
    }

    @Override
    public int div(int val1, int val2) {
        if (val2 == 0)
            throw new ArithmeticException();
        return val1 / val2;

    }

    public static void main(String[] args) {
        SimpleCalculator simCalc = new SimpleCalculator();
        System.out.println(simCalc.div(Integer.MIN_VALUE, Integer.MIN_VALUE));
        synchronized (simCalc) {
            try {
                simCalc.wait(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("все закончилось");
    }
}