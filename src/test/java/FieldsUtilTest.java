import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import br.ufrgs.inf.dsmoura.repository.controller.util.FieldsUtil;

public class FieldsUtilTest {

	@Test
	public void testExtractUsernamesNull() {
		Collection<String> usernames = FieldsUtil.extractUsernames(null);
		assertEquals("List must be null", 0, usernames.size());
	}
	
	@Test
	public void testExtractUsernamesEmpty() {
		Collection<String> usernames = FieldsUtil.extractUsernames("");
		assertEquals("List must be empty", 0, usernames.size());
	}
	
	@Test
	public void testExtractUsernamesOneUsername() {
		Collection<String> usernames = FieldsUtil.extractUsernames("username");
		assertEquals("List must has one element", 1, usernames.size());
	}
	
	@Test
	public void testExtractUsernamesOneUsernameWithHyphen() {
		Collection<String> usernames = FieldsUtil.extractUsernames("user-name");
		assertEquals("List must has one element with hyphen", 1, usernames.size());
	}
	
	@Test
	public void testExtractUsernamesWithTwoUsernames() {
		Collection<String> usernames = FieldsUtil.extractUsernames("username1,username2");
		assertEquals("List must has two elements", 2, usernames.size());
	}
	
	@Test
	public void testExtractUsernamesWithTwoBlankSpacedUsernames() {
		Collection<String> usernames = FieldsUtil.extractUsernames(" username1 , username2 ");
		assertEquals("List must has two elements with blank spaces", 2, usernames.size());
	}
	
	@Test
	public void testExtractUsernamesWithBlankSpaces() {
		Collection<String> usernames = FieldsUtil.extractUsernames("user name, user name two");
		assertEquals("List must has two elements with blank spaces inside", 2, usernames.size());
	}
	
	@Test
	public void testExtractUsernamesFiveUsernames() {
		Collection<String> usernames = FieldsUtil.extractUsernames(" username1,username2 , username3 ,username4,  username5  ");
		assertEquals("List must has two elements", 5, usernames.size());
	}
	
	@Test
	public void testExtractUsernamesWithBlankSpacesOnFirstElement() {
		Collection<String> usernames = FieldsUtil.extractUsernames(" username1 , username2 ");
		assertEquals("Element must not has empty on left or right", "username1", usernames.toArray()[0]);  
	}
	
	@Test
	public void testExtractUsernamesWithBlanckSpacesOnSecondElement() {
		Collection<String> usernames = FieldsUtil.extractUsernames(" username1 , username2 ");
		assertEquals("Element must not has empty on left or right", "username2", usernames.toArray()[1]);  
	}
	
	@Test
	public void testExtractUsernamesWithoutDuplicates() {
		Collection<String> usernames = FieldsUtil.extractUsernames("user-name1, user-name2, user-name1");
		assertEquals("List must has not duplicates", 2, usernames.size());  
	}
	
}
