package com.pratheek.thoughts

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Environment
import android.util.Log
import android.view.View
import java.io.File
import java.io.FileOutputStream


class ScreenUtils {

    companion object {
        fun getScreenShot(view: View): Bitmap {
            val returnedBitmap =
                Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(returnedBitmap)
            val bgDrawable = view.background
            if (bgDrawable != null) bgDrawable.draw(canvas)
            else canvas.drawColor(Color.WHITE)
            view.draw(canvas)
            return returnedBitmap
        }

        fun getMainDirectoryName(context: Context): File? {
            val mainDir = File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Thoughts App"
            )

            if (!mainDir.exists()) {
                if (mainDir.mkdir())
                    Log.e(
                        "Create Directory",
                        "Main Directory Created : $mainDir"
                    )
            }
            return mainDir
        }

        fun store(
            bm: Bitmap,
            fileName: String?,
            saveFilePath: File
        ): File? {


            val dir = File(saveFilePath.absolutePath)
            if (!dir.exists()) dir.mkdirs()
            val file = File(saveFilePath.absolutePath, fileName)
            try {
                val fOut = FileOutputStream(file)
                bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut)
                fOut.flush()
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return file
        }
    }


}