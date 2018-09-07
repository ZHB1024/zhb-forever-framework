package com.zhb.forever.framework.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class PoiUtil {
    
    public static Workbook createWorkbook(InputStream inputStream) throws InvalidFormatException, IOException {
        if (null == inputStream) {
            return null;
        }
        return WorkbookFactory.create(inputStream);
    }

}
