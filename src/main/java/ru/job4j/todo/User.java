package ru.job4j.todo;
import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "user_name")
    private String name;
    @Column(name = "password")
    private String password;
    public static User of(String name, String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        return user;
    }
    /**
     * Method getId. Получение ID
     * @return ID
     */
    public int getId() {
        return id;
    }
    /**
     * Method setId. Установка ID
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Method getName. Получение имени
     * @return Имя
     */
    public String getName() {
        return name;
    }
    /**
     * Method setName. Установка имени
     * @param name Имя
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Method getPassword. Получение пароля
     * @return Пароль
     */
    public String getPassword() {
        return password;
    }
    /**
     * Method setPassword. Установка пароля
     * @param password Пароль
     */
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}