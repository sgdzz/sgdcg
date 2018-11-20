<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	if (request.getSession().getAttribute("loginUser") != null) {
		response.sendRedirect(request.getContextPath() + "/Home?action=index");
	}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>易买网</title>
    <link rel="shortcut icon" href="${ctx }/files/icos.png" />
<style>
.redFont {
	color: red;
	font-size: 16px;
}

#btn {
	border-radius: 10px;
	background: #FD5911;
	border: #FD5911;
	padding: 5px 10px;
	color: #fff;
	outline: none;
	cursor: pointer;
}
</style>
</head>
<body>
	<%@ include file="/common/pre/header.jsp"%>
	<!--Begin Login Begin-->
	<div class="log_bg">
		<div class="top">
			<div class="logo">
				<a href="${ctx}/Home?action=index"><img
					src="${ctx}/statics/images/logo.png"
				/></a>
			</div>
		</div>
		<div class="login">
			<div class="log_img">
				<img src="${ctx}/statics/images/l_img.png" width="611" height="425" />
			</div>
			<div class="log_c">
				<form>
					<table border="0"
						style="width: 370px; font-size: 14px; margin-top: 30px;"
						cellspacing="0" cellpadding="0"
					>
						<tr height="50" valign="top">
							<td width="55">&nbsp;</td>
							<td><span class="fl" style="font-size: 24px;">手机号登录</span> <span
								class="fr"
							>还没有商城账号，<a href="${ctx}/Register?action=toRegister"
									style="color: #ff4e00;"
								>立即注册</a></span></td>
						</tr>
						<tr height="70">
							<td>手机号</td>
							<td><input type="text" id="phone" name="phone"
								placeholder="请输入您的手机号" required autofocus class="l_user"
							/></td>
						</tr>
						<tr><td></td><td id="phoneTag" class="redFont"></td></tr>
						<tr>
							<td></td>
							<td id="l_userTag" class="redFont"></td>
						</tr>
						<tr height="70">
							<td>验证码</td>
							<td><input style="width: 100px" type="code" class="l_pwd"
								id="code" name="code" placeholder="验证码" required
							> <input type="button" id="btn" name="btn" value="发送验证码"
									onclick="sendMessage()"
								/></td>
						</tr>
						<tr>
							<td></td>
							<td id="codeTag" class="redFont"></td>
						</tr>
						<tr>
							<td></td>
							<td><span class="fr">已有商城账号，<a
									href="${ctx}/Login?action=toLogin" style="color: #ff4e00;"
								>我要登录</a></span></td>
						</tr>
						<tr height="60">
							<td>&nbsp;</td>
							<td>
								<button onclick="" type="button" class="log_btn" id="lo">登录</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<!--End Login End-->
	<script>
		var sms="";//服务器返回字符串
		var InterValObj;
		var count = 30;
		var curCount;
		//发送验证码
		function sendMessage() {
			if (ckPhone()) {
				//请求服务器
				$.post("${ctx}/sendSMS","phone="+$("#phone").val(),function(res){sms=res});
				curCount = count;
				$("#btn").attr("disabled", "true");
				$("#btn").val(curCount + "秒后可重新发送");
				InterValObj = window.setInterval(function() {
					if (curCount == 0) {
						window.clearInterval(InterValObj);//停止计时器
						$("#btn").removeAttr("disabled");//启用按钮
						$("#btn").val("重新发送验证码");
					} else {
						curCount--;
						$("#btn").val(curCount + "秒后可重新发送");
					}
				}, 1000);
			}
		}
		
		//验证手机号码
		function ckPhone() {
			$("#phoneTag").html("");
			var mobile = $("#phone");
			var reg = /^(13[0-9]|14[0-9]|15[0-9]|166|17[0-9]|18[0-9]|19[8|9])\d{8}$/;
			if (mobile.val() == "") {
				$("#phoneTag").html("手机号码不能为空！");
			} else if (!reg.test(mobile.val())) {
				$("#phoneTag").html("手机号码格式不正确！");
			} else {
				return true;
			}
			return false;
		}
		//登录
		$("#lo").click(function(){
        var code=$("#code").val();
		$("#codeTag").html("");
        if(code==""){
        	$("#codeTag").html("验证码不能为空");
        }else{
        	alert("验证码为:"+sms);
            if(sms==code){
            	$.post("${ctx}/sendSMS","opr=login",function(data){
            		
            		if (data==0) {
            			showMessage("该手机号用户不存在！");
            			location.reload();
					}else {
		            	showMessage("恭喜你，登录成功！");
		                location="${ctx}/Home?action=index";
					}
            	});
            }else{
            	$("#codeTag").html("验证码错误");
            };
        };
    });//17674719279
	</script>
</body>
</html>
