package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.Item;

import java.util.ArrayList;
import java.util.function.Function;
/**
 * Class PsqlStore - Хранилище в БД postgres.
 * Решение задач уровня Middle. Части 3.3. Hibernate.
 * Создать TODO list [#3786]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 21.02.2022
 * @version 1
 */
public class PsqlStore implements Store, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    /**
     * Method tx. Применение шаблона проектирования wrapper.
     * @param command
     * @param <T>
     * @return T
     */
    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
    /**
     * Class Lazy. Экземпляр хранилища
     */
    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }
    /**
     * Method instOf. Получение экземпляра
     * @return Экземпляр
     */
    public static Store instOf() {
        return Lazy.INST;
    }
    /**
     * Method add. Добавление дела.
     * @param item Дело.
     * @return Заявка
     */
    @Override
    public Item add(Item item) {
        return this.tx(
                session -> {
                    session.save(item);
                    return item;
                }
        );
    }
    /**
     * Method findById. Поиск по ID.
     * @param id ID дела.
     * @return Дело
     */
    @Override
    public Item findById(int id) {
        return this.tx(
                session -> session.get(Item.class, id)
        );
    }
    /**
     * Method setDone. Перевод в состояние - Выполнена.
     * @param id ID дела.
     */
    @Override
    public void setDone(int id) {
        Item itm = findById(id);
        this.tx(
                session -> {
                    if (itm != null && itm.getDone() == 0) {
                        itm.setDone(1);
                        session.update(itm);
                    }
                    return null;
                }
                );
    }
    /**
     * Method findAllItems. Получение списка текущих дел.
     * @return Дела.
     */
    @Override
    public ArrayList<Item> findAllItems(final boolean getAllState) {
        return this.tx(
                session -> {
                    if (getAllState) {
                        return (ArrayList<Item>) session.createQuery("from ru.job4j.todo.Item order by 1").list();
                    } else {
                        return (ArrayList<Item>) session.createQuery("from ru.job4j.todo.Item where done = 0 order by 1").list();
                    }
                }
        );
    }
    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}

