package com.hex.file.service;

import com.hex.file.domain.HexFile;

import java.util.List;
import java.util.Optional;

/**
 * User: hexuan
 * Date: 2019/3/12
 * Time: 5:17 PM
 */
public interface HexFileService {

    HexFile save(HexFile hexFile);

    Optional<HexFile> findById(String id);

    List<HexFile> findByIdList(List<String> idList);

    void deleteById(String id) throws Exception;

    void delete(HexFile hexFile);

    void deleteByIdList(List<String> idList);

}
