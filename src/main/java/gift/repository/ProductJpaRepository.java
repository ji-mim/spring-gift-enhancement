package gift.repository;

import gift.domain.Product;
import gift.dto.CreateProductRequest;
import gift.dto.UpdateProductRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ProductJpaRepository implements ProductRepository {

    private final SpringDataJpaProductRepository jpaRepository;

    public ProductJpaRepository(SpringDataJpaProductRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Product save(CreateProductRequest request) {
        return jpaRepository.save(new Product(null, request.name(), request.price(), request.imageUrl()));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Product update(Long id, UpdateProductRequest request) {
        return jpaRepository.findById(id)
                .map(product -> {
                    product.create(request.name(), request.price(), request.imageUrl());
                    return product;
                }).get();
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }
}
