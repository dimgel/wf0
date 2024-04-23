package ru.dimgel.wf0.validate;


/**
 * Does not transform input. Succeeds if input length is in range.
 */
public final class VLength extends Validator<String, String> {

	private final int min;
	private final int max;

	public VLength(int min, int max) {
		super(in -> "length not in range [" + min + ", " + max + "]");
		this.min = min;
		this.max = max;
	}

	public VLength(int min, int max, VErrorMessageProducer<String> err) {
		super(err);
		this.min = min;
		this.max = max;
	}


	@Override
	public VResult<String> validate(String in) {
		return in.length() >= min && in.length() <= max
				? success(in)
				: failure(in);
	}
}
