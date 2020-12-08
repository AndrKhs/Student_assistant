package botAbility.FunctionsBot.ProviderBot;
import java.io.Serializable;

/**
 * Класс нужен для сериализации дедлайнов
 */
public class Serialize implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Объект домашнего задания
     */
    public Object HomeWork;
    /**
     * Объект учебной дисциплины
     */
    public Object Discipline;
    /**
     * Объект даты дедлайна
     */
    public Object Date;
    /**
     * Объект группы
     */
    public Object Group;

}
