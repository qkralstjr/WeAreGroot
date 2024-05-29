package kr.co.moran.web.action.qna;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.moran.web.action.Action;
import kr.co.moran.web.dao.BoardDAO;
import kr.co.moran.web.dao.CommentDAO;
import kr.co.moran.web.vo.BoardVO;
import kr.co.moran.web.vo.CommentVO;
import kr.co.moran.web.vo.member.MemberVO;

public class QnADetailAction implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		HttpSession session = req.getSession();
		if((MemberVO)session.getAttribute("memberVO") != null) {
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			req.setAttribute("loginId",memberVO.getMId());
		}
		
		
		String sbId = req.getParameter("bId");
		int bId = Integer.parseInt(sbId);
		
		BoardDAO boardDAO = new BoardDAO();
		CommentDAO commentDAO = new CommentDAO();  
		
		HashMap<String, Object> boardVO = new HashMap<String, Object>();
		boardVO = boardDAO.selectBoardBybId(bId);
		
		List<HashMap<String, Object>> commentList = commentDAO.selectCommentBybId(bId);
		
		System.out.println(commentList);
		
		req.setAttribute("boardVO", boardVO);
		req.setAttribute("commentList", commentList);
		

		return "jsp/board/qnaDetail.jsp";
	}

}
