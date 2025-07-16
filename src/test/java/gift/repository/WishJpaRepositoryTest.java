package gift.repository;


import static org.assertj.core.api.Assertions.*;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import gift.util.ShaUtil;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WishJpaRepositoryTest {

    @Autowired
    private WishJpaRepository wishRepository;
    @Autowired
    private MemberJpaRepository memberRepository;
    @Autowired
    private ProductJpaRepository productRepository;

    @Test
    void save() {
        String password = "123";
        String salt = ShaUtil.getSalt();
        String encryptPassword = ShaUtil.encrypt(password, salt);
        Member member = memberRepository.save(
                new Member(null, "ex@email.com", encryptPassword, salt));

        Product savedProduct = productRepository.save(
                new Product(null, "product1", 1000, "url.com"));

        Wish savedWish = wishRepository.save(new Wish(null, member, savedProduct, 1));
        assertThat(savedWish.getId()).isNotNull();
    }

    @Test
    void getWishListByMember() {
        String password = "123";
        String salt = ShaUtil.getSalt();
        String encryptPassword = ShaUtil.encrypt(password, salt);
        Member member = memberRepository.save(
                new Member(null, "ex@email.com", encryptPassword, salt));

        Product product1 = productRepository.save(
                new Product(null, "product1", 1000, "url.com"));

        Product product2 = productRepository.save(
                new Product(null, "product2", 1000, "url.com"));

        wishRepository.save(new Wish(null, member, product1, 1));
        wishRepository.save(new Wish(null, member, product2, 1));

        List<Wish> members = wishRepository.findByMemberId(member.getId());
        assertThat(members.size()).isEqualTo(2);
    }

    @Test
    void updateQuantity() {
        String password = "123";
        String salt = ShaUtil.getSalt();
        String encryptPassword = ShaUtil.encrypt(password, salt);
        Member member = memberRepository.save(
                new Member(null, "ex@email.com", encryptPassword, salt));

        Product savedProduct = productRepository.save(
                new Product(null, "product1", 1000, "url.com"));

        Wish savedWish = wishRepository.save(new Wish(null, member, savedProduct, 1));
        savedWish.update(3);
        assertThat(savedWish.getQuantity()).isEqualTo(3);
    }

    @Test
    void delete() {
        String password = "123";
        String salt = ShaUtil.getSalt();
        String encryptPassword = ShaUtil.encrypt(password, salt);
        Member member = memberRepository.save(
                new Member(null, "ex@email.com", encryptPassword, salt));

        Product product1 = productRepository.save(
                new Product(null, "product1", 1000, "url.com"));

        Product product2 = productRepository.save(
                new Product(null, "product2", 1000, "url.com"));

        Wish wish1 = new Wish(null, member, product1, 1);
        Wish wish2 = new Wish(null, member, product2, 1);
        wishRepository.save(wish1);
        wishRepository.save(wish2);
        wishRepository.deleteById(wish1.getId());
        assertThat(wishRepository.findByMemberId(member.getId()).size()).isEqualTo(1);
    }
}