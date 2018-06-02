package ru.job4j.inheritance;

public class Doctor extends Profession {
    public Diagnose heal(Pacient pacient) {
        return new Diagnose();
    }
}
