package com.zhb.forever.framework.page;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class PageUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(PageUtil.class);

    /**
     ** 获取Page对象
     * 
     * @param <T>
     * @param itr
     * @param start
     **           页的起始位置
     * @param pageCount
     **           每页的大小
     * @param totalCount
     **            记录总数
     * @return
     * 
     */
    public static <T> Page<T> getPage(Iterator<T> itr, long start, int pageCount, long totalCount) {
        List<T> list = new ArrayList<T>((int) (pageCount > totalCount ? totalCount : pageCount));
        while (itr.hasNext()) {
            T pageObject = itr.next();
            if (null != pageObject) {
                list.add(pageObject);
            } else {
                logger.error("Null object founded!");
            }
        }
        if (0 == list.size() || 0 == pageCount) {
            return new Page<T>(new ArrayList<T>(0), 0, 0, pageCount, false);
        } else {
            boolean hasNext = (start + pageCount) < totalCount ? true : false;
            return new Page<T>(list, (int) start, (int) totalCount, pageCount, hasNext);
        }
    }

    /**
     ** 在获取Page时，不合理的start导致有符 合条件的记录存在却返回"空"的page(当最后一页被操作掉时，如高考系统中的招生章程的批量审核)，
     ** 本方法用来检测start值是否合理并返回合理的start值
     * 
     * @param start
     **        页的起始位置
     * @param pageSize
     **            每页的大小
     * @param totalCount
     **            记录总数
     * @return start不合理，如果: start < 0, 直接返回0 start >= totalCount, 返回最后一页的起始位置
     */
    public static int getValidateStart(int start, int pageSize, int totalCount) {
        if (start <= 0 || totalCount <= 0) {
            return 0;
        }
        if (start < totalCount) { // 合理start，直接返回
            return start;
        } else {
            boolean flag = (totalCount % pageSize == 0); // 是否正好整页
            int lastPageCount = (flag ? pageSize : (totalCount % pageSize)); // 最后一页记录的数目
            return totalCount - lastPageCount;
        }
    }
    
    
    //将分页信息和查询结果 转成 JSONObject
    public static JSONObject pageInfo2JSON(int totalCount,int pageSize,int currentPage,JSONArray result){
        JSONObject page = new JSONObject();
        page.put("totalCount", totalCount);
        page.put("pageCount", pageSize);
        page.put("currentPage", currentPage);
        page.put("result", result);
        return page;
    }

}

