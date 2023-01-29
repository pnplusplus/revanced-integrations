package app.revanced.integrations.patches.utils;

import static app.revanced.integrations.utils.StringRef.str;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import java.util.Objects;

import app.revanced.integrations.utils.ReVancedUtils;

public class MicroGPatch {
    private static final String MICROG_PACKAGE_NAME = "com.mgoogle.android.gms";
    private static final Uri VANCED_MICROG_PROVIDER = Uri.parse("content://com.mgoogle.android.gsf.gservices/prefix");

    public static void checkAvailability() {
        var context = Objects.requireNonNull(ReVancedUtils.getContext());

        try {
            context.getPackageManager().getPackageInfo(MICROG_PACKAGE_NAME, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException exception) {
            Toast.makeText(context, str("microg_not_installed_warning"), Toast.LENGTH_LONG).show();
            Toast.makeText(context, str("microg_not_installed_notice"), Toast.LENGTH_LONG).show();

            System.exit(0);
        }

        try (var client = context.getContentResolver().acquireContentProviderClient(VANCED_MICROG_PROVIDER)) {
            if (client != null) return;
            Toast.makeText(context, str("microg_not_running_warning"), Toast.LENGTH_LONG).show();
        }
    }
}
