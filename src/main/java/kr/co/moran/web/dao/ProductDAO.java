package kr.co.moran.web.dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.eclipse.tags.shaded.org.apache.xalan.xsltc.compiler.sym;

import kr.co.moran.web.vo.ProductImgVO;
import kr.co.moran.web.vo.ProductVO;
import lombok.val;

public class ProductDAO {
	private SqlSession session;
	
	public ProductDAO() {
		openSession();
	}
	
	private void openSession() {
		try {
			if(session == null) {
				session = new SqlSessionFactoryBuilder()
					.build(Resources.getResourceAsReader("config/SqlMapConfig.xml"))
					.openSession();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 전체 상품 종류
	public int pdTotal() {
		openSession();
		int cnt = session.selectOne("kr.co.moran.product.pdTotal");
		return cnt;
	}

	// 최신상퐁 종류 총 갯수
	public int pdLatestTotal() {
		openSession();
		int cnt = session.selectOne("kr.co.moran.product.pdLatestTotal");
		return cnt;
	}

	// 카테고리상퐁 종류 총 갯수
	public int pdcategoryTotal() {
		openSession();
		int cnt = session.selectOne("kr.co.moran.product.pdcategoryTotal");
		return cnt;
	}

	// 인기 상퐁 종류 총 갯수
	public int pdPopTotal() {
		openSession();
		int cnt = session.selectOne("kr.co.moran.product.pdPopTotal");
		return cnt;
	}
	
	
	// 상품 페이지 조회
	public List<ProductVO> pdSelectPage(int start, int pageNum) {
		openSession();
		Map<String, Integer> map = new HashMap<>();
        map.put("start", start);
        map.put("pageNum", pageNum);
		List<ProductVO> vos = session.selectList("kr.co.moran.product.selectPage", map);
		return vos;
	}
	
	// 상품 1개 조회
	public ProductVO pdSelsctOneByPId(int pId) {
		openSession();
		ProductVO v = session.selectOne("kr.co.moran.product.selectByPId", pId);
		return v;
	}
	
	// 전체 이미지 조회
	public List<ProductImgVO> piSelsctByPId(int pId) {
		openSession();
		List<ProductImgVO> vos = session.selectList("kr.co.moran.product.selectAllImgByPId", pId);
		return vos;
	}
	
	// 대표이미지 조회
	public ProductImgVO piSelsctBigimgByPId(int pId) {
		openSession();
		ProductImgVO v = session.selectOne("kr.co.moran.product.piSelsctBigimgByPid", pId);
		return v;
	}
	
	// 상품 구매자 수
	public int orderTotalByPid(int pId) {
		openSession();
		int cnt = session.selectOne("kr.co.moran.product.orderTotalByPid", pId);
		return cnt;
	}
	
	// 인기순 조회
	public List<ProductVO> pdSelectPopByPId(int start, int pageNum) {
		openSession();
		Map<String, Integer> map = new HashMap<>();
        map.put("start", start);
        map.put("pageNum", pageNum);
        List<ProductVO> vos = session.selectList("kr.co.moran.product.pdSelectPopByPId", map);
        return vos;
	}
	
	// 최신순 조회
	public List<ProductVO> pdSelectLatest(int start, int pageNum) {
		openSession();
		Map<String, Integer> map = new HashMap<>();
        map.put("start", start);
        map.put("pageNum", pageNum);
        List<ProductVO> vos = session.selectList("kr.co.moran.product.pdSelectLatest", map);
		return vos;
	}
	
	// 카테고리별 조회
	public List<ProductVO> pdSelectByCId(int start, int pageNum, int cId) {
		openSession();
//		System.out.println("C_ID :" + cId);
		Map<String, Integer> map = new HashMap<>();
		map.put("start", start);
		map.put("pageNum", pageNum);
		map.put("C_ID", cId);
        List<ProductVO> vos = session.selectList("kr.co.moran.product.pdSelectByCId", map);
		return vos;
	}
	
	public void closeSession() {
		if(session != null ) {
			session.close();
			session = null;
		}
	}
	
	public static void main(String[] args) {
		ProductDAO dao = new ProductDAO();
		
//		dao.pdSelectPage(0, 12).forEach(v -> {
//			System.out.println(v.getPId());
//			System.out.println(dao.piSelsctBigimgByPId(v.getPId()).getImg());
//		});
//		System.out.println(dao.pdSelsctOneByPId(12938));
		
//		dao.piSelsctByPId(12938).forEach(System.out::println);
//		System.out.println(dao.piSelsctBigimgByPId(12938));
		
//		dao.pdSelectPopByPId(0, 12).forEach(System.out::println);
//		dao.pdSelectLatest(0, 12).forEach(System.out::println);
		
		dao.closeSession();
	}
}