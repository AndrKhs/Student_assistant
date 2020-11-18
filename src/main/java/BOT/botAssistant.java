package BOT;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.io.File;
import java.text.*;
import java.awt.*;

/**
 * Основыне функции бота
 */
public class botAssistant implements botAssist {
    /**
     *
     * @param call Ввод пользоваеля по присетам
     * @return Возвращает действия всех методов
     * @throws IOException
     * @throws ParseException
     * @throws AWTException
     * Делает действия указанные пользователем
     */
    public String botAI(String call) throws IOException {
        if(call.equals(""))
            return ("Ошибка: Пустая строка");
        switch (call) {
            case "1":
                final String nameFile = botMassage();
                File file = new File(nameFile);
                if (!file.exists())
                    return "Такого файла не существует";
                return readFl(nameFile);
            case "2":
                String fileName = botMassage();
                final char error = 'О';
                if (error == (fileName.charAt(0))) return fileName;
                File files = new File(fileName);
                if (files.exists()) {
                    return "Файл записан," + "'" + writeFl(fileName, readFl(fileName)) + "'";
                } else {
                    return "Файл записан," + "'" + writeFl(fileName, "") + "'";
                }
            case "3":
                return delete(botMassage());
            case "6":
                return search();
            default:
                return "Я не знаю такой команды";

        }
    }

    /**
     *
     * @param file -  Имя фаила вводимого польязователем
     * @return Возвращает содержание файла
     * @throws IOException
     * Считывает файл который нам нужен и возвращает нам его содержимое
     */
    private String readFl(String file) throws IOException {
        FileReader fr = new FileReader(file);
        char[] a = new char[200];   // Количество символов, которое будем считывать
        fr.read(a);   // Чтение содержимого в массив
        fr.close();
        StringBuilder builder = new StringBuilder();
        for (char s : a) {
            builder.append(s);
        }
        return builder.toString();
    }

    /**
     *
     * @param file - Имя фаила вводимого польязователем
     * @param fileСontent - Содержание прочитанного нами файла
     * @return Возвращает имя фаила
     * @throws IOException
     * Записывает данные которые ввел пользователь
     */
    public String writeFl(String file, String fileСontent) throws IOException {

        // Создание объекта FileWriter
        FileWriter writer = new FileWriter(file);
        Scanner scanners = new Scanner(System.in);
        System.out.println("Название предмета");
        String nameHoWor = scanners.nextLine();
        System.out.println("Напиши что задали или ссылку");
        String writed = scanners.nextLine();

        writer.write("\n" + nameHoWor + ":" + "\n" + writed + "\n" + fileСontent);
        writer.flush();
        writer.close();
        return file;
    }

    /**
     *
     * @return Выводит ошбки или путь к файлу
     *Проверяет на корректность ввода и выводит путь к файлу
     */
    public String botMassage() {
        System.out.println(search());
        Scanner scanners = new Scanner(System.in);
        System.out.println("напишите дедлайн(dd.MM.yyyy)");
        final String nameFiles = scanners.nextLine();
        if (nameFiles.length() != 10)
            return "Ошибка: Некорректный Ввод";
        final String[] words = nameFiles.split("\\.");
        final int IntDay = Integer.parseInt(words[0]);
        if ((IntDay > 31) || (IntDay < 1))
            return "Ошибка: Некорректный Ввод Дней";
        final int IntMonth = Integer.parseInt(words[1]);
        if ((IntMonth > 12) || (IntMonth < 1))
            return "Ошибка: Некорректный Ввод Месяцев";
        final int IntYear = Integer.parseInt(words[2]);
        if (IntYear < 2020)
            return "Ошибка: Некорректный Ввод Лет";
        for (char ch : nameFiles.toCharArray()) {
            if (Character.isDigit(ch))
                return System.getProperty("user.dir") + "\\Files\\" + nameFiles;
        }
        return "Ошибка: Некорректный Ввод";
    }

    /**
     *
     * @return Выодит список дедлайнов
     * Удаляет старые домашние задания и выводит список дедлайнов
     */
    protected String search() {
        String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date()); // вывод текущей даты
        File baseFile = new File(System.getProperty("user.dir") + "\\Files");
        File nameFile = new File(System.getProperty("user.dir") + "\\Files\\" + date);
        for (File f : baseFile.listFiles()) {
            if (nameFile.equals(f))
                if (f.delete())
                    break;
        }
        StringBuilder builder = new StringBuilder();
        for (String s : baseFile.list()) {
            builder.append(s + "\n");
        }
        return "\n" + "Cписок дедлайнов:" + "\n" + builder.toString();
    }

    /**
     *
     * @param name - Имя файла
     * @return Возыращает ифнормаци об удалении или отсутствии файла
     * Дает пользователью удалить старые домашние задания
     */
    protected String delete(String name) {
        File fi = new File(name);
        if (fi.delete())
            return "Файл удален";
        return "Файла не существет";
    }
}