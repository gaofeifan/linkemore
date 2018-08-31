package cn.linkmore.ops.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码生成工具
 * 
 * @author Jesse
 *
 */
public class QrCodeGenerateUtil {

    /**
     * 生成包含字符串信息的二维码图片
     * 
     * @param os
     * @param content
     * @param qrCodeSize
     * @param imageFormat
     * @throws Exception
     */
    public static void createZxing(OutputStream os, String content, int qrCodeSize, String imageFormat)
            throws Exception {
        // 二维码参数
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);// 纠错等级L,M,Q,H
        hints.put(EncodeHintType.MARGIN, 2); // 边距
        // 创建比特矩阵(位矩阵)的QR码编码的字符串
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize,
                hints);
        MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, os);
        System.out.println("输出图片成功。。。");
    }

    /**
     * 生成包含字符串信息的二维码图片
     * 
     * @param path
     *            二维码图片保存地址
     * @param content
     *            二维码携带信息
     * @param qrCodeSize
     *            二维码图片大小
     * @param imageFormat
     *            二维码图片格式
     * @throws Exception
     */
    public static void createZxing(String path, String content, int qrCodeSize, String imageFormat) throws Exception {
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);// 纠错等级L,M,Q,H
        hints.put(EncodeHintType.MARGIN, 2); // 边距
        // 创建比特矩阵(位矩阵)的QR码编码的字符串
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize,
                hints);
        File file = new File(path);
        // 使用比特矩阵画并保存图像
        MatrixToImageWriter.writeToFile(bitMatrix, imageFormat, file);
        System.out.println("输出图片成功。。。");
    }

    /**
     * 读取二维码并输出携带的信息
     * 
     * @param filePath
     *            二维码图片保存地址
     */
    public static void readZxing(String filePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(filePath));
            // 将图像转换成二进制位图源
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
            JSONObject content = JSONObject.parseObject(result.getText());
            System.out.println("图片中内容：  ");
            System.out.println("group：  " + content.toJSONString());

            System.out.println("图片中格式：  ");
            System.out.println("encode： " + result.getBarcodeFormat());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

}
