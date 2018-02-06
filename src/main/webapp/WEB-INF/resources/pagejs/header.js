/**
 * Created by Tanaye on 5/26/17.
 */
$(document).ready(function () {
    $("#listGameType li").on("click", function () {
        $("#listGameType li").removeClass("active");
        $(this).addClass("active");
    });

})