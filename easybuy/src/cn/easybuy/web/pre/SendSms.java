package cn.easybuy.web.pre;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.easybuy.entity.User;
import cn.easybuy.service.user.UserService;
import cn.easybuy.service.user.UserServiceImpl;
import cn.easybuy.utils.GetMessageCode;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

/**
 * 
 * @Title:发送验证码的servlet
 */
@WebServlet("/sendSMS")
public class SendSms extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String opr =req.getParameter("opr");
		UserService userImpl = new UserServiceImpl();
		User user = null;
		String code = null;
		if (opr==null || opr.equals("")) {
			String phone = req.getParameter("phone");
			// 根据获取到的手机号发送验证码
			user = userImpl.findByPhone(phone);
			code = GetMessageCode.getCode(phone);
			System.out.println(code);
			resp.getWriter().print(code);
			
			if (user!=null) {
				req.getSession().setAttribute("loginUser", user);
			}
		}else if (opr!=null && opr.equals("login")) {//登陆成功
			if (req.getSession().getAttribute("loginUser")==null) {
				resp.getWriter().print(0);
			}else {
				resp.getWriter().print(1);
			}
		}
	}
}
