package com.kh.toy.board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kh.toy.board.model.dto.Board;
import com.kh.toy.common.code.ErrorCode;
import com.kh.toy.common.db.JDBCTemplate;
import com.kh.toy.common.exception.DataAccessException;
import com.kh.toy.common.file.FileDTO;

public class BoardDao {

	JDBCTemplate template = JDBCTemplate.getInstance();
	
	//게시판 등록 기능..
	public void insertBoard(Board board, Connection conn) {
		
		String sql = "INSERT INTO BOARD (BD_IDX,USER_ID,TITLE,CONTENT) VALUES(SC_BOARD_IDX.NEXTVAL,?,?,?)";
		
		PreparedStatement pstm = null;
		
		try {
			pstm =conn.prepareStatement(sql);
			pstm.setString(1, board.getUserId());
			pstm.setString(2, board.getTitle());
			pstm.setString(3, board.getContent());
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}finally{
			template.close(pstm);
		}
		
		
		
		
	}
	//파일정보 불어오는 기능.....
	public void insertFile(FileDTO fileDTO, Connection conn) {
		String sql ="INSERT INTO FILE_INFO "+
				"(FL_IDX,TYPE_IDX,ORIGIN_FILE_NAME,RENAME_FILE_NAME,SAVE_PATH) "+
				"VALUES(SC_FILE_IDX.NEXTVAL,SC_BOARD_IDX.CURRVAL,?,?,?)";
		
		PreparedStatement pstm = null;
		
		try {
			pstm =conn.prepareStatement(sql);
			pstm.setString(1, fileDTO.getOriginFileName());
			pstm.setString(2, fileDTO.getRenameFileName());
			pstm.setString(3, fileDTO.getSavePath());
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}finally{
			template.close(pstm);
		}
	}
	
	
	
	public Board selectBoardDetail(String bdIdx, Connection conn) {
		
		String sql ="SELECT BD_IDX, USER_ID, REG_DATE, TITLE, CONTENT "
				+ " FROM BOARD WHERE BD_IDX = ?";
		
		PreparedStatement pstm = null;
		ResultSet rset =null;
		Board board = null;
		
		try {
			pstm =conn.prepareStatement(sql);
			pstm.setString(1, bdIdx);
			rset = pstm.executeQuery();
			
			if(rset.next()) {
				board = new Board();
				board.setBdIdx(bdIdx);
				board.setTitle(rset.getString("title"));
				board.setContent(rset.getString("content"));
				board.setUserId(rset.getString("user_Id"));
				board.setRegDate(rset.getDate("reg_date"));
				}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}finally{
			template.close(pstm,rset);
		}
		return board;
	}
	
	
	public List<FileDTO> selectFileDTOs(String bdIdx, Connection conn) {
		
		String sql ="SELECT FL_IDX, TYPE_IDX, ORIGIN_FILE_NAME, RENAME_FILE_NAME, SAVE_PATH, REG_DATE "
				+ " FROM FILE_INFO WHERE TYPE_IDX = ?";
		
		PreparedStatement pstm = null;
		ResultSet rset =null;
		List<FileDTO> files = new ArrayList<FileDTO>();
		
		try {
			pstm =conn.prepareStatement(sql);
			pstm.setString(1, bdIdx);
			rset = pstm.executeQuery();
			
			while(rset.next()){
				FileDTO fileDTO = new FileDTO();
				fileDTO.setFlIdx(rset.getString("fl_idx"));
				fileDTO.setTypeIdx(rset.getString("type_idx"));
				fileDTO.setSavePath(rset.getString("save_path"));
				fileDTO.setOriginFileName(rset.getString("origin_file_name"));
				fileDTO.setRenameFileName(rset.getString("rename_file_name"));
				fileDTO.setRegDate(rset.getDate("reg_date"));
				files.add(fileDTO);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}finally{
			template.close(pstm,rset);
		}
		
		return files;
	}

}
