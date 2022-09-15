package ru.job4j.todo.servlet;
import ru.job4j.todo.User;
import ru.job4j.todo.store.PsqlStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * Class UserServlet - Сервлет создания пользователя. Решение задач уровня Middle.
 * Решение задач уровня Middle. Части 3.3. Hibernate.
 * Создать TODO list [#3786]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 13.11.2020
 * @version 1
 */
public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        String usrName = req.getParameter("username");
        String usrPassword = req.getParameter("password");
        if (usrName != null && usrPassword != null) {
            User user = PsqlStore.instOf().findUserByUsername(usrName);
            HttpSession sc = req.getSession();
            if (user != null) {
                sc.setAttribute("user", user);
                resp.sendRedirect(req.getContextPath() + "/index.do");
            } else {
                sc.setAttribute("user",
                        PsqlStore.instOf().createUser(User.of(usrName, usrPassword)));
                resp.sendRedirect(req.getContextPath() + "/index.do");
            }
        } else {
            req.setAttribute("error", "Не указаны логин, или пароль");
            req.getRequestDispatcher("login.jsp?error=").forward(req, resp);
        }
    }
}