<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑${bean.type.value}</title>
<link href="${actx}/wro/${version}/mall_add.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="${actx}/wro/${version}/mall_add.js"></script>
<script type="text/javascript" src="${actx}/js/mall/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${actx}/js/mall/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${actx}/js/mall/jquery.fileupload.js"></script>
<script>
$(function(){
	showOrHide();
});
</script>
</head>
<body>
<section id="main-content" class="clearfix">
	<div class="config_lottery">
    	<h2>
    		编辑${bean.type.value}
    		<c:if test="${not empty message}"><span class="message-box">*${message}</span></c:if>
    		<a href="<c:url value='/mall/exchangeItem/list.mbay'/>" class="now-href"><span>&gt;</span>我的兑换项</a>
    	</h2>
        <!-- form开始 -->
        <form id="exchangeItemForm" class="form-horizontal" action="<c:url value="/mall/exchangeItem/updateExchangeItem.mbay"/>" enctype="multipart/form-data" method="post">
        <input type="hidden" name="startTime" value="${startHour}:${startMinute}"/>
		<input type="hidden" name="endTime" value="${endHour}:${endMinute}"/>
		<input type="hidden" name="itemNumber" value="${bean.itemNumber}"/>
		<input type="hidden" name="type" value="${bean.type}" />
		<input type="hidden" name="delTwo" value="false"/>
		<input type="hidden" name="delThree" value="false"/>
		<input type="hidden" name="delFour" value="false"/>
		<input type="hidden" name="delFive" value="false" />
		<input type="hidden" name="hide" value="${bean.hide}"/>
		<input type="hidden" name="extendLimit" value="${bean.extendLimit}"/>
		<m:token />
        <div class="form-head">
        <h3>展示信息</h3>
        <div class="form-group">
	        <div class="form-combination">
		        <select class="form-control" name="mall.id" datatype="*">
		            <c:forEach items="${relationList}" var="relation">
						<option value="${relation.mall.id}" <c:if test="${bean.mall.id eq relation.mall.id}">selected="selected"</c:if>>${relation.mall.name}</option>
					</c:forEach>
		        </select>
	        </div>
        </div>
    </div>
    <!-- 展示信息 -->
    <div class="lotter_info_content pt20" style="padding-top:0;">
    <dl>
    	<dt>商品标题：</dt>
        <dd>
        	<div class="port_input">
            	<input type="text" autocomplete="off" value="${bean.title}" name="title" datatype="s1-8" errormsg="文字太长，建议8个字符以内"
            		placeholder="MB商城的商品展示名称，建议8个字以内" maxlength="8"/>
            </div>
            <p class="gray8 pt5">前台展示给用户的主标题</p>
        </dd>
     </dl>
     <dl>
     	<dt>说明文字：</dt>
        <dd>
        	<div class="port_input">
            	<input type="text" autocomplete="off" value="${bean.description}" name="description" datatype="s1-16" errormsg="文字太长，建议16个字符以内"
            		placeholder="MB商城列表页的商品附加说明，建议16个字以内" maxlength="16"/>
            </div>
            <p class="gray8 pt5">主标题下的附属文字</p>  
        </dd>
     </dl>
     <dl>
     	<dt>活动规则：</dt>
        <dd>
        	<div class="port_input">
            	<input type="text" autocomplete="off" value="${bean.ruleName}" name="ruleName" datatype="s1-8" errormsg="文字太长，建议8个字符以内"
            		placeholder="活动规则" maxlength="8"/>
            </div>
            <input style="margin-top:10px;" type="text" autocomplete="off" value="${bean.ruleContent}" name="ruleContent" datatype="s1-16" errormsg="文字太长，建议16个字符以内"
            	placeholder="这里加入活动规则" maxlength="16"/>
        </dd>
     </dl>
     <dl>
     	<dt>详细说明：</dt>
        <dd>
        	<div class="port_input">
            	<input type="text" autocomplete="off" value="${bean.detailName}" name="detailName" datatype="s1-8" errormsg="文字太长，建议8个字符以内"
            		placeholder="详细说明" maxlength="8"/>
            </div>
            <input style="margin-top:10px;" type="text" autocomplete="off" value="${bean.detailContent}" name="detailContent" datatype="s1-16" errormsg="文字太长，建议16个字符以内"
            	placeholder="这里加入详细说明" maxlength="16"/>
        </dd>
     </dl>
     <c:if test="${bean.type eq 'COUPON'}">
     <dl style="margin-left: -27px;">
     	<dt>兑换成功文案：</dt>
     	<dd>
     		<div class="port_input">
            	<textarea placeholder='请输入该商品兑换成功后的说明,建议200个字符以内' name="successDescription" class='text-area' 
			 		ignore="ignore" datatype="s1-200" maxlength="200" >${bean.successDescription}</textarea>
            </div>
     	</dd>
     </dl>
	 </c:if>
     <dl>
     <dt>商品图片：</dt>
     	<dd>
        	<div class="upload-Tab">
            	<div class="tab-header">
            		<ul>
                    	<li class="active tooltips" tabname='largeImgTab' >
                        	<span class="redec1d23">*</span>详情图
                        </li>
                        <li class="tooltips" tabname='smallImgTab'>
                        	<span class="redec1d23">*</span>缩略图
                        </li>
                        <li class="tooltips" tabname='logoTab'>
                            <span class="redec1d23">*</span>图标
                        </li>
                        <li class="tooltips" tabname='bannerTab'>首页Banner</li>
                    </ul>
                </div>
                <!-- 详情图 -->
                <div class="upload-content clearfix" id="largeImgTab" style="display:block">
	            	<p class="annotation gray">
	                	<span class="orange">注</span>：详情图为必填选项，可以上传单张多张。尺寸为640*300像素，格式为jpg、png、jpeg
	                </p>
	                <div class="Img-box largeImg-group clearfix">
	                	<!-- 详情图1 -->
		                <div class="img-cotent" id="divOne" style="display:block;">
		                	<div class="img-cont">
		                    	<img id="imageOne" onclick="detailOne.click();" width="148" height="69" src="${details[0].picture}" class="largeImg"/>
		                	</div>
                        	<input class="hiden" type="file" accept="image/jpg,image/png,image/jpeg"
                        		id="detailOne" name="detailOne" ignore="ignore" datatype="suffix" errormsg="类型不符，请重新上传" onchange="showImage('detailOne','imageOne','divTwo','spanOne');"/>
                    		<span class="colse-img" id="spanOne" onclick="removeImage('detailOne','imageOne','divTwo','spanOne')" style="display:inline-block;"><img src="${actx}/images/mall/close.png" /></span>
		                </div>
		                <!-- 详情图2 -->
		                <div class="img-cotent" id="divTwo" <c:if test="${not empty details[0]}">style="display:block;"</c:if>>
		                	<div class="img-cont">
		                		<c:if test="${not empty details[1]}">
									<img id="imageTwo" onclick="detailTwo.click();" width="148" height="69" src="${details[1].picture}" class="largeImg"/>
								</c:if>
								<c:if test="${empty details[1]}">
									<img id="imageTwo" onclick="detailTwo.click();" width="148" height="69" src="${actx}/images/mall/default_img_3.jpg" class="largeImg"/>
								</c:if>
		                	</div>
                        	<input class="hiden" type="file" accept="image/jpg,image/png,image/jpeg"
                        		id="detailTwo" name="detailTwo" ignore="ignore" datatype="suffix" errormsg="类型不符，请重新上传" onchange="showImage('detailTwo','imageTwo','divThree','spanTwo');"/>
                    		<span class="colse-img" id="spanTwo" onclick="removeImage('detailTwo','imageTwo','divThree','spanTwo');delPicture('delTwo');" <c:if test="${not empty details[1]}">style="display:inline-block;"</c:if>><img src="${actx}/images/mall/close.png" /></span>
		                </div>
		                <!-- 详情图3 -->
		                <div class="img-cotent" id="divThree" <c:if test="${not empty details[1]}">style="display:block;"</c:if>>
		                	<div class="img-cont">
		                		<c:if test="${not empty details[2]}">
									<img id="imageThree" onclick="detailThree.click();" width="148" height="69" src="${details[2].picture}" class="largeImg"/>
								</c:if>
								<c:if test="${empty details[2]}">
									<img id="imageThree" onclick="detailThree.click();" width="148" height="69" src="${actx}/images/mall/default_img_3.jpg" class="largeImg"/>
								</c:if>
		                	</div>
                        	<input class="hiden" type="file" accept="image/jpg,image/png,image/jpeg"
                        		id="detailThree" name="detailThree" ignore="ignore" datatype="suffix" errormsg="类型不符，请重新上传" onchange="showImage('detailThree','imageThree','divFour','spanThree');"/>
                    		<span class="colse-img" id="spanThree" onclick="removeImage('detailThree','imageThree','divFour','spanThree');delPicture('delThree');" <c:if test="${not empty details[2]}">style="display:inline-block;"</c:if>><img src="${actx}/images/mall/close.png" /></span>
		                </div>
		                <!-- 详情图4 -->
		                <div class="img-cotent" id="divFour" <c:if test="${not empty details[2]}">style="display:block;"</c:if>>
		                	<div class="img-cont">
		                		<c:if test="${not empty details[3]}">
									<img id="imageFour" onclick="detailFour.click();" width="148" height="69" src="${details[3].picture}" class="largeImg"/>
								</c:if>
								<c:if test="${empty details[3]}">
									<img id="imageFour" onclick="detailFour.click();" width="148" height="69" src="${actx}/images/mall/default_img_3.jpg" class="largeImg"/>
								</c:if>
		                	</div>
                        	<input class="hiden" type="file" accept="image/jpg,image/png,image/jpeg"
                        		id="detailFour" name="detailFour" ignore="ignore" datatype="suffix" errormsg="类型不符，请重新上传" onchange="showImage('detailFour','imageFour','divFive','spanFour');"/>
                    		<span class="colse-img" id="spanFour" onclick="removeImage('detailFour','imageFour','divFive','spanFour');delPicture('delFour');" <c:if test="${not empty details[3]}">style="display:inline-block;"</c:if>><img src="${actx}/images/mall/close.png" /></span>
		                </div>
		                <!-- 详情图5 -->
		                <div class="img-cotent" id="divFive" <c:if test="${not empty details[3]}">style="display:block;"</c:if>>
		                	<div class="img-cont">
		                		<c:if test="${not empty details[4]}">
									<img id="imageFive" onclick="detailFive.click();" width="148" height="69" src="${details[4].picture}" class="largeImg"/>
								</c:if>
								<c:if test="${empty details[4]}">
									<img id="imageFive" onclick="detailFive.click();" width="148" height="69" src="${actx}/images/mall/default_img_3.jpg" class="largeImg"/>
								</c:if>
		                	</div>
                        	<input class="hiden" type="file" accept="image/jpg,image/png,image/jpeg"
                        		id="detailFive" name="detailFive" ignore="ignore" datatype="suffix" errormsg="类型不符，请重新上传" onchange="showImage('detailFive','imageFive','','spanFive');"/>
                    		<span class="colse-img" id="spanFive" onclick="removeImage('detailFive','imageFive','','spanFive');delPicture('delFive');" <c:if test="${not empty details[4]}">style="display:inline-block;"</c:if>><img src="${actx}/images/mall/close.png" /></span>
		                </div>
                    </div>
                </div>
                <!-- 缩略图 -->
                <div class="upload-content" id="smallImgTab" style="display: none;">
                	<p class="annotation gray">
                		<span class="orange">注</span>：缩略图为必填选项。
                	</p>
                    <div class="but-info">
	                    <div class="fl mr20 narrow_img">
	                        <img id="imageThumbnail" width="160" height="100" src="${thumbnail.picture}" class="smallImg"/>
	                    </div>
                        <div class="info fl pt10">
                        	<div class="gray">尺寸为225*140像素,格式为jpg、png、jpeg。</div>
                            <a href="javascript:void(0);" class="button-upload mr18 smallImgBtn" onclick="thumbnail.click()">选择上传</a>
                            <input class="hiden" type="file" accept="image/jpg,image/png,image/jpeg"
                            	id="thumbnail" name="thumbnail" datatype="*,suffix" errormsg="类型不符，请重新上传" onchange="showImage('thumbnail','imageThumbnail');"/>
                        </div>
                    </div>
                 </div>
                 <!-- 图标 -->
                 <div class="upload-content" style="display: none;" id="logoTab">
                 	<p class="annotation gray">
                 		<span class="orange">注</span>：图标为必填项。用于呈现品牌信息的图标，兑换记录等页面。
                 	</p>
                    <div class="but-info">
                    	<div class="fl mr20 narrow_img">
                        	<img id="imageIcon" width="100" height="100" src="${icon.picture}"/>
                        </div>
                        <div class="info fl pt10">
                            <div class="gray">尺寸为100*100像素，格式为jpg、png、jpeg</div>
                            <a href="javascript:void(0);" class="button-upload mr18 logoBtn" onclick="icon.click()">选择上传</a>
                            <input class="hiden" type="file" accept="image/jpg,image/png,image/jpeg"
                            	id="icon" name="icon" datatype="*,suffix" errormsg="类型不符，请重新上传" onchange="showImage('icon','imageIcon');"/>
                        </div>
                  	</div>
                  </div>
                  <!-- 首页banner -->
                  <div class="upload-content" style="display: none;" id="bannerTab">
                  	<p class="annotation gray">
                  		<span class="orange">注</span>：展示在首页Banner区的图片
                  	</p>
                  	<div class="but-info">
                        <div class="fl mr20 narrow_img">
                        	<c:if test="${not empty banner}">
								<img id="imageBanner" width="229" height="100" src="${banner.picture}" class="banner"/>
							</c:if>
							<c:if test="${empty banner}">
								<img id="imageBanner" width="229" height="100" src="${actx}/images/mall/default_img_2.jpg" class="banner"/>
							</c:if>
                        </div>
                        <div class="info fl pt10">
                            <div class="gray">尺寸为640*280像素，格式为jpg、png、jpeg</div>
                            <a href="javascript:;" class="button-upload mr18 bannerBtn" onclick="banner.click()">选择上传</a>
                            <input class="hiden" type="file" accept="image/jpg,image/png,image/jpeg"
                            	id="banner" name="banner" datatype="*,suffix" errormsg="类型不符，请重新上传" onchange="showImage('banner','imageBanner');"/>
                        </div>
                    </div>
                  </div>
              </div>
          </dd>
        </dl>
        <dl>
	    	<dt>兑换项区隐藏：</dt>
	        <dd>
	        	<div class="port_inpit clearfix">
                    <p class="switch-prompt">涉及商品在已添加到图标或Banner图区后，是否需要在首页兑换区隐藏</p>
               		<div class="slider-box off">
						<div class="slider"></div>
						<span class="m">是</span> <span class="w">否</span> 
						<input type="checkbox" name="hide_checkbox" <c:if test="${bean.hide eq 'ENABLED'}">checked="checked"</c:if>/>
					</div>
               </div>
	        </dd>
	    </dl>
     	</div>
        <!-- 活动规则 -->
        <h4 style="margin-top:0">兑换规则</h4>
        <div class="line-bj" style="border-bottom:1px dashed #e9e9e9;"></div>
        <div class="lotter_info_content pt20  form-group" style="padding-top:20px;">
	        <dl>
	             <dt>市面价值：</dt>
	             <dd>
	                 <div class="port_input">
	                     <input type="text" autocomplete="off" value="${bean.price}" name="price" datatype="n1-6" errormsg="最多输入6位数"
	                     	placeholder="该商品的市面售价，用作展示">
	                     <span class="unit">元</span>
	                 </div>
	             </dd>
	         </dl>
	         <dl>
	             <dt>需要 M B：</dt>
	             <dd>
	                 <div class="port_input">
	                     <input type="text" min="1" autocomplete="off" value="${bean.mbay}" name="mbay" datatype="zs1-8" errormsg="最多输入8位数(不包括0)"
	                     	placeholder="兑换该商品所需要的消耗MB">
	                     <span class="unit" style="right: 112px;">M</span>
	                 </div>
	             </dd>
	         </dl>
	         <c:if test="${bean.type eq 'ENTITY'}">
	         <dl>
	         	<dt>剩余库存：</dt>
	         	<dd>
	         		<div class="port_input">
	                     <input type="text" min="1" autocomplete="off" value="${bean.remainder}" name="remainder" datatype="zs1-8" errormsg="最多输入8位数(不包括0)"
	                     	placeholder="该商品库存数">
	                     <span class="unit">件</span>
	                 </div>
	         	</dd>
	         </dl>
			 </c:if>
             <dl>
                 <dt>兑换限制：</dt>
                 <dd>
                     <div class="form-combination input-select" style="height:37.6px;width:590px">
                         <div class="port_input">
                             <input class="form-control1" type="text" autocomplete="off" value="${bean.userLimit}" name="userLimit" ignore="ignore" datatype="n1-6" errormsg="最多输入6位数"
                             	placeholder="每个用户最多可兑换的次数，不填则不做限制" style="width:504px;border-radius:5px 0 0 5px;position:absolute;">
                         </div>
                         <select class="form-control control1" name="userLimitType" datatype="*" 
                         	style="border-radius:0 5px 5px 0; height:35.5px;float:right;  width: 75px; margin:0; padding:0;">
                         	<option value="PERMANENT" <c:if test="${bean.userLimitType eq 'PERMANENT'}">selected="selected"</c:if>>永久</option>
							<option value="EVERYDAY" <c:if test="${bean.userLimitType eq 'EVERYDAY'}">selected="selected"</c:if>>每天</option>
                         </select>
                     </div>

                 </dd>
             </dl>
             <dl class="ml27">
                 <dt>额外兑换限制：</dt>
                 <dd>
                     <div class="port_inpit clearfix">
                         <p class="switch-prompt">涉及商品每日限额、可兑换时间设置</p>
                         <div class="slider-box off">
							<div class="slider"></div>
							<span class="m">是</span> <span class="w">否</span> 
							<input type="checkbox" name="extendLimit_checkbox" onchange="showOrHide();" <c:if test="${bean.extendLimit eq 'ENABLED'}">checked="checked"</c:if>/>
						 </div>
                     </div>
                 </dd>
             </dl>
        	 <!-- 额外兑换限制 -->
             <dl class="pb0 object_closest clearfix ml69" style="height: auto; display: table;">
             	<dt>&nbsp;</dt>
             	<dd class="object_inside" style="display:none;">
                     <dl class="clearfix w822 b-d-gray">
                     	<dt>每日兑换限制：</dt>
                   		<dd>
                           <div class="inputlist port_input">
                                <input type="text" class="w463" value="${limit.dayLimit}" name="dayLimit" ignore="ignore" datatype="n1-6" errormsg="最多输入6位数"
                                	placeholder="每天最多可被兑换的次数，不填则不做限制"/>
                                <span class="unit" style="left: 434px;">次</span>
                           </div>
                           <p class="gray8 line32">您是否希望每天限额发放此兑换项，并带上限量角标</p>
                       	</dd>
                     </dl>
                     <dl class="clearfix  w822 pt20  data_select" style="padding-top:20px;padding-left:0;border-top:1px dashed #ccc;">
                     	<dt>每日兑换限制：</dt>
                        <dd>
	                        <div class="inputlist port_input">
		                        <select id="startHour" class="null">
									<option value="">时</option>
									<c:forEach items="00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23" var="hour" >
										<option value="${hour}" <c:if test="${startHour eq hour}">selected="selected"</c:if>>${hour}</option>
									</c:forEach>
								</select>
		                        :
		                        <select id="startMinute" class='null'>
							 		<option value="">分</option>
						  			<c:forEach items="00,10,20,30,40,50,59" var="minute" >
						   				<option value="${minute}" <c:if test="${startMinute eq minute}">selected="selected"</c:if>>${minute}</option>
							  		</c:forEach>
							 	</select>
		                                                              至
		                        <select id="endHour" class='null'>
							 		<option value="">时</option>
						 			<c:forEach items="00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23" var="hour" >
						   				<option value="${hour}" <c:if test="${endHour eq hour}">selected="selected"</c:if>>${hour}</option>
						 			</c:forEach>
							 	</select>
		                        :
		                        <select id="endMinute" class='select_time'>
							 		<option value="">分</option>
							  		<c:forEach items="00,10,20,30,40,50,59" var="minute" >
							   			<option value="${minute}" <c:if test="${endMinute eq minute}">selected="selected"</c:if>>${minute}</option>
							  		</c:forEach>
							 	</select>    
	                      	</div>
                            <p class="gray8 line32">配置之后，此兑换项只在每天的固定时间段可以兑换，并带上秒杀角标,不填则不做限制</p>
                          </dd>
                        </dl>
                        <span class="arrow"></span>
                        </dd>
                    </dl>
                    <dl class="b-t-gray pt20">
                        <dt style="margin-left:47px;">&nbsp;</dt>
                        <dd>
                        	<input type="button" class="button_sumbit" value="修改" onclick="submit_before()">                             
                        </dd>
                    </dl>
             </div>
          </form>
      </div>
</section>
</body>
</html>