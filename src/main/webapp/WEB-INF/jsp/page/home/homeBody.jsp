<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div>
	<h2>Welcome to the OddJob JavaScript framework</h2>

	<p>
		Oddjob provides a basis for writing classes in JavaScript that mimic classical inheritance.
	</p>

	<p>
		The OddJob framework was inspired by the inheritance functionality in Dean Edward's
		<a href="https://code.google.com/p/base2" target="_blank">base2</a> framework (here are the
		<a href="http://base2.googlecode.com/svn/version/1.0.2/doc/base2.html#/doc/!base2.Base" target="_blank">docs</a>
		) and John Resig's blog on
		<a href="http://ejohn.org/blog/simple-javascript-inheritance" target="_blank">Simple JavaScript Inheritance</a>.
		It also sprung from some front end work I did with
		<a href="http://www.briandillard.com" target="_blank">Brian Dillard</a> and
		<a href="http://www.linkedin.com/in/kemapak" target="_blank">Kem Apak</a>.
	</p>

	<p>
		The core ObbJob library is very lightweight and contains the ability to create classes and class hierarchies,
		define namespaces for package like structures and a minimal set of utility methods.
	</p>

	<div class="componentsList">
		<p>Optional components can be included in custom <a href="<c:url value="/builder" />">builds</a>:</p>
		<ul>
			<li>Ajax: A package for performing asynchronous and synchronous HTTP requests</li>
			<li>Logger: A wrapper around 3rd party logger frameworks to provide a common logging API</li>
			<li>jQuery Utils: Utility methods for use in a jQuery based system if the jQuery library is included</li>
		</ul>
	</div>
</div>
