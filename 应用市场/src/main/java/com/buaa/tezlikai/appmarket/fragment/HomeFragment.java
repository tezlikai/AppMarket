package com.buaa.tezlikai.appmarket.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.buaa.tezlikai.appmarket.base.BaseFragment;
import com.buaa.tezlikai.appmarket.base.BaseHolder;
import com.buaa.tezlikai.appmarket.base.LoadingPager.LoadedResult;
import com.buaa.tezlikai.appmarket.base.SuperBaseAdapter;
import com.buaa.tezlikai.appmarket.bean.AppInfoBean;
import com.buaa.tezlikai.appmarket.bean.HomeBean;
import com.buaa.tezlikai.appmarket.holder.HomeHolder;
import com.buaa.tezlikai.appmarket.protocol.HomeProtocol;
import com.buaa.tezlikai.appmarket.utils.UIUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/4/22.
 */
public class HomeFragment extends BaseFragment {

    private List<AppInfoBean> mDatas;  //listView的数据源
    private List<String> picutures;//轮播图
    private HomeProtocol mProtocol;

    @Override
    public LoadedResult initData() {//真正加载数据
        //发送网络请求
       /* try {
            HttpUtils httpUtils = new HttpUtils();
            // http://localhost:8080/GooglePlayServer/image?name=
            String url = Constants.URLS.BASEURL + "home";
            RequestParams parmas = new RequestParams();
            parmas.addQueryStringParameter("index", "0");
            ResponseStream responseStream = httpUtils.sendSync(HttpMethod.GET, url, parmas);
            String result = responseStream.readString();

            //Gson
            Gson gson = new Gson();
            homeBean = gson.fromJson(result, HomeBean.class);

            LoadedResult state = checkState(homeBean);

            if (state != LoadedResult.SUCCESS){//如果不成功，就直接返回，走到这里说明homeBean是OK的
                return state;
            }

            state = checkState(homeBean.list);
            if (state != LoadedResult.SUCCESS){//如果不成功，就直接返回，走到这里说明homeBean.list是OK得
                return state;
            }
            mDatas = homeBean.list;
            picutures = homeBean.picture;
            return LoadedResult.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return LoadedResult.ERROR;
        }*/
        /*====================  协议简单封装之后  ========================*/
        mProtocol = new HomeProtocol();
        try {
            HomeBean homeBean = mProtocol.loadData(0);
            LoadedResult state = checkState(homeBean);

            if (state != LoadedResult.SUCCESS){//如果不成功，就直接返回，走到这里说明homeBean是OK的
                return state;
            }
            state = checkState(homeBean.list);
            if (state != LoadedResult.SUCCESS){//如果不成功，就直接返回，走到这里说明homeBean.list是OK得
                return state;
            }
            mDatas = homeBean.list;
            picutures = homeBean.picture;
            return LoadedResult.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return LoadedResult.ERROR;
        }
    }

    @Override
    protected View initSuccessView() {
        //返回成功视图
        ListView listView = new ListView(UIUtils.getContext());
        //简单的设置
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setFastScrollEnabled(true);

        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        //设置Adapter
        listView.setAdapter(new HomeAdapter(listView,mDatas));

        return listView;
    }

    class HomeAdapter extends SuperBaseAdapter<AppInfoBean>{


        public HomeAdapter(AbsListView absListView, List<AppInfoBean> dataSource) {
            super(absListView, dataSource);
        }

        @Override
        public BaseHolder<AppInfoBean> getSpecialHolder() {

            return new HomeHolder();
        }

        @Override
        public List<AppInfoBean> onLoadMore() throws Exception {
            //SystemClock.sleep(2000);
            return loadMore(mDatas.size());

        }

        @Override
        public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(UIUtils.getContext(),mDatas.get(position).packageName,Toast.LENGTH_SHORT).show();
        }

        @Nullable
        public List<AppInfoBean> loadMore(int index) throws Exception {
            //真正加载更多的数据
            //发送网络请求
           /* HttpUtils httpUtils = new HttpUtils();
            // http://localhost:8080/GooglePlayServer/image?name=
            String url = Constants.URLS.BASEURL + "home";
            RequestParams parmas = new RequestParams();
            parmas.addQueryStringParameter("index", index+"");//20
            ResponseStream responseStream = httpUtils.sendSync(HttpMethod.GET, url, parmas);
            String result = responseStream.readString();

            //Gson
            Gson gson = new Gson();
            HomeBean homeBean = gson.fromJson(result, HomeBean.class);

            if (homeBean == null){
                return null;
            }
            if (homeBean.list == null || homeBean.list.size() == 0){
                return null;
            }
            return homeBean.list;
        }*/
           /*====================  协议简单封装之后  ========================*/

            HomeBean homeBean = mProtocol.loadData(mDatas.size());
            if (homeBean == null) {
                return null;
            }
            if (homeBean.list == null || homeBean.list.size() == 0) {
                return null;
            }
            return homeBean.list;
        }
        }

}
