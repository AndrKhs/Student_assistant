package bot;

public enum Com {
    Delete,
    Add,
    GroupDelete,
    GroupAdd,
    DateAdd,
    DateDelete,
    DateDeadline,
    DisciplineAdd,
    DisciplineDelete,
    DisciplineDeadline,
    Deadline,
    GroupDeadline;
    public static Com valueOf(int index) {
        return Com.values()[index];
    }
}