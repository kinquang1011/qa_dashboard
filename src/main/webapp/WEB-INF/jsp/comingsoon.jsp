<%--
  Created by IntelliJ IDEA.
  User: Tanaye
  Date: 5/25/17
  Time: 10:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<style>
    body, html {
        height: 100%;
        margin: 0;
    }

    .bgimg {
        background-image: url('http://cdn.sitebureau.com/wp-content/uploads/2016/03/coming-soon-02.jpg');
        height: 100%;
        background-position: center;
        background-size: cover;
        position: relative;
        color: white;
        font-family: "Courier New", Courier, monospace;
        font-size: 25px;
    }

    .topleft {
        position: absolute;
        top: 0;
        left: 16px;
        color: #0c0c0c;
    }

    .bottomleft {
        position: absolute;
        bottom: 0;
        left: 16px;
        color: #0c0c0c
    }

    .middle {
        position: absolute;
        top: 55%;
        left: 50%;
        transform: translate(-50%, -50%);
        text-align: center;
        color: #0c0c0c;
        font-size:30px;
    }

    hr {
        margin: auto;
        width: 40%;
    }
</style>
<body>

<div class="bgimg">
    <%--<div class="topleft">--%>
        <%--<p>VND QA Data DashBoard</p>--%>
    <%--</div>--%>
    <div class="middle">
        <p id="demo" style="font-size:30px; color: #0c0c0c"></p>
    </div>
    <div class="bottomleft">
        <p>Develop By YenTN2</p>
    </div>
</div>

<script>
    // Set the date we're counting down to
    var countDownDate = new Date("May 30, 2017 15:37:25").getTime();

    // Update the count down every 1 second
    var countdownfunction = setInterval(function() {

        // Get todays date and time
        var now = new Date().getTime();

        // Find the distance between now an the count down date
        var distance = countDownDate - now;

        // Time calculations for days, hours, minutes and seconds
        var days = Math.floor(distance / (1000 * 60 * 60 * 24));
        var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = Math.floor((distance % (1000 * 60)) / 1000);

        // Output the result in an element with id="demo"
        document.getElementById("demo").innerHTML = days + "d " + hours + "h "
            + minutes + "m " + seconds + "s ";

        // If the count down is over, write some text
        if (distance < 0) {
            clearInterval(countdownfunction);
            document.getElementById("demo").innerHTML = "EXPIRED";
        }
    }, 1000);
</script>

</body>
</html>
