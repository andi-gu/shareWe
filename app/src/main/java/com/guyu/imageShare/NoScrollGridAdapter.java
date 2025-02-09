package com.guyu.imageShare;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.fragmenttabhost.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class NoScrollGridAdapter extends BaseAdapter {     // 九宫格图片绑定适配器

	/** 上下文 */
	private Context ctx;
	/** 图片Url集合 */
	private List<String> imageUrls;

	public NoScrollGridAdapter(Context ctx, List<String> urls) {
		this.ctx = ctx;
		this.imageUrls = urls;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageUrls == null ? 0 : imageUrls.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return imageUrls.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(ctx, R.layout.item_gridview_share, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.iv_image_grid);
		 DisplayImageOptions options = new DisplayImageOptions.Builder()//
			.cacheInMemory(true)//
			.cacheOnDisk(true)//
			.bitmapConfig(Config.RGB_565)//
			.build();
	ImageLoader.getInstance().displayImage(imageUrls.get(position), imageView, options);
	return view;
	}

}
