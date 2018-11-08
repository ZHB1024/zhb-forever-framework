package com.zhb.forever.framework.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHeight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSignedTwipsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSym;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGrid;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGridCol;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTextScale;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVerticalJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHeightRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHint;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;


/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年11月8日上午10:17:35
*/

public class Word07Util {

    /** 
     * @Description: 设置段落间距信息,一行=100 一磅=20 
     */  
    public static void setParagraphSpacingInfo(XWPFParagraph p, boolean isSpace,  
            String before, String after, String beforeLines, String afterLines,  
            boolean isLine, String line, STLineSpacingRule.Enum lineValue) {  
        CTPPr pPPr = getParagraphCTPPr(p);  
        CTSpacing pSpacing = pPPr.getSpacing() != null ? pPPr.getSpacing()  
                : pPPr.addNewSpacing();  
        if (isSpace) {  
            // 段前磅数  
            if (before != null) {  
                pSpacing.setBefore(new BigInteger(before));  
            }  
            // 段后磅数  
            if (after != null) {  
                pSpacing.setAfter(new BigInteger(after));  
            }  
            // 段前行数  
            if (beforeLines != null) {  
                pSpacing.setBeforeLines(new BigInteger(beforeLines));  
            }  
            // 段后行数  
            if (afterLines != null) {  
                pSpacing.setAfterLines(new BigInteger(afterLines));  
            }  
        }  
        // 间距  
        if (isLine) {  
            if (line != null) {  
                pSpacing.setLine(new BigInteger(line));  
            }  
            if (lineValue != null) {  
                pSpacing.setLineRule(lineValue);  
            }  
        }  
    }  
    /** 
     * @Description: 得到段落CTPPr 
     */  
    private static CTPPr getParagraphCTPPr(XWPFParagraph p) {  
        CTPPr pPPr = null;  
        if (p.getCTP() != null) {  
            if (p.getCTP().getPPr() != null) {  
                pPPr = p.getCTP().getPPr();  
            } else {  
                pPPr = p.getCTP().addNewPPr();  
            }  
        }  
        return pPPr;  
    }  
    
    /** 
     * @Description: 设置段落对齐 
     */  
    public static void setParagraphAlignInfo(XWPFParagraph p,  
            ParagraphAlignment pAlign, TextAlignment valign) {  
        if (pAlign != null) {  
            p.setAlignment(pAlign);  
        }  
        if (valign != null) {  
            p.setVerticalAlignment(valign);  
        }  
    } 
    
    public static XWPFRun getOrAddParagraphFirstRun(XWPFParagraph p, boolean isInsert,  
            boolean isNewLine) {  
        XWPFRun pRun = null;  
        if (isInsert) {  
            pRun = p.createRun();  
        } else {  
            if (p.getRuns() != null && p.getRuns().size() > 0) {  
                pRun = p.getRuns().get(0);  
            } else {  
                pRun = p.createRun();  
            }  
        }  
        if (isNewLine) {  
            pRun.addBreak();  
        }  
        return pRun;  
    }
    
    /** 
     * @Description: 设置段落文本样式(高亮与底纹显示效果不同)设置字符间距信息(CTSignedTwipsMeasure) 
     * @param verticalAlign 
     *            : SUPERSCRIPT上标 SUBSCRIPT下标 
     * @param position 
     *            :字符位置：>0提升 <0降低=磅值*2 如3磅=6 
     * @param spacingValue 
     *            :字符间距间距 >0加宽 <0紧缩 =磅值*20 如2磅=40 
     * @param indent 
     *            :字符间距缩进 <100 缩 
     */  
  
