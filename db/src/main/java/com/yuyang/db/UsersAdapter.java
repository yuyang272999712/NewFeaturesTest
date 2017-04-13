package com.yuyang.db;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuyang.db.bean.User;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private UserClickListener clickListener;
    private UserLongClickListener longClickListener;
    private List<User> dataset;
    private java.text.DateFormat df = java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM);

    public interface UserClickListener {
        void onUserClick(int position);
    }

    public interface UserLongClickListener {
        void onUserLongClick(int position);
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView firstName;
        public TextView height;
        public TextView birthday;

        public UserViewHolder(View itemView, final UserClickListener clickListener, final UserLongClickListener longClickListener) {
            super(itemView);
            firstName = (TextView) itemView.findViewById(R.id.first_name);
            height = (TextView) itemView.findViewById(R.id.height);
            birthday = (TextView) itemView.findViewById(R.id.birthday);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.onUserClick(getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (longClickListener != null) {
                        longClickListener.onUserLongClick(getAdapterPosition());
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    public UsersAdapter(UserClickListener clickListener, UserLongClickListener longClickListener) {
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
        this.dataset = new ArrayList<User>();
    }

    public void setNotes(@NonNull List<User> notes) {
        dataset = notes;
        notifyDataSetChanged();
    }

    public User getUser(int position) {
        return dataset.get(position);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view, clickListener, longClickListener);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User note = dataset.get(position);
        holder.firstName.setText(note.getFirstName());
        holder.height.setText(String.valueOf(note.getHeight()));
        holder.birthday.setText(df.format(note.getBrithday()));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
