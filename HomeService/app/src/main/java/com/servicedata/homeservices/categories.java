package com.servicedata.homeservices;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class categories extends AppCompatActivity {

    String urladdress="http://softanalysis.net/getcategories.php";
    String[] categoriesname;
    String[] prices;
    String[] imagepath;
    int[] categoryid;

    BufferedInputStream is;
    String line=null;
    String result=null;
    Toolbar toolbarcategories;
    RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_categories);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();
        CategoryAdapter categoryAdapter=new CategoryAdapter(this,categoriesname,prices,imagepath,categoryid);
        recyclerView.setAdapter(categoryAdapter);






    }

    private void collectData()
    {
//Connection
        try{

            URL url=new URL(urladdress);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            is=new BufferedInputStream(con.getInputStream());

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        //content
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            StringBuilder sb=new StringBuilder();
            while ((line=br.readLine())!=null){
                sb.append(line+"\n");
            }
            is.close();
            result=sb.toString();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();

        }

//JSON
        try{
            JSONArray ja=new JSONArray(result);
            JSONObject jo=null;
            categoriesname=new String[ja.length()];
            prices=new String[ja.length()];
            imagepath=new String[ja.length()];
            categoryid=new int[ja.length()];

            for(int i=0;i<=ja.length();i++){
                jo=ja.getJSONObject(i);
                categoriesname[i]=jo.getString("categoryname");
                prices[i]=jo.getString("categoryprice");
                imagepath[i]=jo.getString("categorypath");
                categoryid[i]=jo.getInt("categoryid");
            }
        }
        catch (Exception ex)
        {

            ex.printStackTrace();
        }


    }
}
