package main.java.com.soen387.model;

import com.soen387.model.Choice;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

public class ChoiceTest {

    @Test
    public void DefaultChoiceConstructorTest() {
        Choice choice = new Choice();

        assertNotNull(choice.getText());
        assertNotNull(choice.getDescription());
        assertTrue(choice.getText().equals(""));
        assertTrue(choice.getDescription().equals(""));
    }

    @Test
    public void ParameterChoiceConstructorTest() {
        String text = "Text test";
        String description = "Description test";

        Choice choice = new Choice(text, description);

        assertTrue(choice.getText().equals(text));
        assertTrue(choice.getDescription().equals(description));

    }
}
