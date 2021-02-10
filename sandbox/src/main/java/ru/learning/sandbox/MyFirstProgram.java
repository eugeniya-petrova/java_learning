package ru.learning.sandbox;

public class MyFirstProgram {

    public static void main(String[] args) {
        hello("мир");
        hello("брат");
        
        System.out.printf("%.2f", 3.0005);
        System.out.println();
        System.out.println();

        System.out.println("*    * ****** *      *        **  ");
        System.out.println("*    * *      *      *       *  * ");
        System.out.println("****** *****  *      *      *    *");
        System.out.println("*    * *      *      *       *  * ");
        System.out.println("*    * ****** ****** ******   **  ");
        System.out.println();

        Square s = new Square(0.5);
        System.out.println("Площадь квадрата со стороной " + s.l + " = " + s.area());

        Rectangle r = new Rectangle(2, 17);
        System.out.println("Площадь прямоугольника со сторонами " + r.a + " и " + r.b + " = " + r.area());

        Fish myFish = new Fish("зелёная");
        Dog myDog = new Dog("синяя");
        String fishResponse = myFish.speak("рыбка");
        String dogResponse = myDog.speak("собака");
        System.out.println(fishResponse);
        System.out.println(dogResponse);
        myDog.sleep();
    }

    public static void hello(String somebody) {
        System.out.println("Привет, " + somebody + "!");
    }

}
