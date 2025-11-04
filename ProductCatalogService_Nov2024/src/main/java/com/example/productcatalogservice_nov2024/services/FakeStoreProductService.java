package com.example.productcatalogservice_nov2024.services;

import com.example.productcatalogservice_nov2024.clients.IApiClient;
import com.example.productcatalogservice_nov2024.dtos.FakeStoreProductDto;
import com.example.productcatalogservice_nov2024.models.Category;
import com.example.productcatalogservice_nov2024.models.Product;
import com.example.productcatalogservice_nov2024.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Primary
@Service("fks") //for beam
public class FakeStoreProductService implements IProductService {
//This class must talk in-terms of Product not DTO'S

    private static final String CacheKey = "_products_";

    @Autowired
    private IApiClient IfakeStoreApiClient;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
//    @Override
//    public List<Product> getProducts(){
//        Cant use the below line ask GPT (List<genrics> because of that)idk just check
//        ResponseEntity<List<FakeStoreProductDto>> listResponseEntity = restTemplate.getForEntity("http://fakestoreapi.com/products", List<FakeStoreProductDto>.class);
//        ResponseEntity<FakeStoreProductDto[]> listResponseEntity
//                = requestForEntity("http://fakestoreapi.com/products", HttpMethod.GET ,null ,FakeStoreProductDto[].class,null);
//        if(listResponseEntity.getBody() == null || listResponseEntity.getStatusCode() == HttpStatus.valueOf(500)){
//            return null;
//        }
//        return from(listResponseEntity.getBody());
//    }
    //    NO NEED FOR SUCH LONG CODE JUST BELOW


    @Override
    public List<Product> getProducts(){
        //Here we compulsarily make the API call (not from the cache)
        FakeStoreProductDto[] fakeStoreProductDtoList = IfakeStoreApiClient.getProducts();
        return Utility.getProductList(fakeStoreProductDtoList);
    }

    @Override
    public Product getProductById(Long productId) {

        //FIRST WE WILL CHECK IN CACHE (IF PRESENT RETURN, ELSE MAKE 3RD PARTY CALL)

        FakeStoreProductDto fakeStoreProductDto = null;
        fakeStoreProductDto = (FakeStoreProductDto) redisTemplate.opsForHash().get(CacheKey, productId);

        //(CatchMiss)
        if(fakeStoreProductDto == null){
            //Make API call
            fakeStoreProductDto = IfakeStoreApiClient.getProductById(productId);
            //Store this result in the cache
            redisTemplate.opsForHash().put(CacheKey, productId, fakeStoreProductDto); //Like table name, PK, row value
        }

        return Utility.getProduct(fakeStoreProductDto);
    }


    @Override
    public Product createProduct(Product inputProduct) {
        FakeStoreProductDto inputFakeStoreProductDto = Utility.getFakeStoreProductDto(inputProduct);
        FakeStoreProductDto responseFakeStoreProductDto = IfakeStoreApiClient.createProduct(inputFakeStoreProductDto);
        if(responseFakeStoreProductDto != null){
            //Store in cache
            redisTemplate.opsForHash().put(CacheKey, inputProduct.getId(), responseFakeStoreProductDto);
        }
        return Utility.getProduct(responseFakeStoreProductDto);
    }

    @Override
    public Product replaceProduct(Long productId, Product inputProduct) {
        FakeStoreProductDto inputFakeStoreProductDto = Utility.getFakeStoreProductDto(inputProduct);
        FakeStoreProductDto responseFakeStoreProductDto = IfakeStoreApiClient.replaceProduct(productId,inputFakeStoreProductDto);
        if(responseFakeStoreProductDto != null){
            //Store in cache
            redisTemplate.opsForHash().put(CacheKey, inputProduct.getId(), responseFakeStoreProductDto);
        }
        return Utility.getProduct(responseFakeStoreProductDto);
    }

    @Override
    public Product deleteProduct(Long productId) {
        FakeStoreProductDto responseFakeStoreProductDto = IfakeStoreApiClient.deleteProduct(productId);
        return Utility.getProduct(responseFakeStoreProductDto);
    }

    @Override
    public Product getProductBasedOnUserScope(Long productId, Long userId) {
        return null;
    }



}
