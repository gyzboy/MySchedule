package com.example.myschedule;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.Configuration;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.ldrawer.lib.ActionBarDrawerToggle;
import com.ldrawer.lib.DrawerArrowDrawable;

public class Schedule extends Activity implements OnClickListener {

	private ImageView image;

	private ListView lv_content;
	private ListView lv_title;

	private String[] mon = { "�粿", "����ͷ��" };
	private String[] Wed = { "����", "����" };
	private String[] Thur = { "�ز�", "�Ŷ�ͷ��" };
	private String[] Sat = { "����", "С��", "����" };

	private String[] jianbu = { "�ƾ� 4 * 10", "��ƽ�� 3 * 10", "�����ƽ�� 3 * 10", "�ʼ� 4 * 10" };
	private String[] gongsantouji = { "��������� 4 * 10", "��������� 4 * 10" };
	private String[] beibu = { "����˫�ۻ��� 4 * 10", "�����ۻ��� 4 * 10", "ֱ��Ӳ�� 3 * 12", "�������� 10 * 3" };
	private String[] fubu = { "���Ծ��� 3 * 15", "�������� 3 * 15" };

	private String[] xiongbu = { "��б�ƾ� 3 * 12", "ƽ���ƾ� 4 * 10", "ƽ�Է��� 3 * 12" };
	private String[] gongertouji = { "������� 3 * 12", "������� 3 * 12", "����� 3 * 12" };
	private String[] datui = { "��� 5 * 10", "������ 3 * 12", "���������3*12" };

	private String[] xiaotui = { "վ������ 5 * 12" };

	private MyAdapter mAdapter;
	private MyAdapter cAdapter;

	private LinearLayout ll_content;
	private LinearLayout ll_video;

	private int tag = 0;
	private AlertDialog myDialog = null;

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerArrowDrawable drawerArrow;
	private ListView mDrawerList;

	// ����Զ���Ĳ����ļ�
	private ListView list;
	private PicAdapter pAdapter;
	private VideoView vvShow = null;
	private Button btn_control;
	private MediaController mediaController;

	private String[] yinti = null;

	private int[] yintiIds = new int[] { R.drawable.yinti1, R.drawable.yinti2, R.drawable.yinti3, R.drawable.yinti4, R.drawable.yinti5, R.drawable.yinti6,
			R.drawable.yinti7 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);

		yinti = new String[] { getResources().getString(R.string.yintixiangshang1), getResources().getString(R.string.yintixiangshang2),
				getResources().getString(R.string.yintixiangshang3), getResources().getString(R.string.yintixiangshang4),
				getResources().getString(R.string.yintixiangshang5), getResources().getString(R.string.yintixiangshang6),
				getResources().getString(R.string.yintixiangshang7) };

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		vvShow = (VideoView) findViewById(R.id.video);
		btn_control = (Button) findViewById(R.id.btn_control);
		ll_video = (LinearLayout) findViewById(R.id.ll_video);
		btn_control.setOnClickListener(this);
		mediaController = new MediaController(this){
			@Override
			public boolean dispatchTouchEvent(MotionEvent ev) {
				if(mediaController.isShowing()){
					mediaController.hide();
				}else{
					mediaController.show();
				}
				return super.dispatchTouchEvent(ev);
			}
		}; //���ڽ�������ʾ
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

		String[] values = new String[] { "��һ", "����", "����", "����", "��ʾͼƬ", "������Ƶ" };

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				image.setVisibility(View.INVISIBLE);
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
					image.setImageResource(R.drawable.musle);
					image.setVisibility(View.VISIBLE);
					ll_content.setVisibility(View.INVISIBLE);
					ll_video.setVisibility(View.INVISIBLE);
					break;
				case 5:
					image.setVisibility(View.INVISIBLE);
					ll_content.setVisibility(View.INVISIBLE);
					ll_video.setVisibility(View.VISIBLE);
					String uri = "android.resource://" + getPackageName() + "/" + R.raw.videoviewdemo;
					vvShow.setVideoURI(Uri.parse(uri));
					// ����VideView��MediaController��������  
					vvShow.setMediaController(mediaController);  
		            // ����MediaController��VideView��������  
		            mediaController.setMediaPlayer(vvShow);
		         // ʹ��0�Ļ���һֱ��ʾֱ������hide()  
                    mediaController.show(0);  
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
		case R.id.btn_control:
			// ��VideoView��ȡ����  
			vvShow.requestFocus(); 
			if (vvShow.isPlaying()) {
				vvShow.stopPlayback();
			} else {
				vvShow.start();
			}

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

			View layout = LayoutInflater.from(Schedule.this).inflate(R.layout.layout_alert, null);
			list = (ListView) layout.findViewById(R.id.list);
			pAdapter = new PicAdapter(Schedule.this);
			list.setAdapter(pAdapter);
			myDialog = new AlertDialog.Builder(Schedule.this).create();
			myDialog.setCanceledOnTouchOutside(true);
			myDialog.setCancelable(true);
			myDialog.setView(layout, 0, 0, 0, 0);

