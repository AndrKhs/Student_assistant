package bot.model;

import java.io.Serializable;

/**
 * Класс сущности пользователя
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    //Индивидуальный индетификатор пользователя
    private String id;

    //Имя пользователя
    private String userName;

    // Сущность группа
    private Group group = new Group();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
