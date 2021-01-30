package ru.learning.sandbox;

public class MyFirstProgram {

    public static void main(String[] args) {
        hello("мир");
        hello("брат");

        Square s = new Square(5.2);
        System.out.println("Площадь квадрата со стороной " + s.l + " = " + area(s));

        Rectangle r = new Rectangle(7, 8);
        System.out.println("Площадь прямоугольника со сторонами " + r.a + " и " + r.b + " = " + area(r));
    }

    public static void hello(String somebody) {
        System.out.println("Привет, " + somebody + "!");
    }

    public static double area(Square s) {
        return s.l * s.l;
    }

    public static double area(Rectangle r) {
        return r.a * r.b;
    }
}
