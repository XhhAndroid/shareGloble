package globle.xhh.sharefun

import android.view.LayoutInflater
import android.view.ViewGroup
import com.h.android.adapter.HRecyclerViewAdapter
import globle.xhh.sharefun.databinding.AdapterChatLayoutBinding
import org.drinkless.td.libcore.telegram.TdApi

/**
 *2022/6/23
 *@author zhangxiaohui
 *@describe
 */
class ChatListAdapter : HRecyclerViewAdapter<AdapterChatLayoutBinding, AdapterChatsEntity>() {
    override fun bindItemViewHolder(holder: AdapterChatLayoutBinding?, pos: Int, entity: AdapterChatsEntity) {

    }

    override fun bindViewListener(holder: AdapterChatLayoutBinding?, pos: Int, entity: AdapterChatsEntity) {
        holder?.chatTitleTv?.text = entity.title
        holder?.lastMessageTv?.text = entity.lastMessage
        holder?.messageTimeTv?.text = entity.msgTime
    }

    override fun createView(parent: ViewGroup, viewType: Int): AdapterChatLayoutBinding {
        return AdapterChatLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }
}