<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>列表 - Powered By PTNETWORK</title>
<meta name="author" content="PTNETWORK Team" />
<meta name="copyright" content="PTNETWORK" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {

	[@flash_message /]

    var $selectAll = $("#selectAll");
    var $ids = $("#listTable input[name='ids']");
    var $codeButton = $("#codeButton");
    var $contentRow = $("#listTable tr:gt(0)");
    // 选择
    $ids.click( function() {
        var $this = $(this);
        if ($this.prop("checked")) {
            $this.closest("tr").addClass("selected");
            $codeButton.removeClass("disabled");
        } else {
            $this.closest("tr").removeClass("selected");
            if ($("#listTable input[name='ids']:enabled:checked").size() > 0) {
                $codeButton.removeClass("disabled");
            } else {
                $codeButton.addClass("disabled");
            }
        }
    });


    // 全选
    $selectAll.click( function() {
        var $this = $(this);
        var $enabledIds = $("#listTable input[name='ids']:enabled");
        if ($this.prop("checked")) {
            $enabledIds.prop("checked", true);
            if ($enabledIds.filter(":checked").size() > 0) {
                $codeButton.removeClass("disabled");
                $contentRow.addClass("selected");
            } else {
                $codeButton.addClass("disabled");
            }
        } else {
            $enabledIds.prop("checked", false);
            $codeButton.addClass("disabled");
            $contentRow.removeClass("selected");
        }
    });

});
</script>
</head>
<body>
	<div class="breadcrumb">
	</div>
	<form id="listForm" action="openList" method="get">
		<input type="hidden" name="projectId" value="${projectId}"/>
		<input type="hidden" name="selectEntity" id="selectEntity"/>
		<div class="bar">
			<div class="buttonGroup">
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
				<div id="pageSizeMenu" class="dropdownMenu">
					<a href="javascript:;" class="button">
					${message("admin.page.pageSize")}<span class="arrow">&nbsp;</span>
					</a>
					<ul>
						<li[#if page.pageSize == 10] class="current"[/#if] val="10">10</li>
						<li[#if page.pageSize == 20] class="current"[/#if] val="20">20</li>
						<li[#if page.pageSize == 50] class="current"[/#if] val="50">50</li>
						<li[#if page.pageSize == 100] class="current"[/#if] val="100">100</li>
					</ul>
				</div>
			</div>
			<div id="searchPropertyMenu" class="dropdownMenu">
				<div class="search">
					<span class="arrow">&nbsp;</span>
					<input type="text" id="searchValue" name="searchValue" value="${page.searchValue}" maxlength="200" />
					<button type="submit">&nbsp;</button>
				</div>
				<ul>
					<li[#if page.searchProperty == "name"] class="current"[/#if] val="name">类名</li>
				</ul>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check">
					[#--<input type="checkbox" id="selectAll" />--]
				</th>
				<th>
					<a href="javascript:;" class="sort" name="name">类名</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="remark">备注</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="tableName">表名</a>
				</th>
                <th>
                    <a href="javascript:;" class="sort" name="createdDate">${message("admin.common.createdDate")}</a>
                </th>
			</tr>
			[#list page.content as jPAEntity]
				<tr>
					<td>
						<input type="checkbox" name="entityId" value="${jPAEntity.id}" onclick="copyId(this);"/>
					</td>
                    <td>${jPAEntity.name}</td>
                    <td>${jPAEntity.remark}</td>
                    <td>${jPAEntity.tableName}</td>
                    <td>
                        <span title="${jPAEntity.createdDate?string("yyyy-MM-dd HH:mm:ss")}">${jPAEntity.createdDate}</span>
                    </td>
				</tr>
			[/#list]
		</table>
		[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
			[#include "/admin/include/pagination.ftl"]
		[/@pagination]
	</form>
</body>
</html>
<script type="text/javascript">
    function copyId(td) {
        var  $td= $(td);
        $('input[name="entityId"]').each(function(){
            $(this).click(function(){
                if($(this).is(':checked')){
                    $(':checkbox').prop('checked',false);
                    $(this).prop('checked',true);
                }
            });
        });
		var entityName = $td.parent().parent().children("td:eq(1)").text();
		$("#selectEntity").val($td.val()+","+entityName);
    }




</script>