package com.epam.ilya.model;

import java.util.ArrayList;

public class PaginatedList<T> extends ArrayList<T> {
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
