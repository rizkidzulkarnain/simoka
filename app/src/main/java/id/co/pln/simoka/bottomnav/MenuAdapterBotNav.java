package id.co.pln.simoka.bottomnav;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import id.co.pln.simoka.MainActivity;
import id.co.pln.simoka.Menu1Activity;
import id.co.pln.simoka.NotaDinasActivity;
import id.co.pln.simoka.R;
import id.co.pln.simoka.TerbayarActivity;
import id.co.pln.simoka.TerkontrakActivity;
import id.co.pln.simoka.classutama.MenuHome;

/**
 * Created by Suleiman on 03/02/17.
 */

public class MenuAdapterBotNav extends RecyclerView.Adapter<MenuAdapterBotNav.ItemMenu> {

    //  Data
    private List<MenuHome> menuList = new ArrayList<>();

    private Context context;

    public MenuAdapterBotNav(Context context) {
        this.context = context;
        prepareMenus();
    }

    private void prepareMenus() {
        int[] covers = new int[]{
                R.drawable.ic_menu_nota,
                R.drawable.ic_menu_contract,
                R.drawable.ic_menu_bayar
        };

        MenuHome a = new MenuHome(1, "Rencana/Nota Dinas","Deskripsi Menu 1", covers[0]);
        menuList.add(a);

        a = new MenuHome(2, "Terkontrak", "Deskripsi Menu 2",covers[1]);
        menuList.add(a);

        a = new MenuHome(3, "Terbayar", "Deskripsi Menu 3",covers[2]);
        menuList.add(a);
    }

    @Override
    public ItemMenu onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_cardmenu, parent, false);

        return new ItemMenu(v);
    }

    @Override
    public void onBindViewHolder(ItemMenu holder, int position) {
        MenuHome menu = menuList.get(position);
        holder.title.setText(menu.getName( ));
//        holder.desc.setText(menu.getDesc( ));

        final int id = menu.getId( );
        Glide.with(context).load(menu.getThumbnail( )).into(holder.thumbnail);
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
                aclass = NotaDinasActivity.class;
                break;
            case 2:
                aclass = TerkontrakActivity.class;
                break;
            case 3:
                aclass = TerbayarActivity.class;
                break;
        }
        return aclass;
    }

    @Override
    public int getItemCount() {
        return menuList != null ? menuList.size() : 0;
    }

    protected static class ItemMenu extends RecyclerView.ViewHolder {
        public TextView title, desc, id;
        public ImageView thumbnail;
        public CardView cardView;

        public ItemMenu(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
//            desc = (TextView) view.findViewById(R.id.desc);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            cardView = (CardView) view.findViewById(R.id.card_viewmenu);
        }
    }
}
