package gift.repository;

import static org.assertj.core.api.Assertions.*;

import gift.domain.Member;
import gift.util.ShaUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(MemberJpaRepository.class)
class MemberJpaRepositoryTest {

    @Autowired
    private MemberRepository repository;

    @Test
    void save() {
        String password = "123";
        String salt = ShaUtil.getSalt();
        String encryptPassword = ShaUtil.encrypt(password, salt);
        Member saveMember = repository.save("ex@email.com", encryptPassword, salt);
        assertThat(saveMember.getEmail()).isEqualTo("ex@email.com");
    }

    @Test
    void findById() {
        String password = "123";
        String salt = ShaUtil.getSalt();
        String encryptPassword = ShaUtil.encrypt(password, salt);
        Member saveMember = repository.save("ex@email.com", encryptPassword, salt);
        assertThat(repository.findById(saveMember.getId()).get()).isEqualTo((saveMember));
    }

    @Test
    void findByEmail() {
        String password = "123";
        String salt = ShaUtil.getSalt();
        String encryptPassword = ShaUtil.encrypt(password, salt);
        Member saveMember = repository.save("ex@email.com", encryptPassword, salt);
        assertThat(repository.findByEmail(saveMember.getEmail()).get()).isEqualTo(saveMember);
    }

    @Test
    void findAll() {
        String password = "123";
        String salt = ShaUtil.getSalt();
        String encryptPassword = ShaUtil.encrypt(password, salt);
        Member saveMember1 = repository.save("ex1@email.com", encryptPassword, salt);
        Member saveMember2 = repository.save("ex2@email.com", encryptPassword, salt);
        assertThat(repository.findAll().size()).isEqualTo(2);
    }

    @Test
    void update() {
        String password = "123";
        String salt = ShaUtil.getSalt();
        String encryptPassword = ShaUtil.encrypt(password, salt);
        Member saveMember = repository.save("ex@email.com", encryptPassword, salt);
        Member updateMember = repository.update(saveMember.getId(), "update@email.com", encryptPassword, salt);

        assertThat(repository.findById(saveMember.getId()).get().getEmail()).isEqualTo(
                "update@email.com");
    }

    @Test
    void delete() {
        String password = "123";
        String salt = ShaUtil.getSalt();
        String encryptPassword = ShaUtil.encrypt(password, salt);
        Member saveMember1 = repository.save("ex1@email.com", encryptPassword, salt);
        repository.delete(saveMember1.getId());
        assertThat(repository.findAll().size()).isEqualTo(0);

    }

}