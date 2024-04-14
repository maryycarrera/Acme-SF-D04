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

<h2>
	<acme:message code="sponsor.sponsor-dashboard.form.title.amount-sponsorships"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-dashboard.form.label.average-amount-of-sponsorship"/>
		</th>
		<td>
			<acme:print value="${averageAmountSponsorships}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-dashboard.form.label.deviation-amount-of-sponsorship"/>
		</th>
		<td>
			<acme:print value="${deviationAmountSponsorships}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-dashboard.form.label.minimum-amount-of-sponsorship"/>
		</th>
		<td>
			<acme:print value="${minimumAmountSponsorships}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-dashboard.form.label.maximum-amount-of-sponsorship"/>
		</th>
		<td>
			<acme:print value="${maximumAmountSponsorships}"/>
		</td>
	</tr>		
</table>
<h2>
	<acme:message code="sponsor.sponsor-dashboard.form.title.quantity-invoice"/>
</h2>
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-dashboard.form.label.average-quantity-of-invoice"/>
		</th>
		<td>
			<acme:print value="${averageQuantityInvoices}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-dashboard.form.label.deviation-quantity-of-invoice"/>
		</th>
		<td>
			<acme:print value="${deviationQuantityInvoices}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-dashboard.form.label.minimum-quantity-of-invoice"/>
		</th>
		<td>
			<acme:print value="${minimumQuantityInvoices}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-dashboard.form.label.maximum-quantity-of-invoice"/>
		</th>
		<td>
			<acme:print value="${maximumQuantityInvoices }"/>
		</td>
	</tr>		
</table>


<acme:return/>