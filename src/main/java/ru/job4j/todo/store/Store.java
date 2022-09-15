package ru.job4j.todo.store;
import ru.job4j.todo.Item;
import ru.job4j.todo.User;
import java.util.Collection;
/**
 * Interface Store - Хранилище.
 * Решение задач уровня Middle. Части 3.3. Hibernate.
 * Создать TODO list [#3786]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 21.02.2022
 * @version 1
 */
public interface Store {
    Collection<Item> findAllItems(boolean getAllState, User user);
    Item add(Item item);
    void setDone(int id);
    Item findById(int id);
    User findUserByUsername(String username);
    User createUser(User user);
}