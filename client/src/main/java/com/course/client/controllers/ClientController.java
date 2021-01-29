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

    //Créer un cart au démarrage de la page
    /*@RequestMapping("/")
    public String createCart(Model model){

        //créer un cart
        ResponseEntity<CartBean> cartBeanInstance = msCartProxy.createNewCart(new CartBean());

        return "index";


    }*/

    @RequestMapping("/")
    public String index(Model model, @PathVariable Long cartId) {

        List<ProductBean> products =  msProductProxy.list();

        model.addAttribute("products", products);

        return "index";
    }

    @RequestMapping("/product-detail/{id}")
    public String get(@PathVariable Long id, Model model, @PathVariable Long cartId) {
        Optional<ProductBean> productInstance = msProductProxy.get(id);

        if (!productInstance.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Specified product doesn't exist");

        model.addAttribute("productInstance", productInstance.get());

        return "detail";
    }

    @RequestMapping("/add-product/{productId}")
    public String addProduct (Model model,@PathVariable Long productId){

        // id cart
        Long idCart = 7L;

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

    @RequestMapping("/cart/{id}")
    public String getCart(@PathVariable Long id, Model model){
        Optional<CartBean> cartInstance = msCartProxy.getCart(1L);
        // Boucler sur les cart items et pour chaque cart item
        // Récupérer les informations du produit (avec le msProductProxy) pour chaque cart item (productId)
        // Pour chaque produit récupéré, on ajoute au modèle (model) les informations du produit récupéré pour pouvoir les afficher dans la page
        // Pour ajouter les produits au modèle :
        // 1. On construit une liste (List<ProductBean> products)
        // 2. Pour chaque produit on l'ajoute à la liste
        // 3. A la fin, on ajoute notre liste "products" au "model"


        //nb d'élément

        List<CartItemBean> cartItemsBean = cartInstance.get().getProducts();


        List<ProductBean> products = null;
        Double total=0.0;

        for (int i=0; i<cartItemsBean.size(); i++){


            Optional<ProductBean> productBean = msProductProxy.get(cartItemsBean.get(i).getProductId());
            products.add(productBean.get());
            total+=cartItemsBean.get(i).getQuantity()* productBean.get().getPrice();


        }

        if (!cartInstance.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Specified cart doesn't exist");


        model.addAttribute("total", total);

        model.addAttribute("infoProducts", products);

        model.addAttribute("cartInstance", cartInstance.get());

        return "panier";

    }

}
