# dimgel's wf0: zero web framework

Work in progress.

Actually, after 4 years in C++, here I was just recalling my Java reflexes by collecting and combing some old ideas.


## Design principles

1. Be as close as possible to basic technology: no stateful web frameworks over stateless HTTP, no heavy ORMs over SQL databases, no pure functional programming over imperative I/O, etc. All these "no-things" are "paradigm mismatch bridges" -- a wide subclass of "leaky abstractions".

2. Remove magic: e.g. use a little more verbose but straightforward `void f() { inTransaction(_ -> {...}); }` instead of magically processed `@Transactional void f() {...}`, etc.

3. Do as much as possible at buildtime: no runtime-processed annotations, no runtime-generated proxies, etc. E.g. if you DO need annotations, make sure they are processed at buildtime -- e.g. like lombok does.

4. Particularly, static strict typization is ultimately good -- because it's checked at buildtime (and provides for efficient runtime, too).


## How to learn

For each component listed below:

1. Read short annotation below;
2. Study example;
3. (Optionally) study sources.


## Components

### Dispatch

[Example](examples/01-dispatch/) | [Sources](runtime/src/main/java/ru/dimgel/wf0/dispatch/)

This one should be learned first, because it's the simplest one and is used in all examples.

For many years, every now and then I tried to create a good web framework. In the end I LOL'd, realizing that each attempt brought me closer and closer to just mimicking Servlet API -- which turned out to be pretty perfect by itself, except a couple of things that I "fixed":

1. `<servlet>` and `<servlet-mapping>` in `web.xml` are too verbose and ugly for intensive use. So I introduced classes `Page` and `Dispatcher`. You inherit your request handlers from `Page` instead of `Servlet`, and inherit from `Dispatcher` to map request URIs to your `Page` instances with Java code instead of `web.xml`. No annotated POJOs (i.e. no magic), all extension points are explicitly exposed as virtual methods. E.g. in Spring, your entry points are annotated methods, and there can be multiple entry points per class; in fw0, each entry point is separate `Page` subclass overriding pure virtual `Page.service()`.

2. `Page` instances are stateful and temporary, a new instance is created by `Dispatcher` for each HTTP request. To my taste, ability to pass state around in instance vars instead of method parameters is a convenience. The very first such instance vars are `request` and `response`; users can add more, e.g. by declaring common superclass `MyPage extends Page { more vars }` for all their pages.

So this component is pretty tiny and follows "be close to basic technology" principle, where "basic technology" is Servlet API.

Hence the name: "zero web framework".


### Validate

[Example](examples/02-validate/) | [Sources](runtime/src/main/java/ru/dimgel/wf0/validate/)

Initially this was written in Scala and looked pretty nice with user-defined operators: `VRequired & VInt & VRange(1,100)`.

In Java it looks uglier: `vRequired().pipe(vInt().pipe(vRange(1, 100)))`, but still it's simple, no-magic, stateless. Here `vXXX()` are static factory methods, classes are named `VXXX`.

I'd love to say it's also strictly typed, but in Java there's a problem. As you may have guessed, some validators convert data. For example, `VInt` takes `String` and parses it to `Integer`. If parse fails, validator returns error, otherwise it passes that integer on to the next validator in pipe -- `VRange` which takes `Comparable<T>`. So, when you pipe 2 validators together `A.pipe(B)`, `B`'s input type **must be supertype** of `A`'s output type. But unfortunately, unlike Scala, Java does NOT allow `super` keyword in type parameter list -- because someone decided no one would need it. :-/ So, until I implement this check in my maven plugin, be careful or get runtime errors. Sorry. :(


### Compiled JSP

NOT implemented yet.

I was shocked to learn that not only JSP parameters are dynamically typed, but even JSPs themselves are compiled in runtime. Oxymoron. This component compiles JSP at buildtime into some function taking strictly typed parameter object.

Of course, you'll have to specify parameter object's type in JSP header, so my syntax is NOT 100% compatible with JSP. My goal is to be as close to JSP as possible, so IDE wouldn't complain at anything except that single header line specifying parameter type.