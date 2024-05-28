package kr.co.moran.web.action.cart;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.moran.web.action.Action;
import kr.co.moran.web.dao.CartDAO;

public class DeleteCartAction implements Action {
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String[] pIds = req.getParameter("productIds").split(",");
		Map<String, Object> params = new HashMap<>();
		params.put("productIds", pIds);
		
		// 받아온 멤버 아이디를넣어야함.
		// 나중에 수정이 필요함.
		params.put("mId", 9);
		CartDAO dao = new CartDAO();
		return String.valueOf(dao.deleteCart(params));
	}
}