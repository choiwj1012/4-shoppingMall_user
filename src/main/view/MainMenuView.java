package main.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.controller.MainController;

public class MainMenuView {

	// variable
	private Scanner keyboard;

	// constructor
	public MainMenuView() {

		this.keyboard = new Scanner(System.in);

	}

	// method
	public void mainMenuView(){

		try{

			while(true){

				System.out.println("원하시는 메뉴를 선택하여 주십시오");
				System.out.println("1. 상품보기 || 2. 회원가입 || 3. 로그인 || 4. 쇼핑몰 나가기");

				int selectedMenu = keyboard.nextInt();

				if(selectedMenu == 1){

					MainController.getProductController().requestProductlist(); // 상품보기

				} else if(selectedMenu == 2){

					MainController.getUserController().requestNewUserInfo(); // 회원가입

				} else if(selectedMenu == 3){

					MainController.getLoginController().requestLoginUserInfoView(); // 로그인

				} else if(selectedMenu == 4){

					System.out.println("이용해 주셔서 감사합니다");
					MainController.getDbController().requestExitProgram();

				} else {

					System.out.println("메뉴를 잘못 선택하셨습니다");

				}
			}
			
		} catch(InputMismatchException e) {

			System.out.println("잘못입력하셨습니다.");
			MainController.mainMenuView();
		}
	}
}

