package com.example.productcatalogservice_nov2024.clients;

import com.example.productcatalogservice_nov2024.dtos.FakeStoreProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


//BASICALLY COMPONENT IS A BASIC VANILLA FLAVOUR AND @SERVICE IS like chocolate flavour (more precise ) made from the vanilla
// flavour only

//THIS CLASS WILL COMMUNICATE IN-TERMS OF FAKE_STORE_PRODUCT_DTO
//CLIENT LAYER 1 //BASICALLY HOW WE WILL MAKE AN 3RD PARTY API CALL AND HOW RESPONSE COMES IS DONE HERE
@Component
public class FakeStoreApiClient implements IApiClient {
    //THIS IS the sublayer of service layer
    //According to the Design Principal we need to also make a interface for this and then call API request

    @Autowired
    private RestTemplateBuilder restTemplateBuilder; //PROVIDES A REST TEMPLATE FOR EASY REQ RESP

    @Override
    public FakeStoreProductDto[] getProducts() {
        ResponseEntity<FakeStoreProductDto[]> listFakeStoreProductDtoResponseEntity
                = requestForEntity("http://fakestoreapi.com/products", HttpMethod.GET ,null ,FakeStoreProductDto[].class,null);
        return validateResponses(listFakeStoreProductDtoResponseEntity);
    }

    @Override
    public FakeStoreProductDto getProductById(Long productId) {
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity
                = requestForEntity("http://fakestoreapi.com/products/{productId}", HttpMethod.GET, null,FakeStoreProductDto.class, productId);
        return validateResponse(fakeStoreProductDtoResponseEntity);
    }

    @Override
    public FakeStoreProductDto createProduct(FakeStoreProductDto fakeStoreProductDto) {
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity =
                requestForEntity("http://fakestoreapi.com/products/", HttpMethod.POST, fakeStoreProductDto, FakeStoreProductDto.class, (Object[]) null);
        return validateResponse(fakeStoreProductDtoResponseEntity);
    }

    @Override
    public FakeStoreProductDto replaceProduct(Long productId, FakeStoreProductDto inputFakeStoreProductDto) {
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity
                = requestForEntity("http://fakestoreapi.com/products/{productId}",HttpMethod.PUT,inputFakeStoreProductDto,FakeStoreProductDto.class,productId);
        return validateResponse(fakeStoreProductDtoResponseEntity);
    }

    @Override
    public FakeStoreProductDto deleteProduct(Long productId) {
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity =
                requestForEntity("http://fakestoreapi.com/products/{productId}", HttpMethod.DELETE, null, FakeStoreProductDto.class, productId);
        return validateResponse(fakeStoreProductDtoResponseEntity);
    }


    //CUSTOM FUNCTIONS
    //ONE FUNCTION FOR ALL TYPE OF API CALL
    private  <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod, @Nullable Object request, Class<T> responseType, @Nullable Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build(); // This line is added and replaced in place of this from the restTemplate class
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        //if urlVariables empty then don't send urlVariables
        if(uriVariables == null || uriVariables.length == 0){
            return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor);
        }
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    //TO VALIDATE AND SEND RESPONSE ACCORDINGLY
    private FakeStoreProductDto validateResponse(ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity) {
        if(fakeStoreProductDtoResponseEntity.getBody() == null || fakeStoreProductDtoResponseEntity.getStatusCode() == HttpStatus.valueOf(500)){
            return null;
        }
        return fakeStoreProductDtoResponseEntity.getBody();
    }

    private FakeStoreProductDto[] validateResponses(ResponseEntity<FakeStoreProductDto[]> arrayFakeStoreProductDtoResponseEntity) {
        if(arrayFakeStoreProductDtoResponseEntity.getBody() == null || arrayFakeStoreProductDtoResponseEntity.getStatusCode() == HttpStatus.valueOf(500)){
            return null;
        }
        return arrayFakeStoreProductDtoResponseEntity.getBody();
    }
}
