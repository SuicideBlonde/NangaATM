<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>

<p>PIN Entry (1111)</p>

<form method="post" action="${pageContext.request.contextPath}/operations">
    <input id="card-number" type="password" name="pin"/>
    <input type="hidden" name="cardNumber" value="${cardNumber}">
    <button type="submit">Ok</button>
    <button id="clear" type="button">Clear</button>
</form>

</body>
</html>