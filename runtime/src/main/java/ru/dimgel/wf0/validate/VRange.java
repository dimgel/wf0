package ru.dimgel.wf0.validate;


/**
 * Does not transform input. Succeeds if input is in range.
 */
public final class VRange<T extends Comparable<T>> extends Validator<T, T> {
	private final T min;
	private final T max;

	public VRange(T min, T max) {
		super(in -> "not in range [" + min + ", " + max + "]");
		this.min = min;
		this.max = max;
	}

	public VRange(T min, T max, VErrorMessageProducer<T> err) {
		super(err);
		this.min = min;
		this.max = max;
	}

	@Override
	public VResult<T> validate(T in) {
		return (in.compareTo(min) >= 0 && in.compareTo(max) <= 0)
				? success(in)
				: failure(in);
	}
}
