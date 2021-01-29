package com.course.client.controllers;

import com.course.client.beans.CartBean;
import com.course.client.beans.CartItemBean;
import com.course.client.beans.ProductBean;
import com.course.client.proxies.MsCartProxy;
import com.course.client.proxies.MsProductProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {

    @Autowired
    private MsProductProxy msProductProxy;

    @Autowired
    private MsCartProxy msCartProxy;

    @RequestMapping("/")
    public String index(Model model) {

        List<ProductBean> products =  msProductProxy.list();

        model.addAttribute("products", products);

        return "index";
    }

    @RequestMapping("/product-detail/{id}")
    public String get(@PathVariable Long id, Model model) {
        Optional<ProductBean> productInstance = msProductProxy.get(id);

        if (!productInstance.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Specified product doesn't exist");

        model.addAttribute("productInstance", productInstance.get());

        return "detail";
    }

    @RequestMapping("/add-product/{productId}")
    public String addProduct (Model model,@PathVariable Long productId){

        // id cart
        Long idCart = 1L;

        //Get cart
        Optional<CartBean> cart = msCartProxy.getCart(idCart);

        //test cart
        if (!cart.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find cart.");

        //Create cartItem
        CartItemBean cartItemBean = new CartItemBean();
        cartItemBean.setProductId(productId);
        cartItemBean.setQuantity(1);

        //Add CartItem to Cart
        msCartProxy.addProductToCart(idCart, cartItemBean);

        //Go back to index
        List<ProductBean> products = msProductProxy.list();
        model.addAttribute("products", products);

        return "index";
    }

}
