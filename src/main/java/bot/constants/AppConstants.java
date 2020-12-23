package bot.constants;

/**
 * Константы бота
 */
public enum AppConstants {
    BOT_NAME ("Помощник Студента"),// Имя бота
    MUSIC_ADDED ("Музыка добавлена"),// Добавление музыки
    ENTER_CITY("Введите город в чат и получите прогноз на 5 дней!"),
    RE_ENTER("Данные удалены\nВведите группу заного"),
    GROUP_SAVE("Группа сохранена"),
    MESSAGE_PROCESSING_FILE("Я не распознаю файлы \uD83E\uDD16"),//Не распознает фаил
    MESSAGE_PROCESSING_STICKER("Я люблю текст и команды, а не стикеры \uD83D\uDE2C"),//Не распознает стикер
    MESSAGE_PROCESSING_PICTURES("Я не знаю что тут \uD83D\uDE2D"),//Не распознает фото
    MESSAGE_PROCESSING_VIDEO("Не люблю видео \uD83D\uDC94. Люблю команды \uD83D\uDE0D"),//Не распознает видео
    MESSAGE_PROCESSING_VOICE("У меня нет ушек"),//Не распознает голос
    REMOVE_HOMEWORK("Дедлайн удален"),//Удаление дедлайна
    REMOVE("Удалил"),//Завершение удаления
    HELP_FIRST("\"Помощь\" или \"❓\" или \"❔\" - список команд\n\n\"Дедлайн\" - cписок дедлайнов\n\n"),//Первый этап помощи
    HELP_SECOND("\"Удалить\" или \"\uD83D\uDDD1\" - выбор способа удаление дедлайна:\n\n   \"Удалить группу с дедлайнами\" -\n"),//Второй этап помощи
    HELP_THIRD("           - удаляет группу из базы данных\n\n   \"Удалить дедлайн по дате\" -\n"),//Третий этап помощи
    HELP_FOURTH("           - удаляет все дедлайны конкретного дня у группы\n\n   \"Удалить дедлайн\" -\n"),//Четвертый этап помощи
    HELP_FIFTH("           - удаляет конкретный дедлайн\n\n\"Добавить\" - добавить дедлайн и домашнее задание\n\n"),//Пятый этап помощи
    HELP_SIXTH("\"Случайная музыка\" или \"\uD83D\uDD08\" - рандомная музыка из плейлиста бота\n\n"),//Шестой этап помощи
    HELP_SEVENTH("Отправьте музыку боту, чтобы он сохранил в свой плейлист\n\n"),//Седьмой этап помощи
    HELP_EIGHTH("\"Орел и решка\" или \"\uD83E\uDDE9\" - игра орел и решка\n\n"),
    HELP_NINTH("\"Погода\" или \"☁\" - погода интересующего города"),
    CHOICE("Или выберите из существующих"),//выбор из существующих дат
    PERFORMS_CREATE(" Выполняет прочтением дедлайна"),//Считывает делайн
    PERFORMS_DISCIPLINE(" Пишет дисциплину"),//Выпоняет написание дисциплины
    PERFORMS_DATE(" Пишет дату"),//Выпоняет написание даты
    PERFORMS_REMOVE(" удаляет дедлайн по дате "),//Выполняет Удаление дедлайна
    REQUEST(" Запрашивает одну из функций бота"),//Запрос
    WRITE_DATE("Напишите мне ДД.ММ.ГГГГ дедлайна"),//Написание даты делайна
    WRITE_DISCIPLINE("Напишите мне название учебной дисциплины"),//Написание дисциплины
    WRITE_HOME_WORK("Напишите мне что вам задали по "),//Написание домашней работы
    LIST_GROUP_NULL("Список акакдемических групп пуст"),//Проверка на наличие акаемичесских групп
    LIST_GROUP("Cписок академических групп:"),//Просмотр академических групп
    LIST_DISCIPLINE("Список дисциплин"),//Просмотр списка дисциплин
    LIST_DISCIPLINE_NULL("Список дисциплин пуст"),//Проверка на наличие дисциплин
    LIST_DEADLINE("Список дедлайнов"),//Просмотр списка дедлайнов
    LIST_DEADLINE_NULL("Список дедлайнов пуст"),//Проверка на наличие дедлайнов
    MOVE_BACK("Вернулся назад"),//Возвращение назад
    CHOICE_REMOVE("выберите способ удаление"),//Способ удаления
    COMMAND_START("/start"),//Начинает работу программы
    COMMAND_REMOVE_HOMEWORK("удалить дедлайн"),//Удаляет дедлайн
    COMMAND_REMOVE_DATE("удалить дедлайн по дате"),//Удаляет дедлайн по дате
    COMMAND_REMOVE("удалить"),//Команда удаления
    COMMAND_ADD("добавить"),//Команда добавления
    COMMAND_HELP("помощь"),//Команда помощи
    COMMAND_RANDOM_MUSIC("случайная музыка"),//Команда случайной музыки
    COMMAND_DEADLINE("дедлайн"),//Команда показывающая все дедлайны
    COMMAND_BACK("назад"),//Команда возвращения назад
    BUTTON_REMOVE_DEADLINE("Удалить дедлайн"),//Кнопка удаления дедлайна
    BUTTON_REMOVE_DATE("Удалить дедлайн по дате"),//Кнопка удаления дедлайна по дате
    BUTTON_REMOVE("Удалить"),//Кнопка удаления
    BUTTON_ADD("Добавить"),//Кнопка добавления
    BUTTON_HELP("Помощь"),//Кнопка помощи
    BUTTON_RANDOM_MUSIC("Случайная музыка"),//Кнопка случайной музыки
    BUTTON_HOMEWORK("Дедлайн"),//Кнопка показывающая все домашнии задания
    BUTTON_BACK("Назад"),//Кнопка назад
    START_FIRST("Привет  "),//Первое действие после запуска программы
    START_SECOND("\n Напишите мне свою группу"),//Второе действие после запуска программы
    COMMAND_DELETE_GROUP_DATE("удалить данные о группе"),//Удалить данные пользователя о группе
    COMMAND_WRITER("погода"),//Команда отпавления погоды
    COMMAND_HEADS_TAILS("орел и решка"),//Команды игры орел и решка
    EMOJI_QUESTION_FIRST("❓"),//ответ по эмоджи
    EMOJI_QUESTION_SECOND("❔"),
    EMOJI_ADD("➕"),
    EMOJI_SPEAKER("\uD83D\uDD08"),
    EMOJI_CALENDAR("\uD83D\uDCC5"),
    EMOJI_BACK("\uD83D\uDD19"),
    EMOJI_TRASH_CAN("\uD83D\uDDD1"),
    EMOJI_GAME("\uD83E\uDDE9"),
    EMOJI_WEATHER("☁️"),
    API_CALL_TEMPLATE ("https://api.openweathermap.org/data/2.5/forecast?q="),
    API_KEY_TEMPLATE ("&units=metric&APPID=d97a349114c8783aa4c0f7884f74ba63\n"),
    USER_AGENT ("Mozilla/5.0"),
    STICKER_TAIL("CAACAgIAAxkBAAJLQF_Qvw7iE9NWhipkim0pJYn5rK5oAAIBAAMrElolU6yx4uot2oweBA"),
    STICKER_HEAD("CAACAgIAAxkBAAJLP1_QvvGqv6_d8Nd9e1PAalMwEugGAAICAAMrElolHaELi66oTuQeBA");
    /**
     * Конструкция присвоения enum констант с их значениями
     */
    private final String value;
    AppConstants(String value){
        this.value = value;
    }
    public String toStringValue(){
        return value;
    }
}
