package ru.job4j.todo.servlet;
import ru.job4j.todo.Item;
import ru.job4j.todo.User;
import ru.job4j.todo.store.PsqlStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Class IndexServlet - Сервлет обработки корневой страницы.
 * Решение задач уровня Middle. Части 3.3. Hibernate.
 * Создать TODO list [#3786]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 30.07.2022
 * @version 1
 */
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = PsqlStore.instOf().findUserByUsername(req.getParameter("current_user"));
        req.setAttribute("items", PsqlStore.instOf().findAllItems(false, user));
        req.setAttribute("current_user", user.getName());
        req.getRequestDispatcher("index.jsp?current_user=").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        User user = PsqlStore.instOf().findUserByUsername(req.getParameter("current_user"));
        if (!req.getParameter("task_name").equals("")) {
            PsqlStore.instOf().add(Item.of(req.getParameter("task_name"), false, user));
        }
        req.setAttribute("current_user", user.getName());
        resp.sendRedirect(req.getContextPath() + "/index.do?current_user=" + user.getName());
    }
}