package com.qjay.finaladapterdemo.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 抽象普通的适配器,用于ListView,GridView等控件的适配器,内部已经做的优化处理
 * Created by Q.Jay on 2015/6/12 0012.
 */
public abstract class AbsCommonAdapter<T> extends BaseAdapter {

    protected List<T> itemDatas;
    protected final int layoutRes;
    protected Context context;

    public AbsCommonAdapter(Context context,List<T> itemDatas, @LayoutRes int layoutRes) {
        this.context = context;
        this.itemDatas = itemDatas;
        this.layoutRes = layoutRes;

    }

    @Override
    public int getCount() {
        return itemDatas.size();
    }

    @Override
    public T getItem(int position) {
        return itemDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder = getViewHolder(position, convertView,parent);
        onBindViewHolder(viewHolder, getItem(position));
        return viewHolder.getConvertView();
    }

    private ViewHolder getViewHolder(int position, View convertView,ViewGroup parent)
    {
        return ViewHolder.getInstanse(context, convertView, parent, layoutRes,
                position);
    }


    /**
     * 实现此方法并在此方法中为控件设置属性
     * @param viewHolder ItemView 缓存类对象引用
     * @param item 数据对象引用 [ 一般是JavaBean对象 ]
     */
    public abstract void onBindViewHolder(AbsCommonAdapter.ViewHolder viewHolder, T item);


    /**
     * ItemView 缓存类,设置控件的属性方法可继续扩展
     */
    public static class ViewHolder {
        private final SparseArray<View> mViews;
        private int mPosition;
        private View mConvertView;

        private ViewHolder(Context context, ViewGroup parent, @LayoutRes int layoutId, int position) {
            this.mPosition = position;
            this.mViews = new SparseArray<View>();
            this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            // setTag
            mConvertView.setTag(this);
        }

        /**
         * @param context     上下文
         * @param convertView 复用View
         * @param parent      ViewGroup parent
         * @param layoutId    item layoutId
         * @param position    当前条目位置
         * @return 返回当前对象引用
         */
        public static ViewHolder getInstanse(Context context, View convertView, ViewGroup parent, @LayoutRes int layoutId, int position) {
            if (convertView == null) {
                return new ViewHolder(context, parent, layoutId, position);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }

        /**
         * @return 当前复用的ConvertView
         */
        public View getConvertView() {
            return mConvertView;
        }

        /**
         * 通过控件ID获取对应的控件对象,如果没有则加入mViews
         *
         * @param viewId 控件ID
         * @param <T>    确定控件的类型
         * @return 返回查询出来的控件对象引用
         */
        public <T extends View> T getView(@IdRes int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        /**
         * 为TextView对象设置内容
         *
         * @param viewId TextView控件ID
         * @param text   显示的内容
         * @return 返回当前对象
         */
        public ViewHolder setTextViewText(@IdRes int viewId, @NonNull String text) {
            TextView tv = getView(viewId);
            tv.setText(text);
            return this;
        }

        public int getmPosition() {
            return mPosition;
        }
    }
}
