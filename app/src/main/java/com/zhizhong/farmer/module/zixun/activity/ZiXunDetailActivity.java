package com.zhizhong.farmer.module.zixun.activity;

import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.zixun.network.ApiRequest;
import com.zhizhong.farmer.module.zixun.network.response.ZiXunObj;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/1.
 */

public class ZiXunDetailActivity extends BaseActivity {

    @BindView(R.id.tv_zixun_detail_title)
    TextView tv_zixun_detail_title;
    @BindView(R.id.tv_zixun_detail_time)
    TextView tv_zixun_detail_time;
    @BindView(R.id.wv_zixun_detail_content)
    WebView wv_zixun_detail_content;
    @BindView(R.id.wv_zixun_detail_author)
    WebView wv_zixun_detail_author;

    private String id;

    @Override
    protected int getContentView() {
        setAppTitle("正文");
        return R.layout.act_zi_xun_detail;
    }


    @Override
    protected void initView() {
        id = getIntent().getStringExtra("id");
//        wv_zixun_detail_author.setHorizontalScrollBarEnabled(false);//水平不显示
//        wv_zixun_detail_content.setVerticalScrollBarEnabled(false); //垂直不显示
//        WebSettings settings = wv_zixun_detail_author.getSettings();
//        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);
    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {
        addSubscription(ApiRequest.getZiXunDetail(id, getSign("information_id", id)).subscribe(new MySub<ZiXunObj>(mContext,pl_load) {
            @Override
            public void onMyNext(ZiXunObj obj) {
//                tv_zixun_detail_content.setText(obj.getContent());
                wv_zixun_detail_author.loadDataWithBaseURL("about:blank", obj.getAuthor(), "text/html", "utf-8", null);
                wv_zixun_detail_content.loadDataWithBaseURL("about:blank", getNewContent(obj.getContent()), "text/html", "utf-8", null);

                tv_zixun_detail_title.setText(obj.getTitle());
                tv_zixun_detail_time.setText(obj.getAdd_time());
            }
        }));
    }
    public static String getNewContent(String htmltext){
        try {
            Document doc= Jsoup.parse(htmltext);
            Elements elements=doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width","100%").attr("height","auto");
            }
            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }
    @Override
    protected void onViewClick(View v) {

    }


}
