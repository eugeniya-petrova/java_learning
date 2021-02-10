package ru.learning.sandbox;

public class Fish extends Pet{

    public Fish(String color) {
        super(color);
    }

    public String speak(String text) {
        return "Рыбы не разговаривают!";
    }
}
