<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>添加 - Powered By PTNETWORK</title>
<meta name="author" content="PTNETWORK Team" />
<meta name="copyright" content="PTNETWORK" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/webuploader.js"></script>
<script type="text/javascript" src="${base}/resources/admin/ueditor/ueditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	
	[@flash_message /]
	
	// 表单验证
	$inputForm.validate({
		rules: {
            name: {
                required:true,
                validateName:true,
                remote:{
                    url:"uniqueName",
                    cache: false
                }
			},
            copyright:{
                required:true,
                validateName:true
            }
		},
        messages:{
            name:{
                remote:"项目已经存在"
            },
            copyright:{
                validateName:"请正确的包名称(只能是字母数、字下、划线)"
            }
        }
	});

    //类名验证
    jQuery.validator.addMethod("validateName", function(value, element) {
        var path =  /^[a-zA-Z0-9_]{2,}$/;
        return  path.test(value);
    }, "请正确的项目名称(只能是字母数、字下、划线)");
});
</script>
</head>
<body>
	<div class="breadcrumb">
		<a href="${base}/admin/common/index">${message("admin.breadcrumb.home")}</a> &raquo; 添加
	</div>
	<form id="inputForm" action="save" method="post">
		<table class="input">
            <tr>
                <th>
                    <span class="requiredField">*</span>项目名称:
                </th>
                <td>
                    <input type="text" name="name" class="text" maxlength="20" />
                </td>
            </tr>
            <tr>
                <th>
                    <span class="requiredField">*</span>版权所有者:
                </th>
                <td>
                    <input type="text" name="copyright" class="text" maxlength="20" />
                </td>
            </tr>
            <tr>
                <th>
                   备注:
                </th>
                <td>
                    <input type="text" name="remark" class="text" maxlength="20" />
                </td>
            </tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="history.back(); return false;" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
