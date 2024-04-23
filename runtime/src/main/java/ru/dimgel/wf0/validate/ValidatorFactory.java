package ru.dimgel.wf0.validate;

import java.util.regex.Pattern;


/**
 * <p>For those who does not want to write {@code new} everywhere:</p>
 * {@snippet :
 *  import ru.dimgel.wf0.validate.*;
 *  new VOne().pipe(new VRequired().pipe(new VInt().pipe(new VRange<>(1, 100))))
 * }
 * <p>Usage:</p>
 * {@snippet :
 *  import static ru.dimgel.wf0.validate.ValidatorFactory.*;
 *  vOne().pipe(vRequired().pipe(vInt().pipe(vRange(1, 100))))
 * }
 */
public final class ValidatorFactory {

	// Instance construction is not allowed.
	private ValidatorFactory() {}

	// In alphabetic order:

	public static VInt vInt() { return new VInt(); }
	public static VInt vInt(VErrorMessageProducer<String> err) { return new VInt(err); }

	public static VLength vLength(int min, int max) { return new VLength(min, max); }
	public static VLength vLength(int min, int max, VErrorMessageProducer<String> err) { return new VLength(min, max, err); }

	public static VOne vOne() { return new VOne(); }
	public static VOne vOne(VErrorMessageProducer<String[]> err) { return new VOne(err); }

	public static VOptional vOptional() { return new VOptional(); }

	public static <T extends Comparable<T>> VRange<T> vRange(T min, T max) { return new VRange<>(min, max); }
	public static <T extends Comparable<T>> VRange<T> vRange(T min, T max, VErrorMessageProducer<T> err) { return new VRange<>(min, max, err); }

	public static VRegex vRegex(Pattern regex) { return new VRegex(regex); }
	public static VRegex vRegex(String regex) { return new VRegex(regex); }
	public static VRegex vRegex(Pattern regex, VErrorMessageProducer<String> err) { return new VRegex(regex, err); }
	public static VRegex vRegex(String regex, VErrorMessageProducer<String> err) { return new VRegex(regex, err); }

	public static VRequired vRequired() { return new VRequired(); }
	public static VRequired vRequired(VErrorMessageProducer<String> err) { return new VRequired(err); }

	public static VTrim vTrim() { return new VTrim(); }
}
