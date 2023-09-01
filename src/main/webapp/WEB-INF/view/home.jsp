<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../../css/home.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
            crossorigin="anonymous"></script>

    <title>Каталог</title>

</head>
<body>

<div>
    <h2 class="fs-3; text-center">Каталог</h2>
    <div class="container">
        <c:if test="${not empty categories}">
            <div class="row d-flex justify-content-center">
                <c:forEach items="${categories}" var="category">
                    <div class="card w-25 m-2 text-center" type="category">
                        <a href='<c:url value="/category/${category.getId()}"/>'>
                            <div class="card-body">
                                <c:forEach items="${images}" var="image">
                                    <c:if test="${category.getId() == image.getCategoryId()}">
                                        <img class="card-img" style="max-height:150px; max-width:150px;"
                                             src="${image.getImagePath()}"
                                             alt="Card image">
                                    </c:if>
                                </c:forEach>
                                <div class="h5 card-title text-black">${category.getName()}</div>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>