<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>XMPP消息推送</title>
	<meta name="menu" content="notification" /> 
	<link type="text/css" rel="stylesheet" href="<c:url value='/styles/Validform-5.3.2.css'/>" />   
</head>

<body>

<div style="margin:20px 0px;">
	<form id="form" action="<c:url value='/notification/send.mbay' />" method="post" style="margin: 0px;">
		<table width="900" cellpadding="4" cellspacing="0" border="0">
			<tr>
				<td width="120px"><span class="control-label">应用:</span></td>
				<td width="380px">
					<select id="appSel" name="application" onchange="appSelChange(this)" class="input-select" style="width:100%;">
						<option>请选择...</option>
						<c:forEach var="app" items="${applicationList }">
							<option value="${app }" <c:if test="${applicationChoosed == app}">selected="selected"</c:if>>${app }</option>
						</c:forEach>
					</select>
				</td>
				<td></td>
			</tr>
			<tr style="display:none;">
				<td><span class="control-label">发送给:</span></td>
				<td colspan="2">
					<input type="radio" id="broadcast-all" name="broadcast" value="ALL_USER" checked="checked" style="vertical-align: middle;" onclick="hideUsername()" />
					<label for="broadcast-all" style="vertical-align: middle;">所有用户</label>
					<input type="radio" id="broadcast-online" name="broadcast" value="ONLINE_USER" style="margin-left: 25px;vertical-align: middle;" onclick="hideUsername()" />
					<label for="broadcast-online" style="vertical-align: middle;">在线用户</label>
			        <input type="radio" id="broadcast-user" name="broadcast" value="SPECIFIED_USER" style="margin-left: 25px;vertical-align: middle;" onclick="showUsername()" />
			        <label for="broadcast-user" style="vertical-align: middle;">特定用户</label>
				</td>
			</tr>
			<tr id="trUsername" style="display:none;">
				<td><span class="control-label">用户名:</span></td>
				<td>
					<input type="text" id="username" name="username" value="" style="width:100%;" class="input-txt" datatype="uname" nullmsg="必填项！" autocomplete="off" /><br />
					<span class="instruction">注: 多个用户名;分割</span>
				</td>
				<td><span class="Validform_checktip"></span></td>
			</tr>
			<tr style="display:none;">
				<td><span class="control-label">标题:</span></td>
				<td><input type="text" id="title" name="title" class="input-txt" style="width:100%;" datatype="*" nullmsg="必填项！" maxlength="100" autocomplete="off" /></td>
				<td><span class="Validform_checktip">100字以内</span></td>
			</tr>
			<tr style="display:none;">
				<td valign="top"><span class="control-label">消息内容:</span></td>
				<td><textarea id="message" name="message" class="input-txt" style="width:100%; height:80px;resize:none;" datatype="*" nullmsg="必填项！" maxlength="1000" autocomplete="off"></textarea></td>
				<td><span class="Validform_checktip">1000字以内</span></td>
			</tr>
			<tr style="display:none;">
				<td><span class="control-label">通知后续行为:</span></td>
				<td>
					<input type="text" id="uri" name="uri" value="" class="input-txt" style="width:100%;" maxlength="200" autocomplete="off" />
				    <br/><span class="instruction">例) http://www.dokdocorea.com, geo:37.24,131.86, tel:111-222-3333</span>
				</td>
				<td><span class="Validform_checktip">200字以内</span></td>
			</tr>
			<tr style="display:none;">
				<td>&nbsp;</td>
				<td colspan="2"><input type="submit" value="发送" class="send" /></td>
			</tr>
		</table> 
	</form>
</div>

<script type="text/javascript" src="<c:url value='/scripts/Validform_v5.3.2_min.js'/>"></script>
<script type="text/javascript"> 
//<![CDATA[
$(function() {
	$('#broadcast-all').click();		
	$("#appSel").change();
	
	$.Tipmsg.r = "Validated";
	$("#form").Validform({
		tiptype: 2,
		showAllError: true,
		datatype: {
			uname: function(value) {
				if ($('input[name=broadcast]:checked').val() == "SPECIFIED_USER" && value.trim() == '') {
					return false;
				}
				return true;
			}
		}
	});
});

function showUsername() {
	$('#trUsername').show();
}

function hideUsername() {
	$('#trUsername').hide();
}
 
function appSelChange(ele) {
	if (ele.selectedIndex > 0) {
		setSelItemsVisibility(true, ele);
	} else {
		setSelItemsVisibility(false, ele);
	}
}

function setSelItemsVisibility(visible, ele) {
	var $items = $(ele).closest("tr").nextAll(); 
	if (visible) {
		$items.filter(":not(#trUsername)").show();
		if ($('input[name=broadcast]:checked').val() == "SPECIFIED_USER") {
			$items.filter("#trUsername").show();
		}
	} else {
		$items.hide();
	}
}
//]]>
</script>

</body>
</html>
