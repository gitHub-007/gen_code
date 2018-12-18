<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>添加 - Powered By PTNETWORK</title>
    <meta name="author" content="PTNETWORK Team"/>
    <meta name="copyright" content="PTNETWORK"/>
    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/layer/layer.js"></script>
    <style type="text/css">
        table.input th {
            text-align: left;
        }
    </style>
    <script type="text/javascript">
        $().ready(function () {

            var $inputForm = $("#inputForm");
	        [@flash_message /]

            // 表单验证
            $inputForm.validate({
                rules: {
                    name: {
                        required: true,
                        validateClassName: true,
                        remote: {
                            url: "uniqueName?projectId="+${projectId},
                            cache: false
                        }
                    },
                    javaType: {
                        required: true
                    },
                    fieldName: {
                        required: true,
                        unique: true
                    },
                    associationType: {
                        required: true
                    },
                    entityIds: {
                        required: true,
                        unique: true
                    }
                },
                messages: {
                    name: {
                        remote: "实体已经存在"
                    }
                },
                submitHandler: function (form) {
                    $("#ifShowHidden").val(arrToStr("ifShow"));
                    $("#ifSearchHidden").val(arrToStr("ifSearch"));
                    $("#ifRequiredHidden").val(arrToStr("ifRequired"));
                    $("#ifMasterHidden").val(arrToStr("isMaster"));
                    form.submit();
                }
            });

            $(function () {
                if ($.validator) {
                    $.validator.prototype.elements = function () {
                        var validator = this,
                                rulesCache = {};
                        // Select all valid inputs inside the form (no submit or reset buttons)
                        return $(this.currentForm)
                                .find("input, select, textarea, [contenteditable]")
                                .not(":submit, :reset, :image, :disabled")
                                .not(this.settings.ignore)
                                .filter(function () {
                                    var name = this.id || this.name || $(this).attr("name"); // For contenteditable
                                    if (!name && validator.settings.debug && window.console) {
                                        console.error("%o has no name assigned", this);
                                    }
                                    // Set form expando on contenteditable
                                    if (this.hasAttribute("contenteditable")) {
                                        this.form = $(this).closest("form")[0];
                                    }
                                    // Select only the first element for each name, and only those with rules specified
                                    if (name in rulesCache || !validator.objectLength($(this).rules())) {
                                        return false;
                                    }
                                    rulesCache[name] = true;
                                    return true;
                                });
                    }
                }

            });


            //类名验证
            $.validator.addMethod("validateClassName", function (value, element) {
                var mobile = /^[A-Z]+[0-9a-zA-Z_]*$/;
                return mobile.test(value);
            }, "类名必须以大写字母开头,参考java类名的驼峰写法");

            $.validator.addMethod("unique", function (value, element) {
                var $fieldName = $("[name=" + $(element).prop("name") + "]");
                var flag = true;
                var count = 0;
                $fieldName.each(function () {
                    if ($.trim($(this).val()) == $.trim(value)) {
                        count = count + 1;
                        if (count >= 2) {
                            flag = false;
                            return false;
                        }
                    }
                });
                return flag;
            }, "属性不能重复");

        });

        function arrToStr(elementName) {
            var str = "";
            $("input[name=" + elementName + "]").each(function () {
                if ($(this).is(':checked')) {
                    str = str + "true,";
                } else {
                    str = str + "false,";
                }
            });
            return str;
        }
    </script>
</head>
<body>
<div class="breadcrumb">
    <a href="${base}/admin/common/index">${message("admin.breadcrumb.home")}</a> &raquo; 添加
