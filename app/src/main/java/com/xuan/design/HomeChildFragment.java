package com.xuan.design;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeChildFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeChildFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeChildFragment extends Fragment {

    @Bind(R.id.recycler_view)
    PullLoadMoreRecyclerView recycler_view;

    private List<String> itemList = new ArrayList<>();
    // 标志位，标志已经初始化完成，因为setUserVisibleHint是在onCreateView之前调用的，在视图未初始化的时候，在lazyLoad当中就使用的话，就会有空指针的异常
    private boolean isPrepared;
    //标志当前页面是否可见
    private boolean isVisible;

    private MyRecyclerViewAdapter adapter;

    private int page = 1;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    public HomeChildFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeChildFragment newInstance(String param1, String param2) {
        HomeChildFragment fragment = new HomeChildFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static HomeChildFragment newInstance() {
        HomeChildFragment fragment = new HomeChildFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_child, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this,view);

        initRecyclerView();
    }

    private void initRecyclerView() {
        recycler_view.setLinearLayout();
        recycler_view.setRefreshing(true);
        adapter = new MyRecyclerViewAdapter();
        recycler_view.setAdapter(adapter);
        recycler_view.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                queryData(0);
            }

            @Override
            public void onLoadMore() {
                queryData(1);
            }
        });
        isPrepared = true;
        lazyLoad();
    }

    private void queryData(int type) {
        if(type==0){
            //刷新
            page=1;
            itemList.clear();
            addData();
        }else if(type==1){
            page++;
            addData();
        }
    }

    private void addData() {
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())     //定义事件产生线程：io线程
                .observeOn(AndroidSchedulers.mainThread())     //事件消费线程：主线程
                .subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                int start = 20 * (page - 1);
                for (int i = start; i < page * 20; i++) {
                    itemList.add(mParam1 + "  --  " + i);
                }
                adapter.notifyDataSetChanged();
                //Caused by: java.lang.NullPointerException: Attempt to invoke
                // virtual method 'void com.wuxiaolong.pullloadmorerecyclerview.
                // PullLoadMoreRecyclerView.setPullLoadMoreCompleted()' on a null object reference
                recycler_view.setPullLoadMoreCompleted();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //懒加载
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }

    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void lazyLoad() {
        if (!isVisible || !isPrepared) {
            return;
        }
        queryData(0);
    }

    protected void onInvisible() {
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }

    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        MyRecyclerViewAdapter() {
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_child, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.title.setText(itemList.get(position));

            holder.content.setText("嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻");

            if (position % 2 == 0) {
                holder.showImage.setBackgroundResource(R.mipmap.show_img1);
            } else {
                holder.showImage.setBackgroundResource(R.mipmap.show_img2);
            }
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title, content;
            ImageView showImage;

            public ViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.title);
                content = (TextView) itemView.findViewById(R.id.content);
                showImage = (ImageView) itemView.findViewById(R.id.showImage);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(),DetailsActivity.class);
                        intent.putExtra("position", getLayoutPosition());
                        startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //解绑ButterKnife
        ButterKnife.unbind(this);
    }
}
