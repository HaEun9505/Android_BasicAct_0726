package com.example.basicact;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.basicact.databinding.FragmentSecondBinding;

public class ThirdFragment extends Fragment {

    private FragmentSecondBinding binding;
    private EditText mEditText;
    String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        //return binding.getRoot();
        return inflater.inflate(R.layout.fragment_third, container, false);

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText = view.findViewById(R.id.edit);
        view.findViewById(R.id.btnTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });
        createNotificationChannel();
        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ThirdFragment.this)
                        .navigate(R.id.action_ThirdFragment_to_FirstFragment);
                        //ThirdFragment를 누르면 FirstFragment 실행
            }
        });
        Button btnPre=view.findViewById(R.id.button_second2);
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ThirdFragment.this)
                        .navigate(R.id.action_ThirdFragment_to_SecondFragment);
                        //SecondFragment 실행
            }
        });
    }
    public void startTimer(){
        String s = mEditText.getText().toString();
        int min = Integer.parseInt(s.substring(0, 2));
        int sec = Integer.parseInt(s.substring(3, 5));
        new CountDownTimer(min * 60 * 1000 + sec * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                mEditText.setText("" + (int) (millisUntilFinished / (1000)) + "초");
            }

            public void onFinish() {
                mEditText.setText("done!");
                sendNotification();
            }
        }.start();
    }
    public void sendNotification() {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getContext(), NOTIFICATION_CHANNEL_ID);

        // 알림이 클릭되면 이 인텐트가 보내진다.
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.google.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0,
                intent, 0);

        notificationBuilder.setSmallIcon(R.drawable.embos)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.boiled_egg))
                .setContentTitle("Egg Timer")
                .setContentText("계란 삶기가 완료되었습니다.")
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(/*notification id*/1, notificationBuilder.build());
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Channel description");
            NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}