/**
 * Created by Tanaye on 6/2/17.
 */

$(document).ready(function () {
    $("#sidebarmenu li").on("click", function () {
        $("#sidebarmenu li").removeClass("active");
        $(this).addClass("active");
    });
})