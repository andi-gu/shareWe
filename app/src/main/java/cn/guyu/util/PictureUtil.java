package cn.guyu.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class PictureUtil {

	/**
	 * 1.质量压缩
	 * @param image
	 * @return
	 */
	public static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while ( baos.toByteArray().length / 1024>100) {	//循环判断如果压缩后图片是否大于100kb,大于继续压缩		
			baos.reset();//重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;//每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
		
		
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
		return bitmap;
	}
	
	
	/**
	 * 2图片按比例大小压缩方法
	 * @param srcPath  图片路径
	 * @return
	 */
	public static Bitmap getimage(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
	/*	如果我们把它设为true，那么BitmapFactory.decodeFile(String path, Options opt)并不会真的返回一个Bitmap给你，
		它仅仅会把它的宽，高取回来给你，这样就不会占用太多的内存，也就不会那么频繁的发生OOM了。*/
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空
		
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		//高和宽我们设置为800*480分辨率
		float hh = 800f;//这里设置高度为800f
		float ww = 480f;//这里设置宽度为480f
		//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;//be=1表示不缩放
		if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;//设置缩放比例
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		
		//进行 质量压缩
		return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
	}
	
	/**
	 * 3得到临时图片路径
	 * @param filePath
	 * @return 
	 * @return
	 * @throws IOException 
	 */
	public static String bitmapToPath(String filePath) throws IOException {

		Bitmap bm = getimage(filePath);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		
		//得到文件名
		String imgName=getfilepath(filePath);
		//得到存放路径
		String sdPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/ImgTmp";
		//获取 sdcard的跟目录
		
	    File parent =new File(sdPath);
	    if(!parent.exists()){
	    	//创建路径
	    	parent.mkdirs();
	    }
		//写入 临时文件
        File file =new File(parent,imgName);
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(baos.toByteArray());
        fos.flush();
        fos.close();
        baos.close();
        //返回图片路径
		return sdPath+"/"+imgName;
		
	}

	
	/**
	 * 
	 * @param path
	 * @return
	 */
	private static String getfilepath(String path){
		return System.currentTimeMillis()+getExtensionName(path);
	}
	
	
	/*
	 * 4获取文件扩展名
	 */
	private static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot, filename.length());
			}
		}
		return filename;
	}

	/**
	 * 5删除临时文件
	 * 
	 * @param imgs
	 */
	public static void deleteImgTmp(List<String> imgs) {

		for (String string : imgs) {
			File file = new File(string);
			if (file.exists()) {
				file.delete();
			}
		}

   }
	
	
	/**
	 * 根据本地图片的url路径获得Bitmap对象
	 * @param url
	 * @return
	 */
	public static Bitmap returnBitmap(String url) {
		Bitmap bitmap = null;

		File file = new File(url);
		
		if(file.exists()){
			 
			bitmap= BitmapFactory.decodeFile(url);
		       }
	    return bitmap;
	 
	}
	
   
	
}
