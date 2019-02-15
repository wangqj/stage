package com.cloudnative;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController {

    @RequestMapping(value="/price/{id}",method = RequestMethod.GET)
    public String getPriceByID(@PathVariable Long id){
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("id = [" + id + "]");
        return "{id:"+id+"}";
    }
}
