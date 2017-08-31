package mx.com.aries.utils;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

public class DialogsUtil {

    public static void showAlertDialog(Context context, String title, String message,
                                       String okButton, DialogInterface.OnClickListener okListener,
                                       String cancelbutton, DialogInterface.OnClickListener cancelListener) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title)
                .setMessage(message)
                .setPositiveButton(okButton, cancelListener)
                .setNegativeButton(cancelbutton, okListener);
        dialog.show();
    }
}
