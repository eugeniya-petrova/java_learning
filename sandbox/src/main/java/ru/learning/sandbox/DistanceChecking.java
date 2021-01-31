package ru.learning.sandbox;

public class DistanceChecking {

    public static void main(String[] args) {


        Point p1 = new Point(2.1, 2.5);
        Point p2 = new Point(7, 3);
        System.out.println("Расстояние между точками (" + p1.x + ";" + p1.y + ") и (" + p2.x + ";" + p2.y + ") = " + p1.distance(p2));

        Point p3 = new Point(-2.1, 6);
        Point p4 = new Point(5, -3);
        System.out.println("Расстояние между точками (" + p3.x + ";" + p3.y + ") и (" + p4.x + ";" + p4.y + ") = " + p3.distance(p4));

        Point p5 = new Point(0, 0);
        Point p6 = new Point(3, -5);
        System.out.println("Расстояние между точками (" + p5.x + ";" + p5.y + ") и (" + p6.x + ";" + p6.y + ") = " + p5.distance(p6));

    }

}
