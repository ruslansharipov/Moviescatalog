package ru.sharipov.moviescatalog.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_main_list_item.view.*
import ru.sharipov.moviescatalog.R
import ru.sharipov.moviescatalog.domain.MovieItem


class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.Holder>() {

    var movies: List<MovieItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_main_list_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: Holder, position: Int) = with(holder.itemView) {
        val item = movies[position]

        title_tv.text = item.title
        overview_tv.text = item.overview
        premiere_tv.text = item.releaseDate

        Picasso.get()
            .load(item.image)
            .into(poster_iv)
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view)
}