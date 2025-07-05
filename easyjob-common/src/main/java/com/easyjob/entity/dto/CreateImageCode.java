package com.easyjob.entity.dto;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class CreateImageCode {

    private int width = 160;
    private int height = 40;
    private int codeCount = 4;
    private int lineCount = 20; // 干扰线
    private String code = null;
    private BufferedImage buffImg = null; // 图片buffer
    private Random random = new Random();

    public CreateImageCode() {
        createImage();
    }

    public CreateImageCode(int width, int height) {
        this.width = width;
        this.height = height;
        createImage();
    }

    public CreateImageCode(int width, int height, int codeCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        createImage();
    }

    public CreateImageCode(int width, int height, int codeCount, int lineCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.lineCount = lineCount;
        createImage();
    }

    private void createImage() {

        int fontWidth = width / codeCount; // 字体的宽度
        int fontHeight = height - 5; // 字体的高度
        int codeY = height - 8;

        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // 图像buffer
        Graphics g = buffImg.getGraphics();

        // 设置背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        // 设置字体
        Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
        g.setFont(font);

        // 设置干扰线
        for (int i = 0; i < lineCount; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width);
            int ye = ys + random.nextInt(height);
            g.setColor(getRandColor(1, 255));
            g.drawLine(xs, ys, xe, ye);
        }

        float yawpRate = 0.01f; // 噪声率
        int area = (int) (yawpRate * width * height);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            buffImg.setRGB(x, y, random.nextInt(255));
        }

        String str1 = randomStr(codeCount); // 随机字符
        this.code = str1;
        for (int i = 0; i < codeCount; i++) {
            String strRand = str1.substring(i, i + 1);
            g.setColor(getRandColor(1, 255));
            g.drawString(strRand, i * fontWidth + 3, codeY);
        }
    }

    // 得到随机字符
    private String randomStr(int n) {
        String str1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder str2 = new StringBuilder();
        int len = str1.length() - 1;

        for (int i = 0; i < n; i++) {
            double r = Math.random() * len;
            str2.append(str1.charAt((int) r));
        }

        return str2.toString();
    }

    // 得到随机颜色
    private Color getRandColor(int fc, int bc) {
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);

        return new Color(r, g, b);
    }

    // 扭曲方法
    private void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private void shearX(Graphics g, int width, int height, Color color) {
        int period = random.nextInt(3) + 1; //  [1,4)
        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);

        for (int y = 0; y < height; y++) {
            double offset = (period >> 1) * Math.sin(y / (double) period + (2 * Math.PI * phase) / frames);
            g.copyArea(0, y, width, 1, (int) offset, 0);

            g.setColor(color);
            g.drawLine((int) offset, y, 0, y);
            g.drawLine((int) offset + width, y, width, y);
        }
    }

    private void shearY(Graphics g, int width, int height, Color color) {

        int period = random.nextInt(40) + 10; // 防止 period 太小
        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int x = 0; x < width; x++) {
            double offset = (period >> 1) * Math.sin(x / (double) period + (2 * Math.PI * phase) / frames);
            g.copyArea(x, 0, 1, height, 0, (int) offset);

            g.setColor(color);
            g.drawLine(x, (int) offset, x, 0);
            g.drawLine(x, (int) offset + height, x, height);
        }
    }

    public void write(OutputStream sos) throws IOException {

        javax.imageio.ImageIO.write(buffImg, "png", sos);
        sos.close();
    }

    public BufferedImage getBuffImg() {
        return buffImg;
    }

    public String getCode() {
        return code.toLowerCase();
    }
}