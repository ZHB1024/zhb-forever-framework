package com.zhb.forever.framework.util.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ZipUtil {
	
	
	/**
     * 压缩成zip，并加密password
     * @param in 文件路径
     * @param out zip文件路径
     * @param regExp
     * @return password 解压密码
     * @throws IOException
     */
    public static void createEncryptedFile(String in, String out, List<String> regExp, HttpServletRequest request, HttpServletResponse response , String password) throws IOException {
        ZipBuilder zipBuilder = new ZipBuilder(new File(out));
        zipBuilder.add("",new File(in));
        zipBuilder.setPassword(password);
        zipBuilder.build();
    }


	/**
	 * 将filePaths 文件集合打包到zipPath 这个zip文件中
	 * 
	 * @param zipPath
	 *            压缩后的文件路径
	 * @param filePaths
	 *            需要压缩的文件路径列表
	 * 
	 */
	public static void zip(String zipPath, String[] filePaths) {
		File target = new File(zipPath);
		// 压缩文件名=源文件名.zip
		String zipName = zipPath;
		if (target.exists()) {
			target.delete(); // 删除旧的文件
		}
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		try {
			fos = new FileOutputStream(target);
			zos = new ZipOutputStream(new BufferedOutputStream(fos));
			// 添加对应的文件Entry
			addEntry(filePaths, zos);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtil.closeQuietly(zos, fos);
		}
	}

	/**
	 * 扫描添加文件Entry
	 * 
	 * @param base
	 *            基路径
	 * 
	 * @param source
	 *            源文件
	 * @param zos
	 *            Zip文件输出流
	 * @throws IOException
	 */
	private static void addEntry(String[] filePaths, ZipOutputStream zos) throws IOException {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		File tempFile = null;
		try {

			for (String filePath : filePaths) {
				tempFile = new File(filePath);
				fis = new FileInputStream(tempFile);
				byte[] buffer = new byte[1024 * 10];
				bis = new BufferedInputStream(fis, buffer.length);
				int read = 0;
				zos.putNextEntry(new ZipEntry(tempFile.getName()));
				while ((read = bis.read(buffer, 0, buffer.length)) != -1) {
					zos.write(buffer, 0, read);
				}
				zos.closeEntry();
			}
		} finally {
			IOUtil.closeQuietly(bis, fis);
		}
	}

	/**
	 * 解压文件 -- 这个fun没有按照咱们的需要定制
	 * 
	 * @param filePath
	 *            压缩文件路径
	 */
	public static void unzip(String filePath) {
		File source = new File(filePath);
		if (source.exists()) {
			ZipInputStream zis = null;
			BufferedOutputStream bos = null;
			try {
				zis = new ZipInputStream(new FileInputStream(source));
				ZipEntry entry = null;
				while ((entry = zis.getNextEntry()) != null) {
					if (entry.isDirectory()) {
						continue;
					}
					File target = new File(source.getParent(), entry.getName());
					if (!target.getParentFile().exists()) {
						// 创建文件父目录
						target.getParentFile().mkdirs();
					}
					// 写入文件
					bos = new BufferedOutputStream(new FileOutputStream(target));
					int read = 0;
					byte[] buffer = new byte[1024 * 10];
					while ((read = zis.read(buffer, 0, buffer.length)) != -1) {
						bos.write(buffer, 0, read);
					}
					bos.flush();
				}
				zis.closeEntry();
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				IOUtil.closeQuietly(zis, bos);
			}
		}
	}

	public static void main(String[] args) {

		zip("C:\\Users\\ZHB\\Videos\\temp-01.zip", new String[] { "C:\\Users\\ZHB\\Videos\\movie.mp4" });
	}
}

/**
 * 用于关闭流对象
 * 
 * @author wanglei
 * @version [版本号, 2016年6月1日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
class IOUtil {
	/**
	 * 关闭一个或多个流对象
	 * 
	 * @param closeables
	 *            可关闭的流对象列表
	 * @throws IOException
	 */
	public static void close(Closeable... closeables) throws IOException {
		if (closeables != null) {
			for (Closeable closeable : closeables) {
				if (closeable != null) {
					closeable.close();
				}
			}
		}
	}

	/**
	 * 关闭一个或多个流对象
	 * 
	 * @param closeables
	 *            可关闭的流对象列表
	 */
	public static void closeQuietly(Closeable... closeables) {
		try {
			close(closeables);
		} catch (IOException e) {
			// do nothing
		}
	}

}
