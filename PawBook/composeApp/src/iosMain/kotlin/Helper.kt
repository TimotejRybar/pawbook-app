package sk.uplab.pawbook

import appModule
import org.koin.core.context.startKoin

fun doInitKoin(){
    startKoin {
        modules(appModule())
    }
}