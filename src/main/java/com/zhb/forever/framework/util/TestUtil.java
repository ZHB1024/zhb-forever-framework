package com.zhb.forever.framework.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
 
import javax.imageio.ImageIO;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年11月7日下午4:40:57
*/

public class TestUtil {

    /**
    * @param matrix 矩阵
    * @param filedir 文件路径。如,d:\\test.jpg
    * @throws IOException
    */
   public static void createMatrixImage(int[][] matrix, String filedir) throws IOException{
       int cx = matrix.length;
       int cy = matrix[0].length;
       //填充矩形高宽
       int cz = 10;
       //生成图的宽度
       int width = cx * cz;
       //生成图的高度
       int height = cy * cz;

       OutputStream output = new FileOutputStream(new File(filedir));
       BufferedImage bufImg = new BufferedImage(width, height,    BufferedImage.TYPE_INT_RGB);
       Graphics2D gs = bufImg.createGraphics();
       gs.setBackground(Color.WHITE);
       gs.clearRect(0, 0, width, height);

       gs.setColor(Color.BLACK);
       for (int i = 0; i < cx; i++) {
           for (int j = 0; j < cy; j++) {
             //1绘制填充黑矩形
             if(matrix[j][i]==1){
                 gs.drawRect(i*cz, j*cz, cz, cz);
                 gs.fillRect(i*cz, j*cz, cz, cz);
             }
           }
       }
       gs.dispose();
       bufImg.flush();
       //输出文件
       ImageIO.write(bufImg, "jpeg", output);
        
   }
   
   
   
   public static void getData(String path){
       try{
           BufferedImage bimg = ImageIO.read(new File(path));
           int [][] data = new int[bimg.getWidth()][bimg.getHeight()];
           //方式一：通过getRGB()方式获得像素矩阵
           //此方式为沿Height方向扫描
           for(int i=0;i<bimg.getWidth();i++){
               for(int j=0;j<bimg.getHeight();j++){
                   data[i][j]=bimg.getRGB(i,j);
                   //输出一列数据比对
                   if(i==0)
                       System.out.printf("%x\t",data[i][j]);
               }
           }
           Raster raster = bimg.getData();
           System.out.println("");
           int [] temp = new int[raster.getWidth()*raster.getHeight()*raster.getNumBands()];
           //方式二：通过getPixels()方式获得像素矩阵
           //此方式为沿Width方向扫描
           int [] pixels  = raster.getPixels(0,0,raster.getWidth(),raster.getHeight(),temp);
           for (int i=0;i<pixels.length;) {
               //输出一列数据比对
               if((i%raster.getWidth()*raster.getNumBands())==0)
                   System.out.printf("ff%x%x%x\t",pixels[i],pixels[i+1],pixels[i+2]);
               i+=3;
           }
       }catch (IOException e){
           e.printStackTrace();
       }

   }
   
   
   public static void main(String[] args) throws Exception {
       //测试
       int[][] matrix = { 
               { 0, 1, 1, 0, 1,1},
               { 0, 0, 1, 0, 1,1 },
               { 0, 1, 0, 0, 0 ,1}, 
               { 1, 0, 1, 1, 1 ,0},
               { 1, 0, 0, 1, 0 ,1},
               { 0, 0, 1, 0, 1 ,1}};

       //createMatrixImage(matrix, "d:\\test.jpg");
       getData("C:\\workFile\\pi.jpg");
       
   }

}


