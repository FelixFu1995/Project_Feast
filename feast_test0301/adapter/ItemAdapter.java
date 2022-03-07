package tw.com.feast_test0301.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tw.com.feast_test0301.R;
import tw.com.feast_test0301.utils.Item;


//extends_RecyclerView.Adapter
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{
    //JavaBean_List
    List<Item> items;


    //ItemAdapter_Constructor
    public ItemAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_page,parent,false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        holder.getItemData(items.get(position),position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //InnerClass，extends_RecyclerView.ViewHolder
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle;
        private ImageView imageView;

        //InnerClass_Constructors。
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.titleTextViewID);
            imageView = itemView.findViewById(R.id.pageImageID);
        }

        //Fillin_JavaBean_data
        public void getItemData(Item itemData,int position){
            textTitle.setText(itemData.getTitle());
            imageView.setImageResource(itemData.getImage());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println(position);
                }
            });
        }
    }
}