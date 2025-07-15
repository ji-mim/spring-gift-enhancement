package gift.repository;

import gift.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaProductRepository extends JpaRepository<Product, Long> {
}
