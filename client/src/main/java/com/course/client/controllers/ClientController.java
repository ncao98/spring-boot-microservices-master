package com.course.client.controllers;

import com.course.client.beans.CartBean;
import com.course.client.beans.CartItemBean;
import com.course.client.beans.OrderBean;
import com.course.client.beans.ProductBean;
import com.course.client.proxies.MsCartProxy;
import com.course.client.proxies.MsOrdersProxy;
import com.course.client.proxies.MsProductProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {

    @Autowired
    private MsProductProxy msProductProxy;

    @Autowired
    private MsCartProxy msCartProxy;

    @Autowired
    private MsOrdersProxy msOrdersProxy;

    //Créer un cart au démarrage de la page
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

        if (!sessionCartBean.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find cart.");

        model.addAttribute("cart", sessionCartBean.get());

        List<ProductBean> products = msProductProxy.list();

        model.addAttribute("products", products);

        return "index";
    }


    @RequestMapping("/{cartId}/product-detail/{id}")
    public String get(Model model, @PathVariable Long id, @PathVariable Long cartId) {
        //Récupérer le cart de session
        Optional<CartBean> sessionCartBean = msCartProxy.getCart(cartId);
        if (!sessionCartBean.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find cart.");
        model.addAttribute("cart", sessionCartBean.get());

        //Récupérer le produit lié à l'id
        Optional<ProductBean> productInstance = msProductProxy.get(id);

        if (!productInstance.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Specified product doesn't exist");

        model.addAttribute("productInstance", productInstance.get());

        return "detail";
    }


    @RequestMapping("/{cartId}/add-product/{productId}")
    public String addProduct(Model model, @PathVariable Long productId, @PathVariable Long cartId) {
        //Get cart
        Optional<CartBean> sessionCartBean = msCartProxy.getCart(cartId);
        if (!sessionCartBean.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find cart.");
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
    public String emptyCart(Model model, @PathVariable Long cartId) {

        //Récupérer le cart associé à l'id
        Optional<CartBean> sessionCartBean = msCartProxy.getCart(cartId);
        if (!sessionCartBean.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find cart.");
        //Récupérer la liste de cartItem associée et la vider
        /*
        System.out.println(sessionCartBean.get().getProducts());
        sessionCartBean.get().getProducts().clear();
        System.out.println(sessionCartBean.get().getProducts());
        */

        //msCartProxy.getCart(cartId).get().setProducts(new ArrayList<>());

        //long idNewCart = sessionCartBean.get().getId() + 1 ;
        // Création d'un Panier de Session
        ResponseEntity<CartBean> cartBeanInstance = msCartProxy.createNewCart(new CartBean());
        model.addAttribute("cart", cartBeanInstance.getBody());

        return "redirect:/cart/" + cartBeanInstance.getBody().getId() + "/detailCart" ;

    }
//pour vider la commande
    @RequestMapping("/empty-order/{cartId}")
    public String emptyOrder(Model model, @PathVariable Long cartId) {

        //Récupérer le cart associé à l'id
        Optional<CartBean> sessionCartBean = msCartProxy.getCart(cartId);
        if (!sessionCartBean.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find cart.");
        //Récupérer la liste de cartItem associée et la vider
        /*
        System.out.println(sessionCartBean.get().getProducts());
        sessionCartBean.get().getProducts().clear();
        System.out.println(sessionCartBean.get().getProducts());
        */

        //msCartProxy.getCart(cartId).get().setProducts(new ArrayList<>());

        //long idNewCart = sessionCartBean.get().getId() + 1 ;
        // Création d'un Panier de Session
        ResponseEntity<CartBean> cartBeanInstance = msCartProxy.createNewCart(new CartBean());
        model.addAttribute("cart", cartBeanInstance.getBody());

        return "redirect:/index/" + cartBeanInstance.getBody().getId() ;

    }


    @RequestMapping("cart/{cartId}/detailCart")
    public String getCart(@PathVariable Long cartId, Model model) {
        Optional<CartBean> sessionCartBean = msCartProxy.getCart(cartId);
        if (!sessionCartBean.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find cart.");
        // Boucler sur les cart items et pour chaque cart item
        // Récupérer les informations du produit (avec le msProductProxy) pour chaque cart item (productId)
        // Pour chaque produit récupéré, on ajoute au modèle (model) les informations du produit récupéré pour pouvoir les afficher dans la page
        // Pour ajouter les produits au modèle :
        // 1. On construit une liste (List<ProductBean> products)
        // 2. Pour chaque produit on l'ajoute à la liste
        // 3. A la fin, on ajoute notre liste "products" au "model"
        //nb d'élément


        List<CartItemBean> cartItemsBean = sessionCartBean.get().getProducts();
        List<ProductBean> products = new ArrayList<>();


        double total = 0.0;

        for (int i = 0; i < cartItemsBean.size(); i++) {
            System.out.println(cartItemsBean.size());
            Optional<ProductBean> productBean = msProductProxy.get(cartItemsBean.get(i).getProductId());
            if (productBean.isPresent()) {
                products.add(productBean.get());
                total += cartItemsBean.get(i).getQuantity() * productBean.get().getPrice();
            }
        }

        /*if (products == null)
            //throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Specified cart doesn't exist");
            return "panier_vide";*/


        model.addAttribute("total", total);

        model.addAttribute("infoProducts", products);

        model.addAttribute("cart", sessionCartBean.get());

        return "panier";


    }

    @RequestMapping("cart/{cartId}/order")
    public String getOrder(@PathVariable Long cartId, Model model) {

        Optional<CartBean> sessionCartBean = msCartProxy.getCart(cartId);
        if (!sessionCartBean.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find order.");


        List<CartItemBean> cartItemsBean = sessionCartBean.get().getProducts();

        List<ProductBean> products = new ArrayList<>();


        double total = 0.0;

        for (int i = 0; i < cartItemsBean.size(); i++) {
            System.out.println(cartItemsBean.size());
            Optional<ProductBean> productBean = msProductProxy.get(cartItemsBean.get(i).getProductId());
            if (productBean.isPresent()) {
                products.add(productBean.get());
                total += cartItemsBean.get(i).getQuantity() * productBean.get().getPrice();
            }
        }



        model.addAttribute("total", total);

        model.addAttribute("infoProducts", products);

        model.addAttribute("cart", sessionCartBean.get());

        return "order";
    }

}
