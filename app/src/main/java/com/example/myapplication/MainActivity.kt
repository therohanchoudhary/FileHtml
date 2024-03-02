package com.example.myapplication

import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

val daata = """
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sample Web Page</title>
</head>
<body>
    <h1>Welcome to My Sample Web Page</h1>
    <p>This is a paragraph of text to demonstrate simple HTML structure. HTML allows you to create structured documents by denoting structural semantics for text such as headings, paragraphs, lists, links, quotes, and other items.</p>
    
</body>
</html>

"""

class MainActivity : AppCompatActivity() {
    private fun writeToDownloadsFile() {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val downloadsDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val samsungHealthDir = File(downloadsDir, "Samsung Health/Export Data")
            if (!samsungHealthDir.exists()) samsungHealthDir.mkdirs() // Ensure directory exists

            val file = File(samsungHealthDir, "sample.html")

            try {
                MediaScannerConnection.scanFile(
                    this,
                    arrayOf(file.absolutePath),
                    arrayOf("text/html"),
                    null
                )

                FileOutputStream(file).use { fos ->
                    OutputStreamWriter(fos).use { writer ->
                        writer.write(daata)
                    }
                }
                Toast.makeText(this, "File created", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Exception $e", Toast.LENGTH_SHORT).show()
            }
        } else {
            println("External storage not available")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonWriteFile = findViewById<Button>(R.id.btnWriteFile)
        buttonWriteFile.setOnClickListener {
            writeToDownloadsFile()
        }
    }
}
