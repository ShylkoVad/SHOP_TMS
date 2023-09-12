<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../../css/home.css">
    <link rel="stylesheet" type="text/css" href="../../css/product.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
            crossorigin="anonymous"></script>

    <title>Корзина</title>
</head>
<body>

<jsp:include page="header.jsp" />
<div class="container">
    <h2 class="py-4" style="text-align: center">Ваш заказ</h2>
    <c:if test="${not empty cartProductsList}">
        <div class="offset-1 mb-5">
            <c:forEach items="${cartProductsList}" var="product">
                <div class="row g-0" type="product">
                    <div class="col-md-2">

                        <c:forEach items="${images}" var="image">
                            <c:if test="${product.getId() == image.getProductId() && image.getPrimaryImage() == 1}">

                                <img class="img-fluid rounded-start ml-3"
                                     style="max-height:500px; width: auto;"
                                     src="${contextPath}/${image.getImagePath()}" alt="Card image">
                            </c:if>
                        </c:forEach>

                    </div>
                    <div class="col-md-10">
                        <div class="card-body">
                            <p style="vertical-align: middle">
                            <p class="ml-4 priceName"> ${product.getName()}</p>
                            <span class="ml-4 priceProduct">${product.getPrice()} р.</span>
                            <a href="/cart/remove?product_id=${product.getId()}">
                                <button id="removeProductFromCart" type="button" class="btn btn-outline-dark ml-4"
                                        onclick="productRemovedFromShoppingCart()">Удалить
                                </button>
                            </a>
                            </p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
    <a href="/cart/checkout" class="btn-lg d-grid gap-2 d-md-flex justify-content-md-end">
        <button type="button" class="btn btn-outline-dark ml-4"
                onclick="orderIsProcessed()">Оформить заказ
        </button>
    </a>
</div>

<script>
    function productRemovedFromShoppingCart() {
        window.confirm("Продукт успешно удален из корзины!");
    }
</script>
<script>
    function orderIsProcessed() {
        window.confirm("Заказ успешно оформлен! Благодарим за покупку!");
    }
</script>
</body>
</html>