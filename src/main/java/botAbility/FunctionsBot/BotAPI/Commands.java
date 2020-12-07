package botAbility.FunctionsBot.BotAPI;

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
    DeleteGroup;
    public static Commands valueOf(int index) {
        return Commands.values()[index];
    }
}