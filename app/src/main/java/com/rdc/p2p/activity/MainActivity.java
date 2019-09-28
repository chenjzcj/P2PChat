package com.rdc.p2p.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rdc.p2p.R;
import com.rdc.p2p.base.BaseActivity;
import com.rdc.p2p.base.BasePresenter;
import com.rdc.p2p.fragment.AboutFragment;
import com.rdc.p2p.fragment.JoinFragment;
import com.rdc.p2p.fragment.PeerListFragment;
import com.rdc.p2p.fragment.ScanDeviceFragment;
import com.rdc.p2p.gdt.NativeADCommonActivity;

import java.net.InetAddress;
import java.net.MulticastSocket;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    //    @BindView(R.id.dl_drawer_act_main)
//    DrawerLayout mDrawerLayout;
//    @BindView(R.id.ll_bottom_left_layout_act_main)
//    LinearLayout mLlBottomLeft;
//    @BindView(R.id.iv_chat_act_main)
//    ImageView mIvChat;
//    @BindView(R.id.iv_peer_list_act_main)
//    ImageView mIvPeerList;
//    @BindView(R.id.tv_chat_act_main)
//    TextView mTvChat;
//    @BindView(R.id.tv_peer_list_act_main)
//    TextView mTvPeerList;
//    @BindView(R.id.ll_bottom_right_layout_act_main)
//    LinearLayout mLlBottomRight;
    @BindView(R.id.vp_act_main)
    ViewPager mVpContent;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.v_divider)
    View vDivider;
    @BindView(R.id.tv_chat)
    TextView tvChat;
    @BindView(R.id.ll_chat)
    LinearLayout llChat;
    @BindView(R.id.tv_join)
    TextView tvJoin;
    @BindView(R.id.ll_join)
    LinearLayout llJoin;
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    private FragmentPagerAdapter mFragmentPagerAdapter;
    private boolean checking = true;// true 选中聊天列表 , false 选中 聊天室
    private static final String TAG = "LYT";
    private static final int BROADCAST_PORT = 3000;
    private static final String BROADCAST_IP = "239.0.0.3";
    private MulticastSocket mSocket;
    private InetAddress mAddress;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
//                    mTvShow.append("\n"+message.obj.toString());
                    break;
            }
            return true;
        }
    });
    private PeerListFragment mPeerListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public BasePresenter getInstance() {
        return null;
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        NativeADCommonActivity.showNativeADDialog(this, NativeADCommonActivity.NATIVE_AD_TYPE2, -1);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        NativeADCommonActivity.showNativeADDialog(this, NativeADCommonActivity.NATIVE_AD_TYPE1, NativeADCommonActivity.OK_ACTION_TYPE_EXIT);
    }

    @Override
    protected void initView() {
        initToolbar();
//        ActionBarDrawerToggle mDrawerToggle =
//                new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
//        mDrawerToggle.syncState();
//        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mPeerListFragment = new PeerListFragment();
        final JoinFragment joinFragment = new JoinFragment();
        final AboutFragment aboutFragment = new AboutFragment();
        mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return mPeerListFragment;
                    case 1:
                        return joinFragment;
                    case 2:
                        return aboutFragment;
                    default:
                        break;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public void restoreState(Parcelable state, ClassLoader loader) {
                super.restoreState(state, loader);
            }

            @Override
            public Parcelable saveState() {
                return super.saveState();
            }
        };
        mVpContent.setOffscreenPageLimit(3);
        mVpContent.setAdapter(mFragmentPagerAdapter);
        mVpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switchTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        switchTab(0);

        llChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVpContent.setCurrentItem(0);
                switchTab(0);
            }
        });
        llJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVpContent.setCurrentItem(1);
                switchTab(1);
            }
        });
        llAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVpContent.setCurrentItem(2);
                switchTab(2);
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mToolbar.setTitle("");
    }

    private void switchTab(int index) {
        tvChat.setSelected(index == 0);
        tvJoin.setSelected(index == 1);
        tvAbout.setSelected(index == 2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                if (mPeerListFragment.isServerSocketConnected()) {
                    ScanDeviceFragment mScanDeviceFragment = new ScanDeviceFragment();
                    mScanDeviceFragment.setCancelable(false);
                    mScanDeviceFragment.show(getSupportFragmentManager(), "scanDevice");
                } else {
                    showToast("ServerSocket未连接，请检查WIFI！");
                }
                break;
//            case android.R.id.home:
//                mDrawerLayout.openDrawer(GravityCompat.START);
//                break;
        }
        return true;
    }

    @Override
    protected void initListener() {
//        mLlBottomLeft.setOnClickListener(this);
//        mLlBottomRight.setOnClickListener(this);
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.ll_bottom_right_layout_act_main:
//                mIvChat.setImageResource(R.drawable.iv_chat_pressed);
//                mTvChat.setTextColor(getResources().getColor(R.color.colorPrimary));
//                mIvPeerList.setImageResource(R.drawable.iv_peer_list_normal);
//                mTvPeerList.setTextColor(getResources().getColor(R.color.grey_text_or_bg));
//                break;
//            case R.id.ll_bottom_left_layout_act_main:
//                mIvChat.setImageResource(R.drawable.iv_chat_normal);
//                mTvChat.setTextColor(getResources().getColor(R.color.grey_text_or_bg));
//                mIvPeerList.setImageResource(R.drawable.iv_peer_list_pressed);
//                mTvPeerList.setTextColor(getResources().getColor(R.color.colorPrimary));
//                Log.d(TAG, "SocketManager:"+ SocketManager.getInstance().toString());
//                break;
//        }
//    }


//    private void initServerSocket() {
//        try {
//            mSocket = new MulticastSocket(BROADCAST_PORT);
//            mAddress = InetAddress.getByName(BROADCAST_IP);
//            mSocket.setTimeToLive(2);
//            mSocket.joinGroup(mAddress);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private void receiveBroadcast(){
//        byte buf[] = new byte[1024];
//        DatagramPacket packet = new DatagramPacket(buf,buf.length,mAddress,BROADCAST_PORT);
//        try {
//            mSocket.receive(packet);
//            String content = new String(buf,0,packet.getLength());
//            Log.d(TAG, "receiveBroadcast: "+content);
//            MessageEntity message = new MessageEntity();
//            message.what = 0;
//            message.obj = content;
//            mHandler.sendMessage(message);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private void sendMultiBroadcast(){
//        DatagramPacket packet;
//        String msg = mEtInput.getText().toString();
//        if (!msg.equals("")){
//            byte[] bytes = msg.getBytes();
//            packet = new DatagramPacket(bytes,bytes.length,mAddress,BROADCAST_PORT);
//            try {
//                mSocket.send(packet);
//                Log.d(TAG, "sendMultiBroadcast: success");
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.d(TAG, "sendMultiBroadcast: error");
//            }
//        }else {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(MainActivity.this, "输入不能为空！", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }
}
