package com.example.fashion.controller.customer;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.fashion.models.Brand;
import com.example.fashion.models.Cart;
import com.example.fashion.models.CartItem;
import com.example.fashion.models.Category;
import com.example.fashion.models.Product;
import com.example.fashion.models.User;
import com.example.fashion.services.BrandService;
import com.example.fashion.services.CartItemService;
import com.example.fashion.services.CartService;
import com.example.fashion.services.CategoryService;
import com.example.fashion.services.ProductService;
import com.example.fashion.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {

	@Autowired
	private UserService userService;

	@Autowired
	private CartService cartService;

	@Autowired
	private CartItemService itemService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private BrandService brandService;

	@GetMapping("/cart")
	public String index(Model model) {
		List<CartItem> lstItems = this.itemService.getAll();
		model.addAttribute("lstItems", lstItems);
		List<Cart> listViewsCart = this.cartService.getAll();
		model.addAttribute("listViewsCart", listViewsCart);
		List<Product> listViewsProducts = this.productService.getAll();
		model.addAttribute("listViewsProducts", listViewsProducts);
		List<Category> categories = this.categoryService.getAll();
		model.addAttribute("categories", categories);
		List<Brand> listBra = this.brandService.getAll();
		model.addAttribute("listBra", listBra);
		return "cart/index";
	}

	@PostMapping("/addcart")
	public String addItemToCart(@RequestParam("ProductID") Long productID,
			@RequestParam("Quantity") Integer quantity,
			@ModelAttribute("cart") Cart cart,
			Model model,
			HttpServletRequest request) {
		// Gọi phương thức create từ ItemService để thêm sản phẩm vào giỏ hàng
		if (itemService.create(productID, quantity, cart)) {
			// Nếu thêm thành công, chuyển hướng đến trang gọi yêu cầu
			return "redirect:" + request.getHeader("Referer");
		} else {
			// Nếu thêm không thành công, cũng chuyển hướng đến trang gọi yêu cầu
			return "redirect:" + request.getHeader("Referer");
		}
	}

}
