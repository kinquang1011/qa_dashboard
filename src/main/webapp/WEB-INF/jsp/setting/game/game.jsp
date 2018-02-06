<%--
  Created by IntelliJ IDEA.
  User: Tanaye
  Date: 6/15/17
  Time: 02:37
  To change this template use File | Settings | File Templates.
--%>
<section id="gamesection">
    <div class="box-header">
        <h3 class="box-title">List Game Qa</h3>
    </div>
    <div class="box-body">
        <table id="game-editing-example"
               class="table"
               data-paging="true"
               data-filtering="true"
               data-editing-add-text="New game"
               data-editing-always-show="true"
               data-editing-show-text='<span class="fooicon fooicon-pencil" aria-hidden="true"></span> Edit Data'>
            <thead>
            <tr>
                <th data-name="gameCode">Game Code</th>
                <th data-name="gameName">Game Name</th>
                <th data-name="kpiId" data-breakpoints="xs sm md">Kpi Id</th>
                <th data-name="gameDesc" data-breakpoints="xs sm md">Desc</th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
    </div>
</section>

<div class="modal fade" id="game-editor-modal" tabindex="-1" role="dialog"
     aria-labelledby="editor-title">
    <style scoped>
        /* provides a red astrix to denote required fields */
        .form-group.required .control-label:after {
            content: "*";
            color: red;
            margin-left: 4px;
        }
    </style>
    <div class="modal-dialog" role="document">
        <form class="modal-content form-horizontal" id="game-editor">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="game-editor-title">Add Row</h4>
            </div>
            <div class="modal-body">
                <div class="form-group required">
                    <label for="gameCode" class="col-sm-3 control-label">Game
                        Code</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="gameCode"
                               name="gameCode"
                               placeholder="Game Code" required>
                    </div>
                </div>
                <div class="form-group required">
                    <label for="gameName" class="col-sm-3 control-label">Game
                        Name</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="gameName"
                               name="gameName"
                               placeholder="Game Name" required>
                    </div>
                </div>
                <div class="form-group required">
                    <label for="kpiId" class="col-sm-3 control-label">QA KPI Id</label>
                    <div class="col-sm-9">
                        <select id="kpiId"
                                class="selectpicker form-control pull-right">
                            <option name="kpiId" value="16001">Revenue - 16001</option>
                            <option name="kpiId" value="15001">Paying User - 15001
                            </option>
                            <option name="kpiId" value="10001">Active User - 10001
                            </option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="gameDesc" class="col-sm-3 control-label">Game Desc&nbsp;&nbsp;&nbsp;</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="gameDesc"
                               name="gameDesc"
                               placeholder="Game Desc">
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button id="gameButtomSubmit" type="submit"
                        class="btn btn-primary"></button>
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    Cancel
                </button>
            </div>
        </form>
    </div>
</div>