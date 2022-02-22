package ru.job4j.todo.servlet;
import ru.job4j.todo.Item;
import ru.job4j.todo.store.PsqlStore;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
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
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean checkGetAll = ((req.getParameter("all").equals("1")) ? true : false);
        String str = req.getParameter("ids");
        if (str != null) {
            int[] arr = Arrays.stream(str.substring(1, str.length() - 1).split(","))
                    .map(String::trim).mapToInt(Integer::parseInt).toArray();
            for (int i : arr) {
                PsqlStore.instOf().setDone(i);
            }
        }
        String res =
            "<table class=\"table\" id=\"777\">"
            + "<thead>"
            + "<tr>"
            + "<th scope=\"col\">Номер задачи</th>"
            + "<th scope=\"col\">Название</th>"
            + "<th scope=\"col\">Дата создания</th>"
            + "<th scope=\"col\">Статус</th>"
            + "<th scope=\"col\">Выбрать</th>"
            + "</tr>"
            + "</thead>"
            + "<tbody>";

        for (Item item : PsqlStore.instOf().findAllItems(checkGetAll)) {
            res = res + "<tr>"
                      + "<td>" + item.getId() + "</td>"
                      + "<td>" + item.getDescription() + "</td>"
                      + "<td>" + item.getCreated() + "</td>"
                      + "<td>" + item.getDone() + "</td>";
            if (item.getDone() == 0) {
                res = res + "<td><input type=\"checkbox\" name=\"isDone\" id=\"" + item.getId() + "\"/></td>";
            } else {
                res = res + "<td><input type=\"checkbox\" name=\"isDone\" id=\"" + item.getId() + "\" disabled = true/></td>";
            }
            res = res + "</tr>";
        }
        res = res + "</tbody></table>";
        resp.setContentType("text/plane");
        resp.setCharacterEncoding("windows-1251");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(res);
        writer.flush();
    }
}