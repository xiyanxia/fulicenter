package com.example.sty.adapter;

/**
 * Created by 涛 on 2016/10/27.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sty.I;
import com.example.sty.R;
import com.example.sty.bean.CartBean;
import com.example.sty.bean.GoodsDetailsBean;
import com.example.sty.bean.MessageBean;
import com.example.sty.net.NetDao;
import com.example.sty.net.OkHttpUtils;
import com.example.sty.utils.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Context mContext;
    ArrayList<CartBean> mList;

    public CartAdapter(Context context, ArrayList<CartBean> list) {
        mContext = context;
//        mList = new ArrayList<>();
//        mList.addAll(list);
        mList = list;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartViewHolder holder = new CartViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_cart, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        //CartBean cartBean = mList.get(position);
        //        ImageLoader.downloadImg(mContext,holder.mIvBoutiqueImg,boutiqueBean.getImageurl());
        //        holder.mTvBoutiqueTitle.setText(boutiqueBean.getTitle());
        //        holder.mTvBoutiqueName.setText(boutiqueBean.getName());
        //        holder.mTvBoutiqueDescription.setText(boutiqueBean.getDescription());
        //        holder.mLayoutBoutiqueItem.setTag(boutiqueBean);
        final CartBean cartBean = mList.get(position);
        GoodsDetailsBean goods = cartBean.getGoods();
        if (goods != null) {
            ImageLoader.downloadImg(mContext, holder.mIvCartThumb, goods.getGoodsThumb());
            holder.mTvCartGoodName.setText(goods.getGoodsName());
//            holder.mTvCartPrice.setText(goods.getCurrencyPrice());
            holder.mCbCartSelected.setChecked(cartBean.isChecked());
            holder.mCbCartSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    cartBean.setChecked(b);
                    mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                }
            });
            holder.mIvCartAdd.setTag(position);
        }
        holder.mTvCartCount.setText("(" + cartBean.getCount() + ")");
        holder.mCbCartSelected.setChecked(false);

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void initData(ArrayList<CartBean> list) {
//
        mList = list;
        notifyDataSetChanged();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cb_cart_selected)
        CheckBox mCbCartSelected;
        @BindView(R.id.iv_cart_thumb)
        ImageView mIvCartThumb;
        @BindView(R.id.tv_cart_good_name)
        TextView mTvCartGoodName;
        @BindView(R.id.iv_cart_add)
        ImageView mIvCartAdd;
        @BindView(R.id.tv_cart_count)
        TextView mTvCartCount;
        @BindView(R.id.iv_cart_del)
        ImageView mIvCartDel;
        @BindView(R.id.tv_cart_price)
        TextView mTvCartPrice;

        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.iv_cart_add)
        public void addCart() {
            final int position = (int) mIvCartAdd.getTag();
            CartBean cart = mList.get(position);
            NetDao.updateCart(mContext, cart.getId(), cart.getCount() + 1, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        mList.get(position).setCount(mList.get(position).getCount() + 1);
                        mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                        mTvCartCount.setText("(" + (mList.get(position).getCount()) + ")");
                    }
                }

                @Override
                public void onError(String error) {
                }
            });
        }

        @OnClick(R.id.iv_cart_del)
        public void delCart() {
            final int position = (int) mIvCartAdd.getTag();
            CartBean cart = mList.get(position);
            if (cart.getCount() > 1) {
                NetDao.updateCart(mContext, cart.getId(), cart.getCount() - 1, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            mList.get(position).setCount(mList.get(position).getCount() - 1);
                            mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                            mTvCartCount.setText("(" + (mList.get(position).getCount()) + ")");
                        }
                    }

                    @Override
                    public void onError(String error) {
                    }
                });
            } else {
                NetDao.deleteCart(mContext, cart.getId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            mList.remove(position);
                            mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(String error) {
                    }
                });
            }
        }
    }
}
