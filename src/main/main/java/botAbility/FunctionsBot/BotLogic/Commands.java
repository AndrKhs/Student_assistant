package botAbility.FunctionsBot.BotLogic;

/**
 * Enum нужен для объявлении действии бота по запросу пользователя
 */
public enum Commands {
    Add,
    Delete,
    Deadline,

    GroupAdd,
    GroupDelete,
    GroupDeadline,

    DateAdd,
    DateDelete,
    DateDeadline,

    DisciplineAdd,
    DisciplineDelete,
    DisciplineDeadline,

    Back,
    DeleteWholeDate,
    DeleteGroupWholeDate,
    DeleteGroup,
    CommandDelete;

    /**
     * Нужен для получения enum по его индексу
     * @param index     Индекс enum
     * @return          Enum
     */
    public static Commands valueOf(int index) {
        return Commands.values()[index];
    }
}