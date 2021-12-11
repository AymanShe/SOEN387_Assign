package main.java.com.soen387.model;

import com.soen387.usermanager.User;
import com.soen387.usermanager.UserBase;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserBaseTest {

    UserBase userbase = null;

    @Before
    public void before() {
        userbase = new UserBase();
        userbase.createUser("name1", "password1", "mail1@hotmail.com");
        userbase.getUserByName("name1").activate(userbase.getUserByName("name1").getActivationCode());
        userbase.createUser("NaMe2", "PaSsWoRd2", "MAil2@hotmail.com");
        userbase.getUserByName("NaMe2").activate(userbase.getUserByName("NaMe2").getActivationCode());
        userbase.createUser("NAME3", "PASSWORD3", "MAIL3@hotmail.com");
        userbase.getUserByName("NAME3").activate(userbase.getUserByName("NAME3").getActivationCode());
    }

    @Test
    public void LoginTest() {
        assertTrue(userbase.login("name1", "password1"));
        assertTrue(userbase.login("NAME2", "PaSsWoRd2"));
        assertTrue(userbase.login("name3", "PASSWORD3"));
    }

    @Test
    public void WrongLoginTest() {
        assertFalse(userbase.login("name1", "PASSWORD1"));
        assertFalse(userbase.login("name4", "PaSsWoRd2"));
        assertFalse(userbase.login("NAME3", userbase.getUserByName("NAME3").getPassword()));
    }

    @Test
    public void GetUserByIdTest() {
        User user1 = userbase.getUserById(userbase.getUserByName("name1").getId());

        assertEquals("name1", user1.getName());
    }

    @Test
    public void GetUserByInvalidIdtest() {
        assertNull(userbase.getUserById(93848592));
    }

    @Test
    public void GetUserByNameTest() {
        assertNotNull(userbase.getUserByName("name1"));
        assertNotNull(userbase.getUserByName("name2"));
        assertNotNull(userbase.getUserByName("name3"));
    }

    @Test public void GetUserByInvalidNameTest() {
        assertNull(userbase.getUserByName("AbsolutelyInvalidName"));
    }

    @Test
    public void CreateUserTest() {
        String name = "Name4";
        String password = "Password4";
        String email = "email4@hotmail.com";
        userbase.createUser(name, password, email);

        User user = userbase.getUserByName("Name4");

        assertNotNull(user);
        assertEquals(name, user.getName());
        user.activate(user.getActivationCode());
        assertTrue(userbase.login(name, password));
        assertNotEquals(password, user.getPassword());
        assertEquals(email, user.getEmail());
    }

    @Test(expected=Exception.class)
    public void CreateDuplicateUserTest() {
        userbase.createUser("name1", "password1", "email1@hotmail.com");
    }

    @Test
    public void UserBaseToJsonTest() {
        JSONArray jsonUserbase = userbase.toJson();

        assertEquals(3, jsonUserbase.size());

        JSONObject jsonUser = (JSONObject)jsonUserbase.get(0);

        assertNotNull(userbase.getUserByName((String)jsonUser.get("name")));
    }


    /*
    @Test
    public void LoadJsonTest() {
        JSONArray jsonUserbase = new JSONArray();
        JSONObject jsonUser = new JSONObject();

        int id = 0;
        String name = "namejson";
        String password = "passwordjson";
        String email = "emailjson@hotmail.com";


        jsonUser.put("id", id);
        jsonUser.put("name", name);
        jsonUser.put("password", password);
        jsonUser.put("email", email);

        jsonUserbase.add(jsonUser);

        userbase.loadUserBase();

        User user = userbase.getUserById(0);

        assertNotNull(user);
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
    }
    */

}
