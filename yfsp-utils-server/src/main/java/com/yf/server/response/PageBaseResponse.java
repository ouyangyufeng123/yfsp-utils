package com.yf.server.response;

import lombok.Data;

import java.beans.ConstructorProperties;
import java.util.List;

/**
 *
 * @author ouyangyufeng
 * @date 2019/9/24
 */
@Data
public class PageBaseResponse<T> extends BaseResponse {
    private long pageSize;

    private long pageIndex;

    private long pageCount;

    private long totalCount;

    private List<T> list;

    public PageBaseResponse() {
    }

    @ConstructorProperties({"pageSize", "pageIndex", "pageCount", "totalCount", "list"})
    public PageBaseResponse(long pageSize, long pageIndex, long pageCount, long totalCount, List<T> list) {
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.pageCount = pageCount;
        this.totalCount = totalCount;
        this.list = list;
    }
}
