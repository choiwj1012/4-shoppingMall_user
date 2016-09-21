package order.view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import main.controller.MainController;
import order.domain.Order;

public class OrderListView {

	// variable
	private Scanner keyboard;

	// constructor
	public OrderListView() {

		this.keyboard = new Scanner(System.in);

	}

	// method
	// 장바구니 리스트 보기
	public void orderListView(ArrayList<Order> orders) {

		if(orders.size() == 0){

			System.out.println("주문상품이 없습니다");
			return;

		} else {

			System.out.println("상품번호\t상품이름\t\t상품가격\t주문수량\t주문일자");
			for(int i=0; i<orders.size(); i++){

				System.out.print(orders.get(i).getProductNumber() + "\t");
				System.out.print(orders.get(i).getProductName() + "\t");
				System.out.print(orders.get(i).getProductPrice() + "\t");
				System.out.print(orders.get(i).getOrderCount() + "\t");
				System.out.println(orders.get(i).getOrderDate());

			}	

			System.out.println("총 금액 : " + orders.get(0).getOrderSum());

		}

		try{

			System.out.println("결제하시겠습니까 ? y or n");
			char yesOrNo = keyboard.next().charAt(0);

			if(yesOrNo == 'y'){

				System.out.println("결제창으로 이동합니다");
				MainController.getPaymentController().requestPaymentView(orders);

			} else if(yesOrNo == 'n'){

				System.out.println("메뉴로 돌아갑니다");

			} else {

				System.out.println("잘못 입력하셨습니다");

			}

		}catch(InputMismatchException e){
			
			System.err.println("잘못입력하셨습니다");
		}
	}

}
