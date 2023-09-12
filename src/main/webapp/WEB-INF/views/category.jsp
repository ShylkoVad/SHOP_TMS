<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/home.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
            crossorigin="anonymous"></script>

    <title>${category.getName()}</title>
</head>
<body>

<jsp:include page="header.jsp" />
<div class="container-fluid">
    <h1 class="h1Category">${category.getName()}</h1>
    <c:if test="${not empty products}">
        <c:forEach items="${products}" var="product">
            <div class="card mb-5 offset-1 cardCategory" type="product">
                <div class="row g-0">
                    <div class="col-md-2">
                        <a href="${contextPath}/product/${product.getId()}"/>

                            <c:forEach items="${images}" var="image">
                                <c:if test="${product.getId() == image.getProductId() && image.getPrimaryImage() == 1}">
                                    <img class="img-fluid rounded-start ml-3 ingCategory"
                                         src="${contextPath}/${image.getImagePath()}" alt="Card image">
                                </c:if>
                            </c:forEach>
                        </a>
                    </div>

                    <div class="col-md-10">
                        <div class="card-body">
                            <h5 class="card-title text-center">${product.getName()}</h5>
                            <p class="card-text ml-4">${product.getDescription()}</p>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </c:if>
</div>

</body>
</html>