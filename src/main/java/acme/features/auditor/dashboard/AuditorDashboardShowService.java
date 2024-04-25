
package acme.features.auditor.dashboard;

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
		Double averageNumberOfAuditRecords;
		Double deviationNumberOfAuditRecords;
		Integer minumumNumberOfAuditRecords;
		Integer maximumNumberOfAuditRecords;
		Double averagePeriodLengthOfAuditRecords;
		Double deviationPeriodLengthOfAuditRecords;
		Double minumumPeriodLengthOfAuditRecords;
		Double maximumPeriodLengthOfAuditRecords;

		totalStaticCodeAudits = this.repository.totalCodeAuditsForType(auditorId, CodeType.STATIC);
		totalDynamicCodeAudits = this.repository.totalCodeAuditsForType(auditorId, CodeType.DYNAMIC);
		averageNumberOfAuditRecords = this.repository.averageNumberOfAuditRecords(auditorId);
		deviationNumberOfAuditRecords = this.repository.deviationNumberOfAuditRecords(auditorId);
		minumumNumberOfAuditRecords = this.repository.minumumNumberOfAuditRecords(auditorId);
		maximumNumberOfAuditRecords = this.repository.maximumNumberOfAuditRecords(auditorId);
		averagePeriodLengthOfAuditRecords = this.repository.averagePeriodLengthOfAuditRecords(auditorId);
		deviationPeriodLengthOfAuditRecords = this.repository.deviationPeriodLengthOfAuditRecords(auditorId);
		minumumPeriodLengthOfAuditRecords = this.repository.minumumPeriodLengthOfAuditRecords(auditorId);
		maximumPeriodLengthOfAuditRecords = this.repository.maximumPeriodLengthOfAuditRecords(auditorId);

		dashboard = new AuditorDashboard();
		dashboard.setTotalStaticCodeAudits(totalStaticCodeAudits);
		dashboard.setTotalDynamicCodeAudits(totalDynamicCodeAudits);
		dashboard.setAverageNumberOfAuditRecords(averageNumberOfAuditRecords);
		dashboard.setDeviationNumberOfAuditRecords(deviationNumberOfAuditRecords);
		dashboard.setMinumumNumberOfAuditRecords(minumumNumberOfAuditRecords);
		dashboard.setMaximumNumberOfAuditRecords(maximumNumberOfAuditRecords);
		dashboard.setAveragePeriodLengthOfAuditRecords(averagePeriodLengthOfAuditRecords / 3600);
		dashboard.setDeviationPeriodLengthOfAuditRecords(deviationPeriodLengthOfAuditRecords / 3600);
		dashboard.setMaximumPeriodLengthOfAuditRecords(maximumPeriodLengthOfAuditRecords / 3600);
		dashboard.setMinumumPeriodLengthOfAuditRecords(minumumPeriodLengthOfAuditRecords / 3600);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "totalStaticCodeAudits", "totalDynamicCodeAudits", "averageNumberOfAuditRecords", "deviationNumberOfAuditRecords", "minumumNumberOfAuditRecords", "maximumNumberOfAuditRecords", "averagePeriodLengthOfAuditRecords",
			"deviationPeriodLengthOfAuditRecords", "maximumPeriodLengthOfAuditRecords", "minumumPeriodLengthOfAuditRecords");

		super.getResponse().addData(dataset);
	}
}
