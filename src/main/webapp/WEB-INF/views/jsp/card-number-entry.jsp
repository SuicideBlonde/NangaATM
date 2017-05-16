<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>

<p>Card Number Entry (1111-1111-1111-1111)</p>

<form method="post" action="${pageContext.request.contextPath}/pin">
    <input id="card-number" type="text" name="cardNumber" style="width: 160px;"/>

    <div>
        <input class="number-button" type="button" value="0"/>
        <input class="number-button" type="button" value="1"/>
        <input class="number-button" type="button" value="2"/>
        <input class="number-button" type="button" value="3"/>
        <input class="number-button" type="button" value="4"/>
    </div>
    <div>
        <input class="number-button" type="button" value="5"/>
        <input class="number-button" type="button" value="6"/>
        <input class="number-button" type="button" value="7"/>
        <input class="number-button" type="button" value="8"/>
        <input class="number-button" type="button" value="9"/>
    </div>

    <button type="submit">Ok</button>
    <button type="button" id="clear">Clear</button>
</form>

<script>
    (function ($) {

        $("#clear").click(function () {
            $("#card-number").val("");
        });

        $(".number-button").click(function () {
            var MAX_LENGTH = 20;
            var str = $(this).val();
            var $cardNumber = $("#card-number");
            var cardNumberValue = $cardNumber.val();
            var groupsCount = cardNumberValue.split("-").length - 1;

            if (cardNumberValue.length >= MAX_LENGTH) {
                return;
            }

            if ((cardNumberValue.length + 1 - groupsCount) % 4 == 0) {
                $cardNumber.val(cardNumberValue + $(this).val() + '-');
            } else {
                $cardNumber.val(cardNumberValue + $(this).val());
            }
        });
    })(jQuery);
</script>
</body>
</html>