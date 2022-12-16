package com.kejotech.batterysaverapp.ui.usageStats;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.kejotech.batterysaverapp.R;
import com.kejotech.batterysaverapp.databinding.FragmentGalleryBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UsageStatFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private List<AppList> installedApps;
    private AppAdapter installedAppAdapter;
    ListView userInstalledApps;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        userInstalledApps =binding.installedAppList;

        installedApps = getInstalledApps();
        installedAppAdapter = new AppAdapter(requireContext(), installedApps);
        userInstalledApps.setAdapter(installedAppAdapter);
        userInstalledApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                String[] colors = {" Open App", " App Info"};
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Choose Action")
                        .setItems(colors, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position of the selected item
                                if (which==0){
                                    Intent intent = requireContext().getPackageManager().getLaunchIntentForPackage(installedApps.get(i).packages);
                                    if(intent != null){
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(requireContext(), installedApps.get(i).packages + " Error, Please Try Again...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (which==1){
                                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.setData(Uri.parse("package:" + installedApps.get(i).packages));
                                    Toast.makeText(requireContext(), installedApps.get(i).packages, Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                            }
                        });
                builder.show();

            }
        });

        //Total Number of Installed-Apps(i.e. List Size)
        String  abc = userInstalledApps.getCount()+"";
        TextView countApps = binding.countApps;
        countApps.setText("Total Installed Apps: "+abc);
        Toast.makeText(requireContext(), abc+" Apps", Toast.LENGTH_SHORT).show();


        return root;
    }

    private List<AppList> getInstalledApps() {
        PackageManager pm = requireContext().getPackageManager();
        List<AppList> apps = new ArrayList<>();
        List<PackageInfo> packs =requireContext().getPackageManager().getInstalledPackages(0);
        //List<PackageInfo> packs = getPackageManager().getInstalledPackages(PackageManager.GET_PERMISSIONS);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((!isSystemPackage(p))) {
                String appName = p.applicationInfo.loadLabel(requireActivity().getPackageManager()).toString();
                Drawable icon = p.applicationInfo.loadIcon(requireActivity().getPackageManager());
                String packages = p.applicationInfo.packageName;
                apps.add(new AppList(appName, icon, packages));
            }
        }
        return apps;
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }




    public static class AppAdapter extends BaseAdapter {

        public LayoutInflater layoutInflater;
        public List<AppList> listStorage;

        public AppAdapter(Context context, List<AppList> customizedListView) {
            layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listStorage = customizedListView;
        }

        @Override
        public int getCount() {
            return listStorage.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder listViewHolder;
            if(convertView == null){
                listViewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.installed_app_list, parent, false);

                listViewHolder.textInListView = convertView.findViewById(R.id.list_app_name);
                listViewHolder.imageInListView = convertView.findViewById(R.id.app_icon);
                listViewHolder.packageInListView= convertView.findViewById(R.id.app_package);
                convertView.setTag(listViewHolder);
            }else{
                listViewHolder = (ViewHolder)convertView.getTag();
            }
            listViewHolder.textInListView.setText(listStorage.get(position).getName());
            listViewHolder.imageInListView.setImageDrawable(listStorage.get(position).getIcon());
            listViewHolder.packageInListView.setText(listStorage.get(position).getPackages());

            return convertView;
        }

        static class ViewHolder{
            TextView textInListView;
            ImageView imageInListView;
            TextView packageInListView;
        }
    }

    public static class AppList {
        private final String name;
        Drawable icon;
        private final String packages;
        public AppList(String name, Drawable icon, String packages) {
            this.name = name;
            this.icon = icon;
            this.packages = packages;
        }
        public String getName() {
            return name;
        }
        public Drawable getIcon() {
            return icon;
        }
        public String getPackages() {
            return packages;
        }

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}