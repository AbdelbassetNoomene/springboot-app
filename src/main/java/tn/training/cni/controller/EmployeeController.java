package tn.training.cni.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import tn.training.cni.model.Employee;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	private List<Employee> empList = Arrays.asList(
			new Employee(1, "Sandeep", "Data Matrix", "Front-end Developer", 20000),
			new Employee(2, "Prince", "Genpact", "Consultant", 40000),
			new Employee(3, "Gaurav", "Silver Touch ", "Sr. Java Engineer", 47000),
			new Employee(4, "Andre", "Genpact", "Consultant", 28000),
			new Employee(5, "Steve", "Silver Touch ", "Sr. Java Engineer", 32000),
			new Employee(6, "Abhinav", "Akal Info Sys", "CTO", 52000));
	
	
	
	
	@GetMapping(value = "", produces = MediaType.APPLICATION_PDF_VALUE)
	  public ResponseEntity<InputStreamResource> report() {
		HttpHeaders headers = new HttpHeaders();
		ByteArrayInputStream bis = generatePDFReport();
				headers.add("Content-Disposition", "inline; filename=Emp. Report.pdf");
		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	  }
	  
	
	public ByteArrayInputStream generatePDFReport() {
		try {

			File resource = new ClassPathResource("employee-rpt.jrxml").getFile();

			// Compile the Jasper report from .jrxml to .japser
			JasperReport jasperReport = JasperCompileManager.compileReport( resource.getPath());

			// Get your data source
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(empList);

			// Add parameters
			Map<String, Object> parameters = new HashMap();

			parameters.put("createdBy", "cni.tni");

			// Fill the report
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					jrBeanCollectionDataSource);
			
//			JRPdfExporter exporter = new JRPdfExporter();
//			 
//			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//			exporter.setExporterOutput(
//			  new SimpleOutputStreamExporterOutput("employeeReport.pdf"));
//			 
//			SimplePdfReportConfiguration reportConfig
//			  = new SimplePdfReportConfiguration();
//			reportConfig.setSizePageToContent(true);
//			reportConfig.setForceLineBreakPolicy(false);
//			 
//			SimplePdfExporterConfiguration exportConfig
//			  = new SimplePdfExporterConfiguration();
//			exportConfig.setMetadataAuthor("baeldung");
//			exportConfig.setEncrypted(true);
//			exportConfig.setAllowedPermissionsHint("PRINTING");
//			 
//			exporter.setConfiguration(reportConfig);
//			exporter.setConfiguration(exportConfig);
//			 
//			exporter.exportReport();

//			// Export the report to a PDF file
			return new ByteArrayInputStream(JasperExportManager.exportReportToPdf(jasperPrint));

			

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
