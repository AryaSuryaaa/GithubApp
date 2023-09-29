import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aryasurya.githubapp.data.remote.response.FollowersResponseItem
import com.aryasurya.githubapp.databinding.ItemListFollowersBinding
import com.bumptech.glide.Glide

class FollowersAdapter() : ListAdapter<FollowersResponseItem, FollowersAdapter.FollowersViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowersResponseItem>() {
            override fun areItemsTheSame(oldItem: FollowersResponseItem, newItem: FollowersResponseItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FollowersResponseItem, newItem: FollowersResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val binding = ItemListFollowersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowersViewHolder(binding)

    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        val follower = getItem(position)
        holder.bind(follower)
    }

    inner class FollowersViewHolder(private val binding: ItemListFollowersBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(follower: FollowersResponseItem) {
            // Setel data pada tampilan menggunakan databinding
            binding.tvFollowersItem.text = follower.login
            Glide.with(binding.root.context)
                .load(follower.avatarUrl)
                .into(binding.imgFollowersItem)
        }
    }
}
