package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Size
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main7.*
import org.tensorflow.lite.support.image.TensorImage
import com.example.myapplication.ml.Cook
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

typealias ImageProxyListener = (bmp: Bitmap) -> Unit

class MainActivity7 : AppCompatActivity(), PermissionListener {

    private lateinit var cameraExecutor: ExecutorService



    private class ImageAnalyzer(ctx: Context, private val listener: ImageProxyListener) :
            ImageAnalysis.Analyzer {

        private val yuvToRgbConverter = YuvToRgbConverter(ctx)
        private lateinit var bitmapBuffer: Bitmap
        private lateinit var rotationMatrix: Matrix

        override fun analyze(imageProxy: ImageProxy) {

            val bmp:Bitmap? = toBitmap(imageProxy)

            if (bmp != null) {
                listener(bmp)
            }
            // Close the image,this tells CameraX to feed the next image to the analyzer
            imageProxy.close()
        }

        @SuppressLint("UnsafeExperimentalUsageError")
        private fun toBitmap(imageProxy: ImageProxy): Bitmap? {
            val image = imageProxy.image ?: return null
            // Initialise Buffer
            if (!::bitmapBuffer.isInitialized) {
                // The image rotation and RGB image buffer are initialized only once
                rotationMatrix = Matrix()
                rotationMatrix.postRotate(imageProxy.imageInfo.rotationDegrees.toFloat())
                bitmapBuffer = Bitmap.createBitmap(
                        imageProxy.width, imageProxy.height, Bitmap.Config.ARGB_8888
                )
            }

            // Pass image to an image analyser
            yuvToRgbConverter.yuvToRgb(image, bitmapBuffer)

            // Create the Bitmap in the correct orientation
            return Bitmap.createBitmap(bitmapBuffer, 0, 0,
                    bitmapBuffer.width, bitmapBuffer.height, rotationMatrix, false
            )
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main7)


        Dexter.withContext(this)
            .withPermission(android.Manifest.permission.CAMERA)
            .withListener(this)
            .check()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }
    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        Toast.makeText(this, "您已允許拍照權限", Toast.LENGTH_SHORT).show()
        startCamera()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.createSurfaceProvider())
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val imageAnalyzer = ImageAnalysis.Builder()
                .setTargetResolution(Size(224, 224))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalyzer.setAnalyzer(cameraExecutor, ImageAnalyzer(this) { bitmap ->
                val model = Cook.newInstance(this)
                val image = TensorImage.fromBitmap(bitmap)

                val outputs = model.process(image)
                    .probabilityAsCategoryList.apply {
                        sortByDescending { it.score } // 排序，高匹配率優先
                    }
                var Result:String = ""
                for (output in outputs) {
                    when (output.label) {
                        "bread" -> Result += "麵包"
                        "cookie" -> Result += "餅乾"

                    }
                    Result += ": " + String.format("%.1f%%", output.score * 100.0f) + ";  "
                }
                txv.text = Result

                model.close()

            })


            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview,imageAnalyzer)



            } catch(exc: Exception) {
                Toast.makeText(this, "Use case binding failed: ${exc.message}",
                    Toast.LENGTH_SHORT).show()
            }

        }, ContextCompat.getMainExecutor(this))
    }
    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
        if (p0!!.isPermanentlyDenied) {
            Toast.makeText(this, "您永久拒絕拍照權限", Toast.LENGTH_SHORT).show()
            var it: Intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            var uri: Uri = Uri.fromParts("package", getPackageName(), null)
            it.setData(uri)
            startActivity(it)
        }
        else{
            Toast.makeText(this, "您拒絕拍照權限，無法使用本App",
                Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
        p1?.continuePermissionRequest()
    }
}