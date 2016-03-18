<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>商城</title>
<t:assets/>
<link href="${actx}/wro/${version}/mall_list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<%-- <script type="text/javascript" src="${actx}/js/index/bootstrap.js"></script> --%>
<script type="text/javascript" src="${actx}/wro/${version}/mall_list.js"></script>
</head>
<body>
<section class="activity-content">
	<div class="activity-title">
		<h3>活动与兑换项</h3>
	</div>

    <form action="<c:url value="/mall/exchangeItem/list.mbay"/>" method="post"  class="activity-form">
    <article style="height: 45px;">
	    <select class="genre" name="type">
	        <option value="">商品类型</option>
			<option value="ENTITY" <c:if test="${qo.type eq 'ENTITY'}">selected="selected"</c:if>>实物</option>
			<option value="COUPON" <c:if test="${qo.type eq 'COUPON'}">selected="selected"</c:if>>优惠券</option>
	    </select>        
        <select class="status" name="status">
            <option value="">状态</option>
			<option value="ON" <c:if test="${qo.status eq 'ON'}">selected="selected"</c:if>>上架</option>
			<option value="OFF" <c:if test="${qo.status eq 'OFF'}">selected="selected"</c:if>>下架</option>
        </select>
	    <input class="filtrate-input" type="text" value="${qo.title}" name="title" placeholder="兑换项标题" id="mall-exchangeItem-input"/>
		<input class="filtrate-button" type="submit" value="筛选" />
        <div class="filtrate-button-add">
        	<i class="filtrate-button-add-ico"></i>
			<span class="filtrate-button-add-txt">添加兑换项</span>
        </div>
        <div class="convertibility">
			<p>
				<a href="<c:url value='/mall/exchangeItem/toExchangeItemAdd.mbay?type=COUPON'/>">新增优惠券</a>
			</p>
			<p>
				<a href="<c:url value='/mall/exchangeItem/toExchangeItemAdd.mbay?type=ENTITY'/>">新增实物</a>
			</p>
		</div>
	</article>
	    <table class="table">
		    <thead>
		        <tr class="table-th">
		        	<th class="option"></th>
		            <th class="title">标题</th>
		            <th class="market">商城</th>
		            <th class="status">状态</th>
		            <th class="genre">库存</th>
		            <th class="handle">操作</th>
		        </tr>
		    </thead>
		    <c:forEach items="${list}" var="bean" varStatus="vs">
		    <thead class="table-th2">
		    	<!-- 审核通过 -->
		    	<c:if test="${bean.audit eq 'NON' || bean.audit eq 'COMPLETE'}">
				<tr class="piece">
			    	<td class="td-check">
			    		<input type="checkbox" id="check-box-${vs.index}" class="regular-checkbox big-checkbox" name="select" value="${bean.itemNumber}" />
			    		<label for="check-box-${vs.index}"></label>
			    	</td>
			    	<td class="title-td">
			            <img class="preview-img" index="${vs.index}_img" alt="${bean.title}" src="<c:url value='/mall/exchangeItem/getThumbnail.mbay?itemNumber=${bean.itemNumber}'/>" />
			            <div class="actual-img" id="${vs.index}_img"><img src="<c:url value='/mall/exchangeItem/getThumbnail.mbay?itemNumber=${bean.itemNumber}'/>" /></div>
			            <div class="product-item">
				            <h2 title="${bean.title}">${bean.title}</h2>
				            <p><span>${bean.mbay}MB</span></p>
			        	</div>
		      		</td>
					<td class="product">${bean.mall.name}</td>
					<td>${bean.status.value}</td>
					<td>
						<c:if test="${bean.type eq 'COUPON'}">
							<c:if test="${bean.remainder eq 0}">
								<a href="<c:url value='/mall/exchangeItem/couponTicketList.mbay?itemNumber=${bean.itemNumber}'/>">缺货</a>
							</c:if>
							<c:if test="${bean.remainder ne 0}">
								<a href="<c:url value='/mall/exchangeItem/couponTicketList.mbay?itemNumber=${bean.itemNumber}'/>">${bean.remainder}</a>
							</c:if>
							<a href="<c:url value='/mall/exchangeItem/toCouponTicketAdd.mbay?itemNumber=${bean.itemNumber}'/>" title="添加券码">
								<i class="filtrate-ico"></i>
							</a>
						</c:if>
						<c:if test="${bean.type eq 'ENTITY'}">
							${bean.remainder}
						</c:if>
					</td>
			        <td>
			        	<div class="hint-txt">
			        		<c:if test="${bean.status eq 'OFF'}">
								<div id="${vs.index}_on" class="send sold">上架<div class="arrow"></div></div> 
							</c:if>
							<c:if test="${bean.status eq 'ON'}">
								<div id="${vs.index}_off" class="send sold">下架<div class="arrow"></div></div> 
							</c:if>
			        		<div id="${vs.index}_edit" class="send compile">编辑<div class="arrow"></div></div> 
			        		<div id="${vs.index}_preview" class="send preview">预览<div class="arrow"></div></div> 
			        		<div id="${vs.index}_del" class="send delete">删除<div class="arrow"></div></div> 
			        	</div>
			        	<div class="hint-ico">
			        		<c:if test="${bean.status eq 'OFF'}">
				        		<a index="${vs.index}_on" href="javascript:changeStatus('${bean.itemNumber}','ON')" class="handle-ico sold" data-original-title="上架" data-placement="top" data-toggle="tooltip"></a>
				            </c:if>
				            <c:if test="${bean.status eq 'ON'}">
				        		<a index="${vs.index}_off" href="javascript:changeStatus('${bean.itemNumber}','OFF')" class="handle-ico sold" data-original-title="下架" data-placement="top" data-toggle="tooltip"></a>
				            </c:if>
				            <a index="${vs.index}_edit" href="<c:url value='/mall/exchangeItem/toExchangeItemUpdate.mbay?itemNumber=${bean.itemNumber}'/>" class="handle-ico compile" data-original-title="编辑" data-placement="top" data-toggle="tooltip"></a>
				            <a index="${vs.index}_preview" href="javascript:preview('${bean.itemNumber}','${mobileDomain}');" class="handle-ico preview" data-original-title="预览" data-placement="top" data-toggle="tooltip"></a>
				            <a index="${vs.index}_del" href="javascript:del('${bean.itemNumber}')" class="handle-ico delete" data-original-title="删除" data-placement="top" data-toggle="tooltip"></a>
			        	</div>			            
			        </td>
			    </tr>
			    </c:if>
			    <!-- 审核中 -->
			    <c:if test="${bean.audit ne 'NON' && bean.audit ne 'COMPLETE'}">
			    <tr style="background: #F2F2F2;">
			    	<td class="td-check">
			    		<input type="checkbox" id="check-box-${vs.index}" name="auditing" class="regular-checkbox big-checkbox" />
			    		<label for="check-box-${vs.index}"></label>
			    	</td>
			    	<td class="title-td">
			            <img class="preview-img" index="${vs.index}_img" alt="${bean.title}" src="<c:url value='/mall/exchangeItem/getThumbnail.mbay?itemNumber=${bean.itemNumber}'/>" />
			            <div class="actual-img" id="${vs.index}_img"><img src="<c:url value='/mall/exchangeItem/getThumbnail.mbay?itemNumber=${bean.itemNumber}'/>" /></div>
			            <div class="product-item">
				            <h2 title="${bean.title}">${bean.title}</h2>
				            <p><span>${bean.mbay}MB</span></p>
			        	</div>
		      		</td>
			    	<td class="product">${bean.mall.name}</td>
					<td>${bean.audit.value}</td>
					<td>
						<c:if test="${bean.type eq 'COUPON'}">
							<c:if test="${bean.remainder eq 0}">
								缺货
							</c:if>
							<c:if test="${bean.remainder ne 0}">
								${bean.remainder}
							</c:if>
							<a href="javascript:showMessage()" title="添加券码">
								<i class="filtrate-ico"></i>
							</a>
						</c:if>
						<c:if test="${bean.type eq 'ENTITY'}">
							${bean.remainder}
						</c:if>
					</td>
			    	<td>
			    		<div class="hint-txt">
			        		<c:if test="${bean.status eq 'OFF'}">
								<div id="${vs.index}_on" class="send sold">上架<div class="arrow"></div></div> 
							</c:if>
							<c:if test="${bean.status eq 'ON'}">
								<div id="${vs.index}_off" class="send sold">下架<div class="arrow"></div></div> 
							</c:if>
			        		<div id="${vs.index}_edit" class="send compile">编辑<div class="arrow"></div></div> 
			        		<div id="${vs.index}_preview" class="send preview">预览<div class="arrow"></div></div> 
			        		<div id="${vs.index}_del" class="send delete">删除<div class="arrow"></div></div> 
			        	</div>
			        	<div class="hint-ico">
			        		<c:if test="${bean.status eq 'OFF'}">
				        		<a index="${vs.index}_on" href="javascript:showMessage()" class="handle-ico sold" data-original-title="上架" data-placement="top" data-toggle="tooltip"></a>
				            </c:if>
				            <c:if test="${bean.status eq 'ON'}">
				        		<a index="${vs.index}_off" href="javascript:showMessage()" class="handle-ico sold" data-original-title="下架" data-placement="top" data-toggle="tooltip"></a>
				            </c:if>
				            <a index="${vs.index}_edit" href="javascript:showMessage()" class="handle-ico compile" data-original-title="编辑" data-placement="top" data-toggle="tooltip"></a>
				            <a index="${vs.index}_preview" href="javascript:preview('${bean.itemNumber}','${mobileDomain}');" class="handle-ico preview" data-original-title="预览" data-placement="top" data-toggle="tooltip"></a>
				            <a index="${vs.index}_del" href="javascript:showMessage()" class="handle-ico delete" data-original-title="删除" data-placement="top" data-toggle="tooltip"></a>
			        	</div>
			    	</td>
			    </tr>
			    </c:if>
			</thead>
		    </c:forEach> 
		</table>
		<div class="page">
			<span class="page-ico"></span>
			<div class="homepage">
				<span class="homepage-check">
					<input type="checkbox" id="checkAll" class="regular-checkbox big-checkbox" />
					<label for="checkAll"></label>
				</span>
				<span class="homepage-txt">本页</span>
			</div>
			<div class="batch-item">
				<p>
					<span class="putaway-btn"><a href='javascript:void(0);' id='shelfAll'>批量上架</a></span>
					<span class="batch-btn"><a href='javascript:void(0);' id='delAll'>批量删除</a></span>
					<span class="curve"><a>当前选中<i class="num">0</i>条</a></span>
				</p>
			</div>
			<div class="batch-item" style="clear: both;">
				<m:page pageinfo="${pageinfo}" />
			</div>
		</div>
	</form>
	
	<!-- 手机预览 -->
	<div id="preview-modal" style="display: none;">
          <div class="modal-section">
				<div class="qr-code">
					<img id="qr-code-img" src="">
					<div class="right">
						<p style="background:#fff;line-height: 60px;border-bottom:solid #e9e9e9 1px;">手机扫左边二维码预览。</p>
						<p style="background:#fff;line-height: 60px;border-bottom:solid #e9e9e9 1px;">你也可以打开<a id="qr-code-a" target="_blank" href="">新窗口预览</a></p>
					</div>
				</div>
          </div>
	</div>
</section>
</body>
</html>