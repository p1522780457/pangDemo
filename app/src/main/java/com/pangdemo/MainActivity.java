package com.pangdemo;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.XMLReader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity {
    private Button btn;
    private TextView tv;
    private TextView tv2;
    private NotificationManager notificationManager;
    String mString = "来源：<a target='_blank' href='http://114.215.205.38:8015/?1795fb90-0730-11e8-8e98-6df22d7ec876'>im</a><br>内容：请你留言吧<br>电话：23423423<br>地址：北京市<br>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        tv = findViewById(R.id.main_tv);
        tv2 = findViewById(R.id.main_tv2);

        tv.setText(Html.fromHtml(mString));
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contentIntent = new Intent(MainActivity.this,
                        MainActivity.class);
                contentIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                contentIntent.putExtra("type", "erp");
                PendingIntent resultPendingIntent =
                        PendingIntent.getActivity(
                                MainActivity.this,
                                0,
                                contentIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
                    builder.setDefaults(Notification.DEFAULT_SOUND);
                    builder.setVibrate(new long[]{0, 300, 500, 700});
                Notification notification = builder.setTicker("新工单通知")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(resultPendingIntent)
                        .setContentTitle("您有新的工单")
                        .setContentText("点击查看详情")
                        .setAutoCancel(true)
                        .build();
                    notificationManager.notify(1, notification);
                }

        });

//        Pattern p = Pattern.compile("<a\\b[^>]+\\bhref=\"([^\"]*)\"[^>]*>([\\s\\S]*?)</a>");
//        Matcher m = p.matcher(mString);
//        while(m.find()) {
//            System.out.println(m.group(0));
//            System.out.println(m.group(1));
//        }


        String number = getNumbers(mString);
        String newNumber = "<number>" + getNumbers(mString) + "</number>";
        String newHtml = mString.replace(number, newNumber);
        tv2.setText(Html.fromHtml(newHtml, null, new NumberTagHandler()));
        tv2.setClickable(true);
        tv2.setMovementMethod(LinkMovementMethod.getInstance());


    }
    //截取数字
    public String getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d{6,}+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }
    class NumberTagHandler implements Html.TagHandler {

        private int startIndex = 0;

        private int stopIndex = 0;

        @Override
        public void handleTag(boolean opening, String tag, Editable output,
                              XMLReader xmlReader) {
            if (tag.toLowerCase().equals("number")) {
                if (opening) {
                    startNumber(tag, output, xmlReader);
                } else {
                    endNumber(tag, output, xmlReader);
                }
            }

        }

        public void startNumber(String tag, Editable output, XMLReader xmlReader) {
            startIndex = output.length();
        }

        public void endNumber(String tag, Editable output, XMLReader xmlReader) {
            stopIndex = output.length();
            output.setSpan(new NumberSpan(output.toString().substring(startIndex,stopIndex)), startIndex, stopIndex,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }


        private class NumberSpan extends ClickableSpan implements View.OnClickListener {
            private String number;

            public NumberSpan(String number) {
                this.number = number;
            }

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "拨打电话" + number, Toast.LENGTH_LONG).show();
            }
        }
    }

}
