package br.ufrgs.inf.dsmoura.repository.controller.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Random;

import br.ufrgs.inf.dsmoura.repository.model.entity.ApplicationSubdomain;
import br.ufrgs.inf.dsmoura.repository.model.entity.AssetType;
import br.ufrgs.inf.dsmoura.repository.model.entity.ProgrammingLanguageDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.SoftwareLicenseDTO;

public class FieldsUtil {
	
	private static final int LENGHT_NORMALIZED = 30;
	
	public static String normalize(String str) {
		return normalize(str, LENGHT_NORMALIZED);
	}
	
	public static String normalize(String str, int lenght) {
		if (str == null) {
			return str;
		}
		if (str.length() > lenght) {
			return str.substring(0,lenght-3) + " ...";
		}
		return str;
	}
	
	public static String normalizeAverageScore(float averageScore) {
		NumberFormat nFBR = NumberFormat.getInstance();
		nFBR.setMaximumFractionDigits(2);
		return nFBR.format( Math.floor(averageScore * 20) );	/* transform to percentage */
	}
	
	public static String getStrDate(Calendar calendar) {
		if (calendar != null) {
			DateFormat df1 = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("PT", "BR"));
			return df1.format(calendar.getTime());
		}
		return "-";
	}
	
	public static String getRandomCode() {
		int positiveCode = new Random().nextInt();
		if (positiveCode < 0) {
			positiveCode = -positiveCode;
		}
		return Integer.toHexString(positiveCode).toUpperCase(); 
	}
	
	public static String getPositiveHexHashCode(String s) {
		int positiveCode = s.hashCode();
		if (positiveCode < 0) {
			positiveCode = -positiveCode;
		}
		return Integer.toHexString(positiveCode).toUpperCase(); 
	}
	
	public static Boolean isValidType(AssetType assetType) {
		return assetType != null &&
					assetType.getName() != null &&
					assetType.getName().trim().length() > 0;
	}
	
	public static Boolean isValidSoftwareLicense(SoftwareLicenseDTO softwareLicenseDTO) {
		return softwareLicenseDTO != null &&
					softwareLicenseDTO.getName() != null &&
					softwareLicenseDTO.getName().trim().length() > 0;
	}
	
	public static Boolean isValidProgrammingLanguage(ProgrammingLanguageDTO programmingLanguageDTO) {
		return programmingLanguageDTO != null &&
				programmingLanguageDTO.getName() != null &&
				programmingLanguageDTO.getName().trim().length() > 0;
	}
	
	public static Boolean isValidApplicationSubdomain(ApplicationSubdomain applicationSubdomainDTO) {
		return applicationSubdomainDTO != null &&
				applicationSubdomainDTO.getName() != null &&
				applicationSubdomainDTO.getName().trim().length() > 0;
	}
	
	public static Collection<String> extractUsernames(String usernamesCommaSeparated) {
		Collection<String> list = new LinkedHashSet<String>();
		if (usernamesCommaSeparated == null) {
			return list;
		}
		while (usernamesCommaSeparated.contains(",")) {
			int nextIndex = usernamesCommaSeparated.indexOf(",");
			String next = usernamesCommaSeparated.substring(0, nextIndex);
			list.add(next.trim());
			usernamesCommaSeparated = usernamesCommaSeparated.substring(nextIndex+1);
		}
		if (usernamesCommaSeparated.length() > 0) {
			list.add(usernamesCommaSeparated.trim());
		}
		return list;
	}
	
}
