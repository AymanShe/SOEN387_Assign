package main.java.com.soen387.model;

import com.soen387.model.User;
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
    public void ParameterChoiceConstructorTest() {
        String name = "TestName";
        String password = "fk39ff93jff0934jifjsd09if3joi";
        String email = "TestEmail@test.com";
        int id = 949;
        User user = new User(id, name, password, email);

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
        User user = new User(id, name, password, email);

        JSONObject userJson = user.toJson();

        assertEquals(name, userJson.get("name"));
        assertEquals(password, userJson.get("password"));
        assertEquals(email, userJson.get("email"));
        assertEquals(id, userJson.get("id"));
    }
}
