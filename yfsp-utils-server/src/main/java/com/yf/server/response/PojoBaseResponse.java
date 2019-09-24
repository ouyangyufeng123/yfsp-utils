package com.yf.server.response;

import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;

/**
 *
 * @author ouyangyufeng
 * @date 2019/9/24
 */
@Getter
@Setter
public class PojoBaseResponse<T> extends BaseResponse {
    private T data;

    public PojoBaseResponse() {
    }

    @ConstructorProperties({"data"})
    public PojoBaseResponse(T data) {
        this.data = data;
    }
}
