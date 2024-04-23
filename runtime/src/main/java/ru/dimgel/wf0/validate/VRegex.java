package ru.dimgel.wf0.validate;

import java.util.regex.Pattern;


/**
 * Does not transform input. Succeeds if whole input matches regex.
 */
public final class VRegex extends Validator<String, String> {
	private final Pattern regex;

	public VRegex(Pattern regex) {
		super(in -> "does not match regex");
		this.regex = regex;
	}

	public VRegex(String regex) {
		this(Pattern.compile(regex));
	}

	public VRegex(Pattern regex, VErrorMessageProducer<String> err) {
		super(err);
		this.regex = regex;
	}

	public VRegex(String regex, VErrorMessageProducer<String> err) {
		this(Pattern.compile(regex), err);
	}


	@Override
	public VResult<String> validate(String in) {
		return (regex.matcher(in).matches())
				? success(in)
				: failure(in);
	}
}
