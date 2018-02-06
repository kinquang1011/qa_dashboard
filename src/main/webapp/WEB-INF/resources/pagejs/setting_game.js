/**
 * Created by Tanaye on 6/7/17.
 */
$(document).ready(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var _headers = {};
    _headers[header] = token;

    $('#settingpage').addClass('active');

    gameListRender();

    function gameListRender() {
        $.ajax({
            url: "/setting/ajax/gameconfig/list",
            type: 'get',
            dataType: 'json',
            success: function (resp) {

                $("#game-editing-example").find("tbody").empty();

                for (var i = 0; i < resp.length; i++) {
                    var o = resp[i];
                    $("#game-editing-example")
                        .append(
                            "<tr>" +
                            "<td>" + o.gameCode + "</td>" +
                            "<td>" + o.gameName + "</td>" +
                            "<td>" + o.kpiId + "</td>" +
                            "<td>" + (o.desc == null ? '' : o.desc) + "</td>" +
                            "</tr>"
                        )
                }

                var $modal = $('#game-editor-modal'),
                    $editor = $('#game-editor'),
                    $editorTitle = $('#game-editor-title'),
                    $buttomSubmit = $('#gameButtomSubmit');

                ft = FooTable.init('#game-editing-example', {
                    editing: {
                        enabled: true,
                        addRow: function () {
                            $modal.removeData('row');
                            $editor[0].reset();
                            $editorTitle.text('Add a new game');
                            $("#kpiId").prop('disabled', false);
                            $editor.find('#gameCode').prop('disabled', false);
                            $buttomSubmit.text('Save');
                            $modal.modal('show');
                        },
                        editRow: function (row) {
                            var values = row.val();
                            $editor.find('#gameCode').val(values.gameCode);
                            $editor.find('#gameCode').prop('disabled', true);
                            $editor.find('#gameName').val(values.gameName);
                            $editor.find('#gameDesc').val(values.gameDesc);
                            var kpiId = values.kpiId;
                            $("#kpiId option").filter(function(){
                                return $.trim($(this).val()) ==  kpiId;
                            }).prop('selected', true);
                            $('#kpiId').selectpicker('refresh');

                            $("#kpiId").prop('disabled', true);
                            $modal.data('row', row);
                            $editorTitle.text('Edit game : ' + values.gameName);
                            $buttomSubmit.text('Update');
                            $modal.modal('show');
                        },
                        deleteRow: function (row) {
                            if (confirm('Are you sure you want to delete the row?')) {
                                var values = row.val();
                                console.log("abc");
                                var gameCode = values.gameCode;
                                var kpiId = values.kpiId;
                                $.ajax({
                                    url: "/setting/ajax/gameconfig/remove",
                                    method: "post",
                                    dataType: "json",
                                    data: {
                                        _csrf: token,
                                        gameCode: gameCode,
                                        kpiId: kpiId
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

                uid = 10;

                $editor.on('submit', function (e) {
                    if (this.checkValidity && !this.checkValidity()) return;

                    e.preventDefault();

                    var gameCode = $editor.find('#gameCode').val(),
                        gameName = $editor.find('#gameName').val(),
                        kpiId = $editor.find('#kpiId').find("option:selected").val(),
                        gameDesc = $editor.find('#gameDesc').val();

                    var action = $("#gameButtomSubmit").text();
                    var url;
                    if (action.toString() == "Save") {
                        url = "/setting/ajax/gameconfig/add";
                    } else {
                        url = "/setting/ajax/gameconfig/add?isUpdate=true"
                    }

                    $.ajax({
                        url: url,
                        method: "post",
                        dataType: "json",
                        data: {
                            _csrf: token,
                            gameCode: gameCode,
                            gameName: gameName,
                            gameDesc: gameDesc,
                            kpiId: kpiId
                        },
                        success: function (resp) {
                            $modal.modal('hide');

                            if (resp.result == true) {
                                var row = $modal.data('row'),
                                    values = {
                                        gameCode: gameCode,
                                        gameName: gameName,
                                        kpiId: kpiId,
                                        gameDesc: gameDesc
                                    };
                                if (row instanceof FooTable.Row) {
                                    row.val(values);
                                } else {
                                    values.id = uid++;
                                    ft.rows.add(values);
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