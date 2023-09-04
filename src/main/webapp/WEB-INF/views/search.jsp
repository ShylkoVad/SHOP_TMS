<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../../css/home.css">
<%--    <link rel="stylesheet" type="text/css" href="css/product.css">--%>
    <link rel="stylesheet" type="text/css" href="../../css/search.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
            crossorigin="anonymous"></script>

    <title>Страница поиска</title>
</head>
<body>

<jsp:include page="header.jsp"/>
<div class="container">
    <h1 style="text-align: center">Результаты поиска:</h1>

    <div class="container-fluid">
        <c:if test="${not empty products}">
            <div class="row d-flex justify-content-center">
                <c:forEach items="${products}" var="product">
                    <div class="card w-25 m-1" type="product">
                        <div class="card-body">
                            <a href='<c:url value="/product/${product.getId()}"/>'>
                                <h5 class="card-title text-black text-center">${product.getName()}</h5>
                                <c:forEach items="${images}" var="image">
                                    <c:if test="${product.getId() == image.getProductId() && image.getPrimaryImage() == 1}">
                                        <img class="d-block img-fluid rounded-start m-3"
                                             style="max-height:200px; width: auto;"
                                             src="${image.getImagePath()}" alt="Card image">
                                    </c:if>
                                </c:forEach>
                            </a>
                            <p class="card-text text-truncate d-inline-block"
                               style="max-width: 250px;">${product.getDescription()}</p>
                        </div>
                        <div class="card-footer">${product.getPrice()} р.</div>
                    </div>
                </c:forEach>
            </div>
        </c:if>

    </div>
</div>

</body>
</html>
