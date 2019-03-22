package com.hex.file.controller;

import com.hex.file.domain.HexFile;
import com.hex.file.domain.Result;
import com.hex.file.enums.ResultEnum;
import com.hex.file.form.CallBackParam;
import com.hex.file.form.CallBackParamListForm;
import com.hex.file.service.HexFileService;
import com.hex.file.util.FileUtil;
import com.hex.file.util.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: hexuan
 * Date: 2019/3/12
 * Time: 5:12 PM
 */
@CrossOrigin
@RestController
public class FileController {

    @Autowired
    private HexFileService hexFileService;

    @Value("${web.upload-path}")
    private String path;

    @Value("${web.zip-file-limit}")
    private Long zipFileLimit;

    @Value("${web.file-server-path}")
    private String serverPath;

    /**
     * 新存图片
     *
     * @param file
     * @param oFileId
     * @param callBackPath
     * @param callBackParamListForm
     * @return
     */
    @PostMapping("/saveFile")
    public Result saveFile(MultipartFile file,
                           @RequestParam(required = false) String oFileId,
                           String callBackPath, CallBackParamListForm callBackParamListForm) throws Exception {

        /**
         * 如果旧文件id不为空，先删除旧文件
         */
        if (StringUtils.isNotBlank(oFileId)) {
            hexFileService.deleteById(oFileId);
        }

        HexFile hexFile = new HexFile();
        try {
            hexFile.setFileName(FileUtil.uploadImgFileNew(file, null, null, path, zipFileLimit));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.SAVE_ERROR.getCode(), ResultEnum.SAVE_ERROR.getMsg());
        }
        hexFile = hexFileService.save(hexFile);

        if (StringUtils.isNotBlank(callBackPath)) {
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            for (CallBackParam callBackParam : callBackParamListForm.getCallBackParamList()) {
                param.add(callBackParam.getKey(), callBackParam.getValue());
            }
            param.add("fileId", hexFile.getId());
            param.add("filePath", serverPath + hexFile.getFileName());
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(callBackPath, param, String.class);
        }

        return ResultUtil.success();
    }

    @Transactional
    @PostMapping("/saveFileList")
    public Result saveFileList(List<MultipartFile> fileList,
                               String callBackPath, CallBackParamListForm callBackParamListForm) {
        HexFile hexFile;
        List<HexFile> hexFileList = new ArrayList<>();
        for (MultipartFile file : fileList) {
            hexFile = new HexFile();
            try {
                hexFile.setFileName(FileUtil.uploadImgFileNew(file, null, null, path, zipFileLimit));
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.error(ResultEnum.SAVE_ERROR.getCode(), ResultEnum.SAVE_ERROR.getMsg());
            }
            hexFileList.add(hexFileService.save(hexFile));
        }

        if (StringUtils.isNotBlank(callBackPath)) {
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            for (CallBackParam callBackParam : callBackParamListForm.getCallBackParamList()) {
                param.add(callBackParam.getKey(), callBackParam.getValue());
            }
            List<String> fileIdList = hexFileList.stream().map(hexFileTemp -> hexFileTemp.getId()).collect(Collectors.toList());
            List<String> filePathList = hexFileList.stream().map(hexFileTemp -> serverPath + hexFileTemp.getFileName()).collect(Collectors.toList());
            param.add("fileIdList", fileIdList);
            param.add("filePathList", filePathList);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(callBackPath, param, String.class);
        }
        return ResultUtil.success();
    }

    @DeleteMapping("/deleteFile")
    public Result deleteFile(String id,
                             String callBackPath, CallBackParamListForm callBackParamListForm) throws Exception {
        if (StringUtils.isBlank(id)) {
            return ResultUtil.error(ResultEnum.PARAM_ERROR.getCode(), ResultEnum.PARAM_ERROR.getMsg());
        }
        hexFileService.deleteById(id);

        if (StringUtils.isNotBlank(callBackPath)) {
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            for (CallBackParam callBackParam : callBackParamListForm.getCallBackParamList()) {
                param.add(callBackParam.getKey(), callBackParam.getValue());
            }
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(callBackPath, param, String.class);
        }

        return ResultUtil.success();
    }

    @Transactional
    @DeleteMapping("/deleteFileList")
    public Result deleteFileList(@RequestParam(name = "idList") List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return ResultUtil.error(ResultEnum.PARAM_ERROR.getCode(), ResultEnum.PARAM_ERROR.getMsg());
        }
        hexFileService.deleteByIdList(idList);
        return ResultUtil.success();
    }

    @PostMapping("/testMsg")
    public Result testMsg(String msg) {
        return ResultUtil.success(msg);
    }

    @GetMapping("/testRequest")
    public Object testRequest(HttpServletRequest httpServletRequest) {
        /**
         * 获取服务地址 http://127.0.0.1:8082
         */
        String path = httpServletRequest.getRequestURL().substring(0, httpServletRequest.getRequestURL().indexOf(httpServletRequest.getRequestURI()));
        return path;
    }

}
