package com.lib.utils.app.data;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.TypedValue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by orangeLe on 2016/8/5 0005.
 * 数据类型转换的工具类
 */
public class DataTypeSwitch {

    /**
     * 将String数组拼接为字典String
     */
    public static String StringArrayToString(String[] list) {
        String result = "";
        if (list == null) {
            return "[]";
        } else {
            if (list.length == 0) {
                return "[]";
            } else {
                result = "[" + list[0];
                for (int i = 1; i < list.length; i++) {
                    result += "," + list[i];
                }
                result += "]";
            }
        }
        return result;
    }

    /**
     * 将整形数组拼接为字典String
     */
    public static String IntegerArrayToString(Integer[] list) {
        String result = "";
        if (list == null) {
            return "[]";
        } else {
            if (list.length == 0) {
                return "[]";
            } else {
                result = "[" + list[0];
                for (int i = 1; i < list.length; i++) {
                    result += "," + list[i];
                }
                result += "]";
            }
        }
        return result;
    }

    /**
     * 将字符串List转换为字符串数组
     */
    public static String[] ListToStringArray(List<String> list) {
        String[] array = new String[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i).toString();
        }
        return array;
    }

    /**
     * 将整形List转换为整形数组
     */
    public static Integer[] ListToIntegerArray(List<Integer> list) {
        Integer[] array = new Integer[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    /**
     * Base64加密
     */
    public static String StrToBase64(String text) {
        String result = new String(Base64.encode(text.getBytes(),
                Base64.NO_WRAP));
        return result;
    }

    /**
     * Base64解密
     */
    public static String Base64ToStr(String text) {
        String result = new String(Base64.decode(text, Base64.DEFAULT));
        return result;
    }

    /**
     * bitmap转换 bytes
     */
    public static byte[] BitmaptoBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    /**
     * bitmap转换 bytes
     */
    public static byte[] BitmaptoBytes(Bitmap bm, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    /**
     * 将Base64的字符串转换成图片
     *
     * @param str
     * @return
     */
    public static Bitmap Base64StringtoBitmap(String str) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(str, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * 将GBK字符串转成UTF-8数据
     *
     * @param gbkStr
     * @return
     */
    public static String getUTF8StringFromGBKString(String gbkStr) {
        try {
            return new String(getUTF8BytesFromGBKString(gbkStr), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new InternalError();
        }
    }

    public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
        int n = gbkStr.length();
        byte[] utfBytes = new byte[3 * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int m = gbkStr.charAt(i);
            if (m < 128 && m >= 0) {
                utfBytes[k++] = (byte) m;
                continue;
            }
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
        }
        if (k < utfBytes.length) {
            byte[] tmp = new byte[k];
            System.arraycopy(utfBytes, 0, tmp, 0, k);
            return tmp;
        }
        return utfBytes;
    }

    /**
     * Convert Dp to Pixel
     */
    public static int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                resources.getDisplayMetrics());
        return (int) px;
    }

    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static Bitmap compressUploadImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) { // 循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        // 缓存上传图片文件
        String string = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(string)) {
            File history = new File(Environment.getExternalStorageDirectory()
                    .getPath() + "/FlowerFairy");
            if (!history.exists())
                history.mkdir();
            File cache = new File(history, "/share.cache");
            try {
                OutputStream outputStream = new FileOutputStream(cache);
                if (history.exists())
                    history.delete();
                bitmap.compress(Bitmap.CompressFormat.JPEG, options,
                        outputStream);
            } catch (Exception e) {
            }
        }
        return bitmap;
    }

    // 时间戳转String
    @SuppressLint("SimpleDateFormat")
    public static String getDateToString(long time) {
        Date date = new Date(time * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    /**
     * float转换成String类型,并且保留两位小数或多位小数
     */
    public static String formatFloat(float pirce) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(pirce);
    }

    /**
     * double转换成String类型
     * @param value
     * @return
     */
    public static String formartDouble(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(value);
    }

}
