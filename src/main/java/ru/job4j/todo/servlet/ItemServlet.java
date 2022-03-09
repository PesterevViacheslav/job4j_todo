package ru.job4j.todo.servlet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.Item;
import ru.job4j.todo.store.PsqlStore;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
/**
 * Class ItemServlet - Сервлет обработки списка дел.
 * Решение задач уровня Middle. Части 3.3. Hibernate.
 * Создать TODO list [#3786]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 21.02.2022
 * @version 1
 */
public class ItemServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean checkGetAll = ((req.getParameter("all").equals("1")));
        String str = req.getParameter("ids");
        if (str != null) {
            int[] arr = Arrays.stream(str.substring(1, str.length() - 1).split(","))
                    .map(String::trim).mapToInt(Integer::parseInt).toArray();
            for (int i : arr) {
                PsqlStore.instOf().setDone(i);
            }
        }
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        Collection<Item> items = PsqlStore.instOf().findAllItems(checkGetAll);
        String json = GSON.toJson(items);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }
}