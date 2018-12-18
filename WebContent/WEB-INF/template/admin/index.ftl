[#assign shiro = JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html;charset=utf-8"/>
    <title>${message("admin.index.title")} - Powered By PTNETWORK</title>
    <meta name="author" content="PTNETWORK Team"/>
    <meta name="copyright" content="PTNETWORK"/>
    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/resources/admin/css/index.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript">
        $().ready(function () {

            var $nav = $("#nav a:not(:last)");
            var $menu = $("#menu dl");
            var $menuItem = $("#menu a");
            var $iframe = $("#iframe");

            $nav.click(function () {
                var $this = $(this);
                $nav.removeClass("current");
                $this.addClass("current");
                var $currentMenu = $($this.attr("href"));
                $menu.hide();
                $currentMenu.show();
                return false;
            });

            $menuItem.click(function () {
                var $this = $(this);
                $menuItem.removeClass("current");
                $this.addClass("current");
            });

            $iframe.load(function () {
                if ($iframe.is(":hidden") && $iframe.contents().find("body").html() != "") {
                    $iframe.show().siblings().hide();
                }
            });

        });
    </script>
</head>
<body>
<script type="text/javascript">
    if (self != top) {
        top.location = self.location;
    }
</script>
<table class="index">
    <tr>
        <th class="logo">
            <a href="index">
                <img src="#" alt="PTNETWORK"/>
            </a>
        </th>
        <th>
            <div id="nav" class="nav">
                <ul>
						[#list ["admin:setting", "admin:area", "admin:paymentMethod", "admin:shippingMethod", "admin:deliveryCorp", "admin:paymentPlugin", "admin:storagePlugin", "admin:loginPlugin", "admin:promotionPlugin", "admin:admin", "admin:role", "admin:message", "admin:auditLog"] as permission]
                            [@shiro.hasPermission name = permission]
								<li>
                                    <a href="#system">${message("admin.index.systemNav")}</a>
                                </li>
                                [#break /]
                            [/@shiro.hasPermission]
                        [/#list]
                    <li>
                        <a href="/admin/index">主页</a>
                    </li>
                </ul>
            </div>
            <div class="link">
            </div>
            <div class="link">
                <strong>[@shiro.principal property = "displayName" /]</strong>
            ${message("admin.index.hello")}!
                <a href="profile/edit" target="iframe">[${message("admin.index.profile")}]</a>
                <a href="logout" target="_top">[${message("admin.index.logout")}]</a>
            </div>
        </th>
    </tr>
    <tr>
        <td id="menu" class="menu">
            <dl id="system">
                <dt>${message("admin.index.systemGroup")}</dt>
					[@shiro.hasPermission name = "admin:admin"]
						<dd>
                            <a href="admin/list" target="iframe">${message("admin.index.admin")}</a>
                        </dd>
                    [/@shiro.hasPermission]
					[@shiro.hasPermission name = "admin:role"]
						<dd>
                            <a href="role/list" target="iframe">${message("admin.index.role")}</a>
                        </dd>
                    [/@shiro.hasPermission]
                <dd>
                    <a href="gencode/jPAProject/list" target="iframe">项目列表</a>
                </dd>
            </dl>
        </td>
        <td>
            <div class="breadcrumb">
            ${message("admin.index.title")}
            </div>
            <table class="input">
                <tr>
                    <th>
                    ${message("admin.index.systemName")}:
                    </th>
                    <td>${systemName}</td>
                    <th>${message("admin.index.systemVersion")}:</th>
                    <td>${systemVersion}</td>
                </tr>
                <tr>
                    <td colspan="4">
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <th>
                    ${message("admin.index.javaVersion")}:
                    </th>
                    <td>
                    ${javaVersion}
                    </td>
                    <th>
                    ${message("admin.index.javaHome")}:
                    </th>
                    <td>
                    ${javaHome}
                    </td>
                </tr>
                <tr>
                    <th>
                    ${message("admin.index.osName")}:
                    </th>
                    <td>
                    ${osName}
                    </td>
                    <th>
                    ${message("admin.index.osArch")}:
                    </th>
                    <td>
                    ${osArch}
                    </td>
                </tr>
                <tr>
                    <th>
                    ${message("admin.index.serverInfo")}:
                    </th>
                    <td>
                        <span title="${serverInfo}">${abbreviate(serverInfo, 30, "...")}</span>
                    </td>
                    <th>
                    ${message("admin.index.servletVersion")}:
                    </th>
                    <td>
                    ${servletVersion}
                    </td>
                </tr>
                <tr>
                    <td class="powered" colspan="4">
                        COPYRIGHT © 2005-2017 ptnetwork.NET ALL RIGHTS RESERVED.
                    </td>
                </tr>
            </table>
            <iframe id="iframe" name="iframe" frameborder="0"></iframe>
        </td>
    </tr>
</table>
</body>
</html>