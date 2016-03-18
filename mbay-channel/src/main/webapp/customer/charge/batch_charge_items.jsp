<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>充值信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="">
<link href="${actx}/wro/${version}/batch_charge_items.css"	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<c:url value="/requiered/jquery.js" />"></script>
</head>
<body>
	<div id="wrapper" class='con'>
	 <div class='body'>
          <div class='body_con com_width'>
              <div class='b_tit'>充值信息</div>
              <%@ include file="/common/leftcon.jsp" %> 
              <form id='bmf' action="<c:url value="/customerserver/batchcharge/record/charge_items.mbay"/>" method="post" class='clearfix fr'>
              <input type="hidden" name="batchchargeid" value="${chargeinfo.id}">
              <div class='fr' style='width:828px'>
              	  <!-- 
              	  <div class='cxhm'>
		              <span class='hm'>查询号码</span>
		              <input type='text' placeholder='请输入手机号' class='hm_txt' />
		              <input type='button' class='btn_1'  value='查询'/>
		          </div>
		           -->
	              <div class='tb'>
	                 <table>
	                      <tr>
	                      <th width="200px">时间</th>
	                      <th>数量</th>
	                      <th>操作</th>
	                      </tr>
	                      <c:forEach items="${chargeitems}" var="item">
		                      <tr>
		                      	<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"  value="${item.createtime}"/></td>
		                      	<td>${item.num}</td>
		                      	<td><a href="<c:url value='/traffic/recordByBatchChargeItem.mbay?itemid=${item.id}'/>" class='del'>查看</a></td>
		                      </tr>
	                      </c:forEach>
	                 </table>
	              </div>
	              <m:page pageinfo="${pageinfo}" formid="bmf"/>
              </div>
              </form>
        	</div>
        </div>
       </div>
</body>
</html>