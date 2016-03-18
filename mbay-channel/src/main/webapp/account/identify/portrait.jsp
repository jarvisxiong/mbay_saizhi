<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include  file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>头像设置</title>
<link rel="stylesheet" href="${actx}/css/jcrop/jquery.Jcrop.css" type="text/css" />
<link rel="stylesheet" href="${actx}/css/account/portrait.css" type="text/css" />
<script type="text/javascript" src="${actx}/js/jcrop/jquery.Jcrop.js"></script>
<script type="text/javascript" src="${actx}/js/account/portrait.js"></script>
<script type="text/javascript" src="${actx}/js/lib/Validform_v5.3.2_min.js"></script>
</head>
<body>
<div class='con'>
	<div class='body clearfix'>
		<div class='b_con com_width clearfix'>
			<div class='left_con'>
				<div class='hdlb'>头像设置</div>
				<%@ include file="/common/leftcon.jsp"%>
				<div class='hd_con_r fr'>
					<h4>自定义头像</h4>
					<div class="portrait_body">
						<form id="portraitForm" action="<c:url value='/certificate/portrait.mbay'/>" method="post" enctype="multipart/form-data">
							<m:token/>
							<input type="hidden" id="x" name="x" />
							<input type="hidden" id="y" name="y" />
							<input type="hidden" id="w" name="w" />
							<input type="hidden" id="h" name="h" />
							<input type="hidden" id="width" name="width" />
							<input type="hidden" id="height" name="height" />
							<div class="prevrew_content">
								<input class="filePrew" type="file" id="portraitFile" name="portraitFile" datatype="*,suffix" errormsg="请上传正确格式图片,支持jpg、png格式"/>
								<div class="preview_div">
									<img id="preview"/>
								</div>
								<input type="submit" value="保存头像" class="btn_confirm" style="display:none;"/>
								<input type="button" value="保存头像" class="btn_default" />
							</div>		
							<div id="preview-pane">
								<h4>头像预览:</h4>
								<div class="preview-container">
									<c:if test="${portrait eq ''}">
										<img id="preview_short" src="${actx}/images/jcrop/portrait_default.jpg" alt="Preview" />
									</c:if>
									<c:if test="${portrait ne ''}">
										<img id="preview_short" src="${portrait}" alt="Preview" />
									</c:if>
							    </div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>