package com.example.sty.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.sty.R;
import com.example.sty.activity.MainActivity;
import com.example.sty.adapter.CategoryAdapter;
import com.example.sty.bean.CategoryChildBean;
import com.example.sty.bean.CategoryGroupBean;
import com.example.sty.net.NetDao;
import com.example.sty.net.OkHttpUtils;
import com.example.sty.utils.ConvertUtils;
import com.example.sty.utils.L;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Winston on 2016/10/20.
 */

public class CategoryFragment extends BaseFragment {

    @BindView(R.id.elv_category)
    ExpandableListView melvCategory;

    CategoryAdapter mAdapter;
    MainActivity mContext;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;

    int groupCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this,layout);
        mContext = (MainActivity) getContext();
        mGroupList = new ArrayList<>();
        mChildList = new ArrayList<>();
        mAdapter = new CategoryAdapter(mContext, mGroupList, mChildList);
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void initView() {
        melvCategory.setGroupIndicator(null);
        melvCategory.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        downloadGroup();
    }

    private void downloadGroup() {
        NetDao.downloadCategoryGroup(mContext, new OkHttpUtils.OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if (result!= null && result.length > 0) {
                    ArrayList<CategoryGroupBean> groupList = ConvertUtils.array2List(result);
                    L.e("groupList=" + groupList.size());
                    mGroupList.addAll(groupList);
                    for (int i = 0; i < groupList.size(); i++) {
                        mChildList.add(new ArrayList<CategoryChildBean>());
                        CategoryGroupBean g = groupList.get(i);
                        downloadChild(g.getId(),i);
                    }
                }
            }

            @Override
            public void onError(String error) {
                L.e("error="+error);
            }
        });
    }

    private void downloadChild(int id,final int index) {
        NetDao.downloadCategoryChild(mContext, id, new OkHttpUtils.OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                groupCount++;
                L.e("downloadChild,result=" + result);
                if (result != null && result.length > 0) {
                    ArrayList<CategoryChildBean> childList = ConvertUtils.array2List(result);
                    L.e("childList=" + childList.size());
                    mChildList.add(index,childList);
                }
                if (groupCount == mGroupList.size()) {
                    mAdapter.initData(mGroupList, mChildList);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    protected void setListener() {

    }
}
