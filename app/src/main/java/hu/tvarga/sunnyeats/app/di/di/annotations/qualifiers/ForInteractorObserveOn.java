package hu.tvarga.sunnyeats.app.di.di.annotations.qualifiers;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.CLASS;

@Qualifier
@Retention(CLASS)
public @interface ForInteractorObserveOn {}
