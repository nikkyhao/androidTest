package com.xuhao.activity;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidtestproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritaa on 2015/8/1.
 */
public class MessageFragment extends Fragment {
    private Context mContext;
    private View mBaseView;
    private TitleBarView mTitleBarView;
    private List<MessageTabEntity> mMessageEntityList;
    private ListView mMessageListView;
    private FriendMessageAdapter adapter;
    private BaseDialog mDialog;
    private Handler handler;
    private int mPosition;
    private MessageTabEntity chooseMessageEntity;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        mBaseView = inflater.inflate(R.layout.fragment_message, null);
        findView();
        init();
        return mBaseView;
    }

    private void findView() {
        mTitleBarView = (TitleBarView) mBaseView.findViewById(R.id.title_bar);

        mMessageListView = (ListView) mBaseView
                .findViewById(R.id.message_list_listview);
    }
    private void init() {
        //设置标题栏
        mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE);
        mTitleBarView.setTitleText("消息");
        
        mMessageEntityList=new ArrayList<MessageTabEntity>();

        mMessageEntityList.add(new MessageTabEntity("徐豪","我睡了周强女友，你别跟他说","2015-8-1"));
        mMessageEntityList.add(new MessageTabEntity("周强","徐豪是不是睡了我女友","2015-8-1"));
        mMessageEntityList.add(new MessageTabEntity("张凯强","我看到徐豪睡了周强女友","2015-8-1"));

        
        adapter = new FriendMessageAdapter(mContext, mMessageEntityList);
        mMessageListView.setAdapter(adapter);
        mMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        chooseMessageEntity = mMessageEntityList.get(position);
                        chooseMessageEntity.setUnReadCount(0);
                        adapter.notifyDataSetChanged();
                        mPosition = position;
                        if (chooseMessageEntity.getMessageType() == MessageTabEntity.MAKE_FRIEND_REQUEST)
                        //    mDialog.show()
                                            ;
                        else if (chooseMessageEntity.getMessageType() == MessageTabEntity.MAKE_FRIEND_RESPONSE_ACCEPT) {

                        } else {
                            Intent intent = new Intent(mContext, ChatActivity.class);
                            intent.putExtra("friendName", chooseMessageEntity.getName());
                            intent.putExtra("friendId", chooseMessageEntity.getSenderId());
                            startActivity(intent);
                        }
                    }
                });
    }
    class FriendMessageAdapter extends BaseAdapter {
        private List<MessageTabEntity> mMessageEntities;
        private LayoutInflater mInflater;
        private Context mContext0;
        public FriendMessageAdapter(Context context, List<MessageTabEntity> vector) {
            this.mMessageEntities = vector;
            mInflater = LayoutInflater.from(context);
            mContext0 = context;
        }
        public int getCount() {
            return mMessageEntities.size();
        }

        public Object getItem(int position) {
            return mMessageEntities.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView avatarView;
            TextView nameView;
            TextView unReadCountView;
            TextView sendTimeView;
            TextView contentView;
            MessageTabEntity message = mMessageEntities.get(position);
            Integer senderId = message.getSenderId();
            String name = message.getName();
            Bitmap photo=null;
            int messageType = message.getMessageType();
            String sendTime = message.getSendTime();
            int unReadCount = message.getUnReadCount();
            String content = message.getContent();
            convertView = mInflater.inflate(R.layout.fragment_message_item,null);
            avatarView = (ImageView) convertView.findViewById(R.id.user_photo);
            nameView = (TextView) convertView.findViewById(R.id.user_name);
            contentView = (TextView) convertView.findViewById(R.id.user_message);
            unReadCountView = (TextView) convertView
                    .findViewById(R.id.unread_message_count);
            sendTimeView = (TextView) convertView.findViewById(R.id.send_time);
            nameView.setText(name);
            sendTimeView.setText(sendTime);
            // System.out.println(messageType + "message类型");

            contentView.setText(content);
            return convertView;
        }
    }
}
