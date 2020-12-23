package bot.model;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Класс сущности домашнего задания
 */
public class HomeWork implements Serializable {
    private static final long serialVersionUID = 1L;
    //Сущность домашнего задания
    private String homeWork;
    //Сущность учебная дисциплина
    private Discipline discipline = new Discipline();
    //Сущность срок сдачи
    private Calendar date;


    public String getHomeWork() {
        return homeWork;
    }

    public void setHomeWork(String homeWork) {
        this.homeWork = homeWork;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
