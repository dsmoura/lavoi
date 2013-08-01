package br.ufrgs.inf.dsmoura.repository.model.loadData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.ufrgs.inf.dsmoura.repository.controller.SystemPropertyEnum;
import br.ufrgs.inf.dsmoura.repository.model.dao.GenericDAO;
import br.ufrgs.inf.dsmoura.repository.model.dao.TypesDAO;
import br.ufrgs.inf.dsmoura.repository.model.entity.ApplicationDomain;
import br.ufrgs.inf.dsmoura.repository.model.entity.ApplicationSubdomain;
import br.ufrgs.inf.dsmoura.repository.model.entity.ArtifactDependencyTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.AssetStateType;
import br.ufrgs.inf.dsmoura.repository.model.entity.AssetType;
import br.ufrgs.inf.dsmoura.repository.model.entity.DesignPatternDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.FunctionalRequirementTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.InternationalizationTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.NonFunctionalRequirementTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.OperationalSystemTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.OrganizationDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.ProgrammingLanguageDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.ProjectDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.RelatedAssetTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.SoftwareLicenseDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.SourceCodeTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.SystemPropertyDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.TestMethodTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.TestTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserInterfaceTypeDTO;



public class LoadLists {
	
	final static Log logger = LogFactory.getLog(LoadLists.class);
	
	public static void loadAllLists() {
		loadSystemProperties();
		loadAssetTypes();
		loadAssetStateTypes();
		loadSourceCodeTypes();
		loadUserInterfaceTypes();
		loadFunctionalRequirementTypes();
		loadNonFunctionalRequirementTypes();
		loadInternationalizationTypes();
		loadOperationalSystemType();
		loadTestType();
		loadTestMethodType();
		loadRelatedAssetTypes();
		loadArtifactDependencyTypes();
		loadDesignPatterns();
		loadProgrammingLanguages();
	}
	
	private static void loadSystemProperties() {
		LinkedHashMap<String, String> values = new LinkedHashMap<String, String>();
		values.put(SystemPropertyEnum.FEEDBACK_EMAIL.getKey(), "lavoi@mydomain.com");
		
		values.put(SystemPropertyEnum.REPOSITORY_SUBTITLE.getKey(), "Lavoi Reuse Repository");
		
		values.put(SystemPropertyEnum.REPOSITORY_URL.getKey(), "http://localhost:8080/lavoi");
		values.put(SystemPropertyEnum.HOME_PAGE_URL.getKey(), "https://github.com/dsmoura/lavoi");
		
		values.put(SystemPropertyEnum.SMTP_HOSTNAME.getKey(), "smtp.mydomain.com");
		values.put(SystemPropertyEnum.SMTP_SSL.getKey(), "true");
		
		values.put(SystemPropertyEnum.EMAIL_DOMAIN_RESTRICTION.getKey(), "@mydomain.com");
		
		values.put(SystemPropertyEnum.REPOSITORY_EMAIL.getKey(),	"lavoi@mydomain.com");
		values.put(SystemPropertyEnum.REPOSITORY_EMAIL_PASSWORD.getKey(),	"password");
		values.put(SystemPropertyEnum.REPOSITORY_EMAIL_USER.getKey(),	"lavoi@mydomain.com");
		values.put(SystemPropertyEnum.REPOSITORY_EMAIL_PASSWORD.getKey(),	"password");
		
		values.put(SystemPropertyEnum.SOLR_SERVER_URL.getKey(),	"http://localhost:8080/solr/");
		values.put(SystemPropertyEnum.USER_AUTHENTICATION_MODE.getKey(),	"DATABASE");
		values.put(SystemPropertyEnum.USER_AUTHENTICATION_DOMAIN_PREFIX.getKey(),	"mydomain-");
		
		for (String key : values.keySet()) {
			SystemPropertyDTO systemPropertyDTO = new SystemPropertyDTO();
			systemPropertyDTO.setKey(key);
			systemPropertyDTO.setValue(values.get(key));
			GenericDAO.getInstance().insert(systemPropertyDTO);
			logger.info("Property inserted: " + systemPropertyDTO.getKey() + " -> " + systemPropertyDTO.getValue());
		}
	}
	
	private static void loadAssetTypes() {
		String[] names = { "Application",
									"Business Process Pattern",
									"Component",
									"Documentation",
									"Design Pattern",
									"Domain Pattern",
									"Framework",
									"General",
									"Interaction Pattern",
									"Service",
									"Source Code",
									"System Architecture",
									"Use Case Pattern",
									"Web Service",
									"Other"
								};
		for (String n : names) {
			AssetType a = new AssetType();
			a.setName(n);
			GenericDAO.getInstance().insert(a);
			logger.info("AssetType inserted: " + n);
		}
	}
	
	private static void loadAssetStateTypes() {
		String[] names = { "Certified",
      							"Ready for Reuse",
      							"In Review",
      							"In Definition",
      							"Discontinued" };
		for (String n : names) {
			AssetStateType a = new AssetStateType();
			a.setName(n);
			GenericDAO.getInstance().insert(a);
			logger.info("AssetStateType inserted: " + n);
		}
	}
	
	@SuppressWarnings("unused")
	private static void loadOrganizationsAndProjects() {
		TreeMap<String, ArrayList<String>> organizations = new TreeMap<String, ArrayList<String>>();
		
		ArrayList<String> projects = new ArrayList<String>();
		projects.add("Project 1");
		projects.add("Project 2");
		projects.add("Project 3");
		organizations.put("Organization 1", projects);
		
		projects = new ArrayList<String>();
		projects.add("Project A");
		projects.add("Project B");
		projects.add("Project C");
		organizations.put("Organization A", projects);
		
		projects = new ArrayList<String>();
		projects.add("Project X");
		projects.add("Project Y");
		projects.add("Project Z");
		organizations.put("Organization K", projects);
		
		for (String key : organizations.keySet()) {
			OrganizationDTO org = new OrganizationDTO();
			org.setName(key);
			org = (OrganizationDTO) GenericDAO.getInstance().insert(org);
			logger.info("OrganizationDTO inserted: " + key);
			
			ArrayList<String> subdomains = organizations.get(key);
			for (String s : subdomains) {
				ProjectDTO proj = new ProjectDTO();
				proj.setName(s);
				proj.setOrganizationDTO(org);
				GenericDAO.getInstance().insert(proj);
				logger.info("ProjectDTO inserted: " + s);
			}
		}
	}
	
