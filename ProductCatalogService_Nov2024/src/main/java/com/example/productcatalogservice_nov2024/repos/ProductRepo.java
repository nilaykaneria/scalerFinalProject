package com.example.productcatalogservice_nov2024.repos;

import com.example.productcatalogservice_nov2024.models.Product;
import com.example.productcatalogservice_nov2024.models.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//Why we need 1 repo for each Table (is repo here DAO)??
//1 Model, 1 Table, 1 Repository
@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

    Optional<Product> findById(Long productId);

    Optional<Product> createProduct(Product product);

    Optional<List<Product>> getAllProducts();


//    List<Product> findProductByIsPrimeAndSate(Boolean isPrime, State sate);

    Product save(Product product);

    void deleteById(Long productId);

    List<Product> findProductByPriceBetween(Double low, Double high);

    List<Product> findProductByIsPrime(Boolean isPrime);

//    List<Product> findProductOrderByPrice(); HUMAN ERROR
    List<Product> findProductByOrderByPriceAsc();

    //This is used to tell JPA (use our query)
    @Query("SELECT p.description from Product p where p.id =?1")
    String findProductDescriptionById(Long productId);
//    ?1 take the first parameter from the function

    @Query("select c.name from Category c join Product p on p.category.id = c.id where p.id =: productId")
    String findCategoryNameByProductId(Long productId);

    Page<Product> findByName(String query, PageRequest pageRequest);
}
