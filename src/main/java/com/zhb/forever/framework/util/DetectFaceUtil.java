package com.zhb.forever.framework.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
 * @createDate 2018年10月29日下午4:13:05
 */

public class DetectFaceUtil {
    
    private static Logger logger = LoggerFactory.getLogger(DetectFaceUtil.class);
    
    static {
        System.load("C:/workFile/soft/openCV/install/opencv/build/java/x64/opencv_java320.dll");
    }

    private static String ACCESS_TOKEN = "24.89e1d23d677d3bf2cfea7703ee251f25.2592000.1520406800.282335-10795842";

    public static String FACE_URL = "https://aip.baidubce.com/rest/2.0/face/v1/detect";
    
    public static CascadeClassifier faceDetector;

    public static String detectFace(InputStream faceInput) throws Exception {
        byte[] imgData = FileUtil.readInputStreamAsBytes(faceInput);
        String imgStr = Base64Util.encode(imgData);
        String imgParam = URLEncoder.encode(imgStr, "UTF-8");

        String param = "max_face_num=" + 1 + "&face_fields="
                + "age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities" + "&image=" + imgParam;

        return post(FACE_URL, ACCESS_TOKEN, param);
    }

    public static String post(String requestUrl, String accessToken, String params) throws Exception {
        String contentType = "application/x-www-form-urlencoded";
        return post(requestUrl, accessToken, contentType, params);
    }

    public static String post(String requestUrl, String accessToken, String contentType, String params)
            throws Exception {
        String encoding = "UTF-8";
        if (requestUrl.contains("nlp")) {
            encoding = "GBK";
        }
        return post(requestUrl, accessToken, contentType, params, encoding);
    }

    public static String post(String requestUrl, String accessToken, String contentType, String params, String encoding)
            throws Exception {
        String url = requestUrl + "?access_token=" + accessToken;
        return postGeneralUrl(url, contentType, params, encoding);
    }

    public static String postGeneralUrl(String generalUrl, String contentType, String params, String encoding)
            throws Exception {
        URL url = new URL(generalUrl);
        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        // 设置通用的请求属性
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        // 得到请求的输出流对象
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.write(params.getBytes(encoding));
        out.flush();
        out.close();

        // 建立实际的连接
        connection.connect();
        // 获取所有响应头字段
        Map<String, List<String>> headers = connection.getHeaderFields();
        // 遍历所有的响应头字段
        for (String key : headers.keySet()) {
            System.err.println(key + "--->" + headers.get(key));
        }
        // 定义 BufferedReader输入流来读取URL的响应
        BufferedReader in = null;
        in = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));
        String result = "";
        String getLine;
        while ((getLine = in.readLine()) != null) {
            result += getLine;
        }
        in.close();
        return result;
    }
    
    //openCV 检测人脸数量
    public static int getPersonNum(byte[] imageBytes) {
        String classifierFilePath = "haarcascade_frontalface_alt.xml";

        File tempFile = null;
        //生成临时文件
        if (faceDetector == null || faceDetector.empty()) {
            InputStream is = DetectFaceUtil.class.getResourceAsStream("/openCV/" + classifierFilePath);
            try {
                tempFile = File.createTempFile("haarcascade_frontalface_alt", ".xml");
                FileUtil.copyInputStreamToFile(is, tempFile);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //初始化CascadeClassifier
        if (faceDetector == null) {
            logger.info("classifierFilePath:" + tempFile.getAbsolutePath());
            faceDetector = new CascadeClassifier(tempFile.getAbsolutePath());
        }
        if (faceDetector.empty()) {
            System.out.println("CascadeClassifier empty");
            return -1;
        }
        Mat image = Imgcodecs.imdecode(new MatOfByte(imageBytes), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);
        int faceCnt = 0;
        Rect[] rects = faceDetections.toArray();
        for (Rect rect : rects) {
            //照片面部在整个照片中所占百分比的限制
            double persent = ((double)(rect.width * rect.height) * 100) / (image.cols() * image.rows());
            if (persent > 5) {
                faceCnt++;
                continue;
            }
            logger.info("人脸占图片："+persent);
        }
        return faceCnt;
    }

}
