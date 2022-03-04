import java.util.Scanner;
import java.util.regex.*;

class Fraction {
    private int n, d;

    //Создание простой дроби (с заданным числителем и знаменателем)
    public Fraction(int n, int d) throws Exception {
        if (d == 0) {throw new Exception("Некорректный параметр");}
        else
            this.n = n;
            this.d = d;
    }
    //Создание простой дроби по умолчанию (числитель 1, знаменатель 1)
    public Fraction(){
        this.n = 1;
        this.d = 1;
    }

    public String toString() {return n + "/" + d;};

    public int getN(){return n;}
    public void setN(int n){this.n = n;}

    public int getD(){return d;}
    public void setD(int d){this.d = d;}

    //Из двух строковых элементов вида "n/d" получаем массив целых чисел вида [n1, d1, n2, d2]
    public static int[] Numbers(String x, String y){
        int k =0;

        String[] X = x.split("/");
        String[] Y = y.split("/");
        int[] numbers = new int[X.length+Y.length];

        for(int i = 0; i < X.length; i++) {
            numbers[i] = Integer.parseInt(X[i]);
            k++;
        }
        for(int j = 0; j < Y.length; j++) {
            numbers[k++] = Integer.parseInt(Y[j]);
        }

        return numbers;
    }

    //Поиск НОД
    public static int Simp(int n,  int d){
        int div = 0;

        for(int i = Math.abs(n); i >= 1; i--){
            if (n % i == 0 && d % i == 0){
                div = i;
                break;
            }
        }
        if (div == 0){
            return 1;
        }else{
            return div;
        }
    }

    //обработка дробей вида 0/d --> 0/1, n/-d --> -n/d, -n/-d --> n/d
    public static Fraction Check(int n,  int d){
        Fraction r = new Fraction();

        if(n != 0){
            if(n < 0 && d < 0 || n > 0 && d < 0){
                r.setN(-n);
                r.setD(-d);
            } else
                r.setN(n);
                r.setD(d);
        } else
            r.setN(0);
        return r;
    }

    //Сумма
    public static String Sum (Fraction f1, Fraction f2){
        int n_r, d_r;

        int n1 = f1.getN();
        int d1 = f1.getD();
        int n2 = f2.getN();
        int d2 = f2.getD();

        n_r = n1 * d2 + d1 * n2;
        d_r = d1 * d2;

        int div = Simp(n_r, d_r);

        n_r /= div;
        d_r /= div;

        Fraction r = Check(n_r, d_r);

        return (f1.toString() + " + " + f2.toString() + " = " + r.toString());
    }

    //Разность
    public static String Dis (Fraction f1, Fraction f2){
        int n_r, d_r;

        int n1 = f1.getN();
        int d1 = f1.getD();
        int n2 = f2.getN();
        int d2 = f2.getD();

        n_r = n1 * d2 - d1 * n2;
        d_r = d1 * d2;

        int div = Simp(n_r, d_r);

        n_r /= div;
        d_r /= div;

        Fraction r = Check(n_r, d_r);

        return (f1.toString() + " - " + f2.toString() + " = " + r.toString());
    }

    //Умножение
    public static String Mul (Fraction f1, Fraction f2){
        int n_r, d_r;

        int n1 = f1.getN();
        int d1 = f1.getD();
        int n2 = f2.getN();
        int d2 = f2.getD();

        n_r = n1 * n2;
        d_r = d1 * d2;

        int div = Simp(n_r, d_r);

        n_r /= div;
        d_r /= div;

        Fraction r = Check(n_r, d_r);

        return (f1.toString() + " * " + f2.toString() + " = " + r.toString());
    }

    //Деление
    public static String Div (Fraction f1, Fraction f2) {
        int n_r, d_r;

        int n1 = f1.getN();
        int d1 = f1.getD();
        int n2 = f2.getN();
        int d2 = f2.getD();

        if (n2 != 0) {
            n_r = n1 * d2;
            d_r = d1 * n2;
            int div = Simp(n_r, d_r);

            n_r /= div;
            d_r /= div;

            Fraction r = Check(n_r, d_r);

            return (f1.toString() + " : " + f2.toString() + " = " + r.toString());
        } else return ("Нельзя делить на 0");
    }
}


public class Fraction_Calculator {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n1, d1, n2, d2;
        String x, y;
        Fraction f1, f2;
        int[] numbers;

        System.out.print("Введите выражение: ");
        String str = in.nextLine();

        String pattern = "^-?[0-9]+/-?[0-9]+( \\+ | - | \\* | : ){1}+-?[0-9]+/-?[0-9]";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);

        if(m.matches()){
            String elements[] = str.split(" ");

            numbers = Fraction.Numbers(elements[0], elements[2]);

            n1 = numbers[0];
            d1 = numbers[1];

            n2 = numbers[2];
            d2 = numbers[3];

            if (elements[1].equals("+")) {
                try {
                    f1 = new Fraction(n1, d1);
                    f2 = new Fraction(n2, d2);
                    System.out.println("Решение: " + Fraction.Sum(f1, f2));

                } catch (Exception e) {
                    System.out.println(e.toString());
                    System.exit(-1);
                }
            }
            else if (elements[1].equals("-")){
                try {
                    f1 = new Fraction(n1, d1);
                    f2 = new Fraction(n2, d2);
                    System.out.println("Решение: " + Fraction.Dis(f1, f2));

                } catch (Exception e) {
                    System.out.println(e.toString());
                    System.exit(-1);
                }
            }
            else if (elements[1].equals("*")){
                try {
                    f1 = new Fraction(n1, d1);
                    f2 = new Fraction(n2, d2);
                    System.out.print("Решение: " + Fraction.Mul(f1, f2));

                } catch (Exception e) {
                    System.out.println(e.toString());
                    System.exit(-1);
                }

            }
            else{
                try {
                    f1 = new Fraction(n1, d1);
                    f2 = new Fraction(n2, d2);
                    System.out.println("Решение: " + Fraction.Div(f1, f2));

                } catch (Exception e) {
                    System.out.println(e.toString());
                    System.exit(-1);
                }
            }
        }else
            System.out.println("Неверное выражение");

        in.close();
    }
}



