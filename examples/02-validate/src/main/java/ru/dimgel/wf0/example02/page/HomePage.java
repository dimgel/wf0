package ru.dimgel.wf0.example02.page;

import ru.dimgel.wf0.dispatch.Page;
import ru.dimgel.wf0.validate.VResult;
import ru.dimgel.wf0.validate.Validator;
import static ru.dimgel.wf0.validate.ValidatorFactory.*;
import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;


public class HomePage extends Page {

	// Validators are stateless and hence thread-safe, so let's pre-construct them for speed.
	// Since for each HTTP request, new Page instance is created, we pre-construct validators as STATIC members.
	// Java does not allow "var" here, but it's even better: you see final type of whole validator pipes.
	//
	// Logic:
	// - ServletRequest.getParameterValues(String name) returns String[];
	// - vOne checks that String[].length == 1 and converts String[] to String;
	// - vTrim trims String if it's not null;
	// - vRequired & vOptional stop if String is null or empty (vRequired with error, vOptional with success);
	// - vInt converts String to Integer;
	// - vLength checks String.length() against range;
	// - vRange checks Integer value against range.
	//
	private static final Validator<String[], String> valName = vOne().pipe(vTrim().pipe(vRequired().pipe(vLength(1, 10))));
	private static final Validator<String[], Integer> valAge = vOne().pipe(vTrim().pipe(vOptional().pipe(vInt().pipe(vRange(1, 100)))));


	@Override
	protected void service() throws Exception {

		// For simplicity, I will submit form using GET method.
//		boolean isSubmitted = request.getMethod().equals("POST");
		boolean isSubmitted = !request.getParameterMap().isEmpty();

		String[] ssName = null;
		String[] ssAge = null;
		VResult<String> rName = null;
		VResult<Integer> rAge = null;   // <---- Notice <Integer>.

		if (isSubmitted) {
			// Whichever values user submits, valid or not, we must render those values unchanged.
			// (I could render trimmed values, but I don't see how to elegantly avoid code duplication with vOne + vTrim.)
			ssName = request.getParameterValues("name");
			ssAge = request.getParameterValues("age");

			rName = valName.validate(ssName);
			rAge = valAge.validate(ssAge);

			// In this example I don't use validation result which you can get via VResult<>.out() if validation succeeded:
			if (rAge.ok()) {
				// Since valAge contains vOptional(), result may be null:
				@SuppressWarnings("unused")
				int age = rAge.out() == null ? 0 : rAge.out();
			}
		}

		response.setContentType("text/html; charset=UTF-8");
		try (var w = response.getWriter()) {
			w.format("""
					<html>
					<head>
					  <title>wf0-example02-validate</title>
					  <style>
					    body { padding: 1.5em }
					    th,td { vertical-align: top; padding: 0.15em 0.3em }
					    th { text-align: right }
					    th div { font-size: small; color: grey }
					    th:nth-child(2) { color: red }
					    td div { margin-bottom: 0.2em }
					    .ok { color: blue }
					    .err { color: red }
					  </style>
					</head>
					<body><form><table>
					  <tr><th>Name<div>1-10 chars</div></th><th>*</th><td>%s<input name="name" value="%s"/></td></tr>
					  <tr><th>Age<div>1-100</div></th><th></th><td>%s<input name="age" value="%s"/></td></tr>
					  <tr><th/><th/><td><button type="submit">Submit</button></td></tr>
					</table></form></body>
					</html>""",
					!isSubmitted ? "" : (rName.ok() ? "<div class='ok'>OK</div>" : "<div class='err'>" + escapeHtml4(rName.errorMessage()) + "</div>"),
					ssName != null ? escapeHtml4(ssName[0]) : "",
					!isSubmitted ? "" : (rAge.ok() ? "<div class='ok'>OK</div>" : "<div class='err'>" + escapeHtml4(rAge.errorMessage()) + "</div>"),
					ssAge != null ? escapeHtml4(ssAge[0]) : ""
			);
		}
	}
}
