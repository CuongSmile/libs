package com.base.project.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class ImageUtils {
	public static Bitmap scaleBitmap(Bitmap pOrginalBitmap, float newWidth,
			float newHeight) {
		Bitmap ret = null;
		if (pOrginalBitmap == null) {
			ret = null;
		} else {
			// get the original width and height
			int width = pOrginalBitmap.getWidth();
			int height = pOrginalBitmap.getHeight();
			// create a matrix for the manipulation
			Matrix matrix = new Matrix();
			// resize the bitmap
			matrix.postScale(newWidth / width, newHeight / height);
			// recreate the new Bitmap and set it back
			ret = Bitmap.createBitmap(pOrginalBitmap, 0, 0,
					pOrginalBitmap.getWidth(), pOrginalBitmap.getHeight(),
					matrix, true);
		}
		return ret;
	}
}
