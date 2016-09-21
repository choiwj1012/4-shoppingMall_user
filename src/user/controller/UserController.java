package user.controller;

import java.util.ArrayList;

import login.domain.Login;
import main.controller.MainController;
import user.dao.UserDao;
import user.domain.User;
import user.view.DeleteUserView;
import user.view.NewUserInfoView;
import user.view.UpdateUserInfoView;
import user.view.UserListView;
import user.view.UserMainMenu;

public class UserController {

	// variable
	private UserDao userDao;

	
	// constructor
	public UserController() {

		this.userDao = new UserDao();

	}

	
	// 유저 메인메뉴 요청
	public void requestUserMainMenu() {

		UserMainMenu userMainMenu = new UserMainMenu();
		userMainMenu.userMainMenu();

	}
	
	
	// 회원 가입 정보 요청
	public void requestNewUserInfo() {

		NewUserInfoView newUserInfoView = new NewUserInfoView();
		newUserInfoView.newUserInfoView();

	}


	// 받은 회원 정보 데이터베이스 저장 요청
	public void requestSignUp(User newUser){

		boolean success = userDao.userSignUp(newUser);

		if(success){
			MainController.AlertView("가입에 성공하였습니다");
		} else {
			MainController.AlertView("가입에 실패하였습니다");
		}

	}


	// 회원 정보 변경 요청
	public void requestUserUpdateInfo() {

		Login loginUser = MainController.getLoginController().requestLoginUserInfo();
		User loginUserInfo = userDao.loginUserInfo(loginUser);
				
		UpdateUserInfoView updateUserInfoView = new UpdateUserInfoView();
		updateUserInfoView.updateUserInfoView(loginUserInfo);
		
	}

	
	// 변경 정보로 베이터베이스 변경
	public void requestUserUpdate(User updatedUser){
		
		userDao.userUpdate(updatedUser);
		
	}
	
	
	// 회원 정보 삭제 요청
	public void requestDeleteUser(){
		
		DeleteUserView deleteUserView = new DeleteUserView();
		boolean isAgree = deleteUserView.deleteUserView();
		
		if(isAgree){
			
			Login loginUser = MainController.getLoginController().requestLoginUserInfo();
			boolean success = userDao.userDelete(loginUser);
			
			if(success){
				
				System.out.println("회원 탈퇴되었습니다. 이용해주셔서 감사합니다");
				MainController.getLoginController().requestLogout();
				MainController.mainMenuView();
				
			} else {
				
				System.out.println("회원 탈퇴에 실패하였습니다");
				
			}
						
		} else {
			
			System.out.println("회원 탈퇴를 취소합니다");
			
		}
		
	}

	
	// 회원 리스트 출력
	public void requestUserList() {
		
		ArrayList<User> users = null;
		users = userDao.userList();
		UserListView userListView = new UserListView();
		userListView.userListView(users);
		
	}
	
}
