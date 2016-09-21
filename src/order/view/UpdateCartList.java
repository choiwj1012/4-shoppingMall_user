package order.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.controller.MainController;
import order.domain.Order;

public class UpdateCartList {

	// variable
	private Scanner keyboard;
	
	// constructor
	public UpdateCartList() {
		
		this.keyboard = new Scanner(System.in);
		
	}
	
	public void updateCartList() {
		
		Order order = null;
			
		try{
			
			System.out.println("수정하시려면 1번 || 삭제하시려면 2번을 눌러주세요");
			int selectedMenu = keyboard.nextInt();
			
			if(selectedMenu == 1){
				System.out.println("수정할 상품번호를 입력해 주세요");
				int selectedProductNumber = keyboard.nextInt();
				
				System.out.println("수정할 수량을 입력해 주세요");	
				int updateProductCount = keyboard.nextInt();
				
				order = new Order(selectedProductNumber, updateProductCount);
				MainController.getOrderController().requestUpdateCartList(order);
				
			}else if(selectedMenu == 2){
				
				System.out.println("삭제할 상품번호를 입력해 주세요");
				int selectedProductNumber = keyboard.nextInt();
				int updateProductCount = 0;
				
				order = new Order(selectedProductNumber, updateProductCount);
				MainController.getOrderController().requestUpdateCartList(order);
				
			} else {
				
				System.out.println("잘못 입력하셨습니다.");
				
			}
			
		} catch(InputMismatchException e) {
			
			System.err.println("잘못입력하셨습니다");
			
		}
					
	}

}
