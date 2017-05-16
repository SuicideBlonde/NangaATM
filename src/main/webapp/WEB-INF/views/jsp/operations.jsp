<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>

<body>

<p>Operations</p>

<form method="get" action="${pageContext.request.contextPath}/balance">
    <button type="submit">Balance</button>
</form>


<span>Amount to Withdraw:</span>
<form method="post" action="${pageContext.request.contextPath}/withdraw">
    <input type="text" name="amount">
    <button type="submit">withdraw</button>
</form>


<span>Exit</span>
<form method="post" action="${pageContext.request.contextPath}/exit">
    <button type="submit">exit</button>
</form>

</body>
</html>