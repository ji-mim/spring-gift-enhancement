package gift.repository;

import gift.domain.Wish;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishJpaRepository extends JpaRepository<Wish, Long> {
    Page<Wish> findByMemberId(Long memberId, Pageable pageable);

    @Query("select w from Wish w where w.member.id = :memberId and w.product.id = :productId")
    Optional<Wish> findByMemberIdAndProductId(@Param("memberId") Long memberId, @Param("productId") Long productId);
}
