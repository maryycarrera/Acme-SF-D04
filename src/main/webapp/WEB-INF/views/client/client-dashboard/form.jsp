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
	<acme:message code="client.client-dashboard.form.title.operations-progress-logs"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.progress-logs-completeness-below-25"/>
		</th>
		<td>
			<acme:print value="${progressLogsCompletenessBelow25}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.progress-logs-completeness-between-25-and-50"/>
		</th>
		<td>
			<acme:print value="${progressLogsCompletenessBetween25And50}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.progress-logs-completeness-between-50-and-75"/>
		</th>
		<td>
			<acme:print value="${progressLogsCompletenessBetween50And75}"/>
		</td>
	</tr>	
		<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.progress-logs-completeness-above-75"/>
		</th>
		<td>
			<acme:print value="${progressLogsCompletenessAbove75}"/>
		</td>
	</tr>	
</table>

<h2>
	<acme:message code="client.client-dashboard.form.title.budget-contracts"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.average-budget-of-contracts"/>
		</th>
		<td>
			<acme:print value="${averageBudgetOfContracts}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.deviation-budget-of-contracts"/>
		</th>
		<td>
			<acme:print value="${deviationBudgetOfContracts}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.minimum-budget-of-contracts"/>
		</th>
		<td>
			<acme:print value="${minimumBudgetOfContracts}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.maximum-budget-of-contracts"/>
		</th>
		<td>
			<acme:print value="${maximumBudgetOfContracts}"/>
		</td>
	</tr>		
</table>


<acme:return/>

