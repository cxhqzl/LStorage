package com.dcl.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件夹压缩处理
 * @author Administrator
 *
 */
public class FolderToZip {
	/**
	 * 将文件夹下所有文件和文件夹打包为zip
	 * @param sourceFilePath
	 * @return
	 */
	public static String fileToZip(String sourceFilePath,List<String> listNames) {
		String zipFilePath = "";
		List<String> listFile = new ArrayList<String>();
		String fileName = new Date().getTime() + (Math.random()*10 + 1000) + "";
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		for(int i=0;i<listNames.size();i++) {
			String path = sourceFilePath + "\\" + listNames.get(i);
			getFile(path,listFile);
		}
		try {
			File zipFile = new File(sourceFilePath + "\\" + fileName + ".zip");
			zipFilePath = sourceFilePath + "\\" + fileName + ".zip";
			if (zipFile.exists()) {
				
			} else { 
				if(!zipFile.exists()){
					zipFile.getParentFile().mkdirs();
				}
				fos = new FileOutputStream(zipFile);
				zos = new ZipOutputStream(new BufferedOutputStream(fos));
				byte[] bufs = new byte[1024 * 1024];
				for (int i = 0; i < listFile.size(); i++) {
					try {
						//创建ZIP实体，并添加进压缩包
						ZipEntry zipEntry = new ZipEntry(listFile.get(i).substring(sourceFilePath.length()+1, listFile.get(i).length()));
						zos.putNextEntry(zipEntry);
						// 读取待压缩的文件并写进压缩包里
						fis = new FileInputStream(listFile.get(i));
						bis = new BufferedInputStream(fis, 1024 * 1024);
						int read = 0;
						while ((read = bis.read(bufs, 0, 1024 * 1024)) != -1) {
							zos.write(bufs, 0, read);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			try {
				if (null != bis)
					bis.close();
				if (null != zos)
					zos.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return zipFilePath;
	}
	/**
	 * 递归遍历目录下所有文件
	 * @param path 文件夹路径
	 * @param listFile 
	 */
	public static void getFile(String path,List<String> listFile) {
		File file = new File(path);
		if (file.isFile()) {
			listFile.add(file.getPath());
			return;
		}
		File[] tempList = file.listFiles();
		for (File f : tempList) {
			if (f.isFile()) {
				listFile.add(f.getPath());
				continue;
			}
			if (f.isDirectory()) {
				getFile(f.getPath(),listFile);
			}
		}
		
	}
	public static void main(String[] args) {
		String sourceFilePath = "C:\\Users\\caoxinhai\\Desktop\\123";
		List<String> listNames = new ArrayList<String>();
		listNames.add("test");
		listNames.add("test1.docx");
		fileToZip(sourceFilePath,listNames);
	}
}
