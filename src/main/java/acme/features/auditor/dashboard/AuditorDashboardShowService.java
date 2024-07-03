
package acme.features.auditor.dashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeaudits.CodeType;
import acme.forms.AuditorDashboard;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int auditorId = super.getRequest().getPrincipal().getActiveRoleId();

		AuditorDashboard dashboard;
		int totalStaticCodeAudits;
		int totalDynamicCodeAudits;
		int totalAuditRecords;
		Double averageNumberOfAuditRecords;
		Double deviationNumberOfAuditRecords;
		Integer minumumNumberOfAuditRecords;
		Integer maximumNumberOfAuditRecords;
		Double averagePeriodLengthOfAuditRecords;
		Double deviationPeriodLengthOfAuditRecords;
		Double minumumPeriodLengthOfAuditRecords;
		Double maximumPeriodLengthOfAuditRecords;
		Collection<Double> auditRecordsForCodeAudit;

		totalAuditRecords = this.repository.totalAuditRecordsForAuditor(auditorId);
		totalStaticCodeAudits = this.repository.totalCodeAuditsForType(auditorId, CodeType.STATIC);
		totalDynamicCodeAudits = this.repository.totalCodeAuditsForType(auditorId, CodeType.DYNAMIC);
		averageNumberOfAuditRecords = this.repository.averageNumberOfAuditRecords(auditorId);
		auditRecordsForCodeAudit = this.repository.auditRecordsForCodeAudit(auditorId);
		deviationNumberOfAuditRecords = this.deviation(auditRecordsForCodeAudit);
		minumumNumberOfAuditRecords = this.repository.minumumNumberOfAuditRecords(auditorId);
		maximumNumberOfAuditRecords = this.repository.maximumNumberOfAuditRecords(auditorId);
		averagePeriodLengthOfAuditRecords = this.repository.averagePeriodLengthOfAuditRecords(auditorId);
		deviationPeriodLengthOfAuditRecords = this.repository.deviationPeriodLengthOfAuditRecords(auditorId);
		minumumPeriodLengthOfAuditRecords = this.repository.minumumPeriodLengthOfAuditRecords(auditorId);
		maximumPeriodLengthOfAuditRecords = this.repository.maximumPeriodLengthOfAuditRecords(auditorId);

		dashboard = new AuditorDashboard();

		dashboard.setTotalStaticCodeAudits(totalStaticCodeAudits);
		dashboard.setTotalDynamicCodeAudits(totalDynamicCodeAudits);
		if (totalAuditRecords == 0) {
			dashboard.setMinumumNumberOfAuditRecords(null);
			dashboard.setMaximumNumberOfAuditRecords(null);
			dashboard.setAverageNumberOfAuditRecords(null);
			dashboard.setDeviationNumberOfAuditRecords(null);
			dashboard.setAveragePeriodLengthOfAuditRecords(null);
			dashboard.setDeviationPeriodLengthOfAuditRecords(null);
			dashboard.setMaximumPeriodLengthOfAuditRecords(null);
			dashboard.setMinumumPeriodLengthOfAuditRecords(null);

		} else {
			dashboard.setAverageNumberOfAuditRecords(averageNumberOfAuditRecords);
			dashboard.setDeviationNumberOfAuditRecords(deviationNumberOfAuditRecords);
			dashboard.setMinumumNumberOfAuditRecords(minumumNumberOfAuditRecords);
			dashboard.setMaximumNumberOfAuditRecords(maximumNumberOfAuditRecords);
			dashboard.setAveragePeriodLengthOfAuditRecords(averagePeriodLengthOfAuditRecords / 3600);
			dashboard.setDeviationPeriodLengthOfAuditRecords(deviationPeriodLengthOfAuditRecords / 3600);
			dashboard.setMaximumPeriodLengthOfAuditRecords(maximumPeriodLengthOfAuditRecords / 3600);
			dashboard.setMinumumPeriodLengthOfAuditRecords(minumumPeriodLengthOfAuditRecords / 3600);
		}
		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "totalStaticCodeAudits", "totalDynamicCodeAudits", "averageNumberOfAuditRecords", "deviationNumberOfAuditRecords", "minumumNumberOfAuditRecords", "maximumNumberOfAuditRecords", "averagePeriodLengthOfAuditRecords",
			"deviationPeriodLengthOfAuditRecords", "maximumPeriodLengthOfAuditRecords", "minumumPeriodLengthOfAuditRecords");

		super.getResponse().addData(dataset);
	}

	public Double deviation(final Collection<Double> auditRecordsNumber) {
		Double res;
		Double aux;
		res = 0.0;
		if (auditRecordsNumber.isEmpty())
			return null;

		if (!auditRecordsNumber.isEmpty()) {
			double sum = 0.0;
			for (final Double value : auditRecordsNumber)
				sum += value;
			double average = sum / auditRecordsNumber.size();

			aux = 0.0;
			for (final Double number : auditRecordsNumber)
				aux += Math.pow(number - average, 2);
			res = Math.sqrt(aux / auditRecordsNumber.size());
		}
		return res;
	}

}
