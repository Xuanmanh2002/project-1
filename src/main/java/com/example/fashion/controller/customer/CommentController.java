package com.example.fashion.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.fashion.models.Comment;
import com.example.fashion.models.Contact;
import com.example.fashion.models.Post;
import com.example.fashion.models.Product;
import com.example.fashion.services.CommentService;
import com.example.fashion.services.PostService;
import com.example.fashion.services.ProductService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PostService postService;

    @PostMapping("/add-comment")
    public String save(@ModelAttribute("comment") Comment comment, BindingResult bindingResult, Model model, HttpServletRequest request, @RequestParam("ProductID") Long ProductID, @RequestParam("PostID") Long PostID) {
        if (bindingResult.hasErrors()) {
            // Nếu có lỗi hợp lệ, trả về trang thêm danh mục với thông báo lỗi
            return "redirect:" + request.getHeader("Referer");
        }
        Product product = this.productService.findByID(ProductID);
        Post post = this.postService.findByID(PostID);
        comment.setPost(post);
        comment.setProduct(product);
        if (this.commentService.create(comment)) {
            // Thêm thông báo thành công vào model
            model.addAttribute("successMessage", "Bình luận của bạn đã được gửi thành công!");

            // Chuyển hướng đến trang index hoặc trang khác tùy theo yêu cầu của bạn
            return "redirect:" + request.getHeader("Referer");
        } else {
            // Nếu có lỗi khi lưu tin nhắn, bạn có thể thêm thông báo lỗi vào model
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi gửi bình luận. Vui lòng thử lại.");

            return "redirect:" + request.getHeader("Referer");
        }
    }
}
