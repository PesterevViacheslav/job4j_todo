package ru.job4j.todo.store;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.Item;
import java.util.ArrayList;
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
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }
    /**
     * Method findById. Поиск по ID.
     * @param id ID дела.
     * @return Дело
     */
    @Override
    public Item findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item result = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }
    /**
     * Method setDone. Перевод в состояние - Выполнена.
     * @param id ID дела.
     */
    @Override
    public void setDone(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item itm = findById(id);
        if (itm != null && itm.getDone() == 0) {
            itm.setDone(1);
            session.update(itm);
        }
        session.getTransaction().commit();
        session.close();
    }
    /**
     * Method findAllItems. Получение списка текущих дел.
     * @return Дела.
     */
    @Override
    public ArrayList<Item> findAllItems(boolean getAllState) {
        Session session = sf.openSession();
        session.beginTransaction();
        ArrayList<Item> res;
        if (getAllState) {
            res = (ArrayList<Item>) session.createQuery("from ru.job4j.todo.Item order by 1").list();
        } else {
            res = (ArrayList<Item>) session.createQuery("from ru.job4j.todo.Item where done = 0 order by 1").list();
        }
        session.getTransaction().commit();
        session.close();
        return res;
    }
    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}

