package com.hex.file.repository;

import com.hex.file.domain.HexFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * User: hexuan
 * Date: 2019/3/12
 * Time: 5:15 PM
 */
public interface HexFileRepository extends JpaRepository<HexFile, String> {
}
