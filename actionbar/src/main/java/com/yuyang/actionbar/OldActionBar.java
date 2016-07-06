package com.yuyang.actionbar;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 * ActionBar专题
 */
public class OldActionBar extends AppCompatActivity {
    private ActionBar actionBar;
    /**菜单名称***/
    private String[] menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_action_bar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        menuItems = getResources().getStringArray(R.array.menus);//获取string_array.xml中的menus数组
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuItems );
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(adapter, new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                Toast.makeText(OldActionBar.this, "您选择了："+ menuItems[itemPosition], Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    /**
     * 向ActionBar中添加Item
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        setShare(menu);
        setSearch(menu);
        return true;
    }

    /**
     * 为ActionBar实现分享
     * @param menu
     */
    private void setShare(Menu menu) {
        MenuItem item = menu.findItem(R.id.main_share);
        ShareActionProvider sap = (ShareActionProvider) MenuItemCompat
                .getActionProvider(item);
        Intent shareIntent = new Intent();
        shareIntent.setType("text/plain");
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "ActionBar实现分享@author:jph");
        sap.setShareIntent(shareIntent);
    }

    /**
     * 为ActionBar实现查询功能
     * @param menu
     */
    private void setSearch(Menu menu) {
        final MenuItem item = menu.findItem(R.id.main_search);
        final SearchView sv = (SearchView) MenuItemCompat.getActionView(item);
        if(sv != null){
            sv.setIconifiedByDefault(true);
            sv.setSubmitButtonEnabled(true);
            sv.setQueryHint("请输入");
            sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String arg0) {
                    Toast.makeText(OldActionBar.this, arg0, Toast.LENGTH_SHORT);
                    MenuItemCompat.collapseActionView(item);
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String arg0) {
                    return false;
                }
            });
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String toastStr = "";
        switch (id){
            case R.id.main_camera:
                toastStr = "camera";
                break;
            case R.id.main_gallery:
                toastStr = "gallery";
                break;
            case R.id.main_manage:
                toastStr = "manage";
                break;
            case R.id.main_search:
                toastStr = "send";
                break;
            case R.id.main_share:
                toastStr = "share";
                break;
            case R.id.main_slide_show:
                toastStr = "slideshow";
                break;
        }
        Toast.makeText(this, toastStr, Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }

    public void gotoNewToolbarActivity(View view){
        Intent intent = new Intent(this, NewToolbar.class);
        startActivity(intent);
    }
}
