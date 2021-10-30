package com.servicedata.homeservices;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private int[] categoryid;
    private String[] categoriesname;
    private String[] prices;
    private String[] imagepath;
    private Context context;
    Bitmap bitmap;

    public CategoryAdapter(Context context, String[] categoriesname, String[] prices, String[] imagepath, int[] categoryid) {
        this.categoryid = categoryid;
        this.context=context;
        this.categoriesname=categoriesname;
        this.prices=prices;
        this.imagepath=imagepath;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.categories_row, parent, false);
        return  new CategoryViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        String catname = categoriesname[position];
        String pricevalue = prices[position];
        String imagevalue = imagepath[position];
        int catid = categoryid[position];
        holder.txtcategories.setText(catname);


        holder.txtprices.setText(pricevalue);

        new GetImageFromURL(holder.imgcategories).execute(imagepath[position]);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesname.length;
    }





    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        TextView txtcategories;
        TextView txtprices;
        ImageView imgcategories;
        CardView cardView;

        public CategoryViewHolder(@NonNull View v){
            super(v);
            txtcategories=(TextView)v.findViewById(R.id.txtcategories);
            txtprices=(TextView)v.findViewById(R.id.txtcategoriesprices);
            imgcategories=(ImageView)v.findViewById(R.id.imgcategories);
            cardView = (CardView) v.findViewById(R.id.categoriesrow);
        }

    }

    public class GetImageFromURL extends AsyncTask<String,Void,Bitmap>
    {

        ImageView imgView;
        public GetImageFromURL(ImageView imgv)
        {
            this.imgView=imgv;
        }
        @Override
        protected Bitmap doInBackground(String... url) {
            String urldisplay=url[0];
            bitmap=null;

            try{

                InputStream ist=new java.net.URL(urldisplay).openStream();
                bitmap= BitmapFactory.decodeStream(ist);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){

            super.onPostExecute(bitmap);
            imgView.setImageBitmap(bitmap);
        }
    }
}
