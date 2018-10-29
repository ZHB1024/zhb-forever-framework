package com.zhb.forever.framework.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T> implements Serializable {
    
    
    public static final transient Page EMPTY_PAGE = new Page(new ArrayList(0), 0, 0, 0, false);

    /**
     * 
     */
    private static final long serialVersionUID = -3427972949031011621L;
    
    /**
     * objects held by page
     */
    List<T> objects;
    int start;

    /**
     * how many records?
     */
    int totalCount = 0;
    /**
     * How many records in a page
     */
    int pageCount = 0;

    /**
     * determine if it has next page
     */
    boolean hasNext;

    public Page(List<T> l, int s, int totalCount, int pageCount, boolean hasNext) {
        objects = new ArrayList(l);
        start = s;
        this.totalCount = totalCount;
        this.pageCount = pageCount;
        this.hasNext = hasNext;
    }

    public List<T> getList() {
        return objects; 
    }

    /**
     * 返回当前页
     * @return
     */
    public int getCurrrentPage() {
        if (0 == pageCount)
            return 0;
        return start / pageCount + 1;
    }

    public int getTotalPage() {
        if (pageCount != 0)
            if (totalCount % pageCount == 0)
                return totalCount / pageCount;
            else
                return totalCount / pageCount + 1;
        else
            return 0;
    }

    public int getPageCount(){
      return pageCount;
    }

    public boolean isNextPageAvailable() {
        return hasNext;
    }

    public boolean isPreviousPageAvailable() {
        return start > 0;
    }
    
    public int getStartOfNextPage() { return start + pageCount; }

    public int getStartOfPreviousPage() {
        return Math.max(start-pageCount, 0);
    }

    public int getStartOfLastPage(){
      return pageCount*(getTotalPage()-1);
    }

    public int getSize() { return objects.size(); }
    /**
     * @return Returns the totalCount.
     */
    public int getTotalCount() {
        return totalCount;
    }
    /**
     * @param totalCount The totalCount to set.
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
    
}

