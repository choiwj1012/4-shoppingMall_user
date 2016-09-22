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
			String sql = "select * from shop_master.PRODUCTLIST";
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
		}finally {

			if(stmt != null){try{stmt.close();} catch (SQLException e){e.printStackTrace();}}
			if(rs != null){try{rs.close();} catch (SQLException e){e.printStackTrace();}}
	
		}

		return products;
	}

	
}
