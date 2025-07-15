package gift.repository;

import gift.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MemberJpaRepository implements MemberRepository {

    private final SpringDataJpaMemberRepository jpaRepository;

    public MemberJpaRepository(SpringDataJpaMemberRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Member save(String email, String password, String salt) {
        return jpaRepository.save(new Member(null, email, password, salt));
    }

    @Override
    public Optional<Member> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return jpaRepository.findByEmail(email);
    }

    @Override
    public List<Member> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Member update(Long id, String email, String password, String salt) {
        return jpaRepository.findById(id)
                .map(member -> {
                    member.update(email, password, salt);
                    return member;
                }).get();
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }
}
