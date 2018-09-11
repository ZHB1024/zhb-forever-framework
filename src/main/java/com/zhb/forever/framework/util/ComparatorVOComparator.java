package com.zhb.forever.framework.util;

import java.util.Comparator;

import com.zhb.forever.framework.vo.ComparatorVO;

public class ComparatorVOComparator implements Comparator<ComparatorVO> {

    @Override
    public int compare(ComparatorVO o1, ComparatorVO o2) {
        if (null == o1 || null == o1.getOrder()) {
            return 1;
        }
        if (null == o2 || null == o2.getOrder()) {
            return -1;
        }
        return o1.getOrder().compareTo(o2.getOrder());
    }


}
