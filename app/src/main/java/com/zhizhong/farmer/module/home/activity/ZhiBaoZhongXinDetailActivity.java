package com.zhizhong.farmer.module.home.activity;

import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.home.Constant;
import com.zhizhong.farmer.module.home.network.ApiRequest;
import com.zhizhong.farmer.module.home.network.response.ZhiBaoObj;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/8.
 */

public class ZhiBaoZhongXinDetailActivity extends BaseActivity {

    @BindView(R.id.tv_zhibao_detail_title)
    TextView tv_zhibao_detail_title;
    @BindView(R.id.tv_zhibao_detail_time)
    TextView tv_zhibao_detail_time;
    @BindView(R.id.wv_zhibao_detail_content)
    WebView wv_zhibao_detail_content;
    private String zhiBaoId;

    @Override
    protected int getContentView() {
        setAppTitle("正文");
        return R.layout.act_zhi_bao_zhong_xin_detail;
    }

    @Override
    protected void initView() {
        zhiBaoId = getIntent().getStringExtra(Constant.IParam.zhiBaoId);
    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {
        addSubscription(ApiRequest.getZhiBaoDetail(zhiBaoId,getSign("eppo_id",zhiBaoId)).subscribe(new MySub<ZhiBaoObj>(mContext,pl_load) {
            @Override
            public void onMyNext(ZhiBaoObj obj) {
                tv_zhibao_detail_title.setText(obj.getTitle());
//                wv_zhibao_detail_content.setText(obj.getContent());
                wv_zhibao_detail_content.loadDataWithBaseURL("about:blank", getNewContent(obj.getContent()), "text/html", "utf-8", null);

                tv_zhibao_detail_time.setText(obj.getAdd_time());
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
