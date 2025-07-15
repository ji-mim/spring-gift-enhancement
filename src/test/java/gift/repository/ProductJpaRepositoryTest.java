package gift.repository;

import static org.assertj.core.api.Assertions.*;

import gift.domain.Product;
import gift.dto.CreateProductRequest;
import gift.dto.UpdateProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(ProductJpaRepository.class)
class ProductJpaRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Test
    void save() {
        Product savedProduct = repository.save(
                new CreateProductRequest("product1", 1000, "url.com"));
        assertThat(savedProduct.getId()).isNotNull();
    }

    @Test
    void findById() {
        Product savedProduct = repository.save(
                new CreateProductRequest("product1", 1000, "url.com"));
        assertThat(repository.findById(savedProduct.getId()).get()).isEqualTo(savedProduct);
    }

    @Test
    void findAll() {
        repository.save(
                new CreateProductRequest("product1", 1000, "url.com"));
        repository.save(
                new CreateProductRequest("product2", 2000, "url.com"));
        assertThat(repository.findAll().size()).isEqualTo(2);
    }

    @Test
    void update() {
        Product savedProduct = repository.save(
                new CreateProductRequest("product1", 1000, "url.com"));

        Product updateProduct = repository.update(savedProduct.getId(),
                new UpdateProductRequest(savedProduct.getId(), "updateName", 1000, "update.com"));

        assertThat(updateProduct.getName()).isEqualTo("updateName");
    }

    @Test
    void delete() {
        Product savedProduct = repository.save(
                new CreateProductRequest("product1", 1000, "url.com"));

        repository.delete(savedProduct.getId());

        assertThat(repository.findAll()).size().isEqualTo(0);
    }
}