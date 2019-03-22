package com.hex.file.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.util.Date;

/**
 * User: hexuan
 * Date: 2019/3/12
 * Time: 5:12 PM
 */
@Entity
@DynamicUpdate
@Data
public class HexFile {

    @javax.persistence.Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 50)
    private String Id;

    private String fileName;

    /**
     * 创建时间
     */
    private Date createTime = new Date();
}
