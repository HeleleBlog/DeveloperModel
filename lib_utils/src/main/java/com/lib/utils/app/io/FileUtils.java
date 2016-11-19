package com.lib.utils.app.io;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by orangeLe on 2016/8/5 0005.
 * 文件操作的工具类
 */
public class FileUtils {

    /**
     * 创建单个目录
     *
     * @param path    待创建文件夹的路径(路径的分隔符应该用 File.separator )
     * @param dirname 文件夹名
     * @return true创建成功，false创建失败
     */
    public static boolean createDir(String path, String dirname) {
        File file = new File(path, dirname);
        return file.mkdir();
    }

    /**
     * 创建多级目录
     *
     * @param path    待创建文件夹的路径(路径的分隔符应该用 File.separator )
     * @param dirname 文件夹名
     * @return true创建成功，false创建失败
     */
    public static boolean createDirs(String path, String dirname) {
        File file = new File(path, dirname);
        return file.mkdirs();
    }

    /**
     * 创建文件
     *
     * @param path     待创建文件的路径(路径的分隔符应该用 File.separator )
     * @param filename 文件名
     * @return true 成功，false 失败
     * @throws IOException
     */
    public static boolean createNewFile(String path, String filename) throws IOException {
        File file = new File(path, filename);
        return file.createNewFile();
    }

    /**
     * 删除单个文件
     *
     * @param path     文件路径
     * @param filename 文件名
     * @return true 成功，false 失败
     * @throws IOException
     */
    public static boolean deleteFile(String path, String filename) throws IOException {
        File file = new File(path, filename);
        if(!file.isFile()) return false;
        return file.exists() ? file.delete() : false;
    }

    /**
     * 利用递归列出全部文件
     * @param dir
     */
    public static void showDir(File dir){
        File[] files =dir.listFiles();
        for(File file:files){
            if(file.isDirectory())
                showDir(file);
            else
                Log.d("FileUtils", "showDir: "+file);
        }
    }

    private static final String TEMP_PHOTO_FILE = "tempPhoto.jpg";

    private static boolean isSDCARDMounted() {
        String status = Environment.getExternalStorageState();

        if (status.equals(Environment.MEDIA_MOUNTED))
            return true;
        return false;
    }

    private static File getTempFile() {
        if (isSDCARDMounted()) {

            File f = new File(Environment.getExternalStorageDirectory(),
                    TEMP_PHOTO_FILE);
            try {
                f.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                // Toast.makeText(thiz, R.string.fileIOIssue,
                // Toast.LENGTH_LONG).show();
            }
            return f;
        } else {
            return null;
        }
    }
}
