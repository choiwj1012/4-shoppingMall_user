package order.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.controller.MainController;
import order.domain.Order;

public class CartUpdateView {

	// variable
	private Scanner keyboard;


	// constructor
	public CartUpdateView() {

		this.keyboard = new Scanner(System.in);

	}


	// 장바구니 수정 정보 입력
	public void orderUpdateView() {

		try{
			
			System.out.println("1. 수정하기 || 2. 삭제하기");
			int selectedMenu = keyboard.nextInt();

			if(selectedMenu == 1){

				System.out.println("수정하실 상품번호를 입력하세요");
				int selectedProductNumber = keyboard.nextInt();

				System.out.println("수정하실 주문량을 입력하세요");
				int modifyOrderCount = keyboard.nextInt();

				Order updatedOrder = new Order(selectedProductNumber, modifyOrderCount);
				MainController.getOrderController().requestUpdateOrderList(updatedOrder);
				
			} else if(selectedMenu == 2){
					
				System.out.println("삭제하실 상품번호를 입력하세요");
				int selectedProductNumber = keyboard.nextInt();
				
				MainController.getOrderController().requestDeleteOrderList(selectedProductNumber);
				
			}

			
		}catch(InputMismatchException e){	
			
			System.err.println("잘못입력하셨습니다");		
		}

	}

}
