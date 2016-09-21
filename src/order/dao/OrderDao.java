package order.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import login.repository.LoginRepository;
import main.controller.MainController;
import order.domain.Order;
import order.repository.OrderRepository;

public class OrderDao {

	// constructor
	public OrderDao() {

		new OrderRepository();

	}


	// 주문한 상품 장바구니 넣기
	public int cartProduct(Order newOrder) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cartProcessNumber = 0;

		// 이미 주문한 상품에 대해서는 수량만 변경한다.
		for(int i=0; i<OrderRepository.getOrders().size(); i++){
			if(OrderRepository.getOrders().get(i).getProductNumber() == newOrder.getProductNumber()){
				OrderRepository.getOrders().get(i).setOrderCount(newOrder.getOrderCount());
				cartProcessNumber = 1;
				return cartProcessNumber;
			}
		}

		// 상품정보 DB에서 불러오기	
		try {
			
			String sql = "select productName, productPrice from productlist where productNumber = ?";
			pstmt = MainController.getDbController().getConnection().prepareStatement(sql);
			pstmt.setInt(1, newOrder.getProductNumber());
			rs = pstmt.executeQuery();
						
			if(rs.next()){
				
				// 선택한 상품이 없는 상품인 경우
				if(!rs.isFirst()){
					return cartProcessNumber;
				}
				
				// 이미 주문한 상품이 없고, 새롭게 장바구니에 넣는 경우
				OrderRepository.getOrders().add(newOrder);				
				OrderRepository.getOrders().get(OrderRepository.getLastCartNumber()).setProductName(rs.getString(1));
				OrderRepository.getOrders().get(OrderRepository.getLastCartNumber()).setProductPrice(rs.getInt(2));
				OrderRepository.getOrders().get(OrderRepository.getLastCartNumber()).setOrderNumber(OrderRepository.getLastCartNumber() + 1);		
				OrderRepository.setLastCartNumber(OrderRepository.getLastCartNumber() + 1);
				cartProcessNumber = 2;	
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null){try {rs.close();} catch (SQLException e) {e.printStackTrace();}}
			if(pstmt != null){try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}}
		}
	
		return cartProcessNumber;

	}


	// 장바구니에 담긴 물건들 가져오기
	public ArrayList<Order> cartList() {

		ArrayList<Order> orders = OrderRepository.getOrders();

		return orders;

	}


	// 장바구니 물건 수정하기
	public boolean updateCartList(Order order) {

		boolean isFind = false;

		// 삭제 요청한 경우 수량이 0 처리 됨
		if(order.getOrderCount() == 0){
			for(int i=0; i<OrderRepository.getOrders().size(); i++){
				if(order.getProductNumber() == OrderRepository.getOrders().get(i).getProductNumber())
					OrderRepository.getOrders().remove(i);
					isFind = true;
			}
		}
		
		if(isFind){
			return isFind;
		}
		
		// 주문 수량 수정
		for(int i=0; i<OrderRepository.getOrders().size(); i++){	
			if(order.getProductNumber() == OrderRepository.getOrders().get(i).getProductNumber()){
				OrderRepository.getOrders().get(i).setOrderCount(order.getOrderCount());
				isFind = true;
			}
		}

		return isFind;

	}


	// 장바구니 상품을 주문 리스트로 넣기
	public boolean orderProduct() {

		boolean success = false;
		Statement stmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		int maxOrderNumber = 0;
		int userNumber = 0;

		try {

			// transaction
			MainController.getDbController().getConnection().setAutoCommit(false);

			// 현재 접속한 아이디에 해당하는 userNumber를 찾음
			String sql = "select userNumber from userlist where userId = ?";
			pstmt1 = MainController.getDbController().getConnection().prepareStatement(sql);
			pstmt1.setString(1, LoginRepository.getLogin().getLoginUserId());
			rs1 = pstmt1.executeQuery();

			if(rs1.next()){
				userNumber = rs1.getInt(1);
			}

			for(int i=0; i<OrderRepository.getOrders().size(); i++){
				
				// 선택한 제품번호와 유저번호에 해당하는 주문 리스트 내역 찾음
				sql = "select * from CARTLIST where productNumber = ? and userNumber = ? and isPayment = 0";
				pstmt2 = MainController.getDbController().getConnection().prepareStatement(sql);
				pstmt2.setInt(1, OrderRepository.getOrders().get(i).getProductNumber());
				pstmt2.setInt(2, userNumber);
				rs2 = pstmt2.executeQuery();

				if(rs2.next()){	

					// 동일 상품이 이미 장바구니에 있다면 수량만 조정
					sql = "update CARTLIST set orderCount = ? where productNumber = ? and userNumber = ? and isPayment = 0";
					pstmt3 = MainController.getDbController().getConnection().prepareStatement(sql);
					pstmt3.setInt(1, OrderRepository.getOrders().get(i).getOrderCount());
					pstmt3.setInt(2, OrderRepository.getOrders().get(i).getProductNumber());
					pstmt3.setInt(3, userNumber);
					pstmt3.executeUpdate();
					success = true;
					return success;

				}
				
				// 주문 리스트 추가시 주문 번호 증가
				sql = "select max(orderNumber) + 1 as maxOrderNumber from CARTLIST";
				stmt = MainController.getDbController().getConnection().createStatement();
				rs3 = stmt.executeQuery(sql);

				if(rs3.next()){
					maxOrderNumber = rs3.getInt(1);
					if(rs3.wasNull()){
						maxOrderNumber = 1;
					}
				}

				// 주문리스트에 데이터 저장
				sql = "insert into cartlist(orderNumber, productNumber, userNumber, orderCount, isPayment) values(?,?,?,?,?)";
				pstmt4 = MainController.getDbController().getConnection().prepareStatement(sql);
				pstmt4.setInt(1, maxOrderNumber);
				pstmt4.setInt(2, OrderRepository.getOrders().get(i).getProductNumber());
				pstmt4.setInt(3, userNumber);
				pstmt4.setInt(4, OrderRepository.getOrders().get(i).getOrderCount());
				pstmt4.setInt(5, OrderRepository.getOrders().get(i).getIsPayment());
				pstmt4.executeUpdate();

				success = true;

			}
		
			MainController.getDbController().getConnection().commit();
			OrderRepository.getOrders().clear();
			OrderRepository.setLastCartNumber(0);
			
		} catch (SQLException e) {

			e.printStackTrace();

			try {
				MainController.getDbController().getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		} finally {

			if(pstmt4 != null){try{pstmt4.close();} catch (SQLException e) {e.printStackTrace();}}
			if(rs3 != null){try{rs3.close();} catch (SQLException e) {e.printStackTrace();}}
			if(pstmt3 != null){try{pstmt3.close();} catch (SQLException e) {e.printStackTrace();}}
			if(rs2 != null){try{rs2.close();} catch (SQLException e) {e.printStackTrace();}}
			if(stmt != null){try{stmt.close();} catch (SQLException e) {e.printStackTrace();}}
			if(pstmt2 != null){try{pstmt2.close();} catch (SQLException e) {e.printStackTrace();}}
			if(rs1 != null){try{rs1.close();} catch (SQLException e) {e.printStackTrace();}}
			if(pstmt1 != null){try{pstmt1.close();} catch (SQLException e) {e.printStackTrace();}}

			try {
				MainController.getDbController().getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return success;

	}


	// 장바구니 상품리스트 가져오기
	public ArrayList<Order> orderList() {

		ArrayList<Order> orders = new ArrayList<Order>();
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;

		try {

			String sql = "select * from cartlist_view where userNumber = ? ";
					//"select distinct cr.isPayment, pr.productName, pr.productNumber, cr.orderNumber, pr.productPrice, cr.orderCount, cr.orderDate, cr.userNumber from CARTLIST cr, productList pr where cr.userNumber = ? and cr.PRODUCTNUMBER = pr.PRODUCTNUMBER";  
			pstmt1 = MainController.getDbController().getConnection().prepareStatement(sql);
			pstmt1.setInt(1, LoginRepository.getLogin().getUserNumber());
			rs1 = pstmt1.executeQuery();

			while(rs1.next()){	

				int isPayment = rs1.getInt(1);
				if(isPayment == 1){
					continue;
				}
				
				Order orderList = new Order();
				orderList.setProductName(rs1.getString(2));
				orderList.setProductNumber(rs1.getInt(3));
				orderList.setOrderNumber(rs1.getInt(4)); 
				orderList.setProductPrice(rs1.getInt(5));
				orderList.setOrderCount(rs1.getInt(6));
				orderList.setOrderDate(rs1.getDate(7));
				orderList.setUserNumber(rs1.getInt(8));

				sql = "select sum(ct.orderCount * pr.productPrice) from Cartlist ct, productlist pr where ct.productNumber = pr.ProductNumber and userNumber = ?";
				pstmt2 = MainController.getDbController().getConnection().prepareStatement(sql);
				pstmt2.setInt(1, LoginRepository.getLogin().getUserNumber());
				rs2 = pstmt2.executeQuery();

				if(rs2.next()){
					orderList.setOrderSum(rs2.getInt(1));	
				}

				orders.add(orderList);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			if(rs2 != null){try{rs2.close();} catch (SQLException e){e.printStackTrace();}}
			if(pstmt2 != null){try{pstmt2.close();} catch (SQLException e){e.printStackTrace();}}
			if(rs1 != null){try{rs1.close();} catch (SQLException e){e.printStackTrace();}}
			if(pstmt1 != null){try{pstmt1.close();} catch (SQLException e){e.printStackTrace();}}

		}

		return orders;

	}


	// 장바구니 수량 수정
	public boolean updateOrderList(Order updatedOrder) {

		boolean success = true;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int userNumber = 0;

		try {

			String sql = "select userNumber from userlist where userId = ?";
			pstmt = MainController.getDbController().getConnection().prepareStatement(sql);
			pstmt.setString(1, LoginRepository.getLogin().getLoginUserId());
			rs = pstmt.executeQuery();

			if(rs.next()){
				userNumber = rs.getInt(1);
			}

			pstmt.close();

			sql = "update CARTLIST set orderCount = ? where productNumber = ? and userNumber = ?";
			pstmt = MainController.getDbController().getConnection().prepareStatement(sql);
			pstmt.setInt(1, updatedOrder.getOrderCount());
			pstmt.setInt(2, updatedOrder.getProductNumber());
			pstmt.setInt(3, userNumber);
			pstmt.executeUpdate();

			pstmt.close();

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			if(rs != null){try{rs.close();} catch (SQLException e){e.printStackTrace();}}
			if(pstmt != null){try{pstmt.close();} catch (SQLException e){e.printStackTrace();}}

		}

		return success;

	}


	// 선택 상품 삭제
	public boolean deleteOrderList(int selectedProductNumber) {

		boolean success = false;
		PreparedStatement pstmt = null;

		try {

			String sql = "delete CARTLIST where productNumber = ?";
			pstmt = MainController.getDbController().getConnection().prepareStatement(sql);
			pstmt.setInt(1, selectedProductNumber);
			pstmt.executeUpdate();
			success = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {

			if(pstmt != null){try{pstmt.close();} catch (SQLException e){e.printStackTrace();}}

		}

		return success;

	}

}