	@SuppressWarnings("unused")
	private static void loadDomainAndSubdomain() {
//		TreeMap<String, ArrayList<String>> domains = readFileCcs98();
//		TreeMap<String, ArrayList<String>> domains = readFileDomains();
		TreeMap<String, ArrayList<String>> domains = getAllDomainsAndSubDomains();
		
		for (String key : domains.keySet()) {
			ApplicationDomain ad = new ApplicationDomain();
			ad.setName(key);
			ad = (ApplicationDomain) GenericDAO.getInstance().insert(ad);
			logger.info("ApplicationDomain inserted: " + key);
			
			ArrayList<String> subdomains = domains.get(key);
			Collections.sort(subdomains);
			for (String s : subdomains) {
				ApplicationSubdomain as = new ApplicationSubdomain();
				as.setName(s);
				as.setApplicationDomain(ad);
				GenericDAO.getInstance().insert(as);
				logger.info("ApplicationSubdomain inserted: " + s);
			}
		}
	}
	
	private static void loadDesignPatterns() {
		String[] names = { "Abstract Factory",
               				"Builder",
               				"Factory Method",
               				"Lazy Initialization",
               				"Multiton",
               				"Object Pool",
               				"Prototype",
               				"Resource Acquisition is Initialization",
               				"Singleton",
               				"Adapter",
               				"Bridge",
               				"Composite",
               				"Decorator",
               				"Façade",
               				"Front Controller",
               				"Flyweight",
               				"Proxy",
               				"Module",
               				"Chain of Responsibility",
               				"Command",
               				"Interpreter",
               				"Iterator",
               				"Mediator",
               				"Memento",
               				"Null Object",
               				"Observer",
               				"Servant",
               				"Specification",
               				"State",
               				"Strategy",
               				"Template method",
               				"Visitor",
               				"Active Object",
               				"Balking",
               				"Double-checked Locking",
               				"Lock",
               				"Monitor Object",
               				"Reactor",
               				"Read-write Lock",
               				"Scheduler",
               				"Thread Pool",
               				"Thread-specific Storage" };
		Arrays.sort(names);
		for (String n : names) {
			DesignPatternDTO d = new DesignPatternDTO();
			d.setName(n);
			GenericDAO.getInstance().insert(d);
			logger.info("DesignPatternDTO inserted: " + n);
		}
	}
	
	/** http://langpop.com/ */
	private static void loadProgrammingLanguages() {
		String[] names = { 
									"Actionscript",
									"Ada",
									"Ajax",
									"Android",
									"Assembly",
									"Bash",
									"C",
									"C++",
									"C#",
									"Cobol",
									"ColdFusion",
									"CSS",
									"Delphi",
									"Fortran",
									"Groovy",
									"Java",
									"JavaScript",
									"JQuery",
									"JSF (JavaServer Faces)",
									"JSP (JavaServer Pages)",
									"JUnit",
									"Haskell",
									"Lisp",
									"Lua",
									"MATLAB",
									".NET",
									"Object Pascal",
									"Objective C",
									"Pascal",
									"Perl",
									"PHP",
									"PL/SQL",
									"Python",
									"Ruby",
									"Ruby on Rails",
									"Scala",
									"Scheme",
									"Selenium",
									"Shell",
									"SQL",
									"Tcl",
									"Visual Basic",
									"XML" };
		for (String n : names) {
			ProgrammingLanguageDTO p = new ProgrammingLanguageDTO();
			p.setName(n);
			GenericDAO.getInstance().insert(p);
			logger.info("ProgrammingLanguageDTO inserted: " + n);
		}
	}
	
	private static void loadSourceCodeTypes() {
		String[] names = { "Fragment",
									"Procedure",
									"Function",
									"Object",
									"Library",
									"Package",
									"Framework" };
		for (String n : names) {
			SourceCodeTypeDTO u = new SourceCodeTypeDTO();
			u.setName(n);
			GenericDAO.getInstance().insert(u);
			logger.info("SourceCodeTypeDTO inserted: " + n);
		}
	}
	
	private static void loadUserInterfaceTypes() {
		String[] names = { "Task and Concepts",
									"Abstract UI",
									"Sketched UI",
									"Concrete UI",
									"Final UI",
									"Executable" };
		for (String n : names) {
			UserInterfaceTypeDTO u = new UserInterfaceTypeDTO();
			u.setName(n);
			GenericDAO.getInstance().insert(u);
			logger.info("UserInterfaceTypeDTO inserted: " + n);
		}
	}
	
	private static void loadRelatedAssetTypes() {
		String[] names = { "Aggregation",
									"Association",
									"Composition",
									"Dependency",
									"Similar",
									"Parent",
									"Previous Version" };
		Arrays.sort(names);
		for (String n : names) {
			RelatedAssetTypeDTO u = new RelatedAssetTypeDTO();
			u.setName(n);
			GenericDAO.getInstance().insert(u);
			logger.info("RelatedAssetTypeDTO inserted: " + n);
		}
	}
	
