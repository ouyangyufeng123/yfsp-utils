package com.yf.server.response.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author ouyangyufeng
 * @date 2019/9/24
 */
@Data
public class BaseVo {

    private long objId;

    private Date createdTime;

    private Date updatedTime;

    private String createdName;

    private String updatedName;

    private Integer status = 1;

    private String operator;

    private String remark;

}
