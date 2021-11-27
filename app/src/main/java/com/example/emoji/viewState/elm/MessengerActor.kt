package com.example.emoji.viewState.elm

import com.example.emoji.viewState.elm.laod.LoadMessages
import io.reactivex.Observable
import vivid.money.elmslie.core.ActorCompat

class MessengerActor constructor(
    private val loadMessages: LoadMessages,
) : ActorCompat<MessengerCommand, MessageEvent.Internal> {

    override fun execute(command: MessengerCommand): Observable<MessageEvent.Internal> {
        when (command) {
            is MessengerCommand.MessagesLoaded -> {
                return loadMessages.execute(command.streamName, command.topicName, command.lastMessageId, command.count)
                    .mapEvents(
                        { list -> MessageEvent.Internal.PageLoaded(list) },
                        { error -> MessageEvent.Internal.ErrorLoading(error) }
                    )
            }
        }
    }
}