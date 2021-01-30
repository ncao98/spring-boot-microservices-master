package com.course.client.controllers;

import com.course.client.beans.CartBean;
import com.course.client.beans.CartItemBean;
import com.course.client.beans.ProductBean;
import com.course.client.proxies.MsCartProxy;
import com.course.client.proxies.MsProductProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String createCart(Model model) {

        // Création d'un Panier de Session
        ResponseEntity<CartBean> cartBeanInstance = msCartProxy.createNewCart(new CartBean());
        model.addAttribute("cart", cartBeanInstance.getBody());

        return "redirect:/index/" + cartBeanInstance.getBody().getId();
    }

    @RequestMapping("/index/{cartId}")
    public String index(Model model, @PathVariable Long cartId) {

        Optional<CartBean> sessionCartBean = msCartProxy.getCart(cartId);

        if(!sessionCartBean.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Could not find cart.");

        model.addAttribute("cart", sessionCartBean.get());

        List<ProductBean> products =  msProductProxy.list();

        model.addAttribute("products", products);



        return "index";
    }

    @RequestMapping("/{cartId}/product-detail/{id}")
    public String get(Model model, @PathVariable Long id, @PathVariable Long cartId) {
        //Récupérer le cart de session
        Optional<CartBean> sessionCartBean = msCartProxy.getCart(cartId);
        if(!sessionCartBean.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Could not find cart.");
        model.addAttribute("cart", sessionCartBean.get());

        //Récupérer le produit lié à l'id
        Optional<ProductBean> productInstance = msProductProxy.get(id);

        if (!productInstance.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Specified product doesn't exist");

        model.addAttribute("productInstance", productInstance.get());

        return "detail";
    }

    @RequestMapping("/{cartId}/add-product/{productId}")
    public String addProduct (Model model,@PathVariable Long productId, @PathVariable Long cartId){

        // id cart
        //Long idCart = 7L;

        //Get cart
        Optional<CartBean> sessionCartBean = msCartProxy.getCart(cartId);
        if(!sessionCartBean.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Could not find cart.");

        //Create cartItem
        CartItemBean cartItemBean = new CartItemBean();
        cartItemBean.setProductId(productId);
        cartItemBean.setQuantity(1);

        //Add CartItem to Cart
        msCartProxy.addProductToCart(cartId, cartItemBean);

        //Go back to index
        List<ProductBean> products = msProductProxy.list();
        model.addAttribute("products", products);
        model.addAttribute("cart", sessionCartBean.get());

        return "index";
    }

    @RequestMapping("/empty-cart/{cartId}")
    public String emptyCart(Model model, @PathVariable Long cartId){

        //Récupérer le cart associé à l'id
        Optional<CartBean> cartBean = msCartProxy.getCart(cartId);
        //Récupérer la liste de cartItem associée et la vider
        List<CartItemBean> cartItemBeans = cartBean.get().getProducts();

        cartItemBeans = null ;

        return "index";
    }

    @RequestMapping("/delete-cart/{cartId}")
    public String deleteCart(Model model, @PathVariable Long cartId){
        //Récupérer le cart associé à l'id
        Optional<CartBean> cartBean = msCartProxy.getCart(cartId);

        return "index";
    }

}
