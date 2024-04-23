package ru.dimgel.wf0.validate;


/**
 *
 * @param out   Null == missing. If both {@code out} and {@code errorMessage} are {@code null} then it's successful empty result,
 *              as may be returned for example by {@link VOne} or {@link VOptional}.
 * @param errorMessage  Null = success.
 */
public record VResult<Out>(Out out, String errorMessage) {

	public boolean ok() {
		return errorMessage == null;
	}
}
