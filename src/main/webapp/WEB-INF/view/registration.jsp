<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../../css/product.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
            crossorigin="anonymous"></script>

    <title>Регистрация</title>
</head>
<body>
<div class="container">
    <div class="col-md-8 offset-md-4">
        <h2>Регистрация пользователя</h2>
        ${info}
        <form method="post" action="/registration" class="needs-validation">
            <input type="hidden" name="command" value="registration_user"/>
            <div class="form-group">
                <label for="name">Имя:</label>
                <input type="text" class="form-control w-25" id="name" placeholder="Введите имя" name="name"
                       required>
                <div class="invalid-feedback">Поле должно быть заполнено!</div>
            </div>
            <div class="form-group">
                <label for="surname">Фамилия:</label>
                <input type="text" class="form-control w-25" id="surname" placeholder="Введите фамилию" name="surname"
                       required>
                <div class="invalid-feedback">Поле должно быть заполнено!</div>
            </div>

            <div class="form-group">
                <label for="birthday">Дата рождения:</label>
                <input type="date" class="form-control w-25" id="birthday" name="birthday"
                       required>
                <div class="invalid-feedback">Введите дату рождения!</div>
            </div>
            <div class="form-group">
                <label for="email">Почта:</label>
                <input type="email" class="form-control w-25" id="email" placeholder="Введите email" name="email"
                       required>
                <div class="invalid-feedback">Поле должно быть заполнено!</div>
            </div>
            <div class="form-group">
                <label for="password">Пароль:</label>
                <input type="text" class="form-control w-25" id="password" placeholder="Введите пароль" name="password"
                       required>
                <div class="invalid-feedback">Поле должно быть заполнено!</div>
            </div>
            <button id="loginBtn" type="submit" class="btn btn-outline-dark">Зарегистрироваться</button>
        </form>
        <a class="btn btn-outline-dark" href="/login">На страницу входа</a>
    </div>
</div>
<script src="../../script/script.js"></script>
</body>
</html>
