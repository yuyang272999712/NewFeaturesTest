package com.yuyang.db;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyang.db.base.DBManager;
import com.yuyang.db.bean.User;
import com.yuyang.db.bean.UserDao;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView id;
    private EditText firstName;
    private EditText lastName;
    private TextView birthDay;
    private RadioGroup mSex;
    private RadioButton man;
    private RadioButton woman;
    private EditText height;

    private User user;
    private User.Sex sex = User.Sex.MAN;
    java.text.DateFormat df = java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM);

    private UserDao userDao;
    private Query<User> usersQuery;
    private UsersAdapter usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initActions();
        initDatas();
    }

    private void initDatas() {
        birthDay.setText(df.format(new Date()));

        userDao = DBManager.getInstance().getUserDao();
        usersQuery = userDao.queryBuilder().orderAsc(UserDao.Properties.FirstName).build();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        usersAdapter = new UsersAdapter(noteClickListener, noteLongClickListener);
        recyclerView.setAdapter(usersAdapter);

        updateUsers();
    }

    private void initActions() {
        mSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.man:
                        sex = User.Sex.MAN;
                        break;
                    case R.id.woman:
                        sex = User.Sex.WOMAN;
                        break;
                }
            }
        });
    }

    public void add(View view){
        User user = new User();
        if (!TextUtils.isEmpty(firstName.getText())) {
            user.setFirstName(firstName.getText().toString());
        }
        if (!TextUtils.isEmpty(lastName.getText())) {
            user.setLastName(lastName.getText().toString());
        }
        try {
            user.setBrithday(df.parse(birthDay.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setSex(sex);
        if (!TextUtils.isEmpty(height.getText())) {
            user.setHeight(Integer.valueOf(height.getText().toString()));
        }

        //添加
        userDao.insert(user);
        updateUsers();
    }

    public void modify(View view){
        if (TextUtils.isEmpty(id.getText())){
            Toast.makeText(this,"请先选择一个人",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.isEmpty(firstName.getText())) {
            user.setFirstName(firstName.getText().toString());
        }
        if (!TextUtils.isEmpty(lastName.getText())) {
            user.setLastName(lastName.getText().toString());
        }
        try {
            user.setBrithday(df.parse(birthDay.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setSex(sex);
        if (!TextUtils.isEmpty(height.getText())) {
            user.setHeight(Integer.valueOf(height.getText().toString()));
        }

        //修改
        userDao.update(user);
        updateUsers();
    }

    public void query(View view){
        QueryBuilder<User> builder = userDao.queryBuilder();
        List<WhereCondition> conditions = new ArrayList<>();
        if (!TextUtils.isEmpty(firstName.getText())) {
            conditions.add(UserDao.Properties.FirstName.like(firstName.getText().toString()));
        }
        if (!TextUtils.isEmpty(lastName.getText())) {
            conditions.add(UserDao.Properties.LastName.like(lastName.getText().toString()));
        }
        try {
            conditions.add(UserDao.Properties.Brithday.le(df.parse(birthDay.getText().toString())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        User.SexConverter converter = new User.SexConverter();
        conditions.add(UserDao.Properties.Sex.eq(converter.convertToDatabaseValue(sex)));
        if (!TextUtils.isEmpty(height.getText())) {
            conditions.add(UserDao.Properties.Height.eq(Integer.valueOf(height.getText().toString())));
        }
        if (conditions.size() > 0){
            builder.where(new WhereCondition.StringCondition("1==1"), conditions.toArray(new WhereCondition[conditions.size()]));
        }
        usersQuery = builder.orderAsc(UserDao.Properties.FirstName).build();

        updateUsers();
    }

    public void cleanCondition(View view){
        usersQuery = userDao.queryBuilder().orderAsc(UserDao.Properties.FirstName).build();
        updateUsers();
    }

    public void getBirthDay(View view){
        Calendar ca = Calendar.getInstance();
        int mYear = ca.get(Calendar.YEAR);
        int mMonth = ca.get(Calendar.MONTH);
        int mDay = ca.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                birthDay.setText(year+"年"+(month+1)+"月"+dayOfMonth+"日");
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewNotes);
        id = (TextView) findViewById(R.id.user_id);
        firstName = (EditText) findViewById(R.id.user_first_name);
        lastName = (EditText) findViewById(R.id.user_last_name);
        birthDay = (TextView) findViewById(R.id.user_birth_day);
        mSex = (RadioGroup) findViewById(R.id.user_sex);
        height = (EditText) findViewById(R.id.user_height);
        man = (RadioButton) findViewById(R.id.man);
        woman = (RadioButton) findViewById(R.id.woman);
    }

    private void updateUsers() {
        List<User> users = usersQuery.list();
        usersAdapter.setNotes(users);
    }

    private void updateViews(){
        id.setText(String.valueOf(user.getId()));
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        birthDay.setText(df.format(user.getBrithday()));
        if (user.getSex() == User.Sex.MAN){
            man.setChecked(true);
        }else {
            woman.setChecked(true);
        }
        height.setText(String.valueOf(user.getHeight()));
    }

    UsersAdapter.UserClickListener noteClickListener = new UsersAdapter.UserClickListener() {
        @Override
        public void onUserClick(int position) {
            user = usersAdapter.getUser(position);
            updateViews();
        }
    };

    UsersAdapter.UserLongClickListener noteLongClickListener = new UsersAdapter.UserLongClickListener() {
        @Override
        public void onUserLongClick(int position) {
            User user = usersAdapter.getUser(position);
            userDao.delete(user);
            updateUsers();
        }
    };
}
