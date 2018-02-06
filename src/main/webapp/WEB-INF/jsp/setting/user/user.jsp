<%--
  Created by IntelliJ IDEA.
  User: Tanaye
  Date: 6/15/17
  Time: 02:39
  To change this template use File | Settings | File Templates.
--%>
<section id="usersection">
    <div class="box-header">
        <h3 class="box-title">List User</h3>
    </div>
    <div class="box-body">
        <table id="user-editing-example"
               class="table"
               data-paging="true"
               data-filtering="true"
               data-editing-add-text="New User"
               data-editing-always-show="true"
               data-editing-show-text='<span class="fooicon fooicon-pencil" aria-hidden="true"></span> Edit Data'>
            <thead>
            <tr>
                <th data-name="domainName">Domain Name</th>
                <th data-name="permission">Permission</th>
                <th data-name="createDate" data-breakpoints="xs sm md">Create Date</th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
    </div>
</section>

<div class="modal fade" id="user-editor-modal" tabindex="-1" role="dialog"
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
        <form class="modal-content form-horizontal" id="user-editor">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="user-editor-title"></h4>
            </div>
            <div class="modal-body">
                <div class="form-group required">
                    <label for="domainName" class="col-sm-3 control-label">Domain Name</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="domainName"
                               name="domainName"
                               placeholder="Game Code" required>
                    </div>
                </div>

                <div class="form-group required">
                    <label for="permission" class="col-sm-3 control-label">User Permission</label>
                    <div class="col-sm-9">
                        <select id="permission"
                                class="selectpicker form-control pull-right">
                            <option name="permission" value="ADMIN">ADMIN</option>
                            <option name="permission" value="INTERN">INTERN</option>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label for="createDate" class="col-sm-3 control-label">Create Date&nbsp;&nbsp;&nbsp;</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="createDate"
                               name="createDate" disabled="true">
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button id="userButtomSubmit" type="submit"
                        class="btn btn-primary"></button>
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    Cancel
                </button>
            </div>
        </form>
    </div>
</div>
