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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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


    @RequestMapping("/cart")
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

        for (int i=0; i<cartItemsBean.size(); i++){


            Optional<ProductBean> productBean = msProductProxy.get(cartItemsBean.get(i).getProductId());
            products.add(productBean.get());

        }

        if (!cartInstance.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Specified cart doesn't exist");


        model.addAttribute("infoProducts", products);

        model.addAttribute("cartInstance", cartInstance.get());

        return "panier";

    }




}
