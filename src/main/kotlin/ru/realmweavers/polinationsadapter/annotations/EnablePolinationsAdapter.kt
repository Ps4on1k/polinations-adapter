package ru.realmweavers.polinationsadapter.annotations

import org.springframework.context.annotation.Import
import ru.realmweavers.polinationsadapter.config.PolinationsAdapterAutoConfiguration

/**
 * Annotation for manual turn-on Polinations Adapter
 */


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(PolinationsAdapterAutoConfiguration::class)
annotation class EnablePolinationsAdapter