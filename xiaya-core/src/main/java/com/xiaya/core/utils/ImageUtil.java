package com.xiaya.core.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.xiaya.core.exception.B5mException;

public class ImageUtil {
	
	/**
	 * 图像切割(改)
	 * @param in 源图像流
	 * @param x 目标切片起点x坐标
	 * @param y 目标切片起点y坐标
	 * @param destWidth 目标切片宽度
	 * @param destHeight 目标切片高度
	 * @return
	 */
	public static BufferedImage abscut(InputStream in, int x, int y, int destWidth, int destHeight) {
		Image img;
		ImageFilter cropFilter;
		try {
			//读取图片源
			BufferedImage bi = ImageIO.read(in);
			//获取原图片的宽和高,裁剪的尺寸不可以大于这两个值
			int width = bi.getWidth();
			int height = bi.getHeight();
			if(width >= destWidth && height >= destHeight){
				
				//目标图片
				Image image = bi.getScaledInstance(width, height, Image.SCALE_DEFAULT);
				cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
				img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
				
				//新图片尺寸
				BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
				
				//画图类
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, null);
				g.dispose();
				
				return tag;
			}
		} catch (IOException e) {
			throw new B5mException("图像切割出现异常", e);
		}
		
		return null;
	}
	
	/**
	 * 压缩图片
	 * @param src
	 * @param w 目标宽
	 * @param h 目标高
	 * @param per 百分比
	 * @return
	 */
	public static BufferedImage smallerImage(Image src, int w, int h){
		int old_w = src.getWidth(null);
		int old_h = src.getHeight(null);
		int new_w = 0;
		int new_h = 0;
		
		double w2 = (old_w * 1.00)/(w * 1.00);
		double h2 = (old_h * 1.00)/(h * 1.00);
		
		//图片根据长宽留白,成一个正方形图
		BufferedImage oldPic;
		if(old_w > old_h){
			oldPic = new BufferedImage(old_w, old_w, BufferedImage.TYPE_INT_RGB);
		}else{
			oldPic = new BufferedImage(old_h, old_h, BufferedImage.TYPE_INT_RGB);
		}
		Graphics2D g = oldPic.createGraphics();
		g.setColor(Color.white);
		if(old_w > old_h){
			g.fillRect(0, 0, old_w, old_w);
		}else{
			if(old_w < old_h){
				g.fillRect(0, 0, old_h, old_h);
				g.drawImage(src, (old_h - old_w)/2, 0, old_w, old_h, Color.white, null);
			}else{
				g.drawImage(src.getScaledInstance(old_w, old_h, Image.SCALE_SMOOTH), 0, 0, null);
			}
		}
		g.dispose();
		src = oldPic;
		//图片调整为方形结束
		if(old_w > w)
			new_w = (int) Math.round(old_w / w2);
		else
			new_w = old_w;
		if(old_h > h)
			new_h = (int) Math.round(old_h / h2);
		else
			new_h = old_h;
		
		
		BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
		tag.getGraphics().drawImage(src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);
		
		return tag;
	}
	
	
	
	
	
	
	public static void main(String[] args) throws IOException {
		String src = "E:\\test\\koala.jpg";
		FileInputStream fs = new FileInputStream(new File(src));
		BufferedImage abscut = abscut(fs, 100, 100, 700, 700);
		BufferedImage smallerImage = smallerImage(abscut.getScaledInstance(700, 700, Image.SCALE_SMOOTH), 600, 600);
		ImageIO.write(abscut, "jpg", new File("E:\\test\\image.jpg"));
		ImageIO.write(smallerImage, "jpg", new File("E:\\test\\image2.jpg"));
	}
	
	
}
