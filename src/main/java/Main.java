import BOT.botAssist;
import BOT.botAssistant;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

/**
 * Главный класс - нужен для управления бота
 */
class Main {
    public static void main(String[] args) throws IOException {
        botAssist bot = new botAssistant();
        System.out.println("Привет, студент.");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(bot.botAI("6"));
            System.out.println(
                    "1 - Домашнее задание" +"\n"
                    + "2 - Добавление домашнего задания" + "\n"
                    + "3 - Удаление домашнего задания" + "\n"
                    + "4 - Exit"
            );
            String сall = scanner.nextLine();
            if (сall.equals("4")){
                System.out.println("Пока студент.");
                break;
            }
            else System.out.println(bot.botAI(сall));

        }
    }
}
