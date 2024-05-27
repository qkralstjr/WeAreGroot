package kr.co.moran.web.dao;

import java.io.Reader;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import kr.co.moran.web.vo.BoardVO;

public class BoardDAO {
	public SqlSessionFactory factory;
	
	public BoardDAO() {
		try {
			Reader r = Resources.getResourceAsReader("config/SqlMapConfig.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			factory = builder.build(r);
			
			r.close();
			
			
		} catch (IOException e) {
			System.out.println("config.xml파일을 찾을 수 없습니다");
			e.printStackTrace();
		}
		
	}
	
	public List<BoardVO> selectAllKH(){
		SqlSession ss = factory.openSession(true);
		List<BoardVO> list = ss.selectList("kr.co.moran.board.SelectAllKH");
		
		ss.close();
		return list;
	}
	
	public List<BoardVO> selectAllKHOrderByViewCntDesc(){
		SqlSession ss = factory.openSession(true);
		List<BoardVO> list = ss.selectList("kr.co.moran.board.SelectAllKHOrderByViewCntDesc");
		
		ss.close();
		return list;
	}
	
	public List<BoardVO> selectAllQnA(){
		SqlSession ss = factory.openSession(true);
		List<BoardVO> list = ss.selectList("kr.co.moran.board.SelectAllQnA");
		
		ss.close();
		return list;
	}
	
	public List<BoardVO> selectAllQnAOrderByViewCntDesc(){
		SqlSession ss = factory.openSession(true);
		List<BoardVO> list = ss.selectList("kr.co.moran.board.SelectAllQnAOrderByViewCntDesc");
		
		ss.close();
		return list;
	}

	public BoardVO selectQnABybId(int bId) {
		SqlSession ss = factory.openSession(true);
		BoardVO vo = ss.selectOne("kr.co.moran.board.SelectQnABybId", bId);
		
		ss.close();
		return vo;
	}
	
	public BoardVO selectKHBybId(int bId) {
		SqlSession ss = factory.openSession(true);
		BoardVO vo = ss.selectOne("kr.co.moran.board.SelectKHBybId", bId);
		
		ss.close();
		return vo;
	}
	
	public void insertQnA(BoardVO vo) {
		SqlSession ss = factory.openSession(true);
		ss.insert("kr.co.moran.board.InsertQnA",vo);
		
		ss.close();
	}
	
	public void insertKH(BoardVO vo) {
		SqlSession ss = factory.openSession(true);
		ss.insert("kr.co.moran.board.InsertKH",vo);
		
		ss.close();
	}

	public void modifyKHBybId(@Param("bId") int bId, @Param("title") String title, @Param("contents") String contents) {
		SqlSession ss = factory.openSession(true);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bId", bId);
		params.put("title", title);
		params.put("contents", contents);
		
		ss.update("kr.co.moran.board.ModifyKHBybId", params);
		
		ss.close();
		
	}
	
	public void modifyQnABybId(@Param("bId") int bId, @Param("title") String title, @Param("contents") String contents) {
		SqlSession ss = factory.openSession(true);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bId", bId);
		params.put("title", title);
		params.put("contents", contents);
		
		ss.update("kr.co.moran.board.ModifyQnABybId", params);
		
		ss.close();
		
	}
	
	public void deleteKHBybId(int bId) {
		SqlSession ss = factory.openSession(true);
		ss.update("kr.co.moran.board.DeleteKHBybId",bId);
		
		ss.close();
	}
	
	public void deleteQnABybId(int bId) {
		SqlSession ss = factory.openSession(true);
		ss.update("kr.co.moran.board.DeleteQnABybId",bId);
		
		ss.close();
	}

	public void modifyKHViewCntBybId(int bId) {
		SqlSession ss = factory.openSession(true);
		ss.update("kr.co.moran.board.ModifyKHViewCntBybId", bId);
		
		ss.close();
	}
	
	public void modifyKHLikeCntBybId(int bId) {
		SqlSession ss = factory.openSession(true);
		ss.update("kr.co.moran.board.ModifyKHLikeCntBybId", bId);
		
		ss.close();
	}
}