package com.dcl.QR;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.zxing.common.BitMatrix;


  
/** 
 * ��ά���������Ҫ����MatrixToImageWriter�࣬��������Google�ṩ�ģ����Խ�����ֱ�ӿ�����Դ����ʹ�ã���Ȼ��Ҳ�����Լ�д�� 
 * ����������Ļ��� 
 */  
public class MatrixToImageWriter {  
    private static final int BLACK = 0xFF000000;//��������ͼ������ɫ  
    private static final int WHITE = 0xFFFFFFFF; //���ڱ���ɫ  
  
    private MatrixToImageWriter() {  
    	
    }  
  
    public static BufferedImage toBufferedImage(BitMatrix matrix) {  
        int width = matrix.getWidth();  
        int height = matrix.getHeight();  
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        for (int x = 0; x < width; x++) {  
            for (int y = 0; y < height; y++) {  
                image.setRGB(x, y,  (matrix.get(x, y) ? BLACK : WHITE));  
//              image.setRGB(x, y,  (matrix.get(x, y) ? Color.YELLOW.getRGB() : Color.CYAN.getRGB()));  
            }  
        }  
        return image;  
    }  
  
    public static boolean writeToFile(BitMatrix matrix, String format, File file, String imagePath) throws IOException {  
        BufferedImage image = toBufferedImage(matrix);  
        //����logoͼ��  
        LogoConfig logoConfig = new LogoConfig();  
        image = logoConfig.LogoMatrix(image, imagePath);  
          
        if (!ImageIO.write(image, format, file)) {  
            return false;
        }else{  
            return true;
        }  
    }  
  
}
