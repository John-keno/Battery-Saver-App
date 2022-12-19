package com.kejotech.batterysaverapp.ui.batteryStats;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.kejotech.batterysaverapp.databinding.FragmentBatteryStatBinding;

public class BatteryStat extends Fragment {

    private FragmentBatteryStatBinding binding;
    private BatteryReceiver mBatteryReceiver = new BatteryReceiver();
    private IntentFilter mIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBatteryStatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
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