package wootrevived.api;

/**
 * Marks a class as a Woot Revived plugin.
 * <p>
 * Any class implementing {@link IWootPlugin} must be annotated with {@code @WootPlugin}
 * and provide a public no-argument constructor. This allows Woot Revived to
 * automatically detect and load the plugin at runtime.
 */
public @interface WootPlugin {
}
