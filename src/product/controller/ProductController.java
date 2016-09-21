package product.controller;

import java.util.ArrayList;

import main.controller.MainController;
import product.dao.ProductDao;
import product.domain.Product;
import product.view.ProductListView;

public class ProductController {

	// variable
	private ProductDao productDao;
	
	
	// constructor
	public ProductController() {
		
		this.productDao = new ProductDao();
		
	}

	
	// method
	// request product list view
	public void requestProductlist(){
		
		// dao에서 list 받아오기
		ArrayList<Product> products = productDao.productList();
		
		// 받은 list 출력
		if(products.size() == 0){
			MainController.AlertView("상품이 없습니다");
		} else {
			ProductListView productListView = new ProductListView();
			productListView.productList(products);	
		}
		
	}

		
}
