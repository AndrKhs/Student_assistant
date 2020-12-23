package bot.constants;

/**
 * Константы команд
 */
public enum Commands {
    Add, // Добавить дедлайн 1 шаг
    Delete,// Удалить дедлайн 1 шаг
    Deadline,// Посмотреть дедлайн 1 шаг

    GroupAdd,// Добавить дедлайн 2 шаг
    GroupDelete,// Удалить дедлайн 2 шаг
    GroupDeadline,// Посмотреть дедлайн 2 шаг

    DateAdd,// Добавить дедлайн 3 шаг
    DateDelete,// Удалить дедлайн 3 шаг
    DateDeadline,// Посмотреть дедлайн 3 шаг

    DisciplineAdd,// Добавить дедлайн 4 шаг
    DisciplineDelete,// Удалить дедлайн 4 шаг
    DisciplineDeadline,// Посмотреть дедлайн 4 шаг

    Back,// Назад на начальный экран
    DeleteGroupWholeDate,// Удалить дату 1 шаг
    DeleteWholeDate,// Удалить группу 2-ой шаг
    DeleteGroupFirst,// Удалить группу 1-ый шаг
    DeleteGroupSecond,// Удалить группу 2-ой шаг
    CommandDelete, // Выбор что удалить
    Weather
}