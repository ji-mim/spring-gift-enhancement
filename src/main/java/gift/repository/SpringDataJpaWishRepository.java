package gift.repository;

import gift.domain.Wish;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaWishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findByMemberId(Long memberId);
}
