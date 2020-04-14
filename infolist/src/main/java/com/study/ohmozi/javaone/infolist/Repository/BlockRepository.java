package com.study.ohmozi.javaone.infolist.Repository;

import com.study.ohmozi.javaone.infolist.domain.Block;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BlockRepository extends CrudRepository<Block, Long> {      //자동으로 @Repsitory어노테이션이 처리됨

    List<Block> findAll();

    Block save(Block block);
}
