<%--
- form.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<jstl:choose>
		<jstl:when test="${_command == 'show'}">
			<acme:input-moment code="any.notice.form.label.instantiationMoment" path="instantiationMoment"/>
			<acme:input-textbox code="any.notice.form.label.title" path="title"/>
			<acme:input-textbox code="any.notice.form.label.author" path="author"/>
			<acme:input-textbox code="any.notice.form.label.message" path="message"/>
			<acme:input-email code="any.notice.form.label.email" path="email"/>
			<acme:input-url code="any.notice.form.label.link" path="link"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:input-textbox code="any.notice.form.label.title" path="title"/>
			<acme:input-textbox code="any.notice.form.label.author" path="author"/>
			<acme:input-textbox code="any.notice.form.label.message" path="message"/>
			<acme:input-email code="any.notice.form.label.email" path="email"/>
			<acme:input-url code="any.notice.form.label.link" path="link"/>
			<acme:input-checkbox code="any.notice.form.label.accept" path="accept"/>
			<acme:submit test="${_command == 'create'}" code="any.notice.form.button.create" action="/any/notice/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
