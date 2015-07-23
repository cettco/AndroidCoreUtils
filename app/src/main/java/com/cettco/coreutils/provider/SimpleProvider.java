package com.cettco.coreutils.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by cettco on 7/23/15.
 */
public class SimpleProvider extends ContentProvider{
    @Override
    public boolean onCreate() {
        Log.v("refresh", "oncreate");
        File f = new File(getContext().getFilesDir(), "active_commom.png");

        if (!f.exists()) {
            AssetManager assets = getContext().getResources().getAssets();

            try {
                copy(assets.open("active_commom.png"), f);
            } catch (IOException e) {
                Log.e("FileProvider", "Exception copying from assets", e);

                return (false);
            }
        }

        return (true);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        return null;
    }

    @Override
    public String getType(Uri uri) {
        Log.v("refresh","gettype"+uri.getPath());
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.v("refresh","insert"+uri.getPath());
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        Log.v("refresh","update"+uri.getPath());
        return 0;
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode)
            throws FileNotFoundException {
        Log.v("refresh","openfile");
        File cacheDir = getContext().getFilesDir();
        File privateFile = new File(cacheDir, "active_commom.png");

        return ParcelFileDescriptor.open(privateFile,
                ParcelFileDescriptor.MODE_READ_ONLY);
    }

    @Override
    public AssetFileDescriptor openAssetFile(Uri uri, String mode) throws FileNotFoundException {
        Log.v("refresh","open assetfile"+uri.getPath());
        String path = uri.getPath().substring(1);
        try {
            AssetFileDescriptor afd = getContext().getAssets().openFd(path);
            return afd;
        } catch (IOException e) {
            throw new FileNotFoundException("No asset found: " + uri);
        }
    }

    static private void copy(InputStream in, File dst) throws IOException {
        FileOutputStream out = new FileOutputStream(dst);
        byte[] buf = new byte[1024];
        int len;

        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }

        in.close();
        out.close();
    }
}
