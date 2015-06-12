package com.qjay.finaladapterdemo.adapter;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Q.Jay on 2015/6/12 0012.
 */
public abstract class AbsRecyclerViewAdapter<T> extends RecyclerView.Adapter<AbsRecyclerViewAdapter.ViewHolder> {


    protected final List<T> itemDatas;
    protected final int layoutRes;

    public AbsRecyclerViewAdapter(List<T> itemDatas,@LayoutRes int layoutRes) {
        this.itemDatas = itemDatas;
        this.layoutRes = layoutRes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutRes,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    @Override
    public void onBindViewHolder(AbsRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        onBindViewHolder(viewHolder, itemDatas.get(position));
    }
    /**
     * 实现此方法并在此方法中为控件设置属性
     * @param viewHolder ItemView 缓存类对象引用
     * @param item 数据对象引用 [ 一般是JavaBean对象 ]
     */
    public abstract void onBindViewHolder(AbsRecyclerViewAdapter.ViewHolder viewHolder, T item);

    @Override
    public int getItemCount() {
        return itemDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final SparseArray<View> mViews;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mViews = new SparseArray<View>();
        }

        /**
         * 通过控件ID获取对应的控件对象,如果没有则加入mViews
         * @param viewId 控件ID
         * @param <T> 确定控件的类型
         * @return 返回查询出来的控件对象引用
         */
        public <T extends View> T getView(@IdRes int viewId)
        {
            View view = mViews.get(viewId);
            if (view == null)
            {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        /**
         * 为TextView对象设置内容
         * @param viewId TextView控件ID
         * @param text 显示的内容
         * @return 返回当前对象
         */
        public ViewHolder setTextViewText(@IdRes int viewId,@NonNull String text){
            TextView tv = getView(viewId);
            tv.setText(text);
            return this;
        }
    }
}
