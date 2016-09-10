package user.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import login.domain.Login;
import main.controller.MainController;
import user.domain.User;

public class UserDao {

	// 회원 가입
	public boolean userSignUp(User newUser) {

		boolean success = false;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		int maxUserNumber = 0;

		try {

			String sql = "select * from shoppingMall_user where userId = ?";
			pstmt = MainController.getDbController().getConnection().prepareStatement(sql);
			pstmt.setString(1, newUser.getUserId());
			rs = pstmt.executeQuery();

			if(rs.next()){

				MainController.AlertView("이미 아이디가 존재합니다");

			} else {

				sql = "select max(userNumber)+1 as maxUserNumber from shoppingMall_user";
				stmt = MainController.getDbController().getConnection().createStatement();
				rs2 = stmt.executeQuery(sql);

				if(rs2.next()){
					maxUserNumber = rs2.getInt(1);
					if(rs2.wasNull()){
						maxUserNumber = 1;
					}
				}

				newUser.setUserNumber(maxUserNumber);

				sql = "insert into shoppingMall_user values(?,?,?,?)";
				pstmt2 = MainController.getDbController().getConnection().prepareStatement(sql);
				pstmt2.setInt(1, newUser.getUserNumber());
				pstmt2.setString(2, newUser.getUserId());
				pstmt2.setString(3, newUser.getUserPassword());
				pstmt2.setString(4, newUser.getUserName());
				pstmt2.executeUpdate();
				success = true;

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			MainController.getDbController().close(pstmt2);
			MainController.getDbController().close(rs2);
			MainController.getDbController().close(stmt);
			MainController.getDbController().close(rs);
			MainController.getDbController().close(pstmt);

		}

		return success;

	}


	// 로그인한 회원 조회
	public User loginUserInfo(Login loginUser) {

		User loginUserInfo = new User();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			String sql = "select * from shoppingMall_user where userId = ?";
			pstmt = MainController.getDbController().getConnection().prepareStatement(sql);
			pstmt.setString(1, loginUser.getLoginUserId());
			rs = pstmt.executeQuery();

			if(rs.next()){

				loginUserInfo.setUserNumber(rs.getInt(1));
				loginUserInfo.setUserId(rs.getString(2));
				loginUserInfo.setUserPassword(rs.getString(3));
				loginUserInfo.setUserName(rs.getString(4));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			MainController.getDbController().close(rs);
			MainController.getDbController().close(pstmt);

		}	

		return loginUserInfo;
	}


	// 회원정보 변경
	public void userUpdate(User updatedUser) {

		User user = new User();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			String sql = "select * from shoppingMall_user where userId = ?";
			pstmt = MainController.getDbController().getConnection().prepareStatement(sql);
			pstmt.setString(1, updatedUser.getUserId());
			rs = pstmt.executeQuery();

			if(rs.next()){
				user.setUserNumber(rs.getInt(1));
				user.setUserId(rs.getString(2));
				user.setUserPassword(rs.getString(3));
				user.setUserName(rs.getString(4));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MainController.getDbController().close(rs);
			MainController.getDbController().close(pstmt);
		}

		try {

			String sql = "update shoppingMall_user set userPassword = ?, userName = ? where userId = ?";	
			pstmt = MainController.getDbController().getConnection().prepareStatement(sql);

			if(updatedUser.getUserPassword() != null){
				pstmt.setString(1, updatedUser.getUserPassword());	
			} else {
				pstmt.setString(1, user.getUserPassword());
			}

			if(updatedUser.getUserName() != null){
				pstmt.setString(2, updatedUser.getUserName());	
			} else {
				pstmt.setString(2, user.getUserPassword());
			}

			pstmt.setString(3, updatedUser.getUserId());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MainController.getDbController().close(pstmt);
		}

	}


	// 유저 삭제
	public boolean userDelete(Login loginUser) {

		boolean success = false;
		PreparedStatement pstmt = null;

		try {

			String sql = "delete shoppingMall_user where userId = ?";	
			pstmt = MainController.getDbController().getConnection().prepareStatement(sql);
			pstmt.setString(1, loginUser.getLoginUserId());
			pstmt.executeUpdate();
			success = true;

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			MainController.getDbController().close(pstmt);

		}

		return success;

	}

}
