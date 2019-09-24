package com.yf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ouyangyufeng
 * @date 2019/9/24
 */
@Data
public class BaseDomain implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "objId", type = IdType.AUTO)
    private long objId;

    private Date createdTime;

    private Date updatedTime;

    private String createdName;

    private String updatedName;

    private Integer status = 1;

    private String operator;

    private String remark;

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException var2) {
            throw new RuntimeException(var2);
        }
    }

}
