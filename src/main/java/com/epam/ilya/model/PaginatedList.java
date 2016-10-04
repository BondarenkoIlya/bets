package com.epam.ilya.model;

import java.util.ArrayList;

/**
 * PaginatedList ia class-wrapper around ArrayList for contain additional information
 * for more convenient display on view current group of objects <T> in range.
 *
 * @param <T>
 * @author Bondarenco Ilya
 */

public class PaginatedList<T extends BaseEntity> extends ArrayList<T> {
    private int pageNumber;
    private int pageSize;
    private int pageCount;


    public PaginatedList(int pageNumber, int pageSize) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