	/** {@link http://ootips.org/uml-hasa.html } */
	private static void loadArtifactDependencyTypes() {
		String[] names = { "Aggregation",
									"Association",
									"Composition",
									"Dependency",
									"Similar",
									"Parent",
									"Previous Version" };
		Arrays.sort(names);
		for (String n : names) {
			ArtifactDependencyTypeDTO a = new ArtifactDependencyTypeDTO();
			a.setName(n);
			GenericDAO.getInstance().insert(a);
			logger.info("ArtifactDependencyTypeDTO inserted: " + n);
		}
	}
	
//	http://en.wikipedia.org/wiki/Comparison_of_free_software_licences
	@SuppressWarnings("unused")
	private static void loadSoftwareLicenses() {
		Object[][] list = { 
		
		{"GNU All-Permissive", Boolean.TRUE},
		{"GNU GPLv2 - GNU General Public License version 2", Boolean.TRUE},
		{TypesDAO.GNUv3SoftwareLicenseName, Boolean.TRUE},
		{"GNU AGPLv3 - GNU Affero General Public License version 3", Boolean.TRUE},
		{"GNU LGPLv3 - GNU Lesser General Public License version 3", Boolean.TRUE},
		{"GNU LGPLv2.1 - GNU Lesser General Public License version 2.1", Boolean.TRUE},
		{"Apache License version 2.0", Boolean.TRUE},
		{"Artistic License 2.0", Boolean.TRUE},
		{"Clarified Artistic License", Boolean.TRUE},
		{"Berkeley Database License", Boolean.TRUE},
		{"Boost Software License", Boolean.TRUE},
		{"Modified BSD license", Boolean.TRUE},
		{"CC0", Boolean.TRUE},
		{"CeCILL version 2", Boolean.TRUE},
		{"The Clear BSD License", Boolean.TRUE},
		{"Cryptix General License", Boolean.TRUE},
		{"License of the ec fonts for LaTeX", Boolean.TRUE},
		{"Educational Community License 2.0", Boolean.TRUE},
		{"Eiffel Forum License version 2", Boolean.TRUE},
		{"EU DataGrid Software License", Boolean.TRUE},
		{"Expat License", Boolean.TRUE},
		{"FreeBSD License", Boolean.TRUE},
		{"Freetype Project License", Boolean.TRUE},
		{"iMatix Standard Function Library", Boolean.TRUE},
		{"Independent JPEG Group", Boolean.TRUE},
		{"License of imlib2", Boolean.TRUE},
		{"Intel Open Source License", Boolean.TRUE},
		{"ISC License", Boolean.TRUE},
		{"MIT", Boolean.TRUE},
		{"Mozilla Public License (MPL) version 2.0", Boolean.TRUE},
		{"NCSA/University of Illinois Open Source", Boolean.TRUE},
		{"OpenLDAP License, Version 2.7", Boolean.TRUE},
		{"Perl 5 and below", Boolean.TRUE},
		{"Public Domain", Boolean.TRUE},
		{"License of Netscape JavaScript", Boolean.TRUE},
		{"License of Python 1.6a2 and earlier versions", Boolean.TRUE},
		{"License of Python 2.0.1, 2.1.1, and newer versions", Boolean.TRUE},
		{"License of Ruby", Boolean.TRUE},
		{"SGI Free Software License B, version 2.0", Boolean.TRUE},
		{"Standard ML of New Jersey", Boolean.TRUE},
		{"The Clear BSD", Boolean.TRUE},
		{"The Unlicense", Boolean.TRUE},
		{"Unicode, Inc. License Agreement for Data Files and Software", Boolean.TRUE},
		{"License of Vim, Version 6.1 or later", Boolean.TRUE},
		{"W3C Software Notice and License", Boolean.TRUE},
		{"License of WebM", Boolean.TRUE},
		{"WTFPL version 2", Boolean.TRUE},
		{"X11", Boolean.TRUE},
		{"XFree86 1.1", Boolean.TRUE},
		{"ZLib", Boolean.TRUE},
		{"Zope Public versions 2.0 and 2.1", Boolean.TRUE},

		{"Affero General Public License (AGPL) version 1", Boolean.FALSE},
		{"Academic Free License, all versions through 3.0", Boolean.FALSE},
		{"Apache License version 1.1", Boolean.FALSE},
		{"Apache License version 1.0", Boolean.FALSE},
		{"Apple Public Source License (APSL), version 2", Boolean.FALSE},
		{"BitTorrent", Boolean.FALSE},
		{"Original BSD", Boolean.FALSE},
		{"Common Development and Distribution License (CDDL) version 1.0", Boolean.FALSE},
		{"Common Public Attribution License 1.0 (CPAL)", Boolean.FALSE},
		{"Common Public License Version 1.0", Boolean.FALSE},
		{"Condor Public License", Boolean.FALSE},
		{"Eclipse Public License Version 1.0", Boolean.FALSE},
		{"European Union Public License (EUPL) version 1.1", Boolean.FALSE},
		{"IBM Public License, Version 1.0", Boolean.FALSE},
		{"Jabber Open Source License, Version 1.0", Boolean.FALSE},
		{"LaTeX Project Public License 1.3a", Boolean.FALSE},
		{"LaTeX Project Public License 1.2", Boolean.FALSE},
		{"Lucent Public License Version 1.02 (Plan 9 license)", Boolean.FALSE},
		{"Microsoft Public License (Ms-PL)", Boolean.FALSE},
		{"Microsoft Reciprocal License (Ms-RL)", Boolean.FALSE},
		{"Mozilla Public License (MPL) version 1.1", Boolean.FALSE},
		{"Netizen Open Source License (NOSL), Version 1.0", Boolean.FALSE},
		{"Netscape Public License (NPL), versions 1.0 and 1.1", Boolean.FALSE},
		{"Nokia Open Source License", Boolean.FALSE},
		{"Old OpenLDAP License, Version 2.3", Boolean.FALSE},
		{"Open Software License, all versions through 3.0", Boolean.FALSE},
		{"OpenSSL license", Boolean.FALSE},
		{"Phorum License, Version 2.0", Boolean.FALSE},
		{"PHP License, Version 3.01", Boolean.FALSE},
		{"License of Python 1.6b1 through 2.0 and 2.1", Boolean.FALSE},
		{"Q Public License (QPL), Version 1.0", Boolean.FALSE},
		{"RealNetworks Public Source License (RPSL), Version 1.0", Boolean.FALSE},
		{"Sun Industry Standards Source License 1.0", Boolean.FALSE},
		{"Sun Public License", Boolean.FALSE},
		{"xinetd", Boolean.FALSE},
		{"Yahoo! Public License 1.1", Boolean.FALSE},
		{"Zend License, Version 2.0", Boolean.FALSE},
		{"Zimbra Public License 1.3", Boolean.FALSE},
		{"Zope Public License version 1", Boolean.FALSE},
		
		{"Other", Boolean.FALSE}

		};
		
		for (Object[] n : list) {
			SoftwareLicenseDTO u = new SoftwareLicenseDTO();
			u.setName((String)n[0]);
			u.setIsGPLCompatible((Boolean)n[1]);
			GenericDAO.getInstance().insert(u);
			logger.info("SoftwareLicenseDTO inserted: " + u);
		}
	}
	
