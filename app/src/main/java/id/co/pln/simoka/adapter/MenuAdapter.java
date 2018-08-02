package id.co.pln.simoka.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import id.co.pln.simoka.MainActivity;
import id.co.pln.simoka.Menu1Activity;
import id.co.pln.simoka.NotaDinasActivity;
import id.co.pln.simoka.R;
import id.co.pln.simoka.TerkontrakActivity;
import id.co.pln.simoka.classutama.MenuHome;

/**
 * Created by 4741G on 11/02/2018.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private Context mContext;
    private List<MenuHome> menuList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, desc, id;
        public ImageView thumbnail;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
//            desc = (TextView) view.findViewById(R.id.desc);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            cardView = (CardView) view.findViewById(R.id.card_viewmenu);
        }
    }

    public MenuAdapter(Context mContext, List<MenuHome> menuList) {
        this.mContext = mContext;
        this.menuList = menuList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext( ))
                .inflate(R.layout.activity_cardmenu, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        MenuHome menu = menuList.get(position);
        holder.title.setText(menu.getName( ));
        holder.desc.setText(menu.getDesc( ));

        final int id = menu.getId( );
        Glide.with(mContext).load(menu.getThumbnail( )).into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(new View.OnClickListener( ) {
            public void onClick(View v) {
                Class classActivity = getMenuActivity(id);
                Intent aintent = new Intent(v.getContext( ), classActivity);
                ((Activity) v.getContext( )).startActivityForResult(aintent, 1);
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener( ) {
            public void onClick(View v) {
                Class classActivity = getMenuActivity(id);
                Intent aintent = new Intent(v.getContext( ), classActivity);
                ((Activity) v.getContext( )).startActivityForResult(aintent, 1);
            }
        });
    }

    private Class getMenuActivity(int id) {
        Class aclass = null;
        switch (id) {
            case 1:
                //INI WES GAK KANGGO BOS
                aclass = NotaDinasActivity.class;
                break;
            case 2:
                aclass = TerkontrakActivity.class;
                break;
            case 3:
                aclass = Menu1Activity.class;
                break;
        }
        return aclass;
    }

    @Override
    public int getItemCount() {
        return menuList.size( );
    }
}
