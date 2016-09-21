package order.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.controller.MainController;
import order.repository.OrderRepository;

public class CartListView {

	// variable
	private Scanner keyboard;
	
	// constructor
	public CartListView() {
		
		this.keyboard = new Scanner(System.in);
		
	}
	
	public void cartListView() {
		
		int sumPrice = 0;
		
		System.out.println("상품번호\t상품이름\t\t상품가격\t주문수량\t전체가격");
		for(int i=0; i<OrderRepository.getOrders().size(); i++){
			System.out.print(OrderRepository.getOrders().get(i).getProductNumber() + "\t");
			System.out.print(OrderRepository.getOrders().get(i).getProductName() + "\t");
			System.out.print(OrderRepository.getOrders().get(i).getProductPrice() + "\t");
			System.out.print(OrderRepository.getOrders().get(i).getOrderCount() + "\t");
			System.out.println(OrderRepository.getOrders().get(i).getProductPrice() * OrderRepository.getOrders().get(i).getOrderCount());
			sumPrice = sumPrice + (OrderRepository.getOrders().get(i).getProductPrice() * OrderRepository.getOrders().get(i).getOrderCount());
		}
		
		System.out.println("총금액 : " + sumPrice);
		
		if(sumPrice == 0){
			System.out.println("장바구니가 비어있습니다");
			return;
		}
		
		try{
			
			System.out.println("주문하시겠습니끼?");
			System.out.println("주문하시려면 1번을 돌아가시려면 2번을 눌러주세요");
			
			int selectedMenu = keyboard.nextInt();
			
			if(selectedMenu == 1){
				MainController.getOrderController().requestOrderProduct();
			} else if(selectedMenu == 2) {
				return;
			} else {
				System.out.println("잘못 입력하셨습니다.");
			}
			
		}catch(InputMismatchException e){
			
			System.err.println("잘못입력하셨습니다");
		}

	}

}
