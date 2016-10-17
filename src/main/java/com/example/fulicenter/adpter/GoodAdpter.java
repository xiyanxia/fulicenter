package com.example.fulicenter.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fulicenter.I;
import com.example.fulicenter.R;
import com.example.fulicenter.bean.NewGoodsBean;
import com.example.fulicenter.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 涛 on 2016/10/17.
 */
public class GoodAdpter extends Adapter {
    Context mContext;
    List<NewGoodsBean> mList;

    public GoodAdpter(Context Context, List<NewGoodsBean> list) {
        mContext = Context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else {
            holder = new GoodsViewHolder(View.inflate(mContext, R.layout.item_good, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {

        } else {
            GoodsViewHolder vh = (GoodsViewHolder) holder;
            NewGoodsBean goods = mList.get(position);
            ImageLoader.downloadImg(mContext, vh.ivGood, goods.getGoodsThumb());
            vh.ivGoodName.setText(goods.getGoodsName());
            vh.ivGoodPrice.setText(goods.getCurrencyPrice());
        }
    }

    @Override
    public int getItemCount() {
        return mContext != null ? mList.size() + 1 : 1;
    }

    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_FOOTER;
    }

    public void initData(ArrayList<NewGoodsBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();

    }

    static class GoodsViewHolder extends ViewHolder {
        @BindView(R.id.ivGood)
        ImageView ivGood;
        @BindView(R.id.ivGoodName)
        TextView ivGoodName;
        @BindView(R.id.ivGoodPrice)
        TextView ivGoodPrice;
        @BindView(R.id.item_good)
        LinearLayout itemGood;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FooterViewHolder extends ViewHolder {
        @BindView(R.id.ivfooter)
        TextView ivfooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
