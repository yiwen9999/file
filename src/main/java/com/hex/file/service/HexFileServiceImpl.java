package com.hex.file.service;

import com.hex.file.domain.HexFile;
import com.hex.file.enums.ResultEnum;
import com.hex.file.exception.HexException;
import com.hex.file.repository.HexFileRepository;
import com.hex.file.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * User: hexuan
 * Date: 2019/3/12
 * Time: 5:17 PM
 */
@Service
public class HexFileServiceImpl implements HexFileService {

    @Value("${web.upload-path}")
    private String path;

    @Autowired
    private HexFileRepository hexFileRepository;

    @Override
    public HexFile save(HexFile hexFile) {
        return hexFileRepository.save(hexFile);
    }

    @Override
    public Optional<HexFile> findById(String id) {
        return hexFileRepository.findById(id);
    }

    @Override
    public List<HexFile> findByIdList(List<String> idList) {
        return hexFileRepository.findAllById(idList);
    }

    @Override
    public void delete(HexFile hexFile) {
        FileUtil.deleteFile(path, hexFile.getFileName());
        hexFileRepository.delete(hexFile);
    }

    @Override
    public void deleteById(String id) throws Exception{
        Optional<HexFile> hexFileOptional = hexFileRepository.findById(id);
        if (hexFileOptional.isPresent()) {
            this.delete(hexFileOptional.get());
        } else {
            throw new HexException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public void deleteByIdList(List<String> idList) {
        List<HexFile> hexFileList = hexFileRepository.findAllById(idList);
        for (HexFile hexFile : hexFileList){
            this.delete(hexFile);
        }
    }
}
