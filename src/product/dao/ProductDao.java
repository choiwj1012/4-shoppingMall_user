package product.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.controller.MainController;
import product.domain.Product;

public class ProductDao {

	// 상품 리스트 가져오기
	public ArrayList<Product> productList() {
		
		ArrayList<Product> products = new ArrayList<Product>();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			stmt = MainController.getDbController().getConnection().createStatement();
			String sql = "select * from shoppingMall_Product";
			rs = stmt.executeQuery(sql);
				
			while(rs.next()){
			
				Product product = new Product();
				product.setProductNumber(rs.getInt("productNumber"));
				product.setProductName(rs.getString("productName"));
				product.setProductPrice(rs.getInt("productPrice"));
				product.setProductComment(rs.getString("productComment"));
				product.setProductVendor(rs.getString("productVendor"));
				products.add(product);
				
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			MainController.getDbController().close(rs);
			MainController.getDbController().close(stmt);
		}
		
		return products;
	}
	
	
	

}
