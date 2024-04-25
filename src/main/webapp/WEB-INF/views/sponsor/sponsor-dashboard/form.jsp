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
	<acme:message code="sponsor.sponsor-dashboard.form.title.operations"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-dashboard.form.label.invoices-tax-below-equal-21"/>
		</th>
		<td>
			<acme:print value="${numberOfInvoicesTaxLessOrEqual21}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-dashboard.form.label.sponsorships-with-link"/>
		</th>
		<td>
			<acme:print value="${numberOfSponsorshipsWithLink}"/>
		</td>
	</tr>	
</table>
<jstl:forEach var="currency" items="${supportedCurrencies}">
    <h2>
        <acme:message code="sponsor.sponsor-dashboard.form.title.amount-sponsorships"/>
        <acme:message code="${currency}"/>
    </h2>

    <table class="table table-sm">
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.amount-average"/>
            </th>
            <td>
                <acme:print value="${averageAmountSponsorships[currency]}"/>
            </td>
        </tr>
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.amount-deviation"/>
            </th>
            <td>
                <acme:print value="${deviationAmountSponsorships[currency]}"/>
            </td>
        </tr>
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.amount-minimum"/>
            </th>
            <td>
                <acme:print value="${minimumAmountSponsorships[currency]}"/>
            </td>
        </tr>   
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.amount-maximum"/>
            </th>
            <td>
                <acme:print value="${maximumAmountSponsorships[currency]}"/>
            </td>
        </tr>
    </table>
</jstl:forEach>
<jstl:forEach var="currency" items="${supportedCurrencies}">
    <h2>
        <acme:message code="sponsor.sponsor-dashboard.form.title.quantity-invoices"/>
        <acme:message code="${currency}"/>
    </h2>

    <table class="table table-sm">
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.quantity-average"/>
            </th>
            <td>
                <acme:print value="${averageQuantityInvoices[currency]}"/>
            </td>
        </tr>
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.quantity-deviation"/>
            </th>
            <td>
                <acme:print value="${deviationQuantityInvoices[currency]}"/>
            </td>
        </tr>
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.quantity-minimum"/>
            </th>
            <td>
                <acme:print value="${minimumQuantityInvoices[currency]}"/>
            </td>
        </tr>   
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.quantity-maximum"/>
            </th>
            <td>
                <acme:print value="${maximumQuantityInvoices[currency]}"/>
            </td>
        </tr>
    </table>
</jstl:forEach>