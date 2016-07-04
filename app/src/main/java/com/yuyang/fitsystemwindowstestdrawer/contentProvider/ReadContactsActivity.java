package com.yuyang.fitsystemwindowstestdrawer.contentProvider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.loadMoreAdapter.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用ContentResolver读取联系人
 */
public class ReadContactsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private List<String> contactsList = new ArrayList<>();
    private ContentResolver resolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_contacts);

        findViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        readContacts();
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(new MyAdapter());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void readContacts() {
        resolver = getContentResolver();
        Cursor cursor = null;
        cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactsList.add(name + "\n" + number);
        }
        cursor.close();
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.contacts_recycler_view);
    }

    private class MyAdapter extends RecyclerView.Adapter<CommonViewHolder>{

        @Override
        public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return CommonViewHolder.getViewHolder(parent, R.layout.item_just_text);
        }

        @Override
        public void onBindViewHolder(CommonViewHolder holder, int position) {
            TextView textView = holder.getViews(R.id.id_info);
            textView.setText(contactsList.get(position));
        }

        @Override
        public int getItemCount() {
            return contactsList.size();
        }
    }
}