	private static void loadFunctionalRequirementTypes() {
		String[] names = { "General Specification",
									"Product Backlog",
									"Prototype",
									"Use Case",
									"User Story",
									"Other" };
		for (String n : names) {
			FunctionalRequirementTypeDTO f = new FunctionalRequirementTypeDTO();
			f.setName(n);
			GenericDAO.getInstance().insert(f);
			logger.info("FunctionalRequirementTypeDTO inserted: " + n);
		}
	}
	
	private static void loadNonFunctionalRequirementTypes() {
		String[] names = { "Hardware Platform",
									"Internacionalization",
									"Operational System",
									"Performance",
									"Security",
									"Other"};
		for (String n : names) {
			NonFunctionalRequirementTypeDTO nf = new NonFunctionalRequirementTypeDTO();
			nf.setName(n);
			GenericDAO.getInstance().insert(nf);
			logger.info("NonFunctionalRequirementTypeDTO inserted: " + n);
		}
	}
	
	private static void loadInternationalizationTypes() {
		String[] names = { "English",
									"French",
									"German",
									"Italian",
									"Portuguese",
									"Other" };
		for (String n : names) {
			InternationalizationTypeDTO i = new InternationalizationTypeDTO();
			i.setName(n);
			GenericDAO.getInstance().insert(i);
			logger.info("InternationalizationTypeDTO inserted: " + n);
		}
	}
	
	private static void loadOperationalSystemType() {
		String[] names = { "Any",
									"Linux",
									"Mac OS",
									"Red Hat",
									"Windows",
									"Ubuntu",
									"Unix",
									"Other" };
		for (String n : names) {
			OperationalSystemTypeDTO o = new OperationalSystemTypeDTO();
			o.setName(n);
			GenericDAO.getInstance().insert(o);
			logger.info("OperationalSystemTypeDTO inserted: " + n);
		}
	}
	
	private static void loadTestType() {
		String[] names = { "Test Case",
									"Test Code",
									"Test Data",
									"Test Plan",
         						"Test Script" };
		for (String n : names) {
			TestTypeDTO t = new TestTypeDTO();
			t.setName(n);
			GenericDAO.getInstance().insert(t);
			logger.info("TestTypeDTO inserted: " + n);
		}
	}
	
	private static void loadTestMethodType() {
		String[] names = { "Acceptance",
                  			"Black Box",
                  			"Configuration",
                  			"Funcional",
                  			"Instalation",
                  			"Integration",
                  			"Load",
                  			"Operational",
                  			"Performance",
                  			"Regression",
                  			"Security",
                  			"Stress",
                  			"System",
                  			"Unit",
                  			"Usability",
                  			"Volume",
                  			"White Box" };
		Arrays.sort(names);
		for (String n : names) {
			TestMethodTypeDTO t = new TestMethodTypeDTO();
			t.setName(n);
			GenericDAO.getInstance().insert(t);
			logger.info("TestMethodTypeDTO inserted: " + n);
		}
	}
	
