package ru.urfu.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.reactor.mono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.context.ContextView
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun <T> coroutineToMono(func: suspend CoroutineScope.() -> T?): Mono<T> {
    return Mono.deferContextual {  ctx ->
        mono(ctx.toCoroutineContext(), func)
    }
}

fun <T> coroutineToFlux(func: suspend CoroutineScope.() -> Iterable<T>): Flux<T> {
    return Mono.deferContextual { ctx ->
        mono(ctx.toCoroutineContext(), func)
    }.flatMapIterable { it }
}

fun ContextView.toCoroutineContext(): CoroutineContext {
    return this.stream()
        .filter { it.value is CoroutineContext }
        .map { it.value as CoroutineContext }
        .reduce { context1, context2 -> context1 + context2 }
        .orElse(EmptyCoroutineContext)
}