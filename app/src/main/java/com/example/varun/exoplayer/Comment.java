package com.example.varun.exoplayer;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

public class Comment extends AppCompatActivity {
    RecyclerView commentRecyclerView;
    public static boolean flag=true;

   public static final String[] comments={"This is sample Comment This is sample Comment ","This is sample Comment "
           ,"This is sample Comment  This is sample Comment  ",
    "This is sample Comment This is sample Comment This is sample Comment This is sample Comment  This is sample Comment This is sample Comment  This is sample Comment","This is sample Comment This is sample Comment " +
            "This is sample Comment This is sample Comment This is sample Comment This is sample Comment This is sample Comment This is sample Comment "};
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        commentRecyclerView =(RecyclerView)(findViewById(R.id.commentRecyclerView));
        commentRecyclerView.setAdapter(new adapter());
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    class adapter extends RecyclerView.Adapter<adapter.Viewholder>
    {
        @Override
        public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(getApplicationContext()).inflate(R.layout.commentdesign,parent,false);


            return new Viewholder(view);
        }

        @Override
        public void onBindViewHolder(Viewholder holder, int position) {
            TextView comment=holder.comment;
            comment.setText(comments[position]);
            makeTextViewResizable(comment,3,"See more",true);

        }

        @Override
        public int getItemCount() {
            return comments.length;
        }

        class Viewholder extends RecyclerView.ViewHolder
        {
            TextView comment;
            public Viewholder(View itemView) {
                super(itemView);
                comment=itemView.findViewById(R.id.comment);



            }
        }


    }

    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {



        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                String text;
                int lineEndIndex;
                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    lineEndIndex = tv.getLayout().getLineEnd(0);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + "..." + expandText;
                } else if (tv.getLineCount() < maxLine) {
                    //do nothing
                    lineEndIndex = tv.getLayout().getLineEnd(0);

                    text = tv.getText().toString();
                }
//
                else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + "..." + expandText;
                    Log.d("TAG", text);
                } else {
                    lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                }

//                   tv.setText(text);


                tv.setMovementMethod(LinkMovementMethod.getInstance());



                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(text), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);

                tv.setVisibility(View.VISIBLE);

            }
        });

    }

    public static class MySpannable extends ClickableSpan {

        private boolean isUnderline = false;

        /**
         * Constructor
         */
        public MySpannable(boolean isUnderline) {
            this.isUnderline = isUnderline;
        }

        @Override
        public void updateDrawState(TextPaint ds) {

            ds.setUnderlineText(isUnderline);
            ds.setColor(Color.parseColor("#0000ff"));

        }

        @Override
        public void onClick(View widget) {

        }
    }



    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
            ssb.setSpan(new MySpannable(false) {

                @Override
                public void onClick(View widget) {
                    tv.setVisibility(View.INVISIBLE);
                    tv.setLayoutParams(tv.getLayoutParams());

                    tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                    tv.invalidate();
                    if (viewMore) {
                        Log.d("tag1","first block");
                        makeTextViewResizable(tv, -1, "View Less", false);
                    }
                else {
                        Log.d("tag1","second block");
                        makeTextViewResizable(tv, 3, "See more", true);

                    }

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        Log.d("tag3",ssb+"");
        return ssb;

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("destroy","Destroy called");
    }
}