			switch (tag) {
			case 1:
				if (mAdapter.getPos() == 0) {// jianbu
					if (position == 0) {
						pAdapter.setItemType(0);
						pAdapter.setTip(getResources().getString(R.string.tuiju));
						pAdapter.setImage1Id(R.drawable.tuiju1);
						pAdapter.setImage2Id(R.drawable.tuiju2);
					} else if (position == 1) {
						pAdapter.setItemType(0);
						pAdapter.setTip(getResources().getString(R.string.cepingju));
						pAdapter.setImage1Id(R.drawable.cepingju1);
						pAdapter.setImage2Id(R.drawable.cepingju2);
					} else if (position == 2) {
						pAdapter.setItemType(0);
						pAdapter.setTip(getResources().getString(R.string.fushencepingju));
						pAdapter.setImage1Id(R.drawable.fushencepingju1);
						pAdapter.setImage2Id(R.drawable.fushencepingju2);
					} else {
						pAdapter.setItemType(0);
						pAdapter.setTip(getResources().getString(R.string.songjian));
					}
				} else {// gongsantouji
					if (position == 0) {
						pAdapter.setItemType(0);
						pAdapter.setTip(getResources().getString(R.string.jinghouqubishen));
						pAdapter.setImage1Id(R.drawable.jinghouqubishen);
					} else {
						pAdapter.setItemType(0);
						pAdapter.setImage1Id(R.drawable.fushenbiqushen);
					}
				}
				break;
			case 2:
				if (mAdapter.getPos() == 0) {// beibu
					if (position == 0 || position == 1) {
						pAdapter.setItemType(0);
						pAdapter.setTip(getResources().getString(R.string.fushendanbihuachuan));
						pAdapter.setImage1Id(R.drawable.fushendanbihuachuan);
					} else if (position == 2) {
						pAdapter.setItemType(0);
						pAdapter.setTip(getResources().getString(R.string.zhituiyingla));
						pAdapter.setImage1Id(R.drawable.zhituiyingla);
					} else {
						pAdapter.setItemType(1);
						pAdapter.setTips(yinti);
						pAdapter.setImageIds(yintiIds);
					}
				} else {// fubu
					if (position == 0) {
						pAdapter.setItemType(0);
						pAdapter.setTip(getResources().getString(R.string.yangwojutui));
						pAdapter.setImage1Id(R.drawable.yangwojutui1);
						pAdapter.setImage2Id(R.drawable.yangwojutui2);
					} else {
						pAdapter.setItemType(0);
						pAdapter.setTip("��������");
					}
				}
				break;
			case 3:
				if (mAdapter.getPos() == 0) {// xiongbu
					if (position == 0) {
						pAdapter.setItemType(0);
						pAdapter.setTip(getResources().getString(R.string.shangxietuiju));
						pAdapter.setImage1Id(R.drawable.shangxietuiju1);
						pAdapter.setImage2Id(R.drawable.shangxietuiju2);
					} else if (position == 1) {
						pAdapter.setItemType(0);
						pAdapter.setTip(getResources().getString(R.string.pingwotuiju));
						pAdapter.setImage1Id(R.drawable.pingwotuiju1);
						pAdapter.setImage2Id(R.drawable.pingwotuiju2);
					} else {// feiniao
						pAdapter.setItemType(0);
						pAdapter.setTip(getResources().getString(R.string.pingwoyalingfeiniao));
						pAdapter.setImage1Id(R.drawable.pingwofeiniao);
					}
				} else {// gongertouji
					if (position == 0 || position == 1) {
						pAdapter.setItemType(0);
						pAdapter.setTip(getResources().getString(R.string.danbiyalingwanju));
						pAdapter.setImage1Id(R.drawable.danbijiaotiwanju);
					} else {// �����
						pAdapter.setItemType(0);
						pAdapter.setTip(getResources().getString(R.string.cewanju));
						pAdapter.setImage1Id(R.drawable.cewanju);
					}
				}
				break;
			case 4:
				if (mAdapter.getPos() == 0) {// datui
					if (position == 0) {
						pAdapter.setItemType(0);
						pAdapter.setImage1Id(R.drawable.yalingshendun);
					} else if (position == 1) {
						pAdapter.setItemType(0);
						pAdapter.setTip(getResources().getString(R.string.yalingjianbudun));
					} else {
						pAdapter.setItemType(0);
						pAdapter.setImage1Id(R.drawable.yalingwantuiju);
					}
				} else if (mAdapter.getPos() == 1) {// xiaotui
					pAdapter.setItemType(0);
					pAdapter.setTip(getResources().getString(R.string.zhanlitizhong));
				} else {// fubu
					if (position == 0) {
						pAdapter.setItemType(0);
						pAdapter.setTip(getResources().getString(R.string.yangwojutui));
						pAdapter.setImage1Id(R.drawable.yangwojutui1);
						pAdapter.setImage2Id(R.drawable.yangwojutui2);
					} else {
						pAdapter.setItemType(0);
						pAdapter.setTip("��������");
					}
				}
				break;
			}
			pAdapter.notifyDataSetChanged();
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
