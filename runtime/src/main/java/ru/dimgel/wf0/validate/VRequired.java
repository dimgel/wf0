package ru.dimgel.wf0.validate;

/**
 * <ul>
 * <li>Fails if input is {@code null} or empty string;</li>
 * <li>succeeds with {@code out = in} otherwise.</li>
 * </ul>
 * <p>You might want to insert {@link VTrim} between {@link VOne} and {@code VRequired}.</p>
 */
public final class VRequired extends Validator<String, String> {

	public VRequired() {
		super(s -> "missing required value");
	}

	public VRequired(VErrorMessageProducer<String> err) {
		super(err);
	}

	@Override
	public VResult<String> validate(String in) {
		return (in == null || in.isEmpty())
				? failure(in)
				: success(in);
	}
}
