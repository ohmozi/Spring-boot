package com.study.ohmozi.javaone.infolist.domain;

import com.study.ohmozi.javaone.infolist.Repository.BlockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BlockTests {

    @Autowired
    private BlockRepository blockRepository;

    @Test
    public void crud(){

        Block block = Block.builder()
                .name("ohmozi")
                .reason("annoying me")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();

        blockRepository.save(block);

        List<Block> blockList = blockRepository.findAll();

        assertThat(blockList.size()).isEqualTo(3);      //단위테스트로 하면 통과하지만 전체 테스트로하면 sql문과 new block이 생성되서 3개가
        assertThat(blockList.get(2).getName()).isEqualTo("ohmozi");

    }
}