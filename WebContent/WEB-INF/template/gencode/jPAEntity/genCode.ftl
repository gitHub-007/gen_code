<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>编辑 - Powered By PTNETWORK</title>
<meta name="author" content="PTNETWORK Team" />
<meta name="copyright" content="PTNETWORK" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/admin/layer/layer.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	// 表单验证
	$inputForm.validate({
		rules: {
            namespace: {
                required: true
            }
        },
        submitHandler:function (form) {
            $.ajax({
                url: "genCode",
                type: "POST",
                cache: false,
                data:$inputForm.serialize(),
                success: function(message) {
                    $.message(message);
                    setTimeout(function(){
                        var index=parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
					}, 1000);

                }
            });
		}
	});
});
</script>
</head>
<body>
	<form id="inputForm" action="" method="post">
		<input type="hidden" name="ids" value="${ids}" />
		<input type="hidden" name="projectId" value="${projectId}" />
		<table class="input">
            <tr>
                <th>
                    <span class="requiredField">*</span>命名空间:
                </th>
                <td>
                    <input name="namespace"/>
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