    public static void setParagraphRunFontInfo(XWPFParagraph p, XWPFRun pRun,  
            String content, String cnFontFamily, String enFontFamily,  
            String fontSize, boolean isBlod, boolean isItalic,  
            boolean isStrike, boolean isShd, String shdColor,  
            STShd.Enum shdStyle, int position, int spacingValue, int indent) {  
        CTRPr pRpr = getRunCTRPr(p, pRun);  
        if (StringUtils.isNotBlank(content)) {  
            // pRun.setText(content);  
            if (content.contains("\n")) {// System.properties("line.separator")  
                String[] lines = content.split("\n");  
                pRun.setText(lines[0], 0); // set first line into XWPFRun  
                for (int i = 1; i < lines.length; i++) {  
                    // add break and insert new text  
                    pRun.addBreak();  
                    pRun.setText(lines[i]);  
                }  
            } else {  
                pRun.setText(content, 0);  
            }  
        }  
        // 设置字体  
        CTFonts fonts = pRpr.isSetRFonts() ? pRpr.getRFonts() : pRpr  
                .addNewRFonts();  
        if (StringUtils.isNotBlank(enFontFamily)) {  
            fonts.setAscii(enFontFamily);  
            fonts.setHAnsi(enFontFamily);  
        }  
        if (StringUtils.isNotBlank(cnFontFamily)) {  
            fonts.setEastAsia(cnFontFamily);  
//            fonts.setHint(STHint.EAST_ASIA);  
        }  
        // 设置字体大小  
        CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();  
        sz.setVal(new BigInteger(fontSize));  
  
        CTHpsMeasure szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr  
                .addNewSzCs();  
        szCs.setVal(new BigInteger(fontSize));  
  
        // 设置字体样式  
        // 加粗  
        if (isBlod) {  
            pRun.setBold(isBlod);  
        }  
        // 倾斜  
        if (isItalic) {  
            pRun.setItalic(isItalic);  
        }  
        // 删除线  
        if (isStrike) {  
            pRun.setStrike(isStrike);  
        }  
        if (isShd) {  
            // 设置底纹  
            CTShd shd = pRpr.isSetShd() ? pRpr.getShd() : pRpr.addNewShd();  
            if (shdStyle != null) {  
                shd.setVal(shdStyle);  
            }  
            if (shdColor != null) {  
                shd.setColor(shdColor);  
                shd.setFill(shdColor);  
            }  
        }  
  
        // 设置文本位置  
        if (position != 0) {  
            pRun.setTextPosition(position);  
        }  
        if (spacingValue > 0) {  
            // 设置字符间距信息  
            CTSignedTwipsMeasure ctSTwipsMeasure = pRpr.isSetSpacing() ? pRpr.getSpacing() : pRpr.addNewSpacing();  
            ctSTwipsMeasure  
                    .setVal(new BigInteger(String.valueOf(spacingValue)));  
        }  
        if (indent > 0) {  
            CTTextScale paramCTTextScale = pRpr.isSetW() ? pRpr.getW() : pRpr.addNewW();  
            paramCTTextScale.setVal(indent);  
        }  
    }  
    /** 
     * @Description: 得到XWPFRun的CTRPr 
     */  
    private static CTRPr getRunCTRPr(XWPFParagraph p, XWPFRun pRun) {  
        CTRPr pRpr = null;  
        if (pRun.getCTR() != null) {  
            pRpr = pRun.getCTR().getRPr();  
            if (pRpr == null) {  
                pRpr = pRun.getCTR().addNewRPr();  
            }  
        } else {  
            pRpr = p.getCTP().addNewR().addNewRPr();  
        }  
        return pRpr;  
    } 
    
    /** 
     * @Description: 保存文档 
     */  
    public static void saveDocument(XWPFDocument document, String savePath)  
            throws Exception {  
        FileOutputStream fos = new FileOutputStream(savePath);  
        document.write(fos);  
        fos.close();  
    }
    public static void saveDocument(XWPFDocument document, OutputStream os)  
            throws Exception {  
        document.write(os);  
        os.close();  
    } 
    
