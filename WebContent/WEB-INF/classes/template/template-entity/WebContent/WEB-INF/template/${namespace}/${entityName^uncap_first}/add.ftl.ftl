<#assign entityNameLower =  entity.name?uncap_first>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<@head title="添加${entity.remark!''}" author="${copyright?upper_case}" copyright="${copyright?upper_case}"/>
<link href="<@jspEl value="base"/>/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<@jspEl value="base"/>/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="<@jspEl value="base"/>/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="<@jspEl value="base"/>/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="<@jspEl value="base"/>/resources/admin/js/webuploader.js"></script>
<script type="text/javascript" src="<@jspEl value="base"/>/resources/admin/ueditor/ueditor.js"></script>
<script type="text/javascript" src="<@jspEl value="base"/>/resources/admin/js/common.js"></script>
<script type="text/javascript" src="<@jspEl value="base"/>/resources/admin/js/input.js"></script>
<script type="text/javascript" src="<@jspEl value="base"/>/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");

	[@flash_message /]

	// 表单验证
	$inputForm.validate({
		rules: {
		<#list entity.requiredFields as requiredField>
			${requiredField.name}:"require"<#if requiredField_has_next>,</#if>
		</#list>
		}
	});

});
</script>
</head>
<body>
	<div class="breadcrumb">
		<a href="<@jspEl value="base"/>/admin/common/index"><@jspEl value='message("admin.breadcrumb.home")'/></a> &raquo;
		添加${entity.remark!"${entityNameLower}"}
	</div>
	<form id="inputForm" action="save" method="post">
		<table class="input">
			<#list entity.showFields as showField>
            <tr>
                <th>
                    <#if showField.ifRequired><span class="requiredField">*</span></#if><#if showField.remark?? && showField.remark!="">${showField.remark}<#else>${showField.name}</#if>:
                </th>
                <td>
                    <input type="text" name="${showField.name}" class="text" maxlength="<#if showField.len??>${showField.len}<#else >200</#if>" />
                </td>
            </tr>
			</#list>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="<@jspEl value='message("admin.common.submit")'/>" />
					<input type="button" class="button" value="<@jspEl value='message("admin.common.back")'/>" onclick="history.back(); return false;" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
