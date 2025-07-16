package gift.service;

import gift.config.UnAuthorizationException;
import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import gift.dto.*;
import gift.repository.MemberJpaRepository;
import gift.repository.ProductJpaRepository;
import gift.repository.WishJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class WishService {

    private final ProductJpaRepository productRepository;
    private final WishJpaRepository wishJpaRepository;
    private final MemberJpaRepository memberRepository;

    public WishService(ProductJpaRepository productRepository, WishJpaRepository wishJpaRepository,
            MemberJpaRepository memberRepository) {
        this.productRepository = productRepository;
        this.wishJpaRepository = wishJpaRepository;
        this.memberRepository = memberRepository;
    }


    public List<Product> productList() {
        return productRepository.findAll();
    }

    public CreateWishResponse addWishProduct(CreateWishRequest request, Long loginMemberId) {
        Member member = memberRepository.findById(loginMemberId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 멤버입니다."));
        Product product = productRepository.findById(request.productId()).orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품입니다."));
        Wish wish = wishJpaRepository.save(new Wish(null, member, product, request.quantity()));
        return new CreateWishResponse(wish.getId(), wish.getMember().getId(), wish.getProduct().getId(), wish.getQuantity());
    }

    public List<WishResponse> getMemberWishList(Long memberId) {
        return wishJpaRepository.findByMemberId(memberId).stream()
                .map(wish -> new WishResponse(
                        wish.getId(),
                        wish.getProduct().getName(),
                        wish.getProduct().getPrice(),
                        wish.getQuantity()
                ))
                .toList();
    }
    public void delete(Long wishId, Long memberId) {
        Wish wishProduct = findByIdOrThrow(wishId);
        checkAuthorization(wishProduct, memberId, "삭제 권한 없음");
        wishJpaRepository.deleteById(wishId);
    }

    public UpdateWishResponse updateQuantity(UpdateWishRequest request, Long wishId, Long memberId) {
        Wish wishProduct = findByIdOrThrow(wishId);
        checkAuthorization(wishProduct, memberId, "수정 권한 없음");
        wishProduct.update(request.quantity());
        return new UpdateWishResponse(wishProduct.getId(), wishProduct.getId(), request.quantity());
    }

    private static void checkAuthorization(Wish wishProduct, Long memberId, String exMessage) {
        if (!wishProduct.getMember().getId().equals(memberId)) {
            throw new UnAuthorizationException(exMessage);
        }
    }

    private Wish findByIdOrThrow(Long wishId) {
        Optional<Wish> findWishProduct = wishJpaRepository.findById(wishId);
        if (findWishProduct.isEmpty()) {
            throw new NoSuchElementException("존재하지 않는 위시리스트 상품");
        }
        return findWishProduct.get();
    }
}
