<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div>
    <h2>Documentation for the OddJob JavaScript framework</h2>

    <p>The JS Docs can be found <a href="<c:url value="/jsdoc/index.html" />" target="_blank">here</a>.</p>

    <p>
        All of the functionality of the OddJob framework can be accessed via the <code>oj</code> object.  The
        <code>oj</code> object is added as a property to the context in which the OddJob library file is executed when
        the page loads (most likely the top level window object unless it is run in an iframe).
    </p>

    <p>
        The <code>oj</code> object has the single method <code>namespace</code> that creates object hierarchies in an
        idempotent fashion. 
    </p>
</div>
