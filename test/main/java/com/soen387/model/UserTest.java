package main.java.com.soen387.model;

import com.soen387.usermanager.User;
import org.json.simple.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void DefaultUserConstructorTest() {
        User user = new User();

        assertEquals("", user.getName());
        assertEquals("", user.getEmail());
        assertEquals("", user.getPassword());
        assertEquals(0, user.getId());
    }

    @Test
    public void SignupConstructorTest() {
        String name = "TestName";
        String password = "fk39ff93jff0934jifjsd09if3joi";
        String email = "TestEmail@test.com";
        int id = 949;
        User user = new User(id, name, password, email);

        assertEquals(name, user.getName());
        assertNotEquals(password, user.getPassword());
        assertEquals(email, user.getEmail());
        assertEquals(id, user.getId());
    }

    @Test
    public void LoadConstructorTest() {
        String name = "TestName";
        String password = "fk39ff93jff0934jifjsd09if3joi";
        String email = "TestEmail@test.com";
        int id = 949;
        User user = new User(id, name, password, email, true, "");

        assertEquals(name, user.getName());
        assertEquals(password, user.getPassword());
        assertEquals(email, user.getEmail());
        assertEquals(id, user.getId());
    }

    @Test
    public void UserPasswordTest() {
        User user = new User();
        String password = "TestPassword";
        user.setPassword(password);

        assertNotEquals(password, user.getPassword());
        assertTrue(user.comparePassword(password));
    }

    @Test
    public void UserToJsonTest() {
        String name = "TestName";
        String password = "fj3298djfsd398fdsjvds98uwrj";
        String email = "TestEmail@test.com";
        int id = 949;
        boolean activated = true;
        String activationCode = "499391";
        User user = new User(id, name, password, email, activated, activationCode);

        JSONObject userJson = user.toJson();

        assertEquals(name, userJson.get("name"));
        assertEquals(password, userJson.get("password"));
        assertEquals(email, userJson.get("email"));
        assertEquals(id, userJson.get("id"));
        assertEquals(activated, userJson.get("activated"));
        assertEquals(activationCode, userJson.get("activationCode"));
    }

    @Test
    public void ActivateTest() {
        User user = new User(0, "testName", "testPass", "test@mail.ca");
        assertFalse(user.isActivated());
        assertTrue(user.activate(user.getActivationCode()));
        assertTrue(user.isActivated());
    }

    @Test
    public void ActivateFailTest() {
        User user = new User(0, "testName", "testPass", "test@mail.ca");
        assertFalse(user.activate("Not a correct code"));
        assertFalse(user.isActivated());
    }

    @Test
    public void ForgetPasswordTest() {
        String password = "testPass";
        User user = new User(0, "testName", password, "test@mail.ca");
        user.activate(user.getActivationCode());
        assertTrue(user.comparePassword(password));
        user.forgetPassword();
        assertTrue(user.comparePassword(password));
        String newPassword = user.getNewPassword(user.getActivationCode());
        assertFalse(user.comparePassword(password));
        assertTrue(user.comparePassword(newPassword));
    }

    @Test
    public void ForgetPasswordWrongCodeTest() {
        String password = "testPass";
        User user = new User(0, "testName", password, "test@mail.ca");
        user.activate(user.getActivationCode());
        user.forgetPassword();
        String newPassword = user.getNewPassword("Not the correct code");
        assertNull(newPassword);
        assertTrue(user.comparePassword(password));
    }
}
