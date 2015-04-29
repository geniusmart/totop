package com.totop.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.Toast;

import com.totop.fragment.ContactUsFragment;
import com.totop.fragment.GoodsListFragment;
import com.totop.fragment.HelpFragment;
import com.totop.fragment.OnHomeFragmentListener;
import com.totop.manager.VersionManager;
import com.totop.model.Item;
import com.totop.utils.UMengShareUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;


public class MainActivity extends BaseMenuActivity implements OnHomeFragmentListener {

    private FragmentTransaction mFragmentTransaction;
    private FragmentManager mFragmentManager;

    private String mCurrentFragmentTag;

    private static final String STATE_CURRENT_FRAGMENT = "com.totop.activity.MainActivity";
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            mCurrentFragmentTag = savedInstanceState.getString(STATE_CURRENT_FRAGMENT);
        }else{
            openHome();
        }

        mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);
        //TODO 以下代码的作用？
        mMenuDrawer.setOnDrawerStateChangeListener(new MenuDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                if (newState == MenuDrawer.STATE_CLOSED) {
                    commitTransactions();
                }
            }

            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {
                // Do nothing
            }
        });
        VersionManager.getInstance(this).checkVersion(false);

        //友盟分享相关
        UMengShareUtils.addCustomPlatforms(this);

    }

    private void openHome(){
        mCurrentFragmentTag = ((Item) mAdapter.getItem(0)).mTitle;
        attachFragment(mMenuDrawer.getContentContainer().getId(),getFragment(mCurrentFragmentTag),mCurrentFragmentTag);
        commitTransactions();
    }

    @Override
    protected void onMenuItemClicked(int position, Item item) {

        String title = item.mTitle;

        if(!title.equals(mCurrentFragmentTag)){
            if(title.equals(getString(R.string.menu_share))){
                Toast.makeText(this,"分享",Toast.LENGTH_SHORT).show();
                UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
                mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
                // 设置分享图片, 参数2为图片的url地址
                mController.setShareMedia(new UMImage(this,
                        "http://www.umeng.com/images/pic/banner_module_social.png"));
                mController.getConfig().removePlatform(SHARE_MEDIA.SINA);
                mController.getConfig().removePlatform(SHARE_MEDIA.TENCENT);
                mController.openShare(this, false);
            }else if(title.equals(getString(R.string.menu_version))){
                VersionManager.getInstance(this).checkVersion(true);
            }else if(title.equals(getString(R.string.menu_hot))){
                Toast.makeText(this,"热门",Toast.LENGTH_SHORT).show();
            }else {
                if (mCurrentFragmentTag != null){
                    detachFragment(getFragment(mCurrentFragmentTag));
                }
                attachFragment(mMenuDrawer.getContentContainer().getId(), getFragment(title), title);
                mCurrentFragmentTag = title;
            }
        }
        mMenuDrawer.closeMenu();
    }

    @Override
    protected int getDragMode() {
        return MenuDrawer.MENU_DRAG_WINDOW;
    }

    @Override
    protected Position getDrawerPosition() {
        return Position.LEFT;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_CURRENT_FRAGMENT, mCurrentFragmentTag);
    }

    private void commitTransactions() {
        if (mFragmentTransaction != null && !mFragmentTransaction.isEmpty()) {
            mFragmentTransaction.commit();
            mFragmentTransaction = null;
        }
    }

    private FragmentTransaction ensureTransaction() {
        if (mFragmentTransaction == null) {
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        }

        return mFragmentTransaction;
    }

    private void attachFragment(int layout, Fragment f, String tag) {
        if (f != null) {
            if (f.isDetached()) {
                ensureTransaction();
                mFragmentTransaction.attach(f);
            } else if (!f.isAdded()) {
                ensureTransaction();
                mFragmentTransaction.add(layout, f, tag);
            }
        }
    }

    private void detachFragment(Fragment f) {
        if (f != null && !f.isDetached()) {
            ensureTransaction();
            mFragmentTransaction.detach(f);
        }
    }

    private Fragment getFragment(String tag) {
        Fragment f = mFragmentManager.findFragmentByTag(tag);
        if (f == null) {
            if(tag.equals(getString(R.string.menu_home))){
                f = GoodsListFragment.newInstance();
            }else if(tag.equals(getString(R.string.menu_contact))){
                f = ContactUsFragment.newInstance();
            }else if(tag.equals(getString(R.string.menu_help))){
                f = HelpFragment.newInstance();
            }
        }
        return f;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            final int drawerState = mMenuDrawer.getDrawerState();
            if (drawerState == MenuDrawer.STATE_OPEN || drawerState == MenuDrawer.STATE_OPENING) {
                mMenuDrawer.closeMenu();
            }else{
                //首页
                String title = getString(R.string.menu_home);
                if(!title.equals(mCurrentFragmentTag)){
                    back();
                }else{
                    exit();
                }
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public void toggle() {
        mMenuDrawer.toggleMenu();
    }

    @Override
    public void back(){
        if (mCurrentFragmentTag != null){
            detachFragment(getFragment(mCurrentFragmentTag));
        }
        openHome();
    }
}
