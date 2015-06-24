package com.example.myschedule;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ldrawer.lib.ActionBarDrawerToggle;
import com.ldrawer.lib.DrawerArrowDrawable;
import com.squareup.picasso.Picasso;

public class Schedule extends Activity implements OnClickListener {

	private ImageView image;

	private ListView lv_content;
	private ListView lv_title;

	private String[] mon = { "肩部", "肱三头肌" };
	private String[] Wed = { "背部", "腹部" };
	private String[] Thur = { "胸部", "肱二头肌" };
	private String[] Sat = { "大腿", "小腿", "腹部" };

	private String[] jianbu = { "推举 4 * 10", "侧平举 3 * 10", "俯身侧平举 3 * 10", "耸肩 4 * 10" };
	private String[] gongsantouji = { "颈后臂屈伸 4 * 10", "俯身臂屈伸 4 * 10" };
	private String[] beibu = { "俯身双臂划船 4 * 10", "俯身单臂划船 4 * 10", "直腿硬拉 3 * 12" };
	private String[] fubu = { "仰卧举腿 3 * 15", "仰卧起坐 3 * 15" };

	private String[] xiongbu = { "上斜推举 3 * 12", "平卧推举 4 * 10", "平卧飞鸟 3 * 12" };
	private String[] gongertouji = { "交替弯举 3 * 12", "意念弯举 3 * 12", "侧弯举 3 * 12" };
	private String[] datui = { "深蹲 5 * 10", "箭步蹲 3 * 12", "俯卧腿弯举3*12" };

	private String[] xiaotui = { "站立提踵 5 * 12" };

	private MyAdapter mAdapter;
	private MyAdapter cAdapter;

	private LinearLayout ll_content;

	private int tag = 0;
	private AlertDialog myDialog = null;

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerArrowDrawable drawerArrow;
	private ListView mDrawerList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		drawerArrow = new DrawerArrowDrawable(this) {
			@Override
			public boolean isLayoutRtl() {
				return false;
			}
		};
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, drawerArrow, R.string.drawer_open, R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();

		mDrawerList = (ListView) findViewById(R.id.navdrawer);

