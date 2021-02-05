package com.example.sharememes

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    var currentImageUrl: String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMemes()

        val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

    private fun loadMemes(){

        // Instantiate the RequestQueue.
        val pb = findViewById<ProgressBar>(R.id.progressBar)
        pb.visibility = View.VISIBLE
        val url = "https://meme-api.herokuapp.com/gimme"
        val im = findViewById<ImageView>(R.id.imageView)
        // Request a string response from the provided URL.
        val JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                currentImageUrl = response.getString("url")
                Glide.with(this).load(currentImageUrl).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pb.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pb.visibility = View.GONE
                        return false
                    }
                }).into(im)
            },
            {
                Log.d("erro", it.localizedMessage)
            })

        // Add the request to the RequestQueue.
        MySingleton.getInstance(this,).addToRequestQueue(JsonObjectRequest)
    }

    fun shareMeme(view: View) {
        Picasso.get().load(currentImageUrl).into(object: com.squareup.picasso.Target{

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_STREAM, getBitmapFromView(bitmap))
                startActivity(Intent.createChooser(intent,"Share this meme using..."))
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

            }
        })
    }

    private fun getBitmapFromView(bitmap: Bitmap?): Uri? {
        var bmpUri : Uri? = null
        try {
            val file = File(this.externalCacheDir, System.currentTimeMillis().toString() + ".jpg")

            val out = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.close()
            bmpUri = Uri.fromFile(file)
        } catch (e: IOException){
            e.printStackTrace()
        }
        return bmpUri
    }

    fun NextMeme(view: View) {
        loadMemes()
    }

    fun download(view: View) {}
}




















