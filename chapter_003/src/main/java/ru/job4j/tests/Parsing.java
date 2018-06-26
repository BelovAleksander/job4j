package ru.job4j.tests;

import javax.xml.stream.events.Characters;
import java.util.*;

/**
 * Проверяет входящую строку на соответствие
 * открывающих и закрывающих скобок.
 *
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 27.06.18
 */

public class Parsing {
    public boolean isValide(String string) {
        boolean result = true;
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);
            if (ch == '{' || ch == '[' || ch == '(') {
                stack.push(ch);
            } else if (ch == '}' || ch == ']' || ch == ')') {
                if (stack.empty()) {
                    System.out.println(String.format("Bracket %s doesn't have pair!", ch));
                    result = false;
                    break;
                } else {
                    char chx = stack.pop();
                    if ((ch == '}' && chx != '{')
                            || (ch == ']' && chx != '[')
                            || (ch == ')' && chx != '(')) {
                        System.out.println(String.format("Bracket %s not equals %s!", ch, chx));
                        result = false;
                        break;
                    }
                }
            }
        }
        return result;
    }
}