		String[] values = new String[] { "周一", "周三", "周四", "周六", "显示图片" };

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:
					updateStatus(1);
					break;
				case 1:
					updateStatus(2);
					break;
				case 2:
					updateStatus(3);
					break;
				case 3:
					updateStatus(4);
					break;
				case 4:
					image.setVisibility(View.VISIBLE);
					ll_content.setVisibility(View.INVISIBLE);
					break;
				}
				mDrawerLayout.closeDrawer(mDrawerList);
			}
		});

		mAdapter = new MyAdapter(Schedule.this);
		cAdapter = new MyAdapter(Schedule.this);

		ll_content = (LinearLayout) findViewById(R.id.ll_content);

		image = (ImageView) findViewById(R.id.iv_musle);
		image.setOnClickListener(this);

		lv_title = (ListView) findViewById(R.id.lv_title);
		lv_content = (ListView) findViewById(R.id.lv_content);

		lv_title.setAdapter(mAdapter);
		lv_title.setOnItemClickListener(new tItemLsn());

		lv_content.setAdapter(cAdapter);
		lv_content.setOnItemClickListener(new cItemClick());

	}

	private void updateStatus(int pos) {
		cAdapter.setData(null);
		mAdapter.setPos(-1);
		switch (pos) {
		case 1:
			mAdapter.setData(mon);
			tag = 1;
			break;
		case 2:
			mAdapter.setData(Wed);
			tag = 2;
			break;
		case 3:
			mAdapter.setData(Thur);
			tag = 3;
			break;
		case 4:
			mAdapter.setData(Sat);
			tag = 4;
			break;

		}
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_musle:
			image.setVisibility(View.INVISIBLE);
			ll_content.setVisibility(View.VISIBLE);
			break;

		}
	}

	private class tItemLsn implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			mAdapter.setPos(position);
			cAdapter.setPos(-1);
			switch (tag) {
			case 1:
				if (position == 0) {
					cAdapter.setData(jianbu);
				} else {
					cAdapter.setData(gongsantouji);
				}
				break;
			case 2:
				if (position == 0) {
					cAdapter.setData(beibu);
				} else {
					cAdapter.setData(fubu);
				}
				break;
			case 3:
				if (position == 0) {
					cAdapter.setData(xiongbu);
				} else {
					cAdapter.setData(gongertouji);
				}
				break;
			case 4:
				if (position == 0) {
					cAdapter.setData(datui);
				} else if (position == 1) {
					cAdapter.setData(xiaotui);
				} else {
					cAdapter.setData(fubu);
				}
				break;
			}
			mAdapter.notifyDataSetChanged();
			cAdapter.notifyDataSetChanged();
		}

	}

	private class cItemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// 添加自定义的布局文件
			View layout = LayoutInflater.from(Schedule.this).inflate(R.layout.item_click, null);
			TextView text = (TextView) layout.findViewById(R.id.tip);
			ImageView image1 = (ImageView) layout.findViewById(R.id.iv_pic1);
			ImageView image2 = (ImageView) layout.findViewById(R.id.iv_pic2);
			switch (tag) {
			case 1:
				if (mAdapter.getPos() == 0) {// jianbu
					if (position == 0) {
						text.setText(getResources().getString(R.string.tuiju));
						Picasso.with(Schedule.this).load(R.drawable.tuiju1).into(image1);
						Picasso.with(Schedule.this).load(R.drawable.tuiju2).into(image2);
					} else if (position == 1) {
						text.setText(getResources().getString(R.string.cepingju));
						Picasso.with(Schedule.this).load(R.drawable.cepingju1).into(image1);
						Picasso.with(Schedule.this).load(R.drawable.cepingju2).into(image2);
					} else if (position == 2) {
						text.setText(getResources().getString(R.string.fushencepingju));
						Picasso.with(Schedule.this).load(R.drawable.fushencepingju1).into(image1);
						Picasso.with(Schedule.this).load(R.drawable.fushencepingju2).into(image2);
					} else {
						text.setText(getResources().getString(R.string.songjian));
					}
				} else {// gongsantouji
					if (position == 0) {
						text.setText(getResources().getString(R.string.jinghouqubishen));
					} else {
						Picasso.with(Schedule.this).load(R.drawable.fushenbiqushen).into(image1);
					}
				}
				break;
			case 2:
				if (mAdapter.getPos() == 0) {// beibu
					if (position == 0 || position == 1) {
						text.setText(getResources().getString(R.string.fushendanbihuachuan));
						Picasso.with(Schedule.this).load(R.drawable.fushendanbihuachuan).into(image1);
					} else {
						text.setText(getResources().getString(R.string.zhituiyingla));
						Picasso.with(Schedule.this).load(R.drawable.zhituiyingla).into(image1);
					}
				} else {// fubu
					if (position == 0) {
						text.setText(getResources().getString(R.string.yangwojutui));
						Picasso.with(Schedule.this).load(R.drawable.yangwojutui1).into(image1);
						Picasso.with(Schedule.this).load(R.drawable.yangwojutui2).into(image2);
					} else {
						text.setText("仰卧起坐");
					}
				}
				break;
			case 3:
				if (mAdapter.getPos() == 0) {// xiongbu
					if (position == 0) {
						text.setText(getResources().getString(R.string.shangxietuiju));
						Picasso.with(Schedule.this).load(R.drawable.shangxietuiju1).into(image1);
						Picasso.with(Schedule.this).load(R.drawable.shangxietuiju2).into(image2);
					} else if (position == 1) {
						text.setText(getResources().getString(R.string.pingwotuiju));
						Picasso.with(Schedule.this).load(R.drawable.pingwotuiju1).into(image1);
						Picasso.with(Schedule.this).load(R.drawable.pingwotuiju2).into(image2);
					} else {
						text.setText(getResources().getString(R.string.pingwoyalingfeiniao));
					}
				} else {// gongertouji
					text.setText(getResources().getString(R.string.danbiyalingwanju));
					Picasso.with(Schedule.this).load(R.drawable.danbijiaotiwanju).into(image1);
				}
				break;
			case 4:
				if (mAdapter.getPos() == 0) {// datui
					if (position == 0) {
						Picasso.with(Schedule.this).load(R.drawable.yalingshendun).into(image1);
					} else if (position == 1) {
						text.setText(getResources().getString(R.string.yalingjianbudun));
					} else {
						Picasso.with(Schedule.this).load(R.drawable.yalingwantuiju).into(image1);
					}
				} else if (mAdapter.getPos() == 1) {// xiaotui
					text.setText(getResources().getString(R.string.zhanlitizhong));
				} else {// fubu
					if (position == 0) {
						text.setText(getResources().getString(R.string.yangwojutui));
						Picasso.with(Schedule.this).load(R.drawable.yangwojutui1).into(image1);
						Picasso.with(Schedule.this).load(R.drawable.yangwojutui2).into(image2);
					} else {
						text.setText("仰卧起坐");
					}
				}
				break;
			}
			myDialog = new AlertDialog.Builder(Schedule.this).create();
			myDialog.setCanceledOnTouchOutside(true);
			myDialog.setCancelable(true);
			myDialog.setView(layout, 0, 0, 0, 0);
			myDialog.show();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (myDialog != null && myDialog.isShowing()) {
			myDialog.dismiss();
		}
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
}
