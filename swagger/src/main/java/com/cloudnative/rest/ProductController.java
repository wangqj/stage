package com.cloudnative.rest;

import com.cloudnative.dto.Product;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wangqijun
 * @version 1.0.0
 *
 */
@RestController
@RequestMapping(value="/products")     // 通过这里配置使下面的映射都在/products下
public class ProductController {
    private List<Product> productList;
    //初始化
    public ProductController(){
        productList = new ArrayList<Product>();
        for (int i = 0; i < 10; i++) {
            Product product =new Product();
            product.setId(i);
            product.setCount(i+10);
            product.setName("watch"+i);
            product.setDesc("watch desc"+i);
            productList.add(product);
        }
    }
    @ApiOperation(value="获取产品列表", notes="获取产品列表")
    @RequestMapping(value={""}, method= RequestMethod.GET)
    public List<Product> getProductList() {
        return productList;
    }

    @ApiOperation(value="获取产品详细信息", notes="根据url的id来获取产品详细信息")
    @ApiImplicitParam(name = "id", value = "产品ID", required = true, dataType = "Integer",paramType="path")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Product getProduct(@PathVariable Integer id) {
        return productList.get(id);
    }
}
