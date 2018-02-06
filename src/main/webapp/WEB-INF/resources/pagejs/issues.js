$(document).ready(function () {

        $('#reservation').daterangepicker({
            startDate: moment().subtract('days', 6),
            endDate: moment(),
            alwaysShowCalendars: true,
            autoApply: true,
            autoUpdateInput: false,
            ranges: {
                'Today': [moment(), moment()],
                'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                'Last 7 Days': [moment().subtract(6, 'days'), moment()],
                'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                'This Month': [moment().startOf('month'), moment().endOf('month')],
                'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            },
            locale: {
                cancelLabel: 'Clear'
            },
        });

        $('#reservation').on('apply.daterangepicker', function (ev, picker) {
            $(this).val(picker.startDate.format('YYYY-MM-DD') + ' - ' + picker.endDate.format('YYYY-MM-DD'));
        });

        var dateStart = new Date(moment().subtract('days', 6)),
            yrStart = dateStart.getFullYear(),
            monthStart = (dateStart.getMonth() + 1) < 10 ? '0' + (dateStart.getMonth() + 1) : dateStart.getMonth(),
            dayStart = dateStart.getDate() < 10 ? '0' + dateStart.getDate() : dateStart.getDate(),
            startDate = yrStart + '-' + monthStart + '-' + dayStart;

        var dateEnd = new Date(moment()),
            yrEnd = dateEnd.getFullYear(),
            monthEnd = (dateEnd.getMonth() + 1) < 10 ? '0' + (dateEnd.getMonth() + 1) : dateEnd.getMonth(),
            dayEnd = dateEnd.getDate() < 10 ? '0' + dateEnd.getDate() : dateEnd.getDate(),
            endDate = yrEnd + '-' + monthEnd + '-' + dayEnd;

        $('#reservation').val(startDate + " - " + endDate);

        pageActive();

        $("#show").on("click", function () {
            $.ajax({
                url: "/issues/ajax/issuesTable?rangeDate=" + $("#reservation").val() + "&noIssues=" + getParameterByName("noIssues"),
                type: 'get',
                dataType: 'json',
                success: function (resp) {
                    if (resp.length > 0) {
                        $("#fooTable").find("tbody").empty();
                        for (var i = 0; i < resp.length; i++) {
                            var divId = resp[i][1] + resp[i][3];
                            var title = resp[i][1] + "-" + resp[i][3];
                            var valueTool = parseFloat(resp[i][6].toString().split("(")[1].replace(")", ""));
                            var valueQa = parseFloat(resp[i][7].toString().split("(")[1].replace(")", ""));

                            $("#fooTable")
                                .append(
                                    "<tr>" +
                                    "<td>" + resp[i][1] + "</td>" +
                                    "<td>" + resp[i][2] + "</td>" +
                                    "<td>" + resp[i][3] + "</td>" +
                                    "<td>" + "<div subTitle='" + resp[i][5] + "' titleChart='" + title + "' value='" + resp[i][4] + "' date='" + resp[i][0] + "' class='highChartRender' id='" + divId.toString() + "' style=\"height: 150px; \"></div></td>" +
                                    "<td>" + resp[i][5] + " (" + numberWithCommas(valueTool - valueQa) + ")" + "</td>" +
                                    "<td>" + resp[i][6].toString().split("(")[0] + "(" + numberWithCommas(valueTool) + ")" + "</td>" +
                                    "<td>" + resp[i][7].toString().split("(")[0] + "(" + numberWithCommas(valueQa) + ")" + "</td>" +
                                    "</tr>"
                                )
                        }

                        $('#fooTable').footable({
                            "paging": {"size": 20},
                            "on": {
                                "after.ft.paging": function (e, ft, p) {
                                    renderHighChart();
                                },
                                "after.ft.filtering": function (e, ft, filter) {
                                    renderHighChart();
                                }
                            }

                        });

                        renderHighChart();
                    }
                },
            });
        });

        function renderHighChart() {
            $(".highChartRender").each(function () {
                var divId = $(this).attr("id");
                var date = $(this).attr("date");
                var title = $(this).attr("titleChart");
                var subTitle = $(this).attr("subTitle").toString();

                if (subTitle.includes("-")) {
                    subTitle = "Down " + subTitle
                } else {
                    subTitle = "Up " + subTitle
                }

                var s = "[" + $(this).attr("value") + "]";
                var data = JSON.parse(s);
                render(date, data, divId.toString(), title.toString(), subTitle);
            });
        }

        function getParameterByName(name, url) {
            if (!url) url = window.location.href;
            name = name.replace(/[\[\]]/g, "\\$&");
            var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
                results = regex.exec(url);
            if (!results) return null;
            if (!results[2]) return '';
            return decodeURIComponent(results[2].replace(/\+/g, " "));
        }

        function numberWithCommas(x) {
            return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        }

        function pageActive() {
            if (getParameterByName("noIssues") == "true") {
                $('#noissuespage').addClass('active');
            } else {
                $('#issuespage').addClass('active');
            }


        }

        function render(date, data, divName, title, subtitle) {
            var from = date.split("-");
            var f = new Date(from[0], from[1] - 1, from[2]);

            Highcharts.chart(divName, {
                chart: {
                    type: 'column'
                },
                title: {
                    text: title
                },
                exporting: {
                    enabled: false
                },
                subtitle: {
                    text: document.ontouchstart === undefined ?
                        subtitle : subtitle
                },
                xAxis: {
                    type: 'datetime'
                },
                yAxis: {
                    title: {
                        text: ''
                    },
                    plotLines: [{
                        color: '#FF0000',
                        width: 1,
                        value: 0
                    }]
                },
                legend: {
                    enabled: false
                },
                credits: {
                    enabled: false
                },
                plotOptions: {
                    plotOptions: {
                        line: {
                            dataLabels: {
                                enabled: true
                            },
                            enableMouseTracking: false
                        }
                    },
                },

                series: [{
                    name: 'Tỷ lệ chênh lệch (%)',
                    pointInterval: 24 * 3600 * 1000,
                    pointStart: Date.UTC(f.getFullYear(), f.getMonth(), f.getDate()),
                    data: data
                }]

            });
        }

        $("#show").click();

    }
)

