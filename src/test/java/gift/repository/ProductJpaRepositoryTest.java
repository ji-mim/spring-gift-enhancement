package gift.repository;

import static org.assertj.core.api.Assertions.*;

import gift.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductJpaRepositoryTest {

    @Autowired
    private ProductJpaRepository repository;

    @Test
    void save() {
        Product savedProduct = repository.save(
                new Product(null, "product1", 1000, "url.com"));
        assertThat(savedProduct.getId()).isNotNull();
    }

    @Test
    void findById() {
        Product savedProduct = repository.save(
                new Product(null, "product1", 1000, "url.com"));
        assertThat(repository.findById(savedProduct.getId()).get()).isEqualTo(savedProduct);
    }

    @Test
    void findAll() {
        repository.save(
                new Product(null, "product1", 1000, "url.com"));
        repository.save(
                new Product(null, "product2", 1000, "url.com"));
        assertThat(repository.findAll().size()).isEqualTo(2);
    }

    @Test
    void update() {
        Product savedProduct = repository.save(
                new Product(null, "product1", 1000, "url.com"));

        savedProduct.update("updateName", 1000, "update.com");

        assertThat(savedProduct.getName()).isEqualTo("updateName");
    }

    @Test
    void delete() {
        Product savedProduct = repository.save(
                new Product(null, "product1", 1000, "url.com"));

        repository.deleteById(savedProduct.getId());

        assertThat(repository.findAll()).size().isEqualTo(0);
    }
}