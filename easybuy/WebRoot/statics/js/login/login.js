/**
 * Created by bdqn on 2016/5/3.

 */
$(document).keydown(function(event){
	
	if (event.keyCode == "13") { //用户点击的是回车键
		login();
	}
});
//登录的方法
function login(){
    var loginName=$("#loginName").val();
    var password=$("#password").val();
    $.ajax({
        url:contextPath+"/Login",
        method:"post",
        data:{loginName:loginName,password:password,action:"login"},
        success:function(jsonStr){
            var result=eval("("+jsonStr+")");
            if(result.status==1){
            	 showMessage("恭喜你!登陆成功!");
                window.location.href=contextPath+"/Home?action=index";
            }else{
                showMessage(result.message)
            }
        }
    })
}