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
 * ������logo �Ķ�ά��
 * @author Administrator
 *
 */
public class CreateQR {
	
	/**
	 * ������logo�Ķ�ά��---create logo QR
	 * @param contents ��ά������ --- QR content
	 * @param imagePath logo·�� --- logo path
	 * @param outPath ����·�� --- save path
	 * @param fileName �ļ��� --- fileName
	 * @throws IOException
	 * @throws WriterException
	 */
	public static boolean Encode_QR_CODE(String contents, String imagePath, String outPath, String fileName) {
		try {
			int width = 430; // ��ά��ͼƬ��� 300
			int height = 430; // ��ά��ͼƬ�߶�300

			String format = "png";// ��ά���ͼƬ��ʽ png

			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			// ָ������ȼ�,������L 7%��M 15%��Q 25%��H 30%��
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			// ������ʹ���ַ�������
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			// hints.put(EncodeHintType.MAX_SIZE, 350);//����ͼƬ�����ֵ
			// hints.put(EncodeHintType.MIN_SIZE, 100);//����ͼƬ����Сֵ
			hints.put(EncodeHintType.MARGIN, 1);// ���ö�ά��ߵĿնȣ��Ǹ���

			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, // Ҫ���������
					// �������ͣ�Ŀǰzxing֧�֣�Aztec 2D,CODABAR 1D format,Code 39 1D,Code 93 1D ,Code 128 1D,
					// Data Matrix 2D , EAN-8 1D,EAN-13 1D,ITF (Interleaved Two of Five) 1D,
					// MaxiCode 2D barcode,PDF417,QR Code 2D,RSS 14,RSS EXPANDED,UPC-A 1D,UPC-E
					// 1D,UPC/EAN extension,UPC_EAN_EXTENSION
					BarcodeFormat.QR_CODE, width, // ������Ŀ��
					height, // ������ĸ߶�
					hints);// ����������ʱ��һЩ����,�����ѡ

			// ���ɶ�ά��
			File outputFile = new File(outPath + "\\" + fileName);// ָ�����·��

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
			json.put("userName", "����");
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
