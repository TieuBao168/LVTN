package com.example.logisticaliot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Thongtinadapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<ThongTin> ThongTinList;

    public Thongtinadapter(Context context, int layout, List<ThongTin> thongTinList) {
        this.context = context;
        this.layout = layout;
        this.ThongTinList = thongTinList;
    }

    @Override
    public int getCount() {
        return
                ThongTinList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtNhietDo, txtDoAm, txtThoiGian;
//        TextView txtNhietDo, txtDoAm, txtAnhSang, txtThoiGian;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder Holder;
        if(convertView == null){
            Holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            Holder.txtNhietDo   = (TextView) convertView.findViewById(R.id.Nhietdo1);
            Holder.txtDoAm      = (TextView) convertView.findViewById(R.id.Doam1);
//            Holder.txtAnhSang   = (TextView) convertView.findViewById(R.id.Anhsang1);
            Holder.txtThoiGian  = (TextView) convertView.findViewById(R.id.Thoigian1);
            //Holder.txtEdit = (TextView) converView.findViewById(R.id...)
            convertView.setTag(Holder);
        }
        else {
            Holder = (ViewHolder) convertView.getTag();
        }

        ThongTin thongtin = ThongTinList.get(position);

        Holder.txtNhietDo.setText(thongtin.getNhietdo());
        Holder.txtDoAm.setText(thongtin.getDoam());
//        Holder.txtAnhSang.setText(thongtin.getAnhsang());
        Holder.txtThoiGian.setText(thongtin.getThoigian());

        return convertView;
    }
}
