package gift.repository;

import gift.domain.Option;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionJpaRepository extends JpaRepository<Option, Long> {

    List<Option> findByProduct_Id(Long productId);

    Page<Option> findByProduct_Id(Long productId, Pageable pageable);
}
