<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link  href="${contextPath}/css/dropdown.css" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
            crossorigin="anonymous"></script>
    <style>
        span.error {
            color: red;
        }
    </style>

    <title>Страница регистрации</title>
</head>

<body>
<jsp:include page="header.jsp" />
<div class="container-fluid">
    <div class="row">
        <div class="col-md-3 offset-4">
            <h2>Вход в магазин</h2>
            <form method="post" action="/login" class="needs-validation" novalidate>
                <input type="hidden" name="command" value="login"/>
                <div class="form-group">
                    <label for="email">Имя пользователя:</label>
                    <input type="text" class="form-control" id="email" placeholder="Введите email" name="email"
                           >
<%--                    <span class="error">${emailError}</span>--%>
                </div>
                <div class="form-group">
                    <label for="password">Пароль:</label>
                    <input type="password" class="form-control" id="password" placeholder="Введите пароль"
                           name="password"
                          >
<%--                    <span class="error">${passwordError}</span>--%>
                </div>
                <button id="loginBtn" type="submit" class="btn btn-outline-dark">Войти</button>
            </form>
            <a class="btn btn-outline-dark" href="/registration">На страницу регистрации</a>
        </div>
    </div>
</div>
<%--<script src="script/script.js"></script>--%>
</body>
</html>
