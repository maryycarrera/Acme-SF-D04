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

<h2>
	<acme:message code="auditor.auditor-dashboard.form.title.operations-code-audits"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.total-static-code-audits"/>
		</th>
		<td>
			<acme:print value="${totalStaticCodeAudits}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.total-dynamic-code-audits"/>
		</th>
		<td>
			<acme:print value="${totalDynamicCodeAudits}"/>
		</td>
	</tr>
	
</table>

<h2>
	<acme:message code="auditor.auditor-dashboard.form.title.operations-audit-records"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.average-number-of-audit-records"/>
		</th>
		<td>
			<acme:print value="${averageNumberOfAuditRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.deviation-number-of-audit-records"/>
		</th>
		<td>
			<acme:print value="${deviationNumberOfAuditRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.minumum-number-of-audit-records"/>
		</th>
		<td>
			<acme:print value="${minumumNumberOfAuditRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.maximum-number-of-audit-records"/>
		</th>
		<td>
			<acme:print value="${maximumNumberOfAuditRecords}"/>
		</td>
	</tr>	
		<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.average-period-length-of-audit-records"/>
		</th>
		<td>
			<acme:print value="${averagePeriodLengthOfAuditRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.deviation-period-length-of-audit-records"/>
		</th>
		<td>
			<acme:print value="${deviationPeriodLengthOfAuditRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.minimum-period-length-of-audit-records"/>
		</th>
		<td>
			<acme:print value="${minumumPeriodLengthOfAuditRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.maximum-period-length-of-audit-records"/>
		</th>
		<td>
			<acme:print value="${maximumPeriodLengthOfAuditRecords}"/>
		</td>
	</tr>		
</table>


<acme:return/>