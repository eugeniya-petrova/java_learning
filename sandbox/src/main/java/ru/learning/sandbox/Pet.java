package ru.learning.sandbox;

public class Pet {

    public String color;

    public Pet(String color) {
        this.color = color;
    }

    public String speak(String petName) {
        String petResponse = "Привет, я " + color + " " + petName + "!";
        return petResponse;
    }

    public void sleep() {
        System.out.println("Спокойной ночи!");
    }
}
