package tw.com.feast_test0301.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import tw.com.feast_test0301.MainActivity;
import tw.com.feast_test0301.R;
import tw.com.feast_test0301.adapter.ItemAdapter;
import tw.com.feast_test0301.tool.UrlLinkTool;
import tw.com.feast_test0301.utils.DataBean;
import tw.com.feast_test0301.utils.Item;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComAllDataFragment} factory method to
 * create an instance of this fragment.
 */
public class ComAllDataFragment extends Fragment {

    private ItemAdapter itemAdapter;
    private MainActivity mainActivity;
    JSONObject allDataObj;

    public ComAllDataFragment(JSONObject allDataObj, MainActivity mainActivity) {
        this.allDataObj = allDataObj;
        this.mainActivity = mainActivity;
    }

    public ComAllDataFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_com_all_data, container, false);
        comDataShow(view);
        return view;
    }

    /**
     * 顯示商品列表(首頁)
     */
    @SuppressLint("ResourceAsColor")
    public void comDataShow(View view) {
        LinearLayout allDataLayout = view.findViewById(R.id.allComDataLayoutID);
        setItemAdapter();
        ViewPager2 viewPager = view.findViewById(R.id.viewPageID);
        viewPager.setAdapter(itemAdapter);
        SwitchPagerView(viewPager);
        JSONArray names = allDataObj.names();
        for (int i = 0; i < names.length(); i++) {
            try {
                String storName = names.getString(i);
                TextView storeName = new TextView(view.getContext());
                storeName.setText(storName);
                storeName.setTextSize(20);
                storeName.setTextColor(R.color.black);
                ConstraintLayout comLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.comlayout, null);
                allDataLayout.addView(storeName);
                allDataLayout.addView(comLayout);

                LinearLayout oneComLayout = comLayout.findViewById(R.id.oneComLayoutID);
                JSONArray comDataArray = allDataObj.getJSONArray(storName);
                for (int s = 0; s < comDataArray.length(); s++) {
                    JSONObject oneComData = comDataArray.getJSONObject(s);
                    ConstraintLayout comStyle = (ConstraintLayout) getLayoutInflater().inflate(R.layout.comstyle, null);
                    oneComLayout.addView(comStyle);
                    TextView comName = comStyle.findViewById(R.id.comNameTextID);
                    TextView comPrice = comStyle.findViewById(R.id.comPriceTextID);
                    ImageView comImage = comStyle.findViewById(R.id.comImageID);

                    comName.setText(oneComData.getString("comName"));
                    comPrice.setText("$"+oneComData.getString("comPrice"));
                    String sComNumber = oneComData.getString("comNumber");
                    if (oneComData.has("comImage")) {
                        String imageURL = oneComData.getString("comImage");
                        loadImageURL(imageURL, comImage);
                    }
                    comImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DataBean dataBean = new DataBean();
                            dataBean.setsComName(comName.getText().toString());
                            dataBean.setsComNumber(sComNumber);
                            dataBean.setComImage(comImage.getDrawable());
                            try {
                                dataBean.setsComPrice(oneComData.getString("comPrice"));
                                dataBean.setsUserAccount(oneComData.getString("userAccount"));
                                dataBean.setsPKID(oneComData.getString("id"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Fragment comData = new ComDataFragment(dataBean, mainActivity);
                            getParentFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.FrameLayoutID, comData).commit();
                        }
                    });

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return;
    }


    public void setItemAdapter() {

        List<Item> itemList = new ArrayList();


        itemList.add(new Item(R.drawable.page_image_01, R.string.item_page_title));
        itemList.add(new Item(R.drawable.page_image_02, R.string.item_page2_title));

        itemAdapter = new ItemAdapter(itemList);
    }

    /**
     * get_pic_URL
     *
     * @param imageURL  Pic_URL
     * @param imageView show_imageView
     */
    public void loadImageURL(String imageURL, ImageView imageView) {
        UrlLinkTool linkTool = new UrlLinkTool(imageURL);
        linkTool.OnServerAnswerListener = new UrlLinkTool.NetEventListener() {
            @Override
            public void linkStart() {
            }

            @Override
            public void linkEnd(byte[] dataByte) {
                Bitmap imageBitmap = BitmapFactory.decodeByteArray(dataByte, 0, dataByte.length);
                try {
                    viewRun(() -> {
                        imageView.setImageBitmap(imageBitmap);
                    });
                } catch (Exception e) {
                    System.out.println("圖片沒地方放囉");
                }

            }
        };
        linkTool.start();
    }

    /**
     * Runnable
     * @param r
     */
    public void viewRun(Runnable r) {
        getActivity().runOnUiThread(r);
        return;
    }

    /**
     * 自動切換ViewPager2的方法，類似輪播圖
     * @param viewPager
     */
    public void SwitchPagerView(ViewPager2 viewPager ){

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                int pager = viewPager.getCurrentItem();

                switch (pager){
                    case 0:
                        pager++;

                        viewPager.setCurrentItem(pager);
                        break;
                    case 1:
                        pager=0;
                        viewPager.setCurrentItem(pager);
                        break;
                }
            }
        },5000,5000);
    }
}