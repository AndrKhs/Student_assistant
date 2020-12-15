package providerBot.botAbility.essences;
import java.io.Serializable;

/**
 * Класс сущности дедлайна
 */
public class Deadline implements Serializable {
    private static final long serialVersionUID = 1L;
    //Сущность домашнее задание
    public HomeWork homeWork = new HomeWork();
    //Сущность учебная дисциплина
    public Discipline discipline = new Discipline();
    //Сущность срок сдачи
    public EndDate date = new EndDate();
    // Сущность группа
    public Group group = new Group();
}
