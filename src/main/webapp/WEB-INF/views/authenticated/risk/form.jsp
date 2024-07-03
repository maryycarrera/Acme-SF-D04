<%--
- form.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for authenticated particular
- purposes.  The copyright owner does not offer authenticated warranties or representations, nor do
- they accept authenticated liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.risk.form.label.code" path="code"/>
	<acme:input-moment code="authenticated.risk.form.label.identificationDate" path="identificationDate"/>
	<acme:input-double code="authenticated.risk.form.label.value" path="value"/>
	<acme:input-textarea code="authenticated.risk.form.label.description" path="description"/>
	<acme:input-url code="authenticated.risk.form.label.link" path="link"/>
</acme:form>