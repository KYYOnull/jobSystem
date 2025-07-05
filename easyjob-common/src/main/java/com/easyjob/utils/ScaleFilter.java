package com.easyjob.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

// 缩略图
public class ScaleFilter {

    private static final Logger logger= LoggerFactory.getLogger(ScaleFilter.class);

    public static Boolean createThumbNail(File f, int thumbW, int thumbH, File tarFile){

        if (f == null || !f.exists() || thumbW <= 0 || thumbH <= 0 || tarFile == null) {
            logger.warn("无效参数：文件或尺寸非法");
            return false;
        }

        try{
            BufferedImage src = ImageIO.read(f);
            int srcW = src.getWidth();
            int srcH = src.getHeight();
            if (srcW <= thumbW) {
                logger.info("图片宽度已小于目标宽度，无需缩略处理");
                return false;
            }

            int scaledH  = thumbW * srcH / srcW; // 计算等比例缩放高度
            BufferedImage dst = new BufferedImage(thumbW, scaledH, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = dst.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(src, 0, 0, thumbW, scaledH, null);
            g.dispose();

            // 缩放后高度仍超过 thumbH，则从顶部裁剪
            if (scaledH > thumbH) {
                dst = dst.getSubimage(0, 0, thumbW, thumbH); // 从顶部裁剪
            }
            // 写入高质量 JPEG
            Iterator<ImageWriter> w = ImageIO.getImageWritersByFormatName("jpeg");
            if (w.hasNext()) {
                ImageWriter writer = w.next();
                try (ImageOutputStream ios = ImageIO.createImageOutputStream(tarFile)) {
                    writer.setOutput(ios);
                    ImageWriteParam param = writer.getDefaultWriteParam();
                    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    param.setCompressionQuality(0.85f); // 85% 质量
                    writer.write(null, new IIOImage(dst, null, null), param);
                } finally {
                    writer.dispose();
                }
            } else {
                // fallback
                ImageIO.write(dst, "jpeg", tarFile);
            }
            return true;

        } catch (IOException e) {

            logger.error("生成缩略图失败");
            throw new RuntimeException(e);
        }
    }
}
