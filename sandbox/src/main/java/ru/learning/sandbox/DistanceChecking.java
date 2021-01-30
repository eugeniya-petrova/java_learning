package ru.learning.sandbox;

public class DistanceChecking {

    public static void main(String[] args) {


        Point p1 = new Point(-2.1, 2);
        Point p2 = new Point(5.5, -3.6);
        System.out.println("квадрат расстояния между точками " + " = " + distance(p1, p2));
    }

    public static double distance (Point p1, Point p2) {
        return (p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y);
    }

}
