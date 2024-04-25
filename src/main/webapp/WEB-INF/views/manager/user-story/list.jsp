
<%--
- list.jsp
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

<acme:list>
	<acme:list-column code="manager.user-story.list.label.title" path="title" width="17%"/>
	<acme:list-column code="manager.user-story.list.label.estimatedCost" path="estimatedCost" width="17%"/>
	<acme:list-column code="manager.user-story.list.label.priority" path="priority" width="16%"/>
	<acme:list-column code="manager.user-story.list.label.draftMode" path="draftMode" width="16%"/>
</acme:list>

<jstl:if test="${showCreate}">
	<acme:button code="manager.user-story.list.button.create" action="/manager/user-story/create"/>
</jstl:if>