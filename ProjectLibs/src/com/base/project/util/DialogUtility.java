package com.base.project.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.example.projectlibs.R;

public final class DialogUtility {
    /**
     * Show an alert dialog box
     * 
     * @param context
     * @param message
     */
    public static void alert(Context context, String message, int resTitleId) {
        if (context != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle(context.getString(resTitleId));
            alertDialog.setMessage(message);
            alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            alertDialog.show();
        }
    }


    /**
     * Show an alert dialog box by messageId
     * 
     * @param context
     * @param messageId
     */
    public static void alert(Context context, int messageId) {
        if (context != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle(context.getString(R.string.title_dialog));
            alertDialog.setMessage(context.getString(messageId));
            alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            alertDialog.show();
        }
    }

    /**
     * Show an alert dialog box by messageId
     * 
     * @param context
     * @param messageId
     */
    public static void alert(Context context, String message) {
        if (context != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle(context.getString(R.string.title_dialog));
            alertDialog.setMessage(message);
            alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            alertDialog.show();
        }
    }
    
    
    /**
     * Show an alert dialog box by String message
     * 
     * @param context
     * @param message
     * @param onOkClick
     */
    public static void alert(Context context, String message, DialogInterface.OnClickListener onOkClick) {
        if (context != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle(R.string.app_name);
            alertDialog.setMessage(message);
            if(onOkClick != null){
            alertDialog.setPositiveButton(R.string.ok, onOkClick);
            } else {
                alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });
            }
            alertDialog.show();
        }
    }

    public static void alert(Context context, int title, String message) {
        if (context != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setPositiveButton(R.string.ok, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }

    public static void alert(Context context, int resTitle, int resMessage,
            DialogInterface.OnClickListener onOkClick) {
        if (context != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle(resTitle);
            alertDialog.setMessage(resMessage);
            alertDialog.setPositiveButton(R.string.ok, onOkClick);
            alertDialog.show();
        }
    }

    public static void alert(Context context, int resTitle, String resMessage,
            DialogInterface.OnClickListener onOkClick) {
        if (context != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle(resTitle);
            alertDialog.setMessage(resMessage);
            alertDialog.setPositiveButton(R.string.ok, onOkClick);
            alertDialog.show();
        }
    }

    /**
     * Show an option yes/no dialog box
     * 
     * @param context
     * @param messageId
     * @param OkTextId
     * @param cancelTextId
     * @param onOKClick
     */
    public static void showYesNoDialog(Context context, int resTitleId, int messageId, int OkTextId,
            int cancelTextId, final DialogInterface.OnClickListener onOKClick) {
        if (context != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle(context.getString(resTitleId));
            alertDialog.setMessage(context.getString(messageId));
            alertDialog.setNegativeButton(cancelTextId, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    dialog.dismiss();
                }
            });

            alertDialog.setPositiveButton(OkTextId, onOKClick);
            alertDialog.show();
        }
    }

    public static void showYesNoDialog(Context context, int resTitleId, int messageId, int OkTextId,
            int cancelTextId, final DialogInterface.OnClickListener onOKClick,
            final DialogInterface.OnClickListener onCancelClick) {
        if (context != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle(context.getString(resTitleId));
            alertDialog.setMessage(context.getString(messageId));
            alertDialog.setNegativeButton(cancelTextId, onCancelClick);
            alertDialog.setPositiveButton(OkTextId, onOKClick);
            alertDialog.show();
        }
    }


    /**
     * @param mainActivity
     * @param strNetworkErr
     * @param strNoNetwork
     */
    public static void alert(Context context, int strNetworkErr, int strNoNetwork) {
        if (context != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle(strNetworkErr);
            alertDialog.setMessage(strNetworkErr);
            alertDialog.setPositiveButton(R.string.ok, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }

}
