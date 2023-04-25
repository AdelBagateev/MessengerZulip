package com.example.homework2.people.presentation.ui.delegate

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homework2.R
import com.example.homework2.common.adapter.interfaces.AdapterDelegate
import com.example.homework2.databinding.ItemFriendBinding
import com.example.homework2.people.domain.model.PeopleModel
import com.example.homework2.people.domain.model.PresenceModel

class PeopleDelegate(
    private val navigateToProfile: (PeopleModel) -> Unit
) : AdapterDelegate<ViewHolder, PeopleDelegateItem> {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder =
        ViewHolder(
            ItemFriendBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            navigateToProfile
        )

    override fun onBindViewHolder(
        holder: ViewHolder,
        item: PeopleDelegateItem
    ) {
        holder.bind(item.value)
    }

    override fun isOfViewType(item: Any): Boolean {
        return item is PeopleDelegateItem
    }

}

class ViewHolder(
    private val binding: ItemFriendBinding,
    private val navigateToProfile: (PeopleModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(model: PeopleModel) {
        with(binding) {
            tvMail.text = model.mail
            tvName.text = model.fullName
            ravAvatar.load(model.avatarUrl) {
                placeholder(R.drawable.ic_profile)
                error(R.drawable.ic_no_avatar)
            }
            root.setOnClickListener {
                navigateToProfile(model)
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
