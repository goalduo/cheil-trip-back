package com.goalduo.cheilTrip.member.mapper;

import com.goalduo.cheilTrip.member.dto.Member;
import com.goalduo.cheilTrip.util.Encrypt;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 db로 테스트
public class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("회원가입 테스트")
    void getTotalArticleCount() throws SQLException {
        Member member = new Member();
        String salt = Encrypt.getSalt();
        String encrypted = Encrypt.getEncrypt(member.getUserPass(), salt);
        member.setUserId("ssafy");
        member.setUserName("wook2");
        member.setUserEmail("ssafy@ssafy.com");
        member.setSalt(salt);
        member.setUserPass(encrypted);
        int result = memberMapper.registerMember(member);
        Assertions.assertThat(result).isEqualTo(1);
    }

}
