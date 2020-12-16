package providerBot.botAbility.essences;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Класс сущности дедлайна
 */
public class HomeWork implements Serializable {
    private static final long serialVersionUID = 1L;
    public String homeWork;
    //Сущность учебная дисциплина
    public Discipline discipline = new Discipline();
    //Сущность срок сдачи
    public Calendar date;
    // Сущность группа
    public Group group = new Group();
}
