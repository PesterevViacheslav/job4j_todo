<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="index.js"></script>

    <title>Список задач TODO</title>
</head>

<div class="container pt-3">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/user.jsp">Регистрация</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">| Войти</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"> <c:out value="${user.name}"/> | Выйти</a>
            </li>
        </ul>
    </div>
    <form action="<%=request.getContextPath()%>/index.do" method="post">
        <div class="card-header">
            Новая задача
        </div>
        <div class="form-group">
            <label>Название задачи</label>
            <input type="text" class="form-control" name="task_name">
            <input type="hidden" name="current_user" value="${user.name}">
        </div>

        <label>Отображать все</label>
        <input type="checkbox" name="getAll" id="checkAll"/>

        <button type="submit" class="btn btn-primary" onclick="return getItems('${user.name}');">Обновить список задач</button>
        <button type="submit" class="btn btn-primary" onclick="return setDone('${user.name}');">Отметить выбранные задачи как
            выполненные
        </button>
        <button type="submit" class="btn btn-primary" onclick="return validate();">Сохранить</button>
    </form>
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Задачи
            </div>
            <div class="card-body">
                <table class="table" id="table">
                    <thead>
                    <tr>
                        <th scope="col">Номер задачи</th>
                        <th scope="col">Название</th>
                        <th scope="col">Дата создания</th>
                        <th scope="col">Статус</th>
                        <th scope="col">Выбрать</th>
                    </tr>
                    </thead>
                    <tbody id="tbody">
                    <c:forEach items="${items}" var="item">
                        <tr>
                            <td>
                                <c:out value="${item.id}"/>
                            </td>
                            <td>
                                <c:out value="${item.description}"/>
                            </td>
                            <td>
                                <c:out value="${item.created}"/>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${item.done}">
                                        <c:out value="Выполнено"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="Не выполнено"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${item.done}">
                                        <input id="${item.id}" type="checkbox" name="isDone" value="${item.id}" disabled=true/>
                                    </c:when>
                                    <c:otherwise>
                                        <input id="${item.id}" type="checkbox" name="isDone" value="${item.id}"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>