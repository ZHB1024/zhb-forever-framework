package com.zhb.forever.framework.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.apache.sanselan.ImageReadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhb.forever.framework.vo.ImageVO;
import com.zhb.forever.framework.vo.WatermarkVO;

public class ImageUtil {

    protected static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    private static final Color FONT_COLOR = Color.GRAY;
    
    /**
     * file to BufferedImage
     * @param File
     */
    public static BufferedImage file2BufferedImage(File file) {
        if (null == file) {
            return null;
        }
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }
    
    /**
     * inpuStream to BufferedImage
     * @param InputStream
     */
    public static BufferedImage inpuStream2BufferedImage(InputStream is) {
        if (null == is) {
            return null;
        }
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }
    
    /**
     * BufferedImage to OutputStream
     * @param BufferedImage
     */
    public static boolean bufferedImage2OutputStream(BufferedImage bufferedImage,String format,OutputStream out) {
        boolean flag = false;
        if (null == bufferedImage) {
            return flag;
        }
        try {
            flag = ImageIO.write(bufferedImage,format,out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
    
    /**
     * BufferedImage to byte[]
     * @param BufferedImage
     */
    public static byte[] bufferedImage2Bytes(BufferedImage bufferedImage,String format,ByteArrayOutputStream out) {
        if (null == bufferedImage) {
            return null;
        }
        try {
            ImageIO.write(bufferedImage,format,out);
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加水印 
     * @param InputStream
     * @throws IOException 
     */
    public static void pressText(InputStream is, OutputStream os, float alpha, int xNum, int yNum, String[] txts) throws IOException{
        BufferedImage image = ImageIO.read(is);
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        double radian = Math.atan(height / width);
        radian *= 0.8D;
        double sinRadian = Math.sin(radian);
        double cosRadian = Math.cos(radian);

        BufferedImage bufferedImage = new BufferedImage(width, height, 1);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);

        int halfX = width / 2;
        int halfY = height / 2;
        double xiexian = halfX / cosRadian;

        Map<String,WatermarkVO> TXT_WatermarkVO = new HashMap<String,WatermarkVO>();

        for (String str : txts) {
            if (StringUtil.isNotBlank(str)) {
                String fontName;
                int fitPixelWidth;
                if (str.matches("^[a-zA-Z]*")) {
                    fontName = "Times New Roman";
                    fitPixelWidth = (int) Math.round(xiexian * 0.0428D * str.length());
                } else {
                    fontName = "宋体";
                    fitPixelWidth = (int) Math.round(xiexian * 0.06D * str.length());
                }
                Font font = getFitFontPointSizeFont(g, fontName, 1, str, fitPixelWidth);
                g.setFont(font);
                Rectangle2D r = g.getFontMetrics().getStringBounds(str, g);
                TXT_WatermarkVO.put(str, new WatermarkVO(font, (int) r.getCenterX(), (int) r.getCenterY()));
            }
        }

        g.setColor(FONT_COLOR);
        g.setComposite(AlphaComposite.getInstance(10, alpha));

        int right_y = (int) (width / xNum * sinRadian);
        int right_x = (int) (width / xNum * cosRadian);
        int top_x = -(int) (height / yNum * sinRadian);
        int top_y = (int) (height / yNum * cosRadian);

        int cnt = 0;
        for (int i = 0; i < yNum; ++i) {
            int index = cnt % txts.length;
            if (i == 0) {
                g.translate(halfX / xNum, halfY / yNum);
                g.rotate(-radian);
            } else {
                g.translate(top_x, top_y);
            }
            WatermarkVO watermarkVO = (WatermarkVO) TXT_WatermarkVO.get(txts[index]);
            g.setFont(watermarkVO.font);
            g.drawString(txts[index], -watermarkVO.centerX, -watermarkVO.centerY);
            ++cnt;
            for (int j = 1; j < xNum; ++j) {
                g.translate(right_x, right_y);
                index = cnt % txts.length;
                watermarkVO = (WatermarkVO) TXT_WatermarkVO.get(txts[index]);
                g.setFont(watermarkVO.font);
                g.drawString(txts[index], -watermarkVO.centerX, -watermarkVO.centerY);
                ++cnt;
            }
            for (int j = 1; j < xNum; ++j) {
                g.translate(-right_x, -right_y);
            }
        }
        g.dispose();
        ImageIO.write(bufferedImage, "jpg", os);
        os.flush();
    }

    /**
     * 加水印 
     * @param BufferedImage
     * @return
     */
    public static BufferedImage pressText(BufferedImage image, float alpha, int xNum, int yNum, String[] txts)
            throws Exception {
        try {
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            double radian = Math.atan(height / width);
            radian *= 0.8D;
            double sinRadian = Math.sin(radian);
            double cosRadian = Math.cos(radian);

            BufferedImage bufferedImage = new BufferedImage(width, height, 1);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);

            int halfX = width / 2;
            int halfY = height / 2;
            double xiexian = halfX / cosRadian;

            Map TXT_WatermarkVO = new HashMap();

            for (String str : txts) {
                if (StringUtil.isNotBlank(str)) {
                    String fontName;
                    int fitPixelWidth;
                    if (str.matches("^[a-zA-Z]*")) {
                        fontName = "Times New Roman";
                        fitPixelWidth = (int) Math.round(xiexian * 0.0428D * str.length());
                    } else {
                        fontName = "宋体";
                        fitPixelWidth = (int) Math.round(xiexian * 0.06D * str.length());
                    }
                    Font font = getFitFontPointSizeFont(g, fontName, 1, str, fitPixelWidth);
                    g.setFont(font);
                    Rectangle2D r = g.getFontMetrics().getStringBounds(str, g);
                    TXT_WatermarkVO.put(str, new WatermarkVO(font, (int) r.getCenterX(), (int) r.getCenterY()));
                }
            }

            g.setColor(FONT_COLOR);
            g.setComposite(AlphaComposite.getInstance(10, alpha));

            int right_y = (int) (width / xNum * sinRadian);
            int right_x = (int) (width / xNum * cosRadian);
            int top_x = -(int) (height / yNum * sinRadian);
            int top_y = (int) (height / yNum * cosRadian);

            int cnt = 0;
            for (int i = 0; i < yNum; ++i) {
                int index = cnt % txts.length;
                if (i == 0) {
                    g.translate(halfX / xNum, halfY / yNum);
                    g.rotate(-radian);
                } else {
                    g.translate(top_x, top_y);
                }
                WatermarkVO watermarkVO = (WatermarkVO) TXT_WatermarkVO.get(txts[index]);
                g.setFont(watermarkVO.font);
                g.drawString(txts[index], -watermarkVO.centerX, -watermarkVO.centerY);
                ++cnt;
                for (int j = 1; j < xNum; ++j) {
                    g.translate(right_x, right_y);
                    index = cnt % txts.length;
                    watermarkVO = (WatermarkVO) TXT_WatermarkVO.get(txts[index]);
                    g.setFont(watermarkVO.font);
                    g.drawString(txts[index], -watermarkVO.centerX, -watermarkVO.centerY);
                    ++cnt;
                }
                for (int j = 1; j < xNum; ++j) {
                    g.translate(-right_x, -right_y);
                }
            }
            g.dispose();
            return bufferedImage;
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return null;
    }

    /**
     * 缩小图片
     * @param  BufferedImage
     */
    public static byte[] smallImage(File oriFile, String suffix, long targetSize)
            throws ImageReadException, IOException {
        BufferedImage bi = null;
        JpegReaderUtil jr = new JpegReaderUtil();
        if (("jpg".equalsIgnoreCase(suffix)) || ("jpeg".equalsIgnoreCase(suffix)) || ("png".equalsIgnoreCase(suffix)))
            bi = jr.readImage(oriFile);
        else {
            throw new ImageReadException("非jpg或png格式照片");
        }
        long tempSize = oriFile.length();
        byte[] result = null;
        if (tempSize <= targetSize) {
            result = FileUtil.readFileAsBytes(oriFile);
        } else {
            ByteArrayOutputStream tmp = new ByteArrayOutputStream();
            bi = resizeImg(bi, suffix, bi.getWidth(), bi.getHeight());
            ImageIO.write(bi, suffix, tmp);
            tmp.close();
            tempSize = tmp.size();
            while (tempSize > targetSize) {
                bi = resizeImg(bi, suffix, (int) (bi.getWidth() * 0.5F), (int) (bi.getHeight() * 0.5F));
                tmp.reset();
                ImageIO.write(bi, suffix, tmp);
                tmp.close();
                tempSize = tmp.size();
            }
            result = tmp.toByteArray();
        }
        return result;
    }
    
    /**
     * 缩小图片
     * @param  InputStream
     */
    public static byte[] smallImage(InputStream is, String suffix, long tempSize,long targetSize)
            throws ImageReadException, IOException {
        BufferedImage bi = null;
        bi = inpuStream2BufferedImage(is);
        /*if (("jpg".equalsIgnoreCase(suffix)) || ("jpeg".equalsIgnoreCase(suffix)) || ("png".equalsIgnoreCase(suffix))) {
            bi = inpuStream2BufferedImage(is);
        }else {
            throw new ImageReadException("非jpg或png格式照片");
        }*/
        byte[] result = null;
        if (tempSize <= targetSize) {
            result = FileUtil.readInputStreamAsBytes(is);
        } else {
            ByteArrayOutputStream tmp = new ByteArrayOutputStream();
            bi = resizeImg(bi, bi.getWidth(), bi.getHeight());
            ImageIO.write(bi, suffix, tmp);
            tmp.close();
            tempSize = tmp.size();
            while (tempSize > targetSize) {
                bi = resizeImg(bi, (int) (bi.getWidth() * 0.5F), (int) (bi.getHeight() * 0.5F));
                tmp.reset();
                ImageIO.write(bi, suffix, tmp);
                tmp.close();
                tempSize = tmp.size();
            }
            result = tmp.toByteArray();
        }
        return result;
    }
    
    /**
     * 重新设定大小 图片
     * @param BufferedImage
     */
    public static BufferedImage resizeImg(BufferedImage bi, String suffix, int targetWidth, int targetHeight) {
        double scaleX = targetWidth / bi.getWidth();
        double scaleY = targetHeight / bi.getHeight();

        Image Itemp = bi.getScaledInstance(targetWidth, targetHeight, 1);
        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(scaleX, scaleY), null);
        Itemp = op.filter(bi, null);
        return ((BufferedImage) Itemp);
    }
    
    /**
     * 重新设定大小 图片 Graphics2D
     * @param BufferedImage  
     */
    public static BufferedImage resizeImg(BufferedImage image,int targetWidth, int targetHeight) {
        try {
            BufferedImage tmp = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.drawImage(image, 0, 0, targetWidth, targetHeight, null);
            g2.dispose();
            return tmp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 重新设定大小 图片
     * @param File
     */
    public static byte[] resizeImg(File oriFile, String suffix, int width, int height) throws Exception {
        FileInputStream fis = new FileInputStream(oriFile);
        return resizeImg(fis, suffix, width, height);
    }
    
    /**
     * 重新设定大小 图片
     * @param InputStream
     */
    public static byte[] resizeImg(InputStream is, String suffix, int width, int height) throws Exception {
        ByteArrayOutputStream bos = null;
        byte[] result = null;
        try {
            BufferedImage Bi = ImageIO.read(is);
            double scaleX = width / Bi.getWidth();
            double scaleY = height / Bi.getHeight();

            Image Itemp = Bi.getScaledInstance(width, height, 1);
            AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(scaleX, scaleY), null);
            Itemp = op.filter(Bi, null);
            bos = new ByteArrayOutputStream();
            ImageIO.write((BufferedImage) Itemp, suffix.toLowerCase(), bos);
            result = bos.toByteArray();
        } catch (Exception ex) {
        } finally {
            try {
                if (bos != null)
                    bos.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                if (is != null)
                    is.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    
    /**
     * 裁剪图片
     * @param file
     */
    public static ImageVO cropImg(File file, int x, int y, int width, int height) {
        ImageVO vo = new ImageVO(x, y, width, height, 0);
        return cropImg(file, vo);
    }

    /**
     * 裁剪图片
     * @param file
     */
    public static ImageVO cropImg(File file, ImageVO vo) {
        ImageVO doneImageVO = new ImageVO();
        InputStream fis = null;
        try {
            if ((vo.getX() >= 0) && (vo.getY() >= 0) && (vo.getWidth() != 0) && (vo.getHeight() != 0)) {
                String fileName = file.getName();
                String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                Iterator readers = ImageIO.getImageReadersByFormatName(suffix);
                ImageReader reader = (ImageReader) readers.next();
                fis = new FileInputStream(file);
                ImageInputStream iis = ImageIO.createImageInputStream(fis);
                reader.setInput(iis, true);
                ImageReadParam param = reader.getDefaultReadParam();
                Rectangle rect = new Rectangle(vo.getX(), vo.getY(), vo.getWidth(), vo.getHeight());
                param.setSourceRegion(rect);
                BufferedImage bi = reader.read(0, param);
                ImageIO.write(bi, suffix, file);
                doneImageVO.setWidth(bi.getWidth());
                doneImageVO.setHeight(bi.getHeight());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return doneImageVO;
    }

    /**
     * 裁剪图片
     * @param InputStream
     */
    public static ImageVO cropImg(String suffix, InputStream is, OutputStream os, ImageVO vo) {
        ImageVO doneImageVO = new ImageVO();
        try {
            if ((vo.getX() >= 0) && (vo.getY() >= 0) && (vo.getWidth() != 0) && (vo.getHeight() != 0)) {
                Iterator readers = ImageIO.getImageReadersByFormatName(suffix);
                ImageReader reader = (ImageReader) readers.next();
                ImageInputStream iis = ImageIO.createImageInputStream(is);
                reader.setInput(iis, true);
                ImageReadParam param = reader.getDefaultReadParam();
                Rectangle rect = new Rectangle(vo.getX(), vo.getY(), vo.getWidth(), vo.getHeight());
                param.setSourceRegion(rect);
                BufferedImage bi = reader.read(0, param);
                ImageIO.write(bi, suffix, os);
                doneImageVO.setWidth(bi.getWidth());
                doneImageVO.setHeight(bi.getHeight());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return doneImageVO;
    }
    
    /**
     * 旋转图片
     * @param File
     */
    public static ImageVO rotateImgFile(File file, ImageVO vo) {
        return rotateImgFile(file, vo.getRadian());
    }
    /**
     * 旋转图片
     * @param File
     */
    public static ImageVO rotateImgFile(File file, int radian) {
        ImageVO doneImageVO = new ImageVO();
        try {
            String fileName = file.getName();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            BufferedImage bufferedImage = ImageIO.read(file);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();

            BufferedImage dstImage = null;
            AffineTransform affineTransform = new AffineTransform();
            if (radian == 180) {
                affineTransform.translate(width, height);
                dstImage = new BufferedImage(width, height, bufferedImage.getType());
            } else if (radian == 90) {
                affineTransform.translate(height, 0.0D);
                dstImage = new BufferedImage(height, width, bufferedImage.getType());
            } else if (radian == 270) {
                affineTransform.translate(0.0D, width);
                dstImage = new BufferedImage(height, width, bufferedImage.getType());
            } else {
                doneImageVO.setWidth(bufferedImage.getWidth());
                doneImageVO.setHeight(bufferedImage.getHeight());
                doneImageVO.setRadian(radian);
                ImageVO localImageVO1 = doneImageVO;
                return localImageVO1;
            }

            affineTransform.rotate(Math.toRadians(radian));
            AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, 2);
            dstImage = affineTransformOp.filter(bufferedImage, dstImage);
            ImageIO.write(dstImage, suffix, file);
            doneImageVO.setWidth(dstImage.getWidth());
            doneImageVO.setHeight(dstImage.getHeight());
            doneImageVO.setRadian(radian);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
        return doneImageVO;
    }
    
    /**
     * 旋转图片
     * @param InputStream
     */
    public static byte[] rotateImg2(InputStream is, ImageVO vo) throws IOException {
        byte[] array = null;
        ByteArrayOutputStream bos = null;
        ImageOutputStream out = null;
        try {
            String suffix = vo.getSuffix().toLowerCase();
            BufferedImage bufferedImage = ImageIO.read(is);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();

            BufferedImage dstImage = null;
            AffineTransform affineTransform = new AffineTransform();
            int result = vo.getRadian() % 360;
            if (vo.getRadian() % 180 == 0) {
                affineTransform.translate(width, height);
                dstImage = new BufferedImage(width, height, bufferedImage.getType());
            } else if ((result == 90) || (result == -270)) {
                affineTransform.translate(height, 0.0D);
                dstImage = new BufferedImage(height, width, bufferedImage.getType());
            } else if ((result == -90) || (result == 270)) {
                affineTransform.translate(0.0D, width);
                dstImage = new BufferedImage(height, width, bufferedImage.getType());
            } else {
                bos = new ByteArrayOutputStream();
                out = ImageIO.createImageOutputStream(bos);
                ImageIO.write(bufferedImage, suffix, out);
                array = bos.toByteArray();
                byte[] arrayOfByte1 = array;
                return arrayOfByte1;
            }
            affineTransform.rotate(Math.toRadians(vo.getRadian()));
            AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, 2);
            dstImage = affineTransformOp.filter(bufferedImage, dstImage);
            bos = new ByteArrayOutputStream();
            out = ImageIO.createImageOutputStream(bos);
            ImageIO.write(dstImage, suffix, out);
            array = bos.toByteArray();
        } catch (IOException ioe) {
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return array;
    }
    
    /**
     * 转换image到新jpg file
     * @param File
     */
    public static void reEncodeJPG(File src, File dest) throws Exception {
        FileInputStream inFile = null;
        BufferedImage image = null;
        try {
            JpegReaderUtil jr = new JpegReaderUtil();
            image = jr.readImage(src);
            ImageIO.write(image, "jpg", dest);
        } catch (Exception e) {
        } finally {
            if (inFile != null) {
                inFile.close();
            }
            if (image != null)
                image.flush();
        }
    }

    /**
     * 转换imagePath 为 jpg
     * @param filePathName
     */
    public static BufferedImage reEncodeJPG(String filePathName) throws Exception {
        FileInputStream inFile = null;
        FileOutputStream outFile = null;
        BufferedImage image = null;
        try {
            File doc = new File(filePathName);
            JpegReaderUtil jr = new JpegReaderUtil();
            image = jr.readImage(doc);
            outFile = new FileOutputStream(doc);
            ImageIO.write(image, "jpg", outFile);
            BufferedImage localBufferedImage1 = image;

            return localBufferedImage1;
        } catch (Exception ex) {
        } finally {
            if (inFile != null) {
                inFile.close();
            }
            if (outFile != null)
                outFile.close();
        }
        return null;
    }

    public static String getImageSuffix(File image)throws IOException{  
        String formatName = null;  
        ImageInputStream iis = ImageIO.createImageInputStream(image);  
        Iterator<ImageReader> imageReader =  ImageIO.getImageReaders(iis);  
        if(imageReader.hasNext()){  
            ImageReader reader = imageReader.next();  
            formatName = reader.getFormatName();  
        }  
   
        return formatName;  
    }  
    
    private static Font getFitFontPointSizeFont(Graphics2D g, String fontName, int fontStyle, String pressText,
            int fitPixelWidth) {
        int pointSize = 0;
        Font font;
        FontMetrics fm;
        int pixelWidthOfPressTxt;
        do {
            pointSize += 2;
            font = new Font(fontName, fontStyle, pointSize);
            fm = g.getFontMetrics(font);
            pixelWidthOfPressTxt = fm.stringWidth(pressText);
        } while (pixelWidthOfPressTxt < fitPixelWidth);
        Font preFont = new Font(fontName, fontStyle, pointSize - 1);

        fm = g.getFontMetrics(preFont);
        int prePixelWidthOfPressTxt = fm.stringWidth(pressText);
        if (Math.abs(prePixelWidthOfPressTxt - fitPixelWidth) < Math.abs(pixelWidthOfPressTxt - fitPixelWidth)) {
            return preFont;
        }
        return font;
    }
}
