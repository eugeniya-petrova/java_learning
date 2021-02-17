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
        System.out.println();

        Equation eq = new Equation(1, 2, 3);
        int rootNum = eq.rootNumber();
        switch (rootNum) {
            case 2:
                System.out.println("Это уравнение с двумя корнями");
                break;
            case 1:
                System.out.println("Это уравнение с одним корнем");
                break;
            case 0:
                System.out.println("У этого уравнения нет корней");
                break;
        }
    }

    public static void hello(String somebody) {
        System.out.println("Привет, " + somebody + "!");
    }

}
