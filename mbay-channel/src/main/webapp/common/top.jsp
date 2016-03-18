<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<link href="<c:url value="/images/favicon.ico"/>" rel="icon"
	type="image/x-icon" />
<c:if test="${sessionScope.channel.certStatus == 'UNAPPLY'}">
	<script type="text/javascript">
		$(function() {			
			$(".cert_intercept").click(function() {				
				$.messager.alert({
					title: "尚未实名认证!",
					content: "为了更好的保障您的账户交易安全，请完成实名认证后完成此操作！" + 
							"<a target='blank' href='<c:url value="/certificate/auth/id_auth_apply.mbay"/>'>立即认证</a>"
				});	
				return  false;
			});
		});
	</script>
</c:if>
<c:if test="${sessionScope.channel.certStatus == 'UNDERREVIEW'}">
	<script type="text/javascript">
		$(function() {			
			$(".cert_intercept").click(function() {				
				$.messager.alert({
					title: "实名认证审核中!",
					content: "您的实名认证信息正在审核中，请认证通过后完成此操作！"
				});	
				return  false;
			});
		});
	</script>
</c:if>
<c:if test="${sessionScope.channel.certStatus == 'UNPASSED'}">
	<script type="text/javascript">
		$(function() {			
			$(".cert_intercept").click(function() {				
				$.messager.alert({
					title: "实名认证不通过!",
					content: "抱歉！您的实名认证信息未通过审核，请查看原因并重新申请！"
				});	
				return  false;
			});
		});
	</script>
</c:if>
<script type="text/javascript">
	////绝对路径
	var absurl = "${pageContext.request.contextPath}";
	$(function() {
		var loginuser = $("#channel").html();
		if (loginuser != null && loginuser != '') {
			showmsgnum();
			setInterval(showmsgnum, 900000);
		}
	});
	
	function showmsgnum() {
		$.post(absurl + "/message/getMsgnum.mbay", function(data) { 
			var myreg = /^\d+$/;
		    if(!myreg.test(data)){
		      	window.location.href = absurl + "/channel/index.mbay";
		    }else if (data <= 0) {
				$("#msgnum").html("");
				$("#num").html("0");
				$(".znx").trigger("hoverOut");
			} else {
				$("#msgnum").html("(" + data + ")");
				$("#num").html(data);
				$(".znx").trigger("hoverIn");
			}		
		});
	}
</script>

<div class='head'>
	<div class='header com_width'>
		<div class='dianhua fl' style='width: auto;'>
			<div class='mb'></div>
			<div class='dh'>—商业营销最佳伴侣&nbsp;&nbsp;|&nbsp;&nbsp;市场热线：021-64186988</div>
		</div>
		<div class='reg fr'>
			<span style="display: none;" id="channel">${sessionScope.channel}</span>
			<c:choose>
				<c:when test="${sessionScope.channel!=null}">
					<span class='nh'>欢迎您:</span>
					<a href="<c:url value="/channel/workbench.mbay"/>"> <span
						class='user' title='${sessionScope.channel.username}'>
							${sessionScope.channel.username} </span>
					</a>
					<%-- &nbsp;&nbsp;|&nbsp;
					<a href='<c:url value="/account/transfer/payment/fill.mbay"/>'
						class='cert_intercept zz'><span>转账</span></a> --%>
					<span class='wx_con'> <span class='wx'>微信</span>
						<div class='wx_s'>
							<div class='wx_b'>
								<img src='<c:url value="/images/workimages/wx_b.jpg"/>' />
							</div>
							<b></b>
						</div>
					</span>
					<a href='<c:url value="/message/notice_list.mbay"/>' class='tip'>
						<span>公示栏<span id="noticenum"><c:if
									test="${not empty sessionScope.unread_notice_num and sessionScope.unread_notice_num > 0}">(${sessionScope.unread_notice_num})</c:if></span></span>
						<span class='sl'></span>
					</a>
					<a href='<c:url value="/message/msg_list.mbay"/>'
						class='cert_intercept znx'> <span>站内信<span id="msgnum"></span></span>
					</a>
					<a href='<c:url value="/channel/loginout.mbay"/>'>退出</a>
				</c:when>
				<c:otherwise>
					<a style="color: #6E716D;"
						href='<c:url value="/channel/index.mbay"/>'>登录</a>&nbsp;|<a
						style="color: #6E716D;" href='<c:url value="/signup/index.mbay"/>'>注册</a>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>

<div class='hr_30'></div>