    /** 
     * @Description: 设置Table的边框 
     */  
    public static void setTableBorders(XWPFTable table, STBorder.Enum borderType,  
            String size, String color, String space) {  
        CTTblPr tblPr = getTableCTTblPr(table);  
        CTTblBorders borders = tblPr.isSetTblBorders() ? tblPr.getTblBorders()  
                : tblPr.addNewTblBorders();  
        CTBorder hBorder = borders.isSetInsideH() ? borders.getInsideH()  
                : borders.addNewInsideH();  
        hBorder.setVal(borderType);  
        hBorder.setSz(new BigInteger(size));  
        hBorder.setColor(color);  
        hBorder.setSpace(new BigInteger(space));  
  
        CTBorder vBorder = borders.isSetInsideV() ? borders.getInsideV()  
                : borders.addNewInsideV();  
        vBorder.setVal(borderType);  
        vBorder.setSz(new BigInteger(size));  
        vBorder.setColor(color);  
        vBorder.setSpace(new BigInteger(space));  
  
        CTBorder lBorder = borders.isSetLeft() ? borders.getLeft() : borders  
                .addNewLeft();  
        lBorder.setVal(borderType);  
        lBorder.setSz(new BigInteger(size));  
        lBorder.setColor(color);  
        lBorder.setSpace(new BigInteger(space));  
  
        CTBorder rBorder = borders.isSetRight() ? borders.getRight() : borders  
                .addNewRight();  
        rBorder.setVal(borderType);  
        rBorder.setSz(new BigInteger(size));  
        rBorder.setColor(color);  
        rBorder.setSpace(new BigInteger(space));  
  
        CTBorder tBorder = borders.isSetTop() ? borders.getTop() : borders  
                .addNewTop();  
        tBorder.setVal(borderType);  
        tBorder.setSz(new BigInteger(size));  
        tBorder.setColor(color);  
        tBorder.setSpace(new BigInteger(space));  
  
        CTBorder bBorder = borders.isSetBottom() ? borders.getBottom()  
                : borders.addNewBottom();  
        bBorder.setVal(borderType);  
        bBorder.setSz(new BigInteger(size));  
        bBorder.setColor(color);  
        bBorder.setSpace(new BigInteger(space));  
    }  
    /** 
     * @Description: 得到Table的CTTblPr,不存在则新建 
     */  
    private static CTTblPr getTableCTTblPr(XWPFTable table) {  
        CTTbl ttbl = table.getCTTbl();  
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl  
                .getTblPr();  
        return tblPr;  
    } 
    
    /** 
     * @Description: 设置表格总宽度与水平对齐方式 
     */  
    public static void setTableWidthAndHAlign(XWPFTable table, String width,  
            STJc.Enum enumValue) {  
        CTTblPr tblPr = getTableCTTblPr(table);  
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr  
                .addNewTblW();  
        if (enumValue != null) {  
            CTJc cTJc = tblPr.addNewJc();  
            cTJc.setVal(enumValue);  
        }  
        tblWidth.setW(new BigInteger(width));  
        tblWidth.setType(STTblWidth.DXA);  
    } 
    
    /** 
     * @Description: 设置单元格Margin 
     */  
    public static void setTableCellMargin(XWPFTable table, int top, int left,  
            int bottom, int right) {  
        table.setCellMargins(top, left, bottom, right);  
    }
    
    /** 
     * @Description: 设置表格列宽 
     */  
    public static void setTableGridCol(XWPFTable table, int[] colWidths) {  
        CTTbl ttbl = table.getCTTbl();  
        CTTblGrid tblGrid = ttbl.getTblGrid() != null ? ttbl.getTblGrid()  
                : ttbl.addNewTblGrid();  
        for (int j = 0, len = colWidths.length; j < len; j++) {  
            CTTblGridCol gridCol = tblGrid.addNewGridCol();  
            gridCol.setW(new BigInteger(String.valueOf(colWidths[j])));  
        }  
    } 
    
    /** 
     * @Description: 设置行高 
     */  
    public static void setRowHeight(XWPFTableRow row, String hight,  
            STHeightRule.Enum heigthEnum) {  
        CTTrPr trPr = getRowCTTrPr(row);  
        CTHeight trHeight;  
        if (trPr.getTrHeightList() != null && trPr.getTrHeightList().size() > 0) {  
            trHeight = trPr.getTrHeightList().get(0);  
        } else {  
            trHeight = trPr.addNewTrHeight();  
        }  
        trHeight.setVal(new BigInteger(hight));  
        if (heigthEnum != null) {  
            trHeight.setHRule(heigthEnum);  
        }  
    } 
    /** 
     * @Description: 得到CTTrPr,不存在则新建 
     */  
    private static CTTrPr getRowCTTrPr(XWPFTableRow row) {  
        CTRow ctRow = row.getCtRow();  
        CTTrPr trPr = ctRow.isSetTrPr() ? ctRow.getTrPr() : ctRow.addNewTrPr();  
        return trPr;  
    } 
    
