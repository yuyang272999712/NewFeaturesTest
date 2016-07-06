package com.yuyang.actionbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * 新版Toolbar
 */
public class NewToolbar extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_toolbar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
                    Toast.makeText(NewToolbar.this, arg0, Toast.LENGTH_SHORT);
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
}
