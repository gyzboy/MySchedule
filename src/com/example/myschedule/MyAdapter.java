package com.example.myschedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{
	
	private String[] datas;
	private LayoutInflater inflater;
	private Context context;
	private int curPos = 0;

	public void setData(String[] infos){
		this.datas = infos;
	}
	public void setPos(int pos){
		this.curPos = pos;
	}
	public int getPos(){
		return this.curPos;
	}
	public MyAdapter(Context context){
		this.context = context;
	}
	
	@Override
	public int getCount() {
		if(datas == null){
			return 0;
		}else{
			return datas.length;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return getItem(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
			holder.text = (TextView) convertView.findViewById(R.id.text);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		if(position == curPos){
			holder.text.setBackgroundColor(context.getResources().getColor(R.color.blue));
		}else{
			holder.text.setBackgroundColor(context.getResources().getColor(R.color.white));
		}
		holder.text.setText(datas[position]);
		return convertView;
	}
	static class ViewHolder{
		TextView text;
	}
}