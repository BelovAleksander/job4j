package ru.job4j.smartprogress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class ConsoleManager {
    LogicManager logic;
    Bot bot;

    public ConsoleManager() {
        int randomInt = new Random().nextInt(1000);
        System.out.println(randomInt);
        logic = new LogicManager(randomInt);
    }

    public void start() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("1. SINGLE MODE");
            System.out.println("2. MULTI PLAYER");
            System.out.println("SELECT MODE: ");

            String input = reader.readLine();
            if (input.equals("1"))  {
                singleMode();
                break;
            } else if (input.equals("2")) {
                multiplayerMode();
                break;
            } else {
                System.out.println("INPUT '1' or '2'");
            }
        }
    }

    private void multiplayerMode() throws IOException {
        int number;
        String result;
        while (true) {
            number = validateInput("Player1 guesses: ");
            result = this.logic.guess(number);
            System.out.println(result);
            if (result.equals("profit!")) {
                System.out.println("Player1 win!!!");
                break;
            }

            number = validateInput("Player2 guesses: ");
            result = this.logic.guess(number);
            System.out.println(result);
            if (result.equals("profit!")) {
                System.out.println("Player2 win!!!");
                break;
            }
        }
    }

    private void singleMode() {
        bot = new Bot(1, 1000);
        int number;
        String result;
        boolean hasWinner = false;
        while (!hasWinner) {
            number = validateInput("Player guesses: ");
            if (run("Player", number)) {
                break;
            } else if (run("Computer", bot.getNumber())) {
                break;
            }
        }
    }

    private boolean run(String player, int number) {
        boolean hasWinner = false;
        String result = this.logic.guess(number);
        System.out.println(result);
        if (result.equals("profit!")) {
            System.out.println(String.format("%s win!!!", player));
            hasWinner = true;
        } else if (result.equals("larger")) {
            bot.setMin(number + 1);
        } else {
            bot.setMax(number - 1);
        }
        return hasWinner;
    }

    private int validateInput(String invite) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int number;
        while (true) {
            System.out.println(invite);
            try {
                number = Integer.parseInt(reader.readLine());
                if (number > 1000 || number < 0) {
                    System.out.println("Please input correct number");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Please input correct number");
            }
        }
        return number;
    }

    public static void main(String[] args) {
        ConsoleManager consoleManager = new ConsoleManager();
        try {
            consoleManager.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
