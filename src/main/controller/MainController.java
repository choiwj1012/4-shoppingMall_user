package main.controller;

import login.controller.LoginController;
import main.view.AlertView;
import main.view.MainMenuView;
import order.controller.OrderController;
import payment.controller.PaymentController;
import product.controller.ProductController;
import user.controller.UserController;
import user.view.UserMainMenu;

public class MainController {

	
	// Connect Controllers
	private static DB_Controller dbController;
	private static UserController userController;
	private static LoginController loginController;
	private static ProductController productController;
	private static OrderController orderController;
	private static PaymentController paymentController;
	
	
	// constructor
	public MainController() {
	
		dbController = new DB_Controller();
		userController = new UserController();
		loginController = new LoginController();
		productController = new ProductController();
		orderController = new OrderController();
		paymentController = new PaymentController();
		
	}

	
	// getter and setter
	public static DB_Controller getDbController() {
		return dbController;
	}
	
	
	public static UserController getUserController() {
		return userController;
	}

	
	public static LoginController getLoginController() {
		return loginController;
	}

	public static ProductController getProductController() {
		return productController;
	}

	
	public static OrderController getOrderController() {
		return orderController;
	}

	
	public static PaymentController getPaymentController() {
		return paymentController;
	}
	
	
	// method
	// 출력문 공통 메서드
	public static void AlertView(String msg){
		
		AlertView alertView = new AlertView();
		alertView.alert(msg);
		
	}
	
	
	// 메인메뉴보기
	public static void mainMenuView(){
		
		MainMenuView mainMenuView = new MainMenuView();
		mainMenuView.mainMenuView();
		
	}
	

}
