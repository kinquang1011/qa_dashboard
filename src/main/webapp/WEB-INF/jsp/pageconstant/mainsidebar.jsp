<aside class="main-sidebar">
    <section class="sidebar">
        <ul id="sidebarmenu" class="sidebar-menu">

            <li id="issuespage">
                <a href="/issues?noIssues=false">
                    <i class="fa fa-gg"></i> <span>Issues</span>
                </a>
            </li>

            <li id="noissuespage">
                <a href="/issues?noIssues=true">
                    <i class="fa fa-star-o"></i> <span>No Issues</span>
                </a>
            </li>

            <li id="detectpage">
                <a href="/detect">
                    <i class="fa fa-exclamation-triangle"></i> <span>Game Detection</span>
                </a>
            </li>

            <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                <li id="settingpage">
                    <a href="/setting">
                        <i class="fa fa-wrench"></i> <span>Setting</span>
                    </a>
                </li>
            </sec:authorize>

        </ul>
    </section>
</aside>