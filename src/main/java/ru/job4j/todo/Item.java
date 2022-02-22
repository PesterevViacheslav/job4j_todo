package ru.job4j.todo;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "item")
/**
 * Class Item - Событие.
 * Решение задач уровня Middle. Части 3.3. Hibernate.
 * Создать TODO list [#3786]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 21.02.2022
 * @version 1
 */
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "description")
    private String description;
    @Column(name = "created")
    private Timestamp created;
    @Column(name = "done")
    private int done;
    /**
     * Method Item. Конструктор
     * @param description Описание
     */
    public Item(String description) {
        this.description = description;
        this.created = new Timestamp(System.currentTimeMillis());
        this.done = 0;
    }
    /**
     * Method Item. Конструктор
     */
    public Item() {
    };
    /**
     * Method getId. Получение id дела
     * @return id дела
     */
    public int getId() {
        return this.id;
    }
    /**
     * Method getDescription. Получение описания дела
     * @return Описание
     */
    public String getDescription() {
        return this.description;
    }
    /**
     * Method getCreated. Получение даты создания дела
     * @return дата создания
     */
    public Timestamp getCreated() {
        return created;
    }
    /**
     * Method getDone. Получение статуса дела
     * @return статус
     */
    public int getDone() {
        return done;
    }
    /**
     * Method setDone. Изменение статуса дела
     * @param done статус
     */
    public void setDone(int done) {
        this.done = done;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id == item.id && Objects.equals(description, item.description);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }
    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", description='" + description + '\''
                + ", created=" + created + ", done=" + done + '}';
    }
}