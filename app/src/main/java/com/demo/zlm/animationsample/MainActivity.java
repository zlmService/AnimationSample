package com.demo.zlm.animationsample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageView image;
    private ListView ls;
    ArrayAdapter adapter;
    ArrayList list;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        image = (ImageView) findViewById(R.id.imageView);

        ls = (ListView) findViewById(R.id.listView);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "改变了宽度", Toast.LENGTH_SHORT).show();
//                ObjectAnimator.ofInt(new ViewWrapper(image), "width", 300).setDuration(1000).start();
                performAnimate(image,image.getWidth(),300);
            }
        });
        init();
    }

    public void init() {
        list = new ArrayList();
        for (int i = 0; i < 20; i++) {
            list.add("item" + i + 1);
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        ls.setAdapter(adapter);

        //代码设置  ListView的子元素动画
//        Animation anim=AnimationUtils.loadAnimation(this,R.anim.layout_anim);
//        LayoutAnimationController controller=new LayoutAnimationController(anim);
//        controller.setDelay(0.5f);
//        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
//        ls.setLayoutAnimation(controller);
    }

    public void doClick(View v) {

        //通过代码方式
//        ScaleAnimation scaleAnimation=new ScaleAnimation(0.1f,1f,0.1f,1f);
//        scaleAnimation.setDuration(1000);
//        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                //动画启动前
//            }
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                //完成动画后
//            }
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//                //重新运行动画
//            }
//        });
//        image.startAnimation(scaleAnimation);

        //xml方式 进行View动画设置
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
//        image.startAnimation(animation);

        //帧动画
//        image.setBackgroundResource(R.drawable.anim);
//        AnimationDrawable background = (AnimationDrawable) image.getBackground();
//        background.start();

        //切换Activity添加动画效果
//        startActivity();

        //为Fragment添加切换动画效果
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.scale,R.anim.scale);

        //使用属性动画 平移
//        ObjectAnimator.ofFloat(image,"translationX",image.getWidth()).start();

        //修改背景颜色
        ValueAnimator valueAnimator = ObjectAnimator.ofInt(image, "backgroundColor", 0xFFFF8080, 0xFF8080FF);
        valueAnimator.setDuration(2000);
        valueAnimator.setStartDelay(500);
        valueAnimator.setEvaluator(new ArgbEvaluator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.start();

        //监听器
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//
//            }
//        });
//        valueAnimator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {}
//
//            @Override
//            public void onAnimationEnd(Animator animation) {}
//
//            @Override
//            public void onAnimationCancel(Animator animation) {}
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {}
//        });


        //动画集合
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(image, "rotationX", 0, 360),
                ObjectAnimator.ofFloat(image, "rotationY", 0, 180),
                ObjectAnimator.ofFloat(image, "rotation", 0, -180),
                ObjectAnimator.ofFloat(image, "alpha", 1, 0.25f),
                ObjectAnimator.ofFloat(image, "scaleX", 1, 1.5f),
                ObjectAnimator.ofFloat(image, "scaleY", 1, 0.5f)
        );
        set.setDuration(3000);
        set.start();

        set.setInterpolator(new AccelerateDecelerateInterpolator());

    }


    public void startActivity() {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.scale, R.anim.scale);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.scale, R.anim.scale);
    }


    public void performAnimate(final View target, final int start, final int end){
        ValueAnimator valueAnimator=ValueAnimator.ofInt(1,100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private IntEvaluator mEvaluator=new IntEvaluator();
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取当前动画的进度值，整型，1~100之间
                int currentValue = (int) animation.getAnimatedValue();
                System.out.println("currentValue:"+currentValue);
                //获取当前进度占整个动画过程的比例 浮点型 0~1之间
                float fracation=animation.getAnimatedFraction();
                System.out.println("fracation:"+fracation);
                //调用整型估值器，通过比例计算出宽度，然后赋值给ImageView，
                target.getLayoutParams().width=mEvaluator.evaluate(fracation,start,end);
                //重新测量和布局
                target.requestLayout();
                /**
                 * requestLayout:当View确定已经不在适合现有的区域时，调用这个方法，
                 * 告诉父容器重新重新调用它的onMeasure和onLayout方法 来对自己重新设置位置
                 * invalidate：View本身调用迫使View重画
                 * */
            }
        });
        valueAnimator.setDuration(3000).start();
    }
    public static class ViewWrapper {
        private View view;

        public ViewWrapper(View v) {
            this.view = v;
        }
        public int getWidth(){
            return view.getLayoutParams().width;
        }
        public void setWidth(int width){
            view.getLayoutParams().width=width;
            view.requestLayout();
        }
    }
}
