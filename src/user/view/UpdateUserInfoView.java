package user.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.controller.MainController;
import user.domain.User;

public class UpdateUserInfoView {

	// variable
	private Scanner keyboard;

	// constructor
	public UpdateUserInfoView() {

		this.keyboard = new Scanner(System.in);

	}

	// 변경할 회원정보를 입력
	public void updateUserInfoView(User loginUserInfo) {

		User updatedUser = null;
		String userPassword = null;
		String userName = null;

		System.out.println("회원님의 정보는 다음과 같습니다");
		System.out.println("아이디\t패스워드\t이름");
		System.out.print(loginUserInfo.getUserId() + "\t");
		System.out.print(loginUserInfo.getUserPassword() + "\t");
		System.out.println(loginUserInfo.getUserName());

		while(true){

			try{

				System.out.println("수정을 원하시는 정보를 선택하여 주십시오");
				System.out.println(" 1. 패스워드 || 2. 이름 || 3. 회원 탈퇴 || 4. 수정 종료하기 ");
				int selectedMenu = keyboard.nextInt();

				if(selectedMenu == 1){

					System.out.println("수정하실 비밀번호를 입력하여 주십시오 > ");
					userPassword = keyboard.next();

				} else if(selectedMenu == 2) {

					System.out.println("수정하실 이름을 입력하여 주십시오 > ");
					userName = keyboard.next();

				} else if(selectedMenu == 3) {

					MainController.getUserController().requestDeleteUser();

				} else if(selectedMenu == 4) {

					break;

				} else {

					System.out.println("잘못 입력하셨습니다");

				}


				updatedUser = new User(loginUserInfo.getUserId(), userPassword, userName);
				MainController.getUserController().requestUserUpdate(updatedUser);

			}catch(InputMismatchException e){

				System.err.println("잘못입력하셨습니다");
				MainController.getUserController().requestUserUpdateInfo();
			}
		}
	}
	
}