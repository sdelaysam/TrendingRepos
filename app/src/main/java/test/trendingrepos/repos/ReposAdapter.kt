package test.trendingrepos.repos

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import test.trendingrepos.R
import test.trendingrepos.common.api.GithubDto
import test.trendingrepos.databinding.ViewRepoItemBinding
import java.text.NumberFormat
import java.util.*

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

class ReposAdapter(private val onClick: (Int) -> Unit) : RecyclerView.Adapter<ReposAdapter.ViewHolder>() {

    var repos: List<GithubDto.Repository>? = null
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount() = repos?.count() ?: 0

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        return ViewHolder(DataBindingUtil.inflate(inflater, R.layout.view_repo_item, parent, false), onClick)

    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.binding?.let {
            if (it.viewModel == null) {
                it.viewModel = ItemViewModel()
            }
            it.viewModel?.repo = repos?.get(position)
        }
        holder?.binding?.executePendingBindings()
    }

    class ViewHolder(val binding: ViewRepoItemBinding,
                     private val onClick: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener { onClick(adapterPosition) }
        }
    }

    class ItemViewModel {
        val name = ObservableField("")
        val description = ObservableField("")
        val stars = ObservableField("")

        var repo: GithubDto.Repository? = null
        set(value) {
            name.set(value?.name ?: "")
            description.set(value?.description ?: "")
            stars.set(NumberFormat.getNumberInstance(Locale.US).format(value?.stargazersCount ?: 0))
        }
    }

}

