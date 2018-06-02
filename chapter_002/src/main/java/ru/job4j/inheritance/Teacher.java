package ru.job4j.inheritance;

public class Teacher extends Profession {
    public Knowledge teach(Scholar scholar) {
        return new Knowledge();
    }
}
