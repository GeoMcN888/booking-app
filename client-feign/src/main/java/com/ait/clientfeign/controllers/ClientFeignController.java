package com.ait.clientfeign.controllers;

import com.ait.clientfeign.feignclients.SupplierServiceClient;
import com.ait.clientfeign.feignclients.WineCellarServiceClientZuul;
import com.ait.clientfeign.feignclients.WineServiceClient;
import com.ait.clientfeign.model.Wine;
import com.ait.clientfeign.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ClientFeignController
{
    @Autowired
    WineServiceClient wineServiceClient;

    @Autowired
    SupplierServiceClient supplierServiceClient;

    @Autowired
    WineCellarServiceClientZuul wineCellarServiceClientZuul;

/*
    @GetMapping("client-feign/wine/{wineId}")
    public Wine getWineFromWineService(@PathVariable long wineId)
    {
        return wineServiceClient.getWineById(wineId);
    }
*/

/*A lot of the ones that work are still intermittent - getting 500 error sometimes, something to do with the gateway I think*/

    /* Sorted that gateway error now. Should be working.
    */

    //works
    @GetMapping("client-feign-zuul/wine/{wineId}")
    public Wine getWineFromWineServiceZuul(@PathVariable long wineId)
    {
        return wineCellarServiceClientZuul.getWineById(wineId);
    }

    //works
    @DeleteMapping("client-feign-zuul/wine/{wineId}")
    public void deleteWineFromWineServiceZuul(@PathVariable long wineId) {
        wineCellarServiceClientZuul.deleteWineById(wineId);
    }

    //works
    @PostMapping("client-feign-zuul/wine")
    public Wine addWineToWineServiceZuul(@RequestBody Wine wine) {
        return wineCellarServiceClientZuul.addWine(wine);
    }

    //returns empty list for all different requests
    @GetMapping("client-feign-zuul/wine")
    public List<Wine> getWinesFromWineService(@RequestParam("name") Optional<String> name, @RequestParam("country") Optional<String> country, @RequestParam("grapes") Optional<String> grapes) {
        return wineCellarServiceClientZuul.getWines(name, country, grapes);
    }
/*
    @GetMapping("client-feign/suppliers/{supplierId}")
    public Supplier getSupplierFromWineService(@PathVariable long supplierId)
    {
        return supplierServiceClient.getSupplierById(supplierId);
    }*/

    //works
    @GetMapping("client-feign-zuul/suppliers/{supplierId}")
    public Supplier getSupplierFromSupplierServiceZuul(@PathVariable long supplierId){
        return wineCellarServiceClientZuul.getSupplierById(supplierId);
    }

    //works
    @PostMapping("client-feign-zuul/suppliers")
    public Supplier addSupplierFromSupplierServiceZuul(@RequestBody Supplier supplier) {
        return wineCellarServiceClientZuul.addSupplier(supplier);
    }

    //returns empty list for all kinds of requests
    @GetMapping("client-feign-zuul/suppliers")
    public List<Supplier> getSuppliersFromSupplierServiceZuul(@RequestParam("name") Optional<String> name, @RequestParam("country")Optional<String> country){
        return wineCellarServiceClientZuul.getSuppliers(name, country );
    }

    //works
    @DeleteMapping("client-feign-zuul/suppliers/{supplierId}")
    void deleteSupplierFromSupplierServiceZuul(@PathVariable long supplierId) {
        wineCellarServiceClientZuul.deleteSupplierById(supplierId);
    }

}
