package com.drmgms.game_installer.twa;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class UninstallActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("testtapplicatoionnntestttt intentttt");

        // Handle the intent from PWA

        startUninstallProcedure();
        finish();
    }

    private void startUninstallProcedure() {
        // Example uninstallation procedure
        Uri packageUri = Uri.parse("package:com.drmgms.game_installer.twa");
        Intent uninstallIntent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri);
        startActivity(uninstallIntent);
    }
}