    /** 
     * @Description: 设置底纹 
     */  
    public static void setCellShdStyle(XWPFTableCell cell, boolean isShd,  
            String shdColor, STShd.Enum shdStyle) {  
        CTTcPr tcPr = getCellCTTcPr(cell);  
        if (isShd) {  
            // 设置底纹  
            CTShd shd = tcPr.isSetShd() ? tcPr.getShd() : tcPr.addNewShd();  
            if (shdStyle != null) {  
                shd.setVal(shdStyle);  
            }  
            if (shdColor != null) {  
                shd.setColor(shdColor);  
                shd.setFill(shdColor);  
            }  
        }  
    } 
    /** 
     *  
     * @Description: 得到Cell的CTTcPr,不存在则新建 
     */  
    private static CTTcPr getCellCTTcPr(XWPFTableCell cell) {  
        CTTc cttc = cell.getCTTc();  
        CTTcPr tcPr = cttc.isSetTcPr() ? cttc.getTcPr() : cttc.addNewTcPr();  
        return tcPr;  
    } 
    
    /** 
     * @Description: 得到单元格第一个Paragraph 
     */  
    public static XWPFParagraph getCellFirstParagraph(XWPFTableCell cell) {  
        XWPFParagraph p;  
        if (cell.getParagraphs() != null && cell.getParagraphs().size() > 0) {  
            p = cell.getParagraphs().get(0);  
        } else {  
            p = cell.addParagraph();  
        }  
        return p;  
    } 
    
