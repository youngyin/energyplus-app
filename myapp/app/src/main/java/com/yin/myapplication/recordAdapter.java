package com.yin.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recordAdapter extends RecyclerView.Adapter<recordAdapter.ViewHolder> {

    private ArrayList<MyRecyclerItem> mData = null ;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_date ;
        TextView text_reward;
        TextView text_vol;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            text_date = itemView.findViewById(R.id.text_date) ;
            text_reward = itemView.findViewById(R.id.text_reward);
            text_vol = itemView.findViewById(R.id.text_vol);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    recordAdapter(ArrayList<MyRecyclerItem> list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public recordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false) ;
        recordAdapter.ViewHolder vh = new recordAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(recordAdapter.ViewHolder holder, int position) {
        String text_date = mData.get(position).getDate() ;
        String text_reward = mData.get(position).getReward() ;
        String text_vol = mData.get(position).getVol() ;

        holder.text_date.setText(text_date) ;
        holder.text_reward.setText(text_reward) ;
        holder.text_vol.setText(text_vol) ;
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}
