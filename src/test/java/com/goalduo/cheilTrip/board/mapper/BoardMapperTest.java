package com.goalduo.cheilTrip.board.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 db로 테스트
class BoardMapperTest {

    @Autowired
    private BoardMapper boardMapper;

    @Test
    @DisplayName("검색조건으로 게시글 가져오기 테스트")
    void getTotalArticleCount() throws SQLException {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", "ssafy");
        int result = boardMapper.getTotalArticleCount(map);
        Assertions.assertThat(result).isEqualTo(0);
    }


}