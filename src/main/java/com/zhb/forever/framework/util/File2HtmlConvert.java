package com.zhb.forever.framework.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年11月8日上午9:12:10
*/

public class File2HtmlConvert {
    
    private static Logger logger = LoggerFactory.getLogger(File2HtmlConvert.class);
    
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    public static void main(String[] args) {
        File file = new File("D:\\sample.pdf");
        try {
            readPDF2String(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * *将doc文件流转化为html字符串返回
     * @param inputStream
     * @param encode
     * @return
     * @throws TransformerException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public static String doc2Html(InputStream inputStream, String encode, String tempDir) throws TransformerException, IOException, ParserConfigurationException {  
        long startTime = System.currentTimeMillis();
        List<String> uuids = new ArrayList<>();
        HWPFDocument wordDocument = new HWPFDocument(inputStream);  
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());  
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {  
            public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {  
                String uuid = RandomUtil.getRandomUUID();
                uuids.add(uuid);
                return "/temp/" + uuid +suggestedName;  
            }  
        });  
        wordToHtmlConverter.processDocument(wordDocument);  
        // 保存图片  
        List<Picture> pics = wordDocument.getPicturesTable().getAllPictures();  
        if (pics != null) {  
            File outFile = new File(tempDir);  
            if(!outFile.exists()) outFile.mkdirs();  
            for (int i = 0; i < pics.size(); i++) {  
                Picture pic = (Picture) pics.get(i);  
                System.out.println();  
                try {  
                    pic.writeImageContent(new FileOutputStream(tempDir + uuids.get(i) + pic.suggestFullFileName()));  
                } catch (FileNotFoundException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        Document htmlDocument = wordToHtmlConverter.getDocument();  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        DOMSource domSource = new DOMSource(htmlDocument);  
        StreamResult streamResult = new StreamResult(out);  
  
        TransformerFactory tf = TransformerFactory.newInstance();  
        Transformer serializer = tf.newTransformer();  
        serializer.setOutputProperty(OutputKeys.ENCODING, encode);  
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");  
        serializer.setOutputProperty(OutputKeys.METHOD, "html");  
        serializer.transform(domSource, streamResult);  
        out.close();  
        System.out.println("Generate " + " with " + (System.currentTimeMillis() - startTime) + " ms.");  
        return new String(out.toString(encode));
    }  
    
    
    /**
     * *将docx文件流转化为html字符串返回
     * @param inputStream
     * @param encode
     * @param tempDir
     * @return
     * @throws TransformerException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public static String docx2Html(InputStream inputStream, String encode, String tempDir) throws TransformerException, IOException, ParserConfigurationException {  
        long startTime = System.currentTimeMillis();  
        XWPFDocument document = new XWPFDocument(inputStream);  
        XHTMLOptions options = XHTMLOptions.create().indent(4);  
        String uuid = RandomUtil.getRandomUUID();
        File outFile = new File(tempDir+File.separator+uuid);  
        if(!outFile.exists()) outFile.mkdirs();  
        options.setExtractor(new FileImageExtractor(outFile));  
        // URI resolver  
        options.URIResolver(new BasicURIResolver("/" + outFile.getParentFile().getName() + "/"+outFile.getName()+"/"));  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        XHTMLConverter.getInstance().convert(document, out, options);
        out.close();  
        System.out.println("Generate " + " with " + (System.currentTimeMillis() - startTime) + " ms.");  
        return new String(out.toString(encode));
    }  
    
    

    /**
     * *转化word格式doc格式的文档
     * @param path
     * @param file
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws TransformerFactoryConfigurationError
     * @throws TransformerConfigurationException
     * @throws TransformerException
     */
    public static void doc2HtmlOld(String path,String file) throws FileNotFoundException, IOException, ParserConfigurationException,
            TransformerFactoryConfigurationError, TransformerConfigurationException, TransformerException {
        InputStream input = new FileInputStream(path + file);
        HWPFDocument wordDocument = new HWPFDocument(input);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
          DocumentBuilderFactory.newInstance().newDocumentBuilder()
            .newDocument());
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
         public String savePicture(byte[] content, PictureType pictureType,
           String suggestedName, float widthInches, float heightInches) {
          return suggestedName;
         }
        });
        wordToHtmlConverter.processDocument(wordDocument);
        List pics = wordDocument.getPicturesTable().getAllPictures();
        if (pics != null) {
         for (int i = 0; i < pics.size(); i++) {
          Picture pic = (Picture) pics.get(i);
          try {
           pic.writeImageContent(new FileOutputStream(path
             + pic.suggestFullFileName()));
          } catch (FileNotFoundException e) {
           e.printStackTrace();
          }
         }
        }
        Document htmlDocument = wordToHtmlConverter.getDocument();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "GBK");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        outStream.close();
        String content = new String(outStream.toByteArray());
        FileUtils.writeStringToFile(new File(path, "人员选择系分.html"), content, "GBK");
    }
    
