package com.xuhao.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidtestproject.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by ritaa on 2015/8/1.
 */
public class FriendListFragment extends Fragment{
    private Context mContext;
    private View mBaseView;
    private TitleBarView mTitleBarView;
    private ListView mFriendListView;
    private List<User> mFriendList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        mBaseView = inflater.inflate(R.layout.fragment_friendlist, null);

        findView();
        init();
        return mBaseView;
    }
    private void findView() {
        mTitleBarView = (TitleBarView) mBaseView.findViewById(R.id.title_bar);
        mFriendListView = (ListView)mBaseView.findViewById(R.id.friend_list_listview);
    }
    private void init() {
        //设置标题栏
        mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.VISIBLE);
        mTitleBarView.setTitleText("好友");
        mTitleBarView.setBtnLeft(R.string.control);
        mTitleBarView.setBtnRight(R.drawable.qq_constact);
        //设置联系人
        mFriendList=new ArrayList<User>();
        mFriendList.add(new User("徐豪","这是一个逗比"));
        mFriendList.add(new User("�ſ�ǿ","���Ǹ�����"));
        mFriendList.add(new User("��ǿ","���Ǹ�����"));
        mFriendListView.setAdapter(new FriendListAdapter(mContext,mFriendList));

    }
    class FriendListAdapter extends BaseAdapter{
        private List<User> mFriendList;
        private LayoutInflater mInflater;
        public FriendListAdapter(Context context, List<User> vector) {
            super();
            this.mFriendList = vector;
            mInflater = LayoutInflater.from(context);
            System.out.println("��ʼ��FriendAdapter");
        }
        public int getCount() {
            return mFriendList.size();
        }

        public Object getItem(int position) {
            return mFriendList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView avatarView;
            TextView nameView;
            TextView introView;
            User user = mFriendList.get(position);
            Bitmap photo=user.getHead_photo();
            String name = user.getUserName();
            String briefIntro = user.getUserBriefIntro();
            convertView = mInflater.inflate(R.layout.friend_list_item,
                    null);
            avatarView = (ImageView) convertView
                    .findViewById(R.id.user_photo);
            nameView = (TextView) convertView
                    .findViewById(R.id.friend_list_name);
            introView = (TextView) convertView
                    .findViewById(R.id.friend_list_brief);
            avatarView.setImageBitmap(photo);
            introView.setText(briefIntro);
            nameView.setText(name);

            return convertView;
        }
    }
}
