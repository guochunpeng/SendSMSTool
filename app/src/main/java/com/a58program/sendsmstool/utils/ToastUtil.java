package com.a58program.sendsmstool.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	private static Toast toast = null;

	public static final int SHOW = 0;
	public static final int LONG = 1;

	public static void showToast(Context context, String msg) {
		if (msg == null || "".equals(msg)) {
			return;
		}
		if (toast == null) {
			toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		} else {
			toast.setText(msg);
		}
		toast.show();
	}

	public static void showToast(Context context, String msg, int time) {
		if (msg == null || "".equals(msg)) {
			return;
		}
		if (toast == null) {
			toast = Toast.makeText(context, msg, time);
		} else {
			toast.setText(msg);
		}
		toast.show();
	}
	public static void showToast(Context context, int resid) {
		if (toast == null) {
			toast = Toast.makeText(context, resid, Toast.LENGTH_LONG);
		} else {
			toast.setText(context.getResources().getText(resid));
		}
		toast.show();
	}
}
