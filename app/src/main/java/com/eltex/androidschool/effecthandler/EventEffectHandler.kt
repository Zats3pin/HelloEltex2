package com.eltex.androidschool.effecthandler

import com.eltex.androidschool.mapper.EventUiModelMapper
import com.eltex.androidschool.model.EventEffect
import com.eltex.androidschool.model.EventMessage
import com.eltex.androidschool.model.EventStatus
import com.eltex.androidschool.mvi.EffectHandler
import com.eltex.androidschool.repository.EventRepository
import com.eltex.androidschool.utils.Either
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance

import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.merge
import java.util.concurrent.CancellationException

@OptIn(ExperimentalCoroutinesApi::class)
class EventEffectHandler(
    private val repository: EventRepository,
    private val mapper: EventUiModelMapper = EventUiModelMapper(),
): EffectHandler<EventEffect,EventMessage> {
    override fun connect(messages: Flow<EventEffect>): Flow<EventMessage> =
        listOf(
            messages.filterIsInstance<EventEffect.LoadInitialPage>()
                .mapLatest{ it ->
                    EventMessage.InitialLoaded(
                       try {

                          val result =  repository.getLatest(it.count)
                           Either.Right(result.map { mapper.map(it) })
                       }catch (e:Exception){
                            if (e is CancellationException) throw e
                           else Either.Left(e)
                       }

                    )

                }
        )
            .merge()
}