package kr.co.moran.web.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.moran.web.dao.MemberDAO;
import kr.co.moran.web.vo.member.MemberVO;

public class JoinAction implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html;charset=UTF-8");
			String name = req.getParameter("name");
			String email = req.getParameter("email");
			String userPw = req.getParameter("userpw");
			String userRepw = req.getParameter("userrepw");
			String nickName = req.getParameter("nickname");
			String phone = req.getParameter("phone");
			
			if(!userPw.equals(userRepw) ) {
				// 질문 req를 초기화 해주어야 하는가?
				return "joinForm.jsp";
			}
			MemberVO vo = new MemberVO();
			MemberDAO dao = new MemberDAO();
			vo.setMName(name);
			vo.setMEmail(email);
			vo.setMPw(userPw);
			vo.setMNick(nickName);
			vo.setMPhone(phone);
			dao.insert(vo);
			
			
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//성공시 (로그인 전) 메인 화면으로
		return null;
	}

}
