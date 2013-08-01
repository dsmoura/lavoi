package br.ufrgs.inf.dsmoura.repository.controller.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import br.ufrgs.inf.dsmoura.repository.controller.SystemPropertyEnum;
import br.ufrgs.inf.dsmoura.repository.model.dao.TypesDAO;


public class EmailUtil {
	
	static final Log logger = LogFactory.getLog(EmailUtil.class);
	
	public static void sendMail(String to, String subject, String msg) {
		List<String> toList = new ArrayList<String>();
		toList.add(to);
		sendMail(toList, subject, msg);
	}
	
	public static void sendMail(final List<String> toList, final String subject, final String msg) {
		if (toList == null || toList.size() == 0) {
			throw new IllegalArgumentException("Invalid value for toList: " + toList);
		}
		final String SMTP_HOSTNAME = TypesDAO.getInstance().getSystemPropertyValue(SystemPropertyEnum.SMTP_HOSTNAME);
		final String SMTP_SSL = TypesDAO.getInstance().getSystemPropertyValue(SystemPropertyEnum.SMTP_SSL);
		final String REPOSITORY_EMAIL = TypesDAO.getInstance().getSystemPropertyValue(SystemPropertyEnum.REPOSITORY_EMAIL);
		final String REPOSITORY_EMAIL_USER = TypesDAO.getInstance().getSystemPropertyValue(SystemPropertyEnum.REPOSITORY_EMAIL_USER);
		final String REPOSITORY_EMAIL_PASSWORD = TypesDAO.getInstance().getSystemPropertyValue(SystemPropertyEnum.REPOSITORY_EMAIL_PASSWORD);
		final String REPOSITORY_SUBTITLE_NAME = TypesDAO.getInstance().getSystemPropertyValue(SystemPropertyEnum.REPOSITORY_SUBTITLE);
		final String REPOSITORY_SUBTITLE = TypesDAO.getInstance().getSystemPropertyValue(SystemPropertyEnum.REPOSITORY_SUBTITLE);
		final String REPOSITORY_URL = TypesDAO.getInstance().getSystemPropertyValue(SystemPropertyEnum.REPOSITORY_URL);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					SimpleEmail email = new SimpleEmail();
					email.setDebug(true);
					email.setHostName(SMTP_HOSTNAME);
					email.setAuthentication(REPOSITORY_EMAIL_USER, REPOSITORY_EMAIL_PASSWORD);
					email.setSSL(Boolean.parseBoolean(SMTP_SSL));
					email.setFrom(REPOSITORY_EMAIL, REPOSITORY_SUBTITLE_NAME);
					if (toList.size() == 1) {
						email.addTo(toList.get(0));
					}
					else {
						for (String to : toList) {
							email.addBcc(to);
						}
					}
					email.setSubject(subject);
					email.setMsg(msg + "\n" +
										REPOSITORY_SUBTITLE + "\n" +
										REPOSITORY_URL);
					email.send();
					logger.info("email sent: " + subject);
				} catch (EmailException e) {
					logger.error("Error sending email: \n"+
									"to: " + toList + "\n" +
									"subject: " + subject + "\n" +
									"msg: " + msg, e);
				}
			}
		}).start();
	}
	
}
