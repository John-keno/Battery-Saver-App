package com.kejotech.batterysaverapp.ui.home;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.kejotech.batterysaverapp.BatteryReceiver;
import com.kejotech.batterysaverapp.databinding.FragmentBatteryStatBinding;

import java.util.Objects;

public class BatteryStat extends Fragment {

    private FragmentBatteryStatBinding binding;
    private BatteryReceiver mBatteryReceiver = new BatteryReceiver();
    private IntentFilter mIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentBatteryStatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.batteryPercent;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        requireActivity().registerReceiver(mBatteryReceiver, mIntentFilter);
        super.onResume();

    }

    @Override
    public void onPause() {
        requireActivity().unregisterReceiver(mBatteryReceiver);
        super.onPause();

    }
}