package com.example.people.presentation.ui.delegate

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.common.adapter.interfaces.AdapterDelegate
import com.example.common.adapter.interfaces.DelegateItem
import com.example.people.R
import com.example.people.databinding.ItemPeopleBinding
import com.example.people.domain.model.PeopleModel
import com.example.people.domain.model.PresenceModel
import com.example.common.R as CommonR

class PeopleDelegate(
    private val onPeopleClick: (PeopleModel) -> Unit
) : AdapterDelegate<ViewHolder, PeopleDelegateItem> {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        getDelegateItem: (Int) -> DelegateItem
    ): ViewHolder =
        ViewHolder(
            ItemPeopleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            setupActions(this, binding, getDelegateItem)
        }

    override fun onBindViewHolder(
        holder: ViewHolder,
        item: PeopleDelegateItem
    ) {
        holder.bind(item.value)
    }

    override fun isOfViewType(item: Any): Boolean {
        return item is PeopleDelegateItem
    }

    private fun setupActions(
        viewHolder: ViewHolder,
        binding: ItemPeopleBinding,
        getDelegateItem: (Int) -> DelegateItem
    ) {
        binding.root.setOnClickListener {
            val peopleDelegate = getDelegateItem(viewHolder.adapterPosition) as PeopleDelegateItem
            onPeopleClick(peopleDelegate.value)
        }
    }
}

class ViewHolder(
    val binding: ItemPeopleBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: PeopleModel) {
        with(binding) {
            tvName.text = model.fullName
            ravAvatar.load(model.avatarUrl) {
                placeholder(CommonR.drawable.ic_profile)
                error(CommonR.drawable.ic_no_avatar)
            }
            val statusColor = calculateBackgroundRes(model.status, status.context)
            status.background = statusColor
        }
    }

    private fun calculateBackgroundRes(status: String, context: Context): Drawable? {
        return when (status) {
            PresenceModel.ONLINE_STATUS -> ContextCompat.getDrawable(
                context,
                R.drawable.bg_online_status_iv
            )
            PresenceModel.OFFLINE_STATUS -> ContextCompat.getDrawable(
                context,
                R.drawable.bg_offline_status_iv
            )
            PresenceModel.IDLE_STATUS -> ContextCompat.getDrawable(
                context,
                R.drawable.bg_idle_status_iv
            )
            else -> {
                ContextCompat.getDrawable(context, R.drawable.bg_offline_status_iv)
            }
        }
    }
}
