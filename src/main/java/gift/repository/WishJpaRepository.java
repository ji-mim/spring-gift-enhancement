package gift.repository;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class WishJpaRepository {

    private final SpringDataJpaWishRepository jpaRepository;

    public WishJpaRepository(SpringDataJpaWishRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public Wish save(Long productId, Member member, Product product,  int quantity) {
        return jpaRepository.save(new Wish(null, member, product, quantity));
    }

    public List<Wish> findAllByMember(Long memberId) {
        return jpaRepository.findByMemberId(memberId);
    }

    public void delete(Long wishId) {
        jpaRepository.deleteById(wishId);
    }

    public Optional<Wish> findById(Long id) {
        return jpaRepository.findById(id);
    }

    public void update(int quantity, Long wishId) {
        jpaRepository.findById(wishId)
                .map(wish -> {
                    wish.update(quantity);
                    return wish;
                }).get();
    }
}