	private static TreeMap<String, ArrayList<String>> getAllDomainsAndSubDomains() {
		TreeMap<String, ArrayList<String>> domainsMap = new TreeMap<String, ArrayList<String>>();

		String domain6E413974 = "Agricultural And Veterinary Sciences";
		ArrayList<String> domain6E413974List = new ArrayList<String>();
		domain6E413974List.add("Agriculture, Land And Farm Management");
		domain6E413974List.add("Animal Production");
		domain6E413974List.add("Crop And Pasture Production");
		domain6E413974List.add("Fisheries Sciences");
		domain6E413974List.add("Forestry Sciences");
		domain6E413974List.add("Horticultural Production");
		domain6E413974List.add("Other Agricultural And Veterinary Sciences");
		domain6E413974List.add("Veterinary Sciences");
		domainsMap.put(domain6E413974, domain6E413974List);

		String domain4F6DF826 = "Animal Production And Animal Primary Products";
		ArrayList<String> domain4F6DF826List = new ArrayList<String>();
		domain4F6DF826List.add("Environmentally Sustainable Animal Production");
		domain4F6DF826List.add("Fisheries - Aquaculture");
		domain4F6DF826List.add("Fisheries - Wild Caught");
		domain4F6DF826List.add("Livestock Raising");
		domain4F6DF826List.add("Other Animal Production And Animal Primary Products");
		domain4F6DF826List.add("Pasture, Browse And Fodder Crops");
		domain4F6DF826List.add("Primary Animal Products");
		domainsMap.put(domain4F6DF826, domain4F6DF826List);

		String domain4AEF09CE = "Biological Sciences";
		ArrayList<String> domain4AEF09CEList = new ArrayList<String>();
		domain4AEF09CEList.add("Biochemistry And Cell Biology");
		domain4AEF09CEList.add("Ecology");
		domain4AEF09CEList.add("Evolutionary Biology");
		domain4AEF09CEList.add("Genetics");
		domain4AEF09CEList.add("Microbiology");
		domain4AEF09CEList.add("Other Biological Sciences");
		domain4AEF09CEList.add("Physiology");
		domain4AEF09CEList.add("Plant Biology");
		domain4AEF09CEList.add("Zoology");
		domainsMap.put(domain4AEF09CE, domain4AEF09CEList);

		String domain2531174A = "Built Environment And Design";
		ArrayList<String> domain2531174AList = new ArrayList<String>();
		domain2531174AList.add("Architecture");
		domain2531174AList.add("Building");
		domain2531174AList.add("Design Practice And Management");
		domain2531174AList.add("Engineering Design");
		domain2531174AList.add("Other Built Environment And Design");
		domain2531174AList.add("Urban And Regional Planning");
		domainsMap.put(domain2531174A, domain2531174AList);

		String domainA530ADD = "Chemical Sciences";
		ArrayList<String> domainA530ADDList = new ArrayList<String>();
		domainA530ADDList.add("Analytical Chemistry");
		domainA530ADDList.add("Inorganic Chemistry");
		domainA530ADDList.add("Macromolecular And Materials Chemistry");
		domainA530ADDList.add("Medicinal And Biomolecular Chemistry");
		domainA530ADDList.add("Organic Chemistry");
		domainA530ADDList.add("Other Chemical Sciences");
		domainA530ADDList.add("Physical Chemistry");
		domainA530ADDList.add("Theoretical And Computational Chemistry");
		domainsMap.put(domainA530ADD, domainA530ADDList);

		String domain68180502 = "Commerce, Management, Tourism And Services";
		ArrayList<String> domain68180502List = new ArrayList<String>();
		domain68180502List.add("Accounting, Auditing And Accountability");
		domain68180502List.add("Banking, Finance And Investment");
		domain68180502List.add("Business And Management");
		domain68180502List.add("Commercial Services");
		domain68180502List.add("Marketing");
		domain68180502List.add("Other Commerce, Management, Tourism And Services");
		domain68180502List.add("Tourism");
		domain68180502List.add("Transportation And Freight Services");
		domainsMap.put(domain68180502, domain68180502List);

		String domain7C878FF6 = "Commercial Services And Tourism";
		ArrayList<String> domain7C878FF6List = new ArrayList<String>();
		domain7C878FF6List.add("Environmentally Sustainable Commercial Services And Tourism");
		domain7C878FF6List.add("Financial Services");
		domain7C878FF6List.add("Other Commercial Services And Tourism");
		domain7C878FF6List.add("Property, Business Support Services And Trade");
		domain7C878FF6List.add("Tourism");
		domain7C878FF6List.add("Water And Waste Services");
		domainsMap.put(domain7C878FF6, domain7C878FF6List);

		String domain6D2C9B0F = "Construction";
		ArrayList<String> domain6D2C9B0FList = new ArrayList<String>();
		domain6D2C9B0FList.add("Building Management And Services");
		domain6D2C9B0FList.add("Construction Design");
		domain6D2C9B0FList.add("Construction Materials Performance And Processes");
		domain6D2C9B0FList.add("Construction Planning");
		domain6D2C9B0FList.add("Construction Processes");
		domain6D2C9B0FList.add("Environmentally Sustainable Construction");
		domain6D2C9B0FList.add("Other Construction");
		domainsMap.put(domain6D2C9B0F, domain6D2C9B0FList);

		String domain61E0947A = "Cultural Understanding";
		ArrayList<String> domain61E0947AList = new ArrayList<String>();
		domain61E0947AList.add("Arts And Leisure");
		domain61E0947AList.add("Communication");
		domain61E0947AList.add("Heritage");
		domain61E0947AList.add("Other Cultural Understanding");
		domain61E0947AList.add("Religion And Ethics");
		domain61E0947AList.add("Understanding Past Societies");
		domainsMap.put(domain61E0947A, domain61E0947AList);

		String domain40B1DBD0 = "Defence";
		ArrayList<String> domain40B1DBD0List = new ArrayList<String>();
		domain40B1DBD0List.add("Defence");
		domainsMap.put(domain40B1DBD0, domain40B1DBD0List);

		String domain3878A21B = "Earth Sciences";
		ArrayList<String> domain3878A21BList = new ArrayList<String>();
		domain3878A21BList.add("Atmospheric Sciences");
		domain3878A21BList.add("Geochemistry");
		domain3878A21BList.add("Geology");
		domain3878A21BList.add("Geophysics");
		domain3878A21BList.add("Oceanography");
		domain3878A21BList.add("Other Earth Sciences");
		domain3878A21BList.add("Physical Geography And Environmental Geoscience");
		domainsMap.put(domain3878A21B, domain3878A21BList);

		String domainE05FC6D = "Economic Framework";
		ArrayList<String> domainE05FC6DList = new ArrayList<String>();
		domainE05FC6DList.add("International Trade");
		domainE05FC6DList.add("Macroeconomics");
		domainE05FC6DList.add("Management And Productivity");
		domainE05FC6DList.add("Measurement Standards And Calibration Services");
		domainE05FC6DList.add("Microeconomics");
		domainE05FC6DList.add("Other Economic Framework");
		domainsMap.put(domainE05FC6D, domainE05FC6DList);

		String domain2BA1A102 = "Economics";
		ArrayList<String> domain2BA1A102List = new ArrayList<String>();
		domain2BA1A102List.add("Applied Economics");
		domain2BA1A102List.add("Econometrics");
		domain2BA1A102List.add("Economic Theory");
		domain2BA1A102List.add("Other Economics");
		domainsMap.put(domain2BA1A102, domain2BA1A102List);

		String domain661D8788 = "Education";
		ArrayList<String> domain661D8788List = new ArrayList<String>();
		domain661D8788List.add("Curriculum And Pedagogy");
		domain661D8788List.add("Education Systems");
		domain661D8788List.add("Other Education");
		domain661D8788List.add("Specialist Studies In Education");
		domainsMap.put(domain661D8788, domain661D8788List);

		String domain28120EDB = "Education And Training";
		ArrayList<String> domain28120EDBList = new ArrayList<String>();
		domain28120EDBList.add("Curriculum");
		domain28120EDBList.add("Education And Training Systems");
		domain28120EDBList.add("Learner And Learning");
		domain28120EDBList.add("Other Education And Training");
		domain28120EDBList.add("School/institution");
		domain28120EDBList.add("Teaching And Instruction");
		domainsMap.put(domain28120EDB, domain28120EDBList);

		String domain7BFC1EA8 = "Energy";
		ArrayList<String> domain7BFC1EA8List = new ArrayList<String>();
		domain7BFC1EA8List.add("Energy Conservation And Efficiency");
		domain7BFC1EA8List.add("Energy Exploration");
		domain7BFC1EA8List.add("Energy Storage, Distribution And Supply");
		domain7BFC1EA8List.add("Energy Transformation");
		domain7BFC1EA8List.add("Environmentally Sustainable Energy Activities");
		domain7BFC1EA8List.add("Mining And Extraction Of Energy Resources");
		domain7BFC1EA8List.add("Other Energy");
		domain7BFC1EA8List.add("Preparation And Production Of Energy Sources");
		domain7BFC1EA8List.add("Renewable Energy");
		domainsMap.put(domain7BFC1EA8, domain7BFC1EA8List);

		String domain36DD890D = "Engineering";
		ArrayList<String> domain36DD890DList = new ArrayList<String>();
		domain36DD890DList.add("Aerospace Engineering");
		domain36DD890DList.add("Automotive Engineering");
		domain36DD890DList.add("Biomedical Engineering");
		domain36DD890DList.add("Chemical Engineering");
		domain36DD890DList.add("Civil Engineering");
		domain36DD890DList.add("Electrical And Electronic Engineering");
		domain36DD890DList.add("Environmental Engineering");
		domain36DD890DList.add("Food Sciences");
		domain36DD890DList.add("Geomatic Engineering");
		domain36DD890DList.add("Interdisciplinary Engineering");
		domain36DD890DList.add("Manufacturing Engineering");
		domain36DD890DList.add("Maritime Engineering");
		domain36DD890DList.add("Materials Engineering");
		domain36DD890DList.add("Mechanical Engineering");
		domain36DD890DList.add("Other Engineering");
		domain36DD890DList.add("Resources Engineering And Extractive Metallurgy");
		domainsMap.put(domain36DD890D, domain36DD890DList);

		String domain5E4AD9F3 = "Environment";
		ArrayList<String> domain5E4AD9F3List = new ArrayList<String>();
		domain5E4AD9F3List.add("Air Quality");
		domain5E4AD9F3List.add("Atmosphere And Weather");
		domain5E4AD9F3List.add("Climate And Climate Change");
		domain5E4AD9F3List.add("Control Of Pests, Diseases And Exotic Species");
		domain5E4AD9F3List.add("Ecosystem Assessment And Management");
		domain5E4AD9F3List.add("Environmental And Natural Resource Evaluation");
		domain5E4AD9F3List.add("Environmental Policy, Legislation And Standards");
		domain5E4AD9F3List.add("Flora, Fauna And Biodiversity");
		domain5E4AD9F3List.add("Land And Water Management");
		domain5E4AD9F3List.add("Natural Hazards");
		domain5E4AD9F3List.add("Other Environment");
		domain5E4AD9F3List.add("Physical And Chemical Conditions Of Water");
		domain5E4AD9F3List.add("Rehabilitation Of Degraded Environments");
		domain5E4AD9F3List.add("Remnant Vegetation And Protected Conservation Areas");
		domain5E4AD9F3List.add("Soils");
		domainsMap.put(domain5E4AD9F3, domain5E4AD9F3List);

		String domain706EEB31 = "Environmental Sciences";
		ArrayList<String> domain706EEB31List = new ArrayList<String>();
		domain706EEB31List.add("Ecological Applications");
		domain706EEB31List.add("Environmental Science And Management");
		domain706EEB31List.add("Other Environmental Sciences");
		domain706EEB31List.add("Soil Sciences");
		domainsMap.put(domain706EEB31, domain706EEB31List);

		String domain7F661184 = "Health";
		ArrayList<String> domain7F661184List = new ArrayList<String>();
		domain7F661184List.add("Clinical Health");
		domain7F661184List.add("Health And Support Services");
		domain7F661184List.add("Indigenous Health");
		domain7F661184List.add("Other Health");
		domain7F661184List.add("Public Health");
		domain7F661184List.add("Specific Population Health");
		domainsMap.put(domain7F661184, domain7F661184List);

		String domain70C990CB = "History And Archaeology";
		ArrayList<String> domain70C990CBList = new ArrayList<String>();
		domain70C990CBList.add("Archaeology");
		domain70C990CBList.add("Curatorial And Related Studies");
		domain70C990CBList.add("Historical Studies");
		domain70C990CBList.add("Other History And Archaeology");
		domainsMap.put(domain70C990CB, domain70C990CBList);

		String domain5C85023B = "Information And Communication Services";
		ArrayList<String> domain5C85023BList = new ArrayList<String>();
		domain5C85023BList.add("Communication Networks And Services");
		domain5C85023BList.add("Computer Software And Services");
		domain5C85023BList.add("Environmentally Sustainable Information And Communication Services");
		domain5C85023BList.add("Information Services");
		domain5C85023BList.add("Media Services");
		domain5C85023BList.add("Other Information And Communication Services");
		domainsMap.put(domain5C85023B, domain5C85023BList);

		String domain39C88CD8 = "Information And Computing Sciences";
		ArrayList<String> domain39C88CD8List = new ArrayList<String>();
		domain39C88CD8List.add("Artificial Intelligence And Image Processing");
		domain39C88CD8List.add("Computation Theory And Mathematics");
		domain39C88CD8List.add("Computer Software");
		domain39C88CD8List.add("Data Format");
		domain39C88CD8List.add("Distributed Computing");
		domain39C88CD8List.add("Information Systems");
		domain39C88CD8List.add("Library And Information Studies");
		domain39C88CD8List.add("Other Information And Computing Sciences");
		domainsMap.put(domain39C88CD8, domain39C88CD8List);

		String domain3029EEB1 = "Language, Communication And Culture";
		ArrayList<String> domain3029EEB1List = new ArrayList<String>();
		domain3029EEB1List.add("Communication And Media Studies");
		domain3029EEB1List.add("Cultural Studies");
		domain3029EEB1List.add("Language Studies");
		domain3029EEB1List.add("Linguistics");
		domain3029EEB1List.add("Literary Studies");
		domain3029EEB1List.add("Other Language, Communication And Culture");
		domainsMap.put(domain3029EEB1, domain3029EEB1List);

		String domain3777C7B9 = "Law And Legal Studies";
		ArrayList<String> domain3777C7B9List = new ArrayList<String>();
		domain3777C7B9List.add("Law");
		domain3777C7B9List.add("Maori Law");
		domain3777C7B9List.add("Other Law And Legal Studies");
		domainsMap.put(domain3777C7B9, domain3777C7B9List);

		String domainE509E47 = "Law, Politics And Community Services";
		ArrayList<String> domainE509E47List = new ArrayList<String>();
		domainE509E47List.add("Community Service");
		domainE509E47List.add("Government And Politics");
		domainE509E47List.add("International Relations");
		domainE509E47List.add("Justice And The Law");
		domainE509E47List.add("Other Law, Politics And Community Services");
		domainE509E47List.add("Work And Institutional Development");
		domainsMap.put(domainE509E47, domainE509E47List);

		String domain73A6AE2 = "Manufacturing";
		ArrayList<String> domain73A6AE2List = new ArrayList<String>();
		domain73A6AE2List.add("Agricultural Chemicals");
		domain73A6AE2List.add("Basic Metal Products");
		domain73A6AE2List.add("Ceramics, Glass And Industrial Mineral Products");
		domain73A6AE2List.add("Communication Equipment");
		domain73A6AE2List.add("Computer Hardware And Electronic Equipment");
		domain73A6AE2List.add("Dairy Products");
		domain73A6AE2List.add("Environmentally Sustainable Manufacturing");
		domain73A6AE2List.add("Fabricated Metal Products");
		domain73A6AE2List.add("Human Pharmaceutical Products");
		domain73A6AE2List.add("Industrial Chemicals And Related Products");
		domain73A6AE2List.add("Instrumentation");
		domain73A6AE2List.add("Leather Products, Fibre Processing And Textiles");
		domain73A6AE2List.add("Machinery And Equipment");
		domain73A6AE2List.add("Other Manufacturing");
		domain73A6AE2List.add("Processed Food Products And Beverages");
		domain73A6AE2List.add("Processed Non-food Agriculture Products");
		domain73A6AE2List.add("Transport Equipment");
		domain73A6AE2List.add("Veterinary Pharmaceutical Products");
		domain73A6AE2List.add("Wood, Wood Products And Paper");
		domainsMap.put(domain73A6AE2, domain73A6AE2List);

		String domain76D50F79 = "Mathematical Sciences";
		ArrayList<String> domain76D50F79List = new ArrayList<String>();
		domain76D50F79List.add("Applied Mathematics");
		domain76D50F79List.add("Mathematical Physics");
		domain76D50F79List.add("Numerical And Computational Mathematics");
		domain76D50F79List.add("Other Mathematical Sciences");
		domain76D50F79List.add("Pure Mathematics");
		domain76D50F79List.add("Statistics");
		domainsMap.put(domain76D50F79, domain76D50F79List);

		String domain6492DF3B = "Medical And Health Sciences";
		ArrayList<String> domain6492DF3BList = new ArrayList<String>();
		domain6492DF3BList.add("Cardiorespiratory Medicine And Haematology");
		domain6492DF3BList.add("Clinical Sciences");
		domain6492DF3BList.add("Complementary And Alternative Medicine");
		domain6492DF3BList.add("Dentistry");
		domain6492DF3BList.add("Human Movement And Sports Science");
		domain6492DF3BList.add("Immunology");
		domain6492DF3BList.add("Medical Biochemistry And Metabolomics");
		domain6492DF3BList.add("Medical Microbiology");
		domain6492DF3BList.add("Medical Physiology");
		domain6492DF3BList.add("Neurosciences");
		domain6492DF3BList.add("Nursing");
		domain6492DF3BList.add("Nutrition And Dietetics");
		domain6492DF3BList.add("Oncology And Carcinogenesis");
		domain6492DF3BList.add("Ophthalmology And Optometry");
		domain6492DF3BList.add("Other Medical And Health Sciences");
		domain6492DF3BList.add("Paediatrics And Reproductive Medicine");
		domain6492DF3BList.add("Pharmacology And Pharmaceutical Sciences");
		domain6492DF3BList.add("Public Health And Health Services");
		domainsMap.put(domain6492DF3B, domain6492DF3BList);

		String domain3BB0BD1 = "Mineral Resources";
		ArrayList<String> domain3BB0BD1List = new ArrayList<String>();
		domain3BB0BD1List.add("Environmentally Sustainable Mineral Resource Activities");
		domain3BB0BD1List.add("First Stage Treatment Of Ores And Minerals");
		domain3BB0BD1List.add("Mineral Exploration");
		domain3BB0BD1List.add("Other Mineral Resources");
		domain3BB0BD1List.add("Primary Mining And Extraction Of Mineral Resources");
		domainsMap.put(domain3BB0BD1, domain3BB0BD1List);

		String domain48F8EF0 = "Other";
		ArrayList<String> domain48F8EF0List = new ArrayList<String>();
		domain48F8EF0List.add("Other");
		domainsMap.put(domain48F8EF0, domain48F8EF0List);

		String domain4C8D4A76 = "Philosophy And Religious Studies";
		ArrayList<String> domain4C8D4A76List = new ArrayList<String>();
		domain4C8D4A76List.add("Applied Ethics");
		domain4C8D4A76List.add("History And Philosophy Of Specific Fields");
		domain4C8D4A76List.add("Other Philosophy And Religious Studies");
		domain4C8D4A76List.add("Philosophy");
		domain4C8D4A76List.add("Religion And Religious Studies");
		domainsMap.put(domain4C8D4A76, domain4C8D4A76List);

		String domain734A57B8 = "Physical Sciences";
		ArrayList<String> domain734A57B8List = new ArrayList<String>();
		domain734A57B8List.add("Astronomical And Space Sciences");
		domain734A57B8List.add("Atomic, Molecular, Nuclear, Particle And Plasma Physics");
		domain734A57B8List.add("Classical Physics");
		domain734A57B8List.add("Condensed Matter Physics");
		domain734A57B8List.add("Optical Physics");
		domain734A57B8List.add("Other Physical Sciences");
		domain734A57B8List.add("Quantum Physics");
		domainsMap.put(domain734A57B8, domain734A57B8List);

		String domain15FA1AAE = "Plant Production And Plant Primary Products";
		ArrayList<String> domain15FA1AAEList = new ArrayList<String>();
		domain15FA1AAEList.add("Environmentally Sustainable Plant Production");
		domain15FA1AAEList.add("Forestry");
		domain15FA1AAEList.add("Harvesting And Packing Of Plant Products");
		domain15FA1AAEList.add("Horticultural Crops");
		domain15FA1AAEList.add("Industrial Crops");
		domain15FA1AAEList.add("Other Plant Production And Plant Primary Products");
		domain15FA1AAEList.add("Summer Grains And Oilseeds");
		domain15FA1AAEList.add("Winter Grains And Oilseeds");
		domainsMap.put(domain15FA1AAE, domain15FA1AAEList);

		String domain38E6EBF5 = "Psychology And Cognitive Sciences";
		ArrayList<String> domain38E6EBF5List = new ArrayList<String>();
		domain38E6EBF5List.add("Cognitive Sciences");
		domain38E6EBF5List.add("Other Psychology And Cognitive Sciences");
		domain38E6EBF5List.add("Psychology");
		domainsMap.put(domain38E6EBF5, domain38E6EBF5List);

		String domain208BEFAE = "Studies In Creative Arts And Writing";
		ArrayList<String> domain208BEFAEList = new ArrayList<String>();
		domain208BEFAEList.add("Art Theory And Criticism");
		domain208BEFAEList.add("Film, Television And Digital Media");
		domain208BEFAEList.add("Journalism And Professional Writing");
		domain208BEFAEList.add("Other Studies In Creative Arts And Writing");
		domain208BEFAEList.add("Performing Arts And Creative Writing");
		domain208BEFAEList.add("Visual Arts And Crafts");
		domainsMap.put(domain208BEFAE, domain208BEFAEList);

		String domain69B0EA2D = "Studies In Human Society";
		ArrayList<String> domain69B0EA2DList = new ArrayList<String>();
		domain69B0EA2DList.add("Anthropology");
		domain69B0EA2DList.add("Criminology");
		domain69B0EA2DList.add("Demography");
		domain69B0EA2DList.add("Human Geography");
		domain69B0EA2DList.add("Other Studies In Human Society");
		domain69B0EA2DList.add("Policy And Administration");
		domain69B0EA2DList.add("Political Science");
		domain69B0EA2DList.add("Social Work");
		domain69B0EA2DList.add("Sociology");
		domainsMap.put(domain69B0EA2D, domain69B0EA2DList);

		String domain12B9612C = "Technology";
		ArrayList<String> domain12B9612CList = new ArrayList<String>();
		domain12B9612CList.add("Agricultural Biotechnology");
		domain12B9612CList.add("Communications Technologies");
		domain12B9612CList.add("Computer Hardware");
		domain12B9612CList.add("Environmental Biotechnology");
		domain12B9612CList.add("Industrial Biotechnology");
		domain12B9612CList.add("Medical Biotechnology");
		domain12B9612CList.add("Nanotechnology");
		domain12B9612CList.add("Other Technology");
		domainsMap.put(domain12B9612C, domain12B9612CList);

		String domain49CAE8F7 = "Transport";
		ArrayList<String> domain49CAE8F7List = new ArrayList<String>();
		domain49CAE8F7List.add("Aerospace Transport");
		domain49CAE8F7List.add("Environmentally Sustainable Transport");
		domain49CAE8F7List.add("Ground Transport");
		domain49CAE8F7List.add("Other Transport");
		domain49CAE8F7List.add("Water Transport");
		domainsMap.put(domain49CAE8F7, domain49CAE8F7List);

		return domainsMap;
	}
	
}
