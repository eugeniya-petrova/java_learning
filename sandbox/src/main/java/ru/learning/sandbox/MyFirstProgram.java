package ru.learning.sandbox;

public class MyFirstProgram {

    public static void main(String[] args) {
        hello("мир");
		hello("брат");
		double len = 5.2;
		System.out.println("Площадь квадрата со стороной " + len + " = " + square(len));
		double a = 3;
		double b = 4;
		System.out.println("Площадь прямоугольника со сторонами " + a + " и " + b + " = " + square(a, b));
    }

    public static void hello(String somebody) {
        System.out.println("Привет, " + somebody + "!");
    }

    public static double square (double l) {
    	return l * l;
	}

	public static double square (double a, double b) {
		return a * b;
	}
}
