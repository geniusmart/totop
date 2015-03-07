package com.totop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.totop.activity.R;
import com.totop.bean.Goods;
import com.totop.utils.UILHelper;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class GoodsAdapter extends BaseAdapter{

    private List<Goods> list;
    private LayoutInflater mInflater;
    private Context mContext;

    public GoodsAdapter(Context context,List<Goods> list){
        this.list = list;
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Goods goods = list.get(position);
        ViewHolder holder = null;

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_goods_list,null);
            holder = new ViewHolder(convertView);

            //holder.goodsPicImage.setImageURI();
            UILHelper.displayImage(goods.icon,holder.goodsPicImage);
            holder.titleText.setText(goods.title);
            holder.noticeText.setText(goods.description);
            holder.priceText.setText(String.valueOf(goods.currentprice));
            holder.originalPriceText.setText(String.valueOf(goods.originalprice));
            holder.orderCountText.setText(String.valueOf(goods.salesvolume));
            holder.goods = goods;

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.imageview_goods_pic)ImageView goodsPicImage;
        @InjectView(R.id.textview_title)TextView titleText;
        @InjectView(R.id.textview_notice)TextView noticeText;
        @InjectView(R.id.text_price)TextView priceText;
        @InjectView(R.id.text_original_price)TextView originalPriceText;
        @InjectView(R.id.text_order_count)TextView orderCountText;
        Goods goods;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