    /**
     * *将excel文件流转化为html
     * @param input
     * @param suffix
     * @param encode
     * @throws TransformerFactoryConfigurationError
     */
    public static String xls2Html(InputStream input, String suffix ,String encode) throws TransformerFactoryConfigurationError {
        try{
            HSSFWorkbook excelBook = new HSSFWorkbook();

            //判断Excel文件将07+版本转换为03版本
            if(suffix.endsWith(EXCEL_XLS)){  //Excel 2003 
                excelBook = new HSSFWorkbook(input); 
            }
            else if(suffix.endsWith(EXCEL_XLSX)){  // Excel 2007/2010 
                Transform xls = new Transform();   
                XSSFWorkbook workbookOld = new XSSFWorkbook(input);
                xls.transformXSSF(workbookOld, excelBook);
            } 

             
            ExcelToHtmlConverter excelToHtmlConverter = new ExcelToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());         
            //去掉Excel头行 
            excelToHtmlConverter.setOutputColumnHeaders(false); 
            //去掉Excel行号 
            excelToHtmlConverter.setOutputRowNumbers(false); 
               
            excelToHtmlConverter.processWorkbook(excelBook);
               
            Document htmlDocument = excelToHtmlConverter.getDocument(); 
       
            ByteArrayOutputStream outStream = new ByteArrayOutputStream(); 
            DOMSource domSource = new DOMSource(htmlDocument); 
            StreamResult streamResult = new StreamResult(outStream); 
            TransformerFactory tf = TransformerFactory.newInstance(); 
            Transformer serializer = tf.newTransformer(); 
                         
            serializer.setOutputProperty(OutputKeys.ENCODING, encode); 
            serializer.setOutputProperty(OutputKeys.INDENT, "yes"); 
            serializer.setOutputProperty(OutputKeys.METHOD, "html"); 
               
            serializer.transform(domSource, streamResult); 
            outStream.close(); 
       
            //Excel转换成Html
            String content = new String(outStream.toByteArray(),encode); 
            return content;
        } 
        catch(Exception e) {
            e.printStackTrace();    
            return "转换失败";
        }
    }
    
    /**
     * *将ppt文件流转化为image
     * @param file
     */
    public static boolean ppt2Image(File file) {   
        boolean isppt = checkFile(file);   
        if (!isppt) {
            return false;
        }   
        try {
            FileInputStream is = new FileInputStream(file);   
            SlideShow ppt = new SlideShow(is);   
            is.close();   
            Dimension pgsize = ppt.getPageSize();   
            org.apache.poi.hslf.model.Slide[] slide = ppt.getSlides();   
            for (int i = 0; i < slide.length; i++) {   
                TextRun[] truns = slide[i].getTextRuns();      
                for ( int k=0;k<truns.length;k++){
                    RichTextRun[] rtruns = truns[k].getRichTextRuns();      
                    for(int l=0;l<rtruns.length;l++){
                        int index = rtruns[l].getFontIndex();      
                        String name = rtruns[l].getFontName();                
                        rtruns[l].setFontIndex(1);      
                        rtruns[l].setFontName("宋体");  
                        System.out.println(rtruns[l].getText());
                     }
                }      
                BufferedImage img = new BufferedImage(pgsize.width,pgsize.height, BufferedImage.TYPE_INT_RGB);   

                Graphics2D graphics = img.createGraphics();   
                graphics.setPaint(Color.BLUE);   
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));   
                slide[i].draw(graphics);   

                // 这里设置图片的存放路径和图片的格式(jpeg,png,bmp等等),注意生成文件路径   
                FileOutputStream out = new FileOutputStream("D:/poi-test/pptToImg/pict_"+ (i + 1) + ".jpg");   
                javax.imageio.ImageIO.write(img, "jpg", out);   
                out.close();   
            }   
            return true;   
        } catch (FileNotFoundException e) {   
            System.out.println(e);   
        } catch (IOException e) {   
        }   
        return false;   
    }   
    
    
    /**
     * *读取ppt文件的内容
     * @param file 
     * @return 返回文件内容
     * @throws IOException 
     */
    public static String readPPT2String(File file) throws IOException {
        FileInputStream fi = new FileInputStream(file);
        PowerPointExtractor ppExtractor = new PowerPointExtractor(fi);
        return ppExtractor.getText();
    }
    
    /**
     * *读取txt
     * @param file 
     * @return 返回文件内容
     * @throws IOException 
     * @throws FileNotFoundException 
     * @throws UnsupportedEncodingException 
     */
    public static String readTxt2String(File file) throws UnsupportedEncodingException, FileNotFoundException, IOException{
        StringBuilder sb = new StringBuilder();
        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file),FileUtil.getCharset(file)));
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append("\n") ;
            sb.append(line) ;
        }
        br.close();
        return sb.toString();
    }
    
    /**
     * *将txt文件流转化为html
     * @param file 
     * @return 返回文件内容
     * @throws IOException 
     * @throws FileNotFoundException 
     * @throws UnsupportedEncodingException 
     */
    public static String readTxt2Html(File file) throws UnsupportedEncodingException, FileNotFoundException, IOException{
        StringBuilder sb = new StringBuilder();
        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file),FileUtil.getCharset(file)));
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append("<br>") ;
            sb.append(line) ;
        }
        br.close();
        return sb.toString();
    }
    
    /**
     * 读取pdf
     * @param file
     * @return
     * @throws IOException
     */
    public static String readPDF2String(File file) throws IOException{
        PDDocument document = PDDocument.load(file);
        // 获取页码
        int pages = document.getNumberOfPages();
        logger.info("共 " + pages + " 页");
        
        StringBuilder sb = new StringBuilder();
        for(int page=1;page <= pages;page++) {
            PDFTextStripper stripper=new PDFTextStripper();
            stripper.setSortByPosition(false);
            stripper.setStartPage(page);
            stripper.setEndPage(page);
            String result = stripper.getText(document);
            sb.append(result);
            logger.info("----------------------------------------------------");
            logger.info(result);
        }
        document.close();
        return sb.toString();
    }
    
    // function 检查文件是否为PPT   
    public static boolean checkFile(File file) {   

        boolean isppt = false;   
        String filename = file.getName();   
        String suffixname = null;   
        if (filename != null && filename.indexOf(".") != -1) {   
            suffixname = filename.substring(filename.indexOf("."));   
            if (suffixname.equals(".ppt")) {   
                isppt = true;   
            }   
            return isppt;   
        } else {   
            return isppt;   
        }   
    }   

    
    /**
     * *将html内容转为word
     * @param contentDiv
     * @param os
     */
    public static void htmlToWordDoc(String contentDiv,OutputStream os){
        StringBuilder sb = new StringBuilder();
        sb.append("<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:w='urn:schemas-microsoft-com:office:word'xmlns='http://www.w3.org/TR/REC-html40'><head><title>Time</title><!--[if gte mso 9]><xml><w:WordDocument><w:View>Print</w:View><w:Zoom>90</w:Zoom><w:DoNotOptimizeForBrowser/></w:WordDocument></xml><![endif]--><style><!-- /* Style Definitions */@page Section1   {size:8.5in 11.0in;    margin:1.0in 1.25in 1.0in 1.25in ;    mso-header-margin:.5in;    mso-footer-margin:.5in; mso-paper-source:0;} div.Section1   {page:Section1;}--></style></head><body lang=EN-US style='tab-interval:.5in'><div class=Section1>");
        sb.append(contentDiv);
        sb.append("</div></body></html>");
        
        try {
            os.write(sb.toString().getBytes("GBK"));
            os.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}


