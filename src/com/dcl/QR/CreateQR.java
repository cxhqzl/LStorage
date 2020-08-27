package com.dcl.QR;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import net.sf.json.JSONObject;

/**
 * 创建带logo 的二维码
 * @author Administrator
 *
 */
public class CreateQR {
	
	/**
	 * 创建带logo的二维码---create logo QR
	 * @param contents 二维码内容 --- QR content
	 * @param imagePath logo路径 --- logo path
	 * @param outPath 保存路径 --- save path
	 * @param fileName 文件名 --- fileName
	 * @throws IOException
	 * @throws WriterException
	 */
	public static boolean Encode_QR_CODE(String contents, String imagePath, String outPath, String fileName) {
		try {
			int width = 430; // 二维码图片宽度 300
			int height = 430; // 二维码图片高度300

			String format = "png";// 二维码的图片格式 png

			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			// 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			// 内容所使用字符集编码
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			// hints.put(EncodeHintType.MAX_SIZE, 350);//设置图片的最大值
			// hints.put(EncodeHintType.MIN_SIZE, 100);//设置图片的最小值
			hints.put(EncodeHintType.MARGIN, 1);// 设置二维码边的空度，非负数

			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, // 要编码的内容
					// 编码类型，目前zxing支持：Aztec 2D,CODABAR 1D format,Code 39 1D,Code 93 1D ,Code 128 1D,
					// Data Matrix 2D , EAN-8 1D,EAN-13 1D,ITF (Interleaved Two of Five) 1D,
					// MaxiCode 2D barcode,PDF417,QR Code 2D,RSS 14,RSS EXPANDED,UPC-A 1D,UPC-E
					// 1D,UPC/EAN extension,UPC_EAN_EXTENSION
					BarcodeFormat.QR_CODE, width, // 条形码的宽度
					height, // 条形码的高度
					hints);// 生成条形码时的一些配置,此项可选

			// 生成二维码
			File outputFile = new File(outPath + "\\" + fileName);// 指定输出路径

			boolean flag = MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile, imagePath);
			return flag;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) throws Exception {
		try {
			JSONObject json = new JSONObject();
			json.put("account", "123456");
			json.put("userName", "测试");
			String imagePath = "C:\\Users\\cxhqz\\Desktop\\javatest\\test.jpg";
			String outPath = "C:\\Users\\cxhqz\\Desktop\\javatest";
			String fileName = "acccount.png";
			Encode_QR_CODE(json.toString(), imagePath, outPath, fileName);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
