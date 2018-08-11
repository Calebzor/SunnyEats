package hu.tvarga.sunnyeats.common.app.di.annotations.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Scope used in the ApplicationModule, classes annotated with this scope will live for the lifetime
 * of the application class
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationScoped {}
