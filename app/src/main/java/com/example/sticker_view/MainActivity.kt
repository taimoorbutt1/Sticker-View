package com.example.sticker_view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stickerview.StickerView

class MainActivity : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var cleanTv: TextView? = null
    var nextTv: TextView? = null
    var stickerView: StickerView? = null

    var icons = intArrayOf(
        R.drawable.icon_0,
        R.drawable.icon_1,
        R.drawable.icon_2,
        R.drawable.icon_3,
        R.drawable.icon_4,
        R.drawable.icon_5,
        R.drawable.icon_6,
        R.drawable.bg
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        cleanTv = findViewById<View>(R.id.cleanTv) as TextView
        nextTv = findViewById<View>(R.id.nextTv) as TextView
        stickerView = findViewById<View>(R.id.stickerView) as StickerView
        stickerView!!.minStickerSizeScale = 0.9f
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView!!.layoutManager = linearLayoutManager
        val imageAdapter = ImageAdapter()
        recyclerView!!.adapter = imageAdapter
        imageAdapter.setOnItemClickListener(object : OnRecyclerViewItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                stickerView!!.addSticker(icons[position])
            }
        })
        cleanTv!!.setOnClickListener { stickerView!!.clearSticker() }
        nextTv!!.setOnClickListener {

            BitmapUtil.FINAL_BITMAP = stickerView!!.saveSticker()
            val intent = Intent(this@MainActivity, PictureActivity::class.java)
            startActivity(intent)
        }
    }

    inner class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ViewHolder>(),
        View.OnClickListener {
        private var mOnItemClickListener: OnRecyclerViewItemClickListener? = null
        fun setOnItemClickListener(listener: OnRecyclerViewItemClickListener?) {
            mOnItemClickListener = listener
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val view: View = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.image_item, viewGroup, false)
            val vh = ViewHolder(view)
            view.setOnClickListener(this)
            return vh
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            viewHolder.imageView.setImageResource(icons[position])
            viewHolder.itemView.tag = position
        }

        override fun getItemCount(): Int {
            return icons.size
        }

        override fun onClick(v: View) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener!!.onItemClick(v, v.tag as Int)
            }
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var imageView: ImageView

            init {
                imageView = view.findViewById<View>(R.id.imageView) as ImageView
            }
        }
    }

    interface OnRecyclerViewItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}
