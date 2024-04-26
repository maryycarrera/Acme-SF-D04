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
			<acme:message code="client.dashboard.form.label.progress-log-less-than-25"/>
		</th>
		<td>
			<acme:print value="${progressLogsCompletenessBelow25}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.dashboard.form.label.progress-log-between-25-50"/>
		</th>
		<td>
			<acme:print value="${progressLogsCompletenessBetween25And50}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.dashboard.form.label.progress-log-between-50-75"/>
		</th>
		<td>
			<acme:print value="${progressLogsCompletenessBetween50And75}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.dashboard.form.label.progress-log-above-75"/>
		</th>
		<td>
			<acme:print value="${progressLogsCompletenessAbove75}"/>
		</td>
	</tr>
	</table>

<jstl:forEach var="currency" items="${supportedCurrencies}">
    <h2>
        <acme:message code="client.client-dashboard.form.title.budget-contracts"/>
        <acme:message code="${currency}"/>
    </h2>

    <table class="table table-sm">
        <tr>
            <th scope="row">
                <acme:message code="client.dashboard.form.label.contract-average"/>
            </th>
            <td>
                <acme:print value="${averageBudgetOfContracts[currency]}"/>
            </td>
        </tr>
        <tr>
            <th scope="row">
                <acme:message code="client.dashboard.form.label.contract-deviation"/>
            </th>
            <td>
                <acme:print value="${deviationBudgetOfContracts[currency]}"/>
            </td>
        </tr>
        <tr>
            <th scope="row">
                <acme:message code="client.dashboard.form.label.contract-minimum"/>
            </th>
            <td>
                <acme:print value="${minimumBudgetOfContracts[currency]}"/>
            </td>
        </tr>   
        <tr>
            <th scope="row">
                <acme:message code="client.dashboard.form.label.contract-maximum"/>
            </th>
            <td>
                <acme:print value="${maximumBudgetOfContracts[currency]}"/>
            </td>
        </tr>
    </table>
</jstl:forEach>