</div>
<form id="inputForm" action="save" method="post">
    <input type="hidden" name="projectId" value="${projectId}"/>
    <input type="hidden" name="ifShowHidden" id="ifShowHidden"/>
    <input type="hidden" name="ifSearchHidden" id="ifSearchHidden"/>
    <input type="hidden" name="ifRequiredHidden" id="ifRequiredHidden"/>
    <input type="hidden" name="ifMasterHidden" id="ifMasterHidden"/>
    <ul id="tab" class="tab">
        <li>
            <input type="button" value="类信息"/>
        </li>
        <li>
            <input type="button" value="字段"/>
        </li>
        <li>
            <input type="button" value="关联信息"/>
        </li>
    </ul>
    <table class="input tabContent">
        <tr>
            <th style="text-align:right;">
                <span class="requiredField">*</span>类名:
            </th>
            <td>
                <input type="text" name="name" class="text" maxlength="20"/>
            </td>
        </tr>
        <tr>
            <th style="text-align:right;">
                备注:
            </th>
            <td>
                <input type="text" name="remark" class="text" maxlength="20"/>
            </td>
        </tr>
        <tr>
            <th style="text-align:right;">
                表名:
            </th>
            <td>
                <input type="text" name="tableName" class="text" maxlength="20"/>
            </td>
        </tr>
    </table>
    <table id="fieldTable" class="input tabContent">
        <tr>
            <td colspan="9"><input type="button" class="button" onclick="addFieldTr();" value="新增字段"/></td>
        </tr>
        <tr>
            <th align="left">
                序号
            </th>
            <th align="left">
                <span class="requiredField">*</span>属性类型
            </th>
            <th align="left">
                <span class="requiredField">*</span>属性名称
            </th>
            <th align="left">
                属性备注
            </th>
            <th align="left">
                长度
            </th>
            <th align="left">
                小数精度
            </th>
        [#--<th align="left">--]
        [#--默认值--]
        [#--</th>--]
            <th align="left">
                是否显示
            </th>
            <th align="left">
                是否搜索条件
            </th>
        [#--<th align="left">--]
        [#--允许空值--]
        [#--</th>--]
            <th align="left">
                是否必填
            </th>
        </tr>
        <tr>
            <td>1</td>
            <td>BigInteger</td>
            <td>id</td>
            <td>主键</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>2</td>
            <td>Date</td>
            <td>createDate</td>
            <td>创建时间</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        <tr id="lastModifiedDate">
            <td>3</td>
            <td>Date</td>
            <td>lastModifiedDate</td>
            <td>最后修改时间</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
    </table>
    <table class="input tabContent" id="assosationTable">
        <tr id="assosationTr">
            <td colspan="4"><input type="button" class="button" onclick="addAssosationTr();" value="新增关系"/></td>
        </tr>
        <tr>
            <th align="left">
                序号
            </th>
            <th align="left">
                <span class="requiredField">*</span>关系类型
            </th>
            <th align="left">
                <span class="requiredField">*</span>对应实体
            </th>
            <th align="left">
                <span class="requiredField">*</span>集合类型
            </th>
            <th align="left">
                <span class="requiredField"></span>是否关系主控方
            </th>
        </tr>
    </table>
    <table class="input">
        <tr>
            <th>
                &nbsp;
            </th>
            <td>
                <input type="submit" class="button" value="${message("admin.common.submit")}"/>
                <input type="button" class="button" value="${message("admin.common.back")}"
                       onclick="history.back(); return false;"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
<script type="text/javascript">
    var $basicTypeSelectArr = '${basicTypeSelect}'.split(",");
    var $selectStr = '<td><select name="javaType" ><option value="">请选择字段类型</option>';
    for (arrIndex in $basicTypeSelectArr) {
        var javaType = $basicTypeSelectArr[arrIndex];
        $selectStr += '<option value="' + javaType + '">' + javaType + '</option> '
    }
    $selectStr += '</select></td><td><input name="fieldName" type="text" /></td>' +
            '<td><input name="fieldRemark" type="text" /></td>' +
            '<td><input name="len" type="text" /></td>' +
            '<td><input name="pointPrecision" type="text"/></td>' +
            // '<td><input name="defaultValue" type="text"/></td>' +
            '<td><input name="ifShow" type="checkbox"/></td>' +
            '<td><input name="ifSearch" type="checkbox" /></td>' +
            '<td><input name="ifRequired" type="checkbox" /></td>';

    function addFieldTr() {
        var $table = $("#fieldTable");
        var trCount = $('#fieldTable tr').length - 1;
        var $tr = $("<tr id=\"" + trCount + "_tr\" onmouseover=\"tips(this);\"><td>" + trCount + "</td>" + $selectStr + "</tr>");
        $table.append($tr);
        generateId($("[name=javaType]"));
        generateId($("[name=fieldName]"));
        $tr.children(":first").click(function () {
            return delTr(this,$('#fieldTable tr:gt(3) td:nth-child(1)'),3);
        });
    }

    function delTr(td,tdIds,first) {
        var $td = $(td);
        $.dialog({
            type: "warn",
            content: "您确定要删除 第" + $td.text() + "行 吗？",
            ok: message("admin.dialog.ok"),
            cancel: message("admin.dialog.cancel"),
            onOk: function () {
                $td.parent("tr").remove();
                // var tdIds = $(tdIds);
                var ids = first;
                tdIds.each(function () {
                    $(this).text(ids);
                    ids++;
                });
            }
        });
        return false;
    }

    var $associationTypeArr = '${associationSelect}'.split(",");
    var $associatioStr = '<td><select name="associationType" ><option value="">请选择关系类型</option>';
    for (arrIndex in $associationTypeArr) {
        var associationType = $associationTypeArr[arrIndex];
        $associatioStr += '<option value="' + associationType + '">' + associationType + '</option> '
    }
    $associatioStr += '</select></td><td><select name="entityIds"  onclick="showEntity(this);"' +
            ' readonly="readonly"><option value="">请选择实体类</option></select></td>' +
            '<td><select name="collectionType" ><option value="None">请选择集合类型</option>';

    var $collectionTypeArr = '${collectionTypeSelect}'.split(",");
    for (arrIndex in $collectionTypeArr) {
        var associationType = $collectionTypeArr[arrIndex];
        $associatioStr += '<option value="' + associationType + '">' + associationType + '</option> '
    }
    $associatioStr += '</select></td><td><input type="checkbox" name="isMaster"/></td>';

    function addAssosationTr() {
        var $table = $("#assosationTable");
        var trCount = $('#assosationTable tr').length - 1;
        var $tr = $("<tr id=\"" + trCount + "_tr\" onmouseover=\"tips(this);\"><td>" + trCount + "</td>" + $associatioStr + "</tr>");
        $table.append($tr);
        generateId($("[name=associationType]"));
        generateId($("[name=entityIds]"));
        $tr.children(":first").click(function () {
            return delTr(this,$('#addAssosationTr tr:gt(0) td:nth-child(1)'),1);
        });
    }

    function generateId(objArr) {
        var idStr = 0;
        objArr.each(function () {
            $(this).prop("id", $(this).prop("name") + idStr);
            idStr++;
        });
    }

    function tips(tr) {
        var $tr = $(tr);
        layer.tips('点击该行第一列可以删除该行', '#' + $tr.prop("id"), {
            tips: [3, '#0FA6D8'],
            tipsMore: false,
            time: 1500
        });
        return false;
    }

    function showEntity(input) {
        var $input = $(input);
        layer.open({
            title: "实体列表",
            type: 2,
            skin: 'layui-layer-rim',
            area: ['820px', '480px'],
            btn: ['确定', '取消'],
            yes: function (index, layero) {
                var body = layer.getChildFrame('body', index);
                var str = body.find("#selectEntity").val();
                $input.empty();
                $input.append("<option value='" + str.substring(0, str.lastIndexOf(",")) + "' selected='selected'>" +
                        str.substring(str.lastIndexOf(",") + 1) + "</option>");
                layer.close(index);
            },
            btn2: function (index, layero) {
                layer.close(index);
            },
            content: 'openList?projectId=' +${projectId}
        });
    }
</script>