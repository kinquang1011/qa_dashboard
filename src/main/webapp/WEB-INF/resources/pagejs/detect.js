/**
 * Created by Tanaye on 6/2/17.
 */
$(document).ready(function () {

    $('#detectpage').addClass('active');

    var dateEnd = new Date(moment().subtract('days', 1)),
        yrEnd = dateEnd.getFullYear(),
        monthEnd = (dateEnd.getMonth() + 1) < 10 ? '0' + (dateEnd.getMonth() + 1) : dateEnd.getMonth(),
        dayEnd = dateEnd.getDate() < 10 ? '0' + dateEnd.getDate() : dateEnd.getDate(),
        endDate = yrEnd + '-' + monthEnd + '-' + dayEnd;

    $('#reservation').val(endDate);

    $('#reservation').datepicker({
        format: 'yyyy-mm-dd',
    });

    $("#show").on("click", function () {
        $.ajax({
            url: "/detect/ajax/detectTable?date=" + $("#reservation").val(),
            type: 'get',
            dataType: 'json',
            success: function (resp) {
                if (resp.length > 0) {
                    $("#fooTable").find("tbody").empty();

                    for (var i = 0; i < resp.length; i ++) {
                        var o = resp[i];
                        $("#fooTable")
                            .append(
                                "<tr>" +
                                "<td>" + o.date + "</td>" +
                                "<td>" + o.gameCode + "</td>" +
                                "<td>" + o.source + "</td>" +
                                "</tr>"
                            )
                    }

                    $('#fooTable').footable({
                        "paging": {"size": 15}
                    });

                }
            },
        });
    });

    $("#show").click();

});