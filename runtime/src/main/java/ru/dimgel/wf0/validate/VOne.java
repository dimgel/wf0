package ru.dimgel.wf0.validate;

/**
 * <p>Input has same semantics as return value of {@link jakarta.servlet.http.HttpServletRequest#getParameterValues(String)}:
 * {@code null} means parameter is missing, {@code (length > 1)} means parameter is multi-valued.</p>
 *
 * <ul>
 * <li>Fails on multi-valued input;</li>
 * <li>succeeds with {@code out = null} on missing input.</li>
 * <li>succeeds with {@code out = in[0]} on single-valued input;</li>
 * </ul>
 * <p>Does NOT check input array elements for being empty strings; e.g. in URL {@code /hello?a=&a=} parameter {@code a} is still multi-valued.
 * Null or empty string check is responsibility of {@link VRequired} and {@link VOptional}, one of which should be piped after {@code VOne},
 * maybe with {@link VTrim} in between.</p>
 */
public final class VOne extends Validator<String[], String> {

	public VOne() {
		super(ss -> "expected one value, got multiple values");
	}

	public VOne(VErrorMessageProducer<String[]> err) {
		super(err);
	}


	@Override
	public VResult<String> validate(String[] in) {
		if (in == null) {
			return success(null);
		}
		return (in.length > 1)
				? failure(in)
				: success(in[0]);
	}
}
