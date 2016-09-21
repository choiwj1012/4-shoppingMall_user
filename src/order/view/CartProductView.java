package order.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.controller.MainController;
import order.domain.Order;

public class CartProductView {

	// variable
	private Scanner keyboard;


	// constructor
	public CartProductView() {

		this.keyboard = new Scanner(System.in);

	}


	// method
	public void cartProductView() {

		try{

			System.out.println("장바구니에 넣을 상품 번호를 입력하십시오");
			int selectedProductNumber = keyboard.nextInt();

			System.out.println("몇개 주문하시겠습니까?");
			int cartProductCount = keyboard.nextInt();

			Order newOrder = new Order(selectedProductNumber, cartProductCount);

			MainController.getOrderController().requestCartProduct(newOrder);

		}catch(InputMismatchException e){	
			
			System.err.println("잘못입력하셨습니다");		
		}
	}
}


