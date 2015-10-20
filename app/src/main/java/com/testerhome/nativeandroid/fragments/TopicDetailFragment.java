package com.testerhome.nativeandroid.fragments;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.testerhome.nativeandroid.Config;
import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.models.TopicDetailEntity;
import com.testerhome.nativeandroid.models.TopicDetailResponse;
import com.testerhome.nativeandroid.networks.TesterHomeApi;
import com.testerhome.nativeandroid.utils.StringUtils;
import com.testerhome.nativeandroid.views.TopicReplyActivity;

import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import us.feras.mdv.MarkdownView;

/**
 * Created by vclub on 15/9/17.
 */
public class TopicDetailFragment extends BaseFragment {

    @Bind(R.id.tv_detail_title)
    TextView tvDetailTitle;
    @Bind(R.id.sdv_detail_user_avatar)
    SimpleDraweeView sdvDetailUserAvatar;
    @Bind(R.id.tv_detail_name)
    TextView tvDetailName;
    @Bind(R.id.tv_detail_username)
    TextView tvDetailUsername;
    @Bind(R.id.tv_detail_publish_date)
    TextView tvDetailPublishDate;

    @Bind(R.id.tv_detail_replies_count)
    TextView tvDetailRepliesCount;

    private String mTopicId;

    @Bind(R.id.tv_detail_body)
    MarkdownView tvDetailBody;

    public static TopicDetailFragment newInstance(String topicId) {
        Bundle args = new Bundle();
        args.putString("topic_id", topicId);
        TopicDetailFragment fragment = new TopicDetailFragment();
        fragment.setArguments(args);
        fragment.mTopicId = topicId.toString();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadInfo();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_topic_detail;
    }

    private void loadInfo() {
        TesterHomeApi.getInstance().getTopicsService().getTopicById(mTopicId,
                new Callback<TopicDetailResponse>() {
                    @Override
                    public void success(TopicDetailResponse topicDetailResponse, Response response) {
                        TopicDetailEntity topicEntity = topicDetailResponse.getTopic();
                        tvDetailTitle.setText(topicEntity.getTitle());
                        tvDetailName.setText(topicEntity.getNode_name().concat("."));
                        tvDetailUsername.setText(TextUtils.isEmpty(topicEntity.getUser().getLogin()) ?
                                "匿名用户" : topicEntity.getUser().getName());
                        tvDetailPublishDate.setText(StringUtils.formatPublishDateTime(
                                topicEntity.getCreated_at()).concat(".")
                                .concat(topicEntity.getHits()).concat("次阅读"));
                        sdvDetailUserAvatar.setImageURI(Uri.parse(Config.getImageUrl(topicEntity.getUser().getAvatar_url())));

                        // 用户回复数
                        tvDetailRepliesCount.setText(String.format("%s条回复", topicEntity.getReplies_count()));

                        showWebContent(topicEntity.getBody_html());

                        // TODO: 15/10/20 loadMarkdown text
//                         tvDetailBody.loadMarkdown(topicEntity.getBody()
//                                  .replace("![](/photo/", "![](https://testerhome.com/photo/")
//                                 ,"file:///android_asset/markdown_css_themes/alt.css");
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }

    private void showWebContent(String htmlBody) {
        String prompt = "";
        AssetManager assetManager = getActivity().getResources().getAssets();

        try {
            InputStream inputStream = assetManager.open("h5_template.html");
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);
            prompt = new String(b);
            prompt = prompt.concat(htmlBody.replace("<img src=\"/photo/",
                    "<img src=\"https://testerhome.com/photo/")).concat("</body></html>");
            inputStream.close();
        } catch (IOException e) {
            Log.e("", "Counldn't open updrage-alter.html", e);
        }

        tvDetailBody.setBackgroundColor(0);
        tvDetailBody.loadDataWithBaseURL(null, prompt, "text/html", "utf-8", null);
    }

    @OnClick(R.id.tv_detail_replies_count)
    void onDetailRepliesClick() {
        startActivity(new Intent(getContext(), TopicReplyActivity.class)
                .putExtra("topic_id", mTopicId));
    }
}
