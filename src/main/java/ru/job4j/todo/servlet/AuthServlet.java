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
 * Class AuthServlet - Сервлет аутентификации. Решение задач уровня Middle.
 * Решение задач уровня Middle. Части 3.3. Hibernate.
 * Создать TODO list [#3786]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 30.07.2022
 * @version 1
 */
public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (password != null && username != null) {
            req.setAttribute("current_user", username);
            User user = PsqlStore.instOf().findUserByUsername(username);
            HttpSession sc = req.getSession();
            if (user == null || !user.getPassword().equals(password)) {
                req.setAttribute("error", "Не верный логин или пароль");
                req.getRequestDispatcher("login.jsp?error=").forward(req, resp);
            } else {
                sc.setAttribute("user", user);
                sc.setAttribute("user_name", username);
                sc.setAttribute("current_user", username);
                resp.sendRedirect(req.getContextPath() + "/index.do?current_user=" + username);
            }
        } else {
            req.setAttribute("error", "Не верный логин или пароль");
            req.getRequestDispatcher("login.jsp?error=").forward(req, resp);
        }
    }
}