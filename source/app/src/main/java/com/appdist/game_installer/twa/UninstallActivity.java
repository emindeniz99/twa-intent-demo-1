package com.appdist.game_installer.twa;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;

public class UninstallActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("testtapplicatoionnntestttt intentttt");

        // Handle the intent from PWA

        try {
            startUninstallProcedure();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finish();
    }

    private void startUninstallProcedure() throws IOException {
        // Example uninstallation procedure

        String str="https://twa-demo-1.github-pages.emindeniz99.com/app-tester-google.apk";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            PackageInstaller packageInstaller = getPackageManager().getPackageInstaller();
            int session = packageInstaller.createSession(
                    new PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL)
            );
            packageInstaller.openSession(session);
            System.out.println("testtapplicatoionnntestttt openSession");
            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str));
          //  request.setTitle("APK Download"+88);
            request.setDescription("Downloading APK...");
            System.out.println("testtapplicatoionnntestttt request");
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "installer.apk");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            downloadManager.enqueue(request);

            System.out.println("testtapplicatoionnntestttt enqueue");
            Toast.makeText(this, "Download started", Toast.LENGTH_SHORT).show();

            installApk();
            System.out.println("testtapplicatoionnntestttt installApk");
        }

        Uri packageUri = Uri.parse("package:dev.firebase.appdistribution");
        Intent uninstallIntent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri);
        startActivity(uninstallIntent);
    }


    private void installApk() {
        File apkFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "app-tester-google.apk");
        if (apkFile.exists()) {
            Uri apkUri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                apkUri = FileProvider.getUriForFile(this, "com.example.appinstaller.fileprovider", apkFile);
                grantUriPermission("com.example.appinstaller", apkUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                apkUri = Uri.fromFile(apkFile);
            }

            Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
            intent.setData(apkUri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "APK file not found", Toast.LENGTH_SHORT).show();
        }
    }

}
