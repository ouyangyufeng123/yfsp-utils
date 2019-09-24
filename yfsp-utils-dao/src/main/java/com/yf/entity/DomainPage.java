package com.yf.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ouyangyufeng
 * @date 2019/9/24
 */
@Data
public class DomainPage <T extends BaseDomain> implements Serializable {

    private static final long serialVersionUID = 1L;
    private long pageSize;
    private long pageIndex;
    private long pageCount;
    private long domainTotalCount;
    private List<T> domains = new ArrayList();

    public DomainPage() {
    }

    public DomainPage(long pageSize, long pageIndex, long domainTotalCount) {
        if (pageIndex < 1L) {
            pageIndex = 1L;
        }

        if (pageSize < 1L) {
            pageSize = 1L;
        }

        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.domainTotalCount = domainTotalCount;
        if (domainTotalCount % pageSize > 0L) {
            this.pageCount = domainTotalCount / pageSize + 1L;
        } else {
            this.pageCount = domainTotalCount / pageSize;
        }
    }

    public DomainPage(long pageSize, long pageIndex, long pageCount, long domainTotalCount, List<T> domains) {
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.pageCount = pageCount;
        this.domainTotalCount = domainTotalCount;
        this.domains = domains;
    }

}