    /** 
     * @Description: 跨列合并 
     */  
    public static void mergeCellsHorizontal(XWPFTable table, int row, int fromCell,  
            int toCell) {  
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {  
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);  
            if (cellIndex == fromCell) {  
                // The first merged cell is set with RESTART merge value  
                getCellCTTcPr(cell).addNewHMerge().setVal(STMerge.RESTART);  
            } else {  
                // Cells which join (merge) the first one,are set with CONTINUE  
                getCellCTTcPr(cell).addNewHMerge().setVal(STMerge.CONTINUE);  
            }  
        }  
    }
    
    /**
     * @Description: 设置列宽和垂直对齐方式
     */
    public static void setCellWidthAndVAlign(XWPFTableCell cell, String width,
            STTblWidth.Enum typeEnum, STVerticalJc.Enum vAlign) {
        CTTcPr tcPr = getCellCTTcPr(cell);
        CTTblWidth tcw = tcPr.isSetTcW() ? tcPr.getTcW() : tcPr.addNewTcW();
        if (width != null) {
            tcw.setW(new BigInteger(width));
        }
        if (typeEnum != null) {
            tcw.setType(typeEnum);
        }
        if (vAlign != null) {
            CTVerticalJc vJc = tcPr.isSetVAlign() ? tcPr.getVAlign() : tcPr
                    .addNewVAlign();
            vJc.setVal(vAlign);
        }
    }
    
    public static void setParagraphRunSymInfo(XWPFParagraph p, XWPFRun pRun,
            String cnFontFamily, String enFontFamily, String fontSize,
            boolean isBlod, boolean isItalic, boolean isStrike, int position,
            int spacingValue, int indent) throws Exception {
        CTRPr pRpr = getRunCTRPr(p, pRun);
        // 设置字体
        CTFonts fonts = pRpr.isSetRFonts() ? pRpr.getRFonts() : pRpr
                .addNewRFonts();
        if (StringUtils.isNotBlank(enFontFamily)) {
            fonts.setAscii(enFontFamily);
            fonts.setHAnsi(enFontFamily);
        }
        if (StringUtils.isNotBlank(cnFontFamily)) {
            fonts.setEastAsia(cnFontFamily);
            fonts.setHint(STHint.EAST_ASIA);
        }
        // 设置字体大小
        CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
        sz.setVal(new BigInteger(fontSize));

        CTHpsMeasure szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr
                .addNewSzCs();
        szCs.setVal(new BigInteger(fontSize));

        // 设置字体样式
        // 加粗
        if (isBlod) {
            pRun.setBold(isBlod);
        }
        // 倾斜
        if (isItalic) {
            pRun.setItalic(isItalic);
        }
        // 删除线
        if (isStrike) {
            pRun.setStrike(isStrike);
        }
        // 设置文本位置
        if (position != 0) {
            pRun.setTextPosition(position);
        }
        if (spacingValue > 0) {
            // 设置字符间距信息
            CTSignedTwipsMeasure ctSTwipsMeasure = pRpr.isSetSpacing() ? pRpr
                    .getSpacing() : pRpr.addNewSpacing();
            ctSTwipsMeasure
                    .setVal(new BigInteger(String.valueOf(spacingValue)));
        }
        if (indent > 0) {
            CTTextScale paramCTTextScale = pRpr.isSetW() ? pRpr.getW() : pRpr
                    .addNewW();
            paramCTTextScale.setVal(indent);
        }
        List<CTSym> symList = pRun.getCTR().getSymList();
        CTSym sym = CTSym.Factory
                .parse("<xml-fragment w:font=\"Wingdings 2\" w:char=\"00A3\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\"> </xml-fragment>");
        symList.add(sym);
    }
    
    /**
     * @Description: 添加方块♢
     */
    public static void setCellContentCommonFunction(XWPFTableCell cell, String content)
            throws Exception {
        XWPFParagraph p = cell.addParagraph();
        setParagraphSpacingInfo(p, true, "0", "0", "0", "0", true, "300",
                STLineSpacingRule.AUTO);
        setParagraphAlignInfo(p, ParagraphAlignment.BOTH, TextAlignment.CENTER);
        XWPFRun pRun = getOrAddParagraphFirstRun(p, false, false);
        setParagraphRunSymInfo(p, pRun, "宋体", "Times New Roman", "21", true,
                false, false, 0, 6, 0);
        pRun = getOrAddParagraphFirstRun(p, true, false);
        setParagraphRunFontInfo(p, pRun, content, "宋体", "Times New Roman",
                "21", true, false, false, false, null, null, 0, 6, 0);
    }
    
    public static void insertPicture(XWPFDocument document,String filePath,CTInline inline,int width, int height) throws InvalidFormatException, FileNotFoundException{
        String blipId = document.addPictureData(new FileInputStream(filePath), XWPFDocument.PICTURE_TYPE_JPEG);
        int id = document.getAllPictures().size()-1;
        final int EMU = 9525;
        width *= EMU;
        height *= EMU;
//        String blipId = document.getAllPictures().get(id).getPackageRelationship()
//                .getId();
        String picXml = ""
                + "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"
                + "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
                + "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
                + "         <pic:nvPicPr>" + "            <pic:cNvPr id=\""
                + id
                + "\" name=\"Generated\"/>"
                + "            <pic:cNvPicPr/>"
                + "         </pic:nvPicPr>"
                + "         <pic:blipFill>"
                + "            <a:blip r:embed=\""
                + blipId
                + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
                + "            <a:stretch>"
                + "               <a:fillRect/>"
                + "            </a:stretch>"
                + "         </pic:blipFill>"
                + "         <pic:spPr>"
                + "            <a:xfrm>"
                + "               <a:off x=\"0\" y=\"0\"/>"
                + "               <a:ext cx=\""
                + width
                + "\" cy=\""
                + height
                + "\"/>"
                + "            </a:xfrm>"
                + "            <a:prstGeom prst=\"rect\">"
                + "               <a:avLst/>"
                + "            </a:prstGeom>"
                + "         </pic:spPr>"
                + "      </pic:pic>"
                + "   </a:graphicData>" + "</a:graphic>";
        inline.addNewGraphic().addNewGraphicData();
        XmlToken xmlToken = null;
        try {
            xmlToken = XmlToken.Factory.parse(picXml);
        } catch (XmlException xe) {
            xe.printStackTrace();
        }
        inline.set(xmlToken);
        inline.setDistT(0);
        inline.setDistB(0);
        inline.setDistL(0);
        inline.setDistR(0);
        CTPositiveSize2D extent = inline.addNewExtent();
        extent.setCx(width);
        extent.setCy(height);
        CTNonVisualDrawingProps docPr = inline.addNewDocPr();
        docPr.setId(id);
        docPr.setName("IMG_" + id);
        docPr.setDescr("IMG_" + id);
    }

}


