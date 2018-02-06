/**
 * Created by Tanaye on 6/7/17.
 */
$(document).ready(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var _headers = {};
    _headers[header] = token;

    userListRender();

    function userListRender() {
        $.ajax({
            url: "/setting/ajax/user/list",
            type: 'get',
            dataType: 'json',
            success: function (resp) {

                $("#user-editing-example").find("tbody").empty();

                for (var i = 0; i < resp.length; i++) {
                    var o = resp[i];
                    $("#user-editing-example")
                        .append(
                            "<tr>" +
                            "<td>" + o.domainName + "</td>" +
                            "<td>" + o.permission + "</td>" +
                            "<td>" + o.createDate + "</td>" +
                            "</tr>"
                        )
                }

                var $modal = $('#user-editor-modal'),
                    $editor = $('#user-editor'),
                    $editorTitle = $('#user-editor-title'),
                    $buttomSubmit = $('#userButtomSubmit');

                uft = FooTable.init('#user-editing-example', {
                    editing: {
                        enabled: true,
                        addRow: function () {
                            $modal.removeData('row');
                            $editor[0].reset();
                            $editorTitle.text('Add a new user');
                            $editor.find('#domainName').prop('disabled', false);
                            $buttomSubmit.text('Save');
                            $modal.modal('show');
                        },
                        editRow: function (row) {
                            var values = row.val();
                            $editor.find('#domainName').val(values.domainName);
                            $editor.find('#domainName').prop('disabled', true);
                            $editor.find('#createDate').val(values.createDate);
                            var kpiId = values.kpiId;
                            $("#permission option").filter(function () {
                                return $.trim($(this).val()) == kpiId;
                            }).prop('selected', true);
                            $('#permission').selectpicker('refresh');

                            $modal.data('row', row);
                            $editorTitle.text('Edit User : ' + values.gameName);
                            $buttomSubmit.text('Update');
                            $modal.modal('show');
                        },
                        deleteRow: function (row) {
                            if (confirm('Are you sure you want to delete the row?')) {
                                var values = row.val();
                                var domainName = values.domainName;
                                $.ajax({
                                    url: "/setting/ajax/user/remove",
                                    method: "post",
                                    dataType: "json",
                                    data: {
                                        _csrf: token,
                                        domainName: domainName
                                    },
                                    success: function (resp) {
                                        $modal.modal('hide');
                                        if (resp.result == true) {
                                            row.delete();
                                        } else {
                                            alert(resp.message);
                                        }
                                    },
                                    error: function (e) {
                                        $modal.modal('hide');
                                        alert("Có lỗi phát sinh");
                                    }
                                });

                            }
                        }
                    }
                });

                uuid = 10;

                $editor.on('submit', function (e) {
                    if (this.checkValidity && !this.checkValidity()) return;

                    e.preventDefault();

                    var domainName = $editor.find('#domainName').val(),
                        permission = $editor.find('#permission').find("option:selected").val();

                    var action = $("#userButtomSubmit").text();
                    var url;
                    if (action.toString() == "Save") {
                        url = "/setting/ajax/user/add";
                    } else {
                        url = "/setting/ajax/user/add?isUpdate=true"
                    }

                    $.ajax({
                        url: url,
                        method: "post",
                        dataType: "json",
                        data: {
                            _csrf: token,
                            domainName: domainName,
                            permission: permission,
                        },
                        success: function (resp) {
                            $modal.modal('hide');

                            if (resp.result == true) {
                                var dateEnd = new Date(moment()),
                                    yrEnd = dateEnd.getFullYear(),
                                    monthEnd = (dateEnd.getMonth() + 1) < 10 ? '0' + (dateEnd.getMonth() + 1) : dateEnd.getMonth(),
                                    dayEnd = dateEnd.getDate() < 10 ? '0' + dateEnd.getDate() : dateEnd.getDate(),
                                    endDate = yrEnd + '-' + monthEnd + '-' + dayEnd;

                                var row = $modal.data('row'),
                                    values = {
                                        domainName: domainName,
                                        permission: permission,
                                        createDate: endDate
                                    };
                                if (row instanceof FooTable.Row) {
                                    row.val(values);
                                } else {
                                    values.id = uuid++;
                                    uft.rows.add(values);
                                }
                            } else {
                                alert(resp.message);
                            }
                        },
                        error: function (e) {
                            $modal.modal('hide');
                            alert("Có lỗi phát sinh");
                        }
                    });


                });
            }
        });
    }

})