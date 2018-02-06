$(document)
    .ready(
        function () {

            $('#button_add_user').click(function () {

                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                var _headers = {};
                _headers[header] = token;

                var domainName = $("#domainName").val();
                var permissionId = [];

                for (var i = 0; i < $("#permissionId").find("option:selected").length; i++) {
                    permissionId[i] = ($("#permissionId").find("option:selected")[i]).value;
                }

                $.ajax({
                    url: "../ajax/user/add",
                    headers: _headers,
                    method: "post",
                    dataType: "json",
                    data: {
                        domainName: domainName,
                        permissionId: permissionId
                    }
                    ,
                    success: function (resp) {
                        if (resp === true) {
                            table.ajax.url("../ajax/user/list").load();
                        } else {
                            alert("Có lỗi phát sinh. Vui lòng thử lại.");
                        }
                    }
                });

            });

            var table = $('#example1').DataTable({
                "processing": true,
                "serverSide": true,
                "searching": false,
                "ordering": false,
                "bInfo": false,
                "ajax": {
                    "url": "../ajax/user/list",
                    "type": "POST",
                    "dataType": "json",
                    "data": {
                        "_csrf": $("#_csrf").val()
                    }
                },
                "columns": [
                    {"data": "id"},
                    {"data": "domainName"},
                    {"data": "roleString"}
                ]
            });

            // $('#example1 tbody').on('click', 'tr', function () {
            //     var data = table.row( this ).data();
            //     alert( 'You clicked on ' + data[0] + data[1] + data[3] +'row' );
            // } );

        }
    )