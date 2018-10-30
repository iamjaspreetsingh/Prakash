package com.jskgmail.prakash;

/**
 * Created by JASPREET SINGH on 16-10-2017.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JASPREET SINGH on 06-08-2017.
 */

public class ListViewAdaptersea extends BaseAdapter {
    Activity mcontext;
    ArrayList<String> title=new ArrayList<>();
    ArrayList<String> description=new ArrayList<>();
    ArrayList<String> det1=new ArrayList<>();
    ArrayList<String> det2=new ArrayList<>();
    ArrayList<String> det3=new ArrayList<>();
    ArrayList<String> det4=new ArrayList<>();
    ArrayList<String> det5=new ArrayList<>();
    ArrayList<String> det6=new ArrayList<>();
    ArrayList<String> det7=new ArrayList<>();
    ArrayList<String> latlong=new ArrayList<>();



    public ListViewAdaptersea(SearchActivity context, ArrayList<String> arrayList, ArrayList<String> arrayList1, ArrayList<String> arrayList2,
                              ArrayList<String> arrayList3
            , ArrayList<String> arrayList4
            , ArrayList<String> arrayList5, ArrayList<String> arrayList6, ArrayList<String> arrayList7, ArrayList<String> arrayList8
    , ArrayList<String> arrayList69) {
        mcontext=context;
        title=arrayList;
        description=arrayList1;
        det1=arrayList2;
        det2=arrayList3;
        det3=arrayList4;
        det4=arrayList5;
        det5=arrayList6;
        det6=arrayList7;
        det7=arrayList8;
        latlong=arrayList69;
    }





    @Override
    public int getCount() {

        return title.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        TextView txtviewtitle;
        TextView txtviewdesc;
RelativeLayout rl;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;


        LayoutInflater inflater=mcontext.getLayoutInflater();
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.allfriend,null);
            holder=new ViewHolder();
            holder.txtviewtitle=(TextView)convertView.findViewById(R.id.textView68);
            holder.txtviewdesc=(TextView)convertView.findViewById(R.id.textView54);
            holder.rl=convertView.findViewById(R.id.rl);




          {
               holder.txtviewtitle.setText("" + title.get(position));
               holder.txtviewdesc.setText(""+det3.get(position));
holder.rl.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        go(title.get(position),description.get(position),det1.get(position),det2.get(position),det3.get(position),det4.get(position)
                ,det5.get(position),det6.get(position),det7.get(position),latlong.get(position));
    }
});






           //    holder.inc.setText(String.valueOf(in) + "%");

           }











    }




















        else{
            holder=(ViewHolder)convertView.getTag();
        }





        return convertView;
    }

private void go(String name,String name1,String name2,String name3,String name4,
                String name5,String name6,String name7,String name8,String lat)
{



SearchActivity.f1.setText(name);
    SearchActivity.f2.setText(name1);
    SearchActivity.f3.setText(name2);
    SearchActivity.f4.setText(name3);
    SearchActivity.f5.setText(name4);
    SearchActivity.f6.setText(name5);
    SearchActivity.f7.setText(name6);
    SearchActivity.no=name6;
    SearchActivity.f8.setText(name7);
    SearchActivity.f9.setText(name8);
    SearchActivity.list.setVisibility(View.GONE);
    SearchActivity.det.setVisibility(View.VISIBLE);

  if (lat.length()>10) {
      int l = lat.length() - 1;
      MainActivity.latitude = lat.substring(0, l);
  }
}





}

