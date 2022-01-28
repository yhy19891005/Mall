package com.yhy.base.utils

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.BaseColumns
import android.provider.DocumentsContract
import android.provider.MediaStore
import java.io.*
import java.lang.Exception

object AppUtil {

    private fun getFileRoot(context: Context): String? {
        if (Environment.getExternalStorageState() ==
            Environment.MEDIA_MOUNTED
        ) {
            val external = context.getExternalFilesDir(null)
            if (external != null) {
                return external.absolutePath
            }
        }
        return context.filesDir.absolutePath
    }

    //bitmap转换为file
    fun getFile(context: Context?, bitmap: Bitmap): File {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        val file =
            File(getFileRoot(context!!) + "/mall/" + System.currentTimeMillis() + ".jpg")
        try {
            if (!file.exists()) {
                file.parentFile.mkdirs()
                file.createNewFile()
            }
            val fos = FileOutputStream(file)
            val `is`: InputStream = ByteArrayInputStream(baos.toByteArray())
            var x = 0
            val b = ByteArray(1024 * 100)
            while (`is`.read(b).also { x = it } != -1) {
                fos.write(b, 0, x)
            }
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file
    }

    fun getRealPathFromUriAboveApi19(context: Context?, uri: Uri): String {
        var filePath = ""
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的 uri, 则通过document id来进行处理
            val documentId = DocumentsContract.getDocumentId(uri)
            if (isMediaDocument(uri)) { // MediaProvider
                val divide = documentId.split(":").toTypedArray()
                val type = divide[0]
                var mediaUri: Uri? = null
                mediaUri = if ("image" == type) {
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                } else {
                    return ""
                }
                val selection = BaseColumns._ID + "=?"
                val selectionArgs = arrayOf(divide[1])
                filePath = getDataColumn(
                    context!!,
                    mediaUri,
                    selection,
                    selectionArgs
                )
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(documentId)
                )
                filePath = getDataColumn(
                    context!!,
                    contentUri,
                    null,
                    null
                )
            } else if (isExternalStorageDocument(uri)) {
                val split = documentId.split(":").toTypedArray()
                if (split.size >= 2) {
                    val type = split[0]
                    if ("primary".equals(type, ignoreCase = true)) {
                        filePath =
                            Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    }
                }
            }
        } else if (ContentResolver.SCHEME_CONTENT.equals(uri.scheme, ignoreCase = true)) {
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(context!!, uri, null, null)
        } else if (ContentResolver.SCHEME_FILE == uri.scheme) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.path.toString()
        }
        return filePath
    }

    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
     * @return
     */
    private fun getDataColumn(
        context: Context,
        uri: Uri,
        selection: String?,
        selectionArgs: Array<String>?
    ): String {
        var path = ""
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        var cursor: Cursor? = null
        try {
            cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(projection[0])
                path = cursor.getString(columnIndex)
            }
        } catch (e: Exception) {
            cursor?.close()
        }
        return path
    }
}