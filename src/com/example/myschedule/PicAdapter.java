package com.example.myschedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class PicAdapter extends BaseAdapter {
	
	private Context mContext;
	private int[] imageIds;
	private String[] tips;
	
	private String tip;
	private int image1Id;
	private int image2Id;
	
	private int itemType = 0;
	
	public PicAdapter(Context context){
		mContext = context;
	}
	public void setImageIds(int[] ids){
		this.imageIds = ids;
	}
	
	public void setItemType(int type){
		this.itemType = type;
	}
	
	public void setTip(String tip){
		this.tip = tip;
	}
	public void setImage1Id(int image1){
		this.image1Id = image1;
	}
	public void setImage2Id(int image2){
		this.image2Id = image2;
	}

	public void setTips(String[] tips){
		this.tips = tips;
	}
	@Override
	public int getCount() {
		if(tip != null || image1Id != 0){
			return 1;
		}else if(imageIds != null){
			return imageIds.length;
		}else{
			return 0;
		}
	}


	@Override
	public Object getItem(int arg0) {
		return getItem(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder = null;
		if(arg1 == null){
			holder = new ViewHolder();
			if(itemType == 0){//两张图片，一段文字
				arg1 = LayoutInflater.from(mContext).inflate(R.layout.item_click, null);
				holder.text = (TextView) arg1.findViewById(R.id.tip);
				holder.image1 = (ImageView) arg1.findViewById(R.id.iv_pic1);
				holder.image2 = (ImageView) arg1.findViewById(R.id.iv_pic2);
			}else{
				arg1 = LayoutInflater.from(mContext).inflate(R.layout.item_pic, null);
				holder.text = (TextView) arg1.findViewById(R.id.tip_pic);
				holder.image = (ImageView) arg1.findViewById(R.id.image);
			}
			arg1.setTag(holder);
		}else{
			holder = (ViewHolder) arg1.getTag();
		}
		if(itemType == 0){
			holder.text.setText(tip);
			if(image1Id != 0){
				Glide.with(mContext).load(image1Id)
		        .crossFade().into(holder.image1);
				if(image2Id != 0){
					Glide.with(mContext).load(image2Id)
			        .crossFade().into(holder.image2);
				}
			}
		}else{
			holder.text.setText(tips[arg0]);
			Glide.with(mContext).load(imageIds[arg0])
	        .crossFade().into(holder.image);
		}
		return arg1;
	}
	static class ViewHolder{
		private TextView text;
		private ImageView image;
		private ImageView image1;
		private ImageView image2;
	}
}
