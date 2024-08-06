package `in`.planckstudio.filters.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import `in`.planckstudio.filters.R
import `in`.planckstudio.filters.util.FilterRepository
import kotlin.math.min

class FilterAdapter(
    private val context: Context,
    private var originalBitmap: Bitmap,
    private val onFilterApplied: (Bitmap) -> Unit
) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    private val filterNames = FilterRepository.getFilterNames()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    inner class FilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val filterImageView: ImageView = itemView.findViewById(R.id.filter_image_view)
        val filterTitle: TextView = itemView.findViewById(R.id.filter_title)
        val shimmerViewContainer: ShimmerFrameLayout =
            itemView.findViewById(R.id.shimmer_view_container)

        fun bind(filterName: String) {
            filterTitle.text = filterName
            shimmerViewContainer.startShimmer()

            coroutineScope.launch {
                val resizedBitmap = withContext(Dispatchers.Default) {
                    resizeBitmap(originalBitmap, 80, 80)
                }
                val filterBitmap = withContext(Dispatchers.Default) {
                    try {
                        val filter = FilterRepository.getFilterByName(filterName)
                        filter?.apply(resizedBitmap) ?: resizedBitmap
                    } catch (e: Exception) {
                        e.printStackTrace()
                        resizedBitmap
                    }
                }

                // Use Glide to set the image bitmap
                Glide.with(context)
                    .asBitmap()
                    .load(filterBitmap)
                    .apply(RequestOptions().placeholder(R.drawable.placeholder))
                    .into(filterImageView)

                shimmerViewContainer.stopShimmer()
                shimmerViewContainer.hideShimmer()

                // Handle item click
                filterImageView.setOnClickListener {

                    Toast.makeText(context, "Applying $filterName Filter", Toast.LENGTH_SHORT)
                        .show()

                    coroutineScope.launch {
                        val newFilterBitmap = withContext(Dispatchers.Default) {
                            try {
                                val filter = FilterRepository.getFilterByName(filterName)
                                val bitmapToApplyFilter =
                                    originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
                                filter?.apply(bitmapToApplyFilter) ?: bitmapToApplyFilter
                            } catch (e: Exception) {
                                e.printStackTrace()
                                originalBitmap
                            }
                        }
                        withContext(Dispatchers.Main) {
                            Log.d("FilterAdapter", "Applying filter to ImageView...")
                            onFilterApplied(newFilterBitmap)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_filter, parent, false)
        return FilterViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val filterName = filterNames[position]
        holder.bind(filterName)
    }

    override fun getItemCount(): Int {
        return filterNames.size
    }

    private fun resizeBitmap(bitmap: Bitmap, widthDp: Int, heightDp: Int): Bitmap {
        val widthPx = (widthDp * context.resources.displayMetrics.density).toInt()
        val heightPx = (heightDp * context.resources.displayMetrics.density).toInt()

        // Ensure we get a square bitmap
        val size = min(bitmap.width, bitmap.height)
        val xOffset = (bitmap.width - size) / 2
        val yOffset = (bitmap.height - size) / 2

        // Create a square cropped bitmap with ARGB_8888 configuration to avoid hardware bitmap issues
        val croppedBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(croppedBitmap)
        val paint = Paint(Paint.FILTER_BITMAP_FLAG)
        canvas.drawBitmap(
            bitmap.copy(Bitmap.Config.ARGB_8888, true),
            -xOffset.toFloat(),
            -yOffset.toFloat(),
            paint
        )

        // Create a resized bitmap with ARGB_8888 configuration
        return Bitmap.createScaledBitmap(croppedBitmap, widthPx, heightPx, true)
    }

    fun updateBitmap(newBitmap: Bitmap) {
        originalBitmap = newBitmap
        notifyDataSetChanged()
    }
}