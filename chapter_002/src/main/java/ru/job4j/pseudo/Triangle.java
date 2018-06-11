package ru.job4j.pseudo;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 11.06.2018
 */
public class Triangle implements Shape {
    @Override
    public String draw() {
        StringBuilder pic = new StringBuilder();
        pic.append("+\n");
        pic.append("++\n");
        pic.append("+ +\n");
        pic.append("++++");
        return pic.toString();
    }
}
