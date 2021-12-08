package main.java.com.soen387.model;

import com.soen387.business.PollException;
import com.soen387.business.PollStateException;
import com.soen387.model.Choice;
import com.soen387.model.Poll;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class PollTest {

    private Poll poll;

    @Before
    public void before() {
        try {
            String name = "nameTest";
            String question = "questionTest";
            Choice[] choices = new Choice[4];
            for (int i = 0; i < choices.length; i++) {
                choices[i] = new Choice("text" + i, "description" + i);
            }
            poll = new Poll(name, question, choices);
        } catch (Exception e) {
            fail("Failed during initialization");

        }

    }

    @Test
    public void DefaultPollConstructorTest() {
        Poll defaultPoll = new Poll();

        assertNull(defaultPoll.getPollId());
        assertNull(defaultPoll.getReleaseDate());
        assertNotNull(defaultPoll.getName());
        assertNotNull(defaultPoll.getQuestion());
        assertNotNull(defaultPoll.getCreatedBy());
        assertNotNull(defaultPoll.getChoices());
        assertNotNull(defaultPoll.getVotes());

        assertEquals("", defaultPoll.getName());
        assertEquals("", defaultPoll.getQuestion());
        assertEquals("", defaultPoll.getCreatedBy());
        assertSame(Poll.PollStatus.created, defaultPoll.getStatus());
        assertEquals(0, defaultPoll.getChoices().length);
        assertEquals(0, defaultPoll.getVotes().length);
    }

    @Test
    public void ParameterPollConstructorTest() {
        String name = "nameTest";
        String question = "questionTest";
        Choice[] choices = new Choice[4];
        for (int i = 0; i < choices.length; i++) {
            choices[i] = new Choice("text" + i, "description" + i);
        }
        try {
            Poll paramPoll = new Poll(name, question, choices);

            assertNull(paramPoll.getPollId());
            assertNull(paramPoll.getReleaseDate());
            assertNotNull(paramPoll.getCreatedBy());

            assertEquals(name, paramPoll.getName());
            assertEquals(question, paramPoll.getQuestion());
            assertEquals(choices.length, paramPoll.getChoices().length);
            assertEquals(choices.length, paramPoll.getVotes().length);
            assertSame(Poll.PollStatus.created, paramPoll.getStatus());
            for (int i = 0; i < choices.length; i++) {
                assertEquals(choices[i].getText(), paramPoll.getChoices()[i].getText());
                assertEquals(choices[i].getDescription(), paramPoll.getChoices()[i].getDescription());
            }
            assertEquals("", paramPoll.getCreatedBy());
        }
        catch (Exception e) {
            fail("No Exception should be thrown when creating a poll with valid parameters");
        }

    }

    @Test
    public void CopyPollConstructorTest() {
        try {
            poll.setCreatedBy("Username");
            poll.run();
            poll.addVote(1);
            poll.addVote(2);
            poll.release();

            Poll copyPoll = new Poll(poll);


            assertNull(poll.getPollId());
            assertNotNull(poll.getReleaseDate());
            assertNotNull(poll.getCreatedBy());

            assertEquals(poll.getName(), copyPoll.getName());
            assertEquals(poll.getQuestion(), copyPoll.getQuestion());
            assertSame(poll.getStatus(), copyPoll.getStatus());

            assertEquals(poll.getChoices().length, copyPoll.getChoices().length);
            assertEquals(poll.getVotes().length, copyPoll.getVotes().length);
            assertNotSame(poll.getChoices(), copyPoll.getChoices());
            assertNotSame(poll.getVotes(), copyPoll.getVotes());

            for (int i = 0; i < poll.getChoices().length; i++) {
                assertEquals(poll.getVotes()[i], copyPoll.getVotes()[i]);
                assertNotSame(poll.getChoices()[i], copyPoll.getChoices()[i]);
                assertEquals(poll.getChoices()[i].getText(), copyPoll.getChoices()[i].getText());
                assertEquals(poll.getChoices()[i].getDescription(), copyPoll.getChoices()[i].getDescription());
            }

            assertEquals(poll.getCreatedBy(), copyPoll.getCreatedBy());
            assertEquals(poll.getPollId(), copyPoll.getPollId());
            assertEquals(poll.getReleaseDate(), copyPoll.getReleaseDate());
        }
        catch (Exception e) {
            fail("No Exception should be thrown when creating a poll with valid parameters");
        }
    }

    @Test
    public void AddVoteTest() {
        poll.addVote(0);
        poll.addVote(3);
        poll.addVote(0);
        poll.addVote(1);
        poll.addVote(0);
        poll.addVote(2);
        poll.addVote(3);
        poll.addVote(0);
        poll.addVote(3);
        poll.addVote(0);
        poll.addVote(1);

        assertEquals(5, poll.getVotes()[0]);
        assertEquals(2, poll.getVotes()[1]);
        assertEquals(1, poll.getVotes()[2]);
        assertEquals(3, poll.getVotes()[3]);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void AddVoteExceptionTest() {
        poll.addVote(4);
    }

    @Test
    public void ReleaseDateTest() {
        try {
            Date dateBefore = new Date();
            poll.run();
            poll.release();
            Date dateAfter = new Date();
            assertTrue(poll.getReleaseDate().after(dateBefore) &&
                    poll.getReleaseDate().before(dateAfter));
        } catch (Exception e) {
            fail("No exception should be returned.");
        }

    }

    @Test
    public void RunPollTest() {
        try {
            poll.run();
            assertSame(Poll.PollStatus.running, poll.getStatus());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail("No exception should be returned.");
        }
    }

    @Test(expected = PollStateException.class)
    public void RunPollExceptionTest() throws Exception {
        poll.run();
        poll.run();

    }

    @Test
    public void ReleasePollTest() throws Exception {
        poll.run();
        poll.release();
        assertSame(Poll.PollStatus.released, poll.getStatus());
    }

    @Test(expected = PollStateException.class)
    public void ReleasePollExceptionTest() throws Exception {
        poll.release();
    }

    @Test
    public void UnreleasePollTest() throws Exception {
        poll.run();
        poll.release();
        poll.unrelease();
        assertSame(Poll.PollStatus.running, poll.getStatus());
    }

    @Test(expected = PollStateException.class)
    public void UnreleasePollExceptionTest() throws Exception {
        poll.unrelease();
    }

    @Test
    public void ClearPollTest() throws Exception {
        poll.run();
        poll.addVote(0);
        poll.addVote(1);
        poll.addVote(1);
        poll.addVote(2);
        poll.addVote(2);
        poll.addVote(2);
        poll.release();
        poll.clear();
        assertSame(Poll.PollStatus.created, poll.getStatus());
        int votes[] = poll.getVotes();
        for (int i = 0; i < votes.length; i++) {
            assertEquals(0, votes[i]);
        }

        poll.run();
        poll.addVote(0);
        poll.addVote(1);
        poll.addVote(1);
        poll.addVote(2);
        poll.addVote(2);
        poll.addVote(2);
        poll.clear();
        assertSame(Poll.PollStatus.running, poll.getStatus());
        votes = poll.getVotes();
        for (int i = 0; i < votes.length; i++) {
            assertEquals(0, votes[i]);
        }
    }

    @Test(expected = PollStateException.class)
    public void ClearPollExceptionTest() throws Exception {
        poll.clear();
    }

    @Test
    public void UpdatePollTest() throws Exception {
        String name = "nameUpdateTest";
        String question = "questionUpdateTest";
        Choice[] choices = new Choice[4];
        for (int i = 0; i < choices.length; i++) {
            choices[i] = new Choice("textUpdate" + i, "descriptionUpdate" + i);
        }

        poll.run();
        poll.addVote(0);
        poll.addVote(1);
        poll.addVote(1);
        poll.addVote(2);
        poll.addVote(2);
        poll.addVote(2);

        poll.update(name, question, choices);

        assertEquals(name, poll.getName());
        assertEquals(question, poll.getQuestion());
        assertEquals(choices.length, poll.getChoices().length);
        assertEquals(choices.length, poll.getVotes().length);
        assertSame(Poll.PollStatus.created, poll.getStatus());
        for (int i = 0; i < choices.length; i++) {
            assertEquals(choices[i].getText(), poll.getChoices()[i].getText());
            assertEquals(choices[i].getDescription(), poll.getChoices()[i].getDescription());
            assertEquals(0, poll.getVotes()[i]);
        }

        poll.run();
        poll.release();
        poll.unrelease();
        assertSame(Poll.PollStatus.running, poll.getStatus());
    }

    @Test(expected = PollStateException.class)
    public void UpdatePollExceptionTest() throws Exception {
        String name = "nameUpdateTest";
        String question = "questionUpdateTest";
        Choice[] choices = new Choice[4];
        for (int i = 0; i < choices.length; i++) {
            choices[i] = new Choice("textUpdate" + i, "descriptionUpdate" + i);
        }
        poll.update(name, question, choices);
    }

    @Test
    public void PollToStringTest() throws Exception {
        poll.run();
        poll.addVote(0);
        poll.addVote(1);
        poll.addVote(1);
        poll.addVote(2);
        poll.addVote(2);
        poll.addVote(2);
        poll.release();

        String text = poll.toString();

        assertTrue(text.contains(poll.getName()));
        assertTrue(text.contains(poll.getQuestion()));
        Choice[] choices = poll.getChoices();
        for (int i = 0; i < choices.length; i++) {
            assertTrue(text.contains(choices[i].getText()));
            assertTrue(text.contains(Integer.toString(poll.getVotes()[i])));
        }

    }

    @Test
    public void PollToJsonText() throws Exception {
        poll.run();
        poll.addVote(0);
        poll.addVote(1);
        poll.addVote(1);
        poll.addVote(2);
        poll.addVote(2);
        poll.addVote(2);
        poll.release();

        String text = poll.toJson();

        assertTrue(text.contains(poll.getName()));
        assertTrue(text.contains(poll.getQuestion()));
        Choice[] choices = poll.getChoices();
        for (int i = 0; i < choices.length; i++) {
            assertTrue(text.contains(choices[i].getText()));
            assertTrue(text.contains(Integer.toString(poll.getVotes()[i])));
        }
    }

    @Test
    public void PollToXMLText() throws Exception {
        poll.run();
        poll.addVote(0);
        poll.addVote(1);
        poll.addVote(1);
        poll.addVote(2);
        poll.addVote(2);
        poll.addVote(2);
        poll.release();

        String text = poll.toXML();

        assertTrue(text.contains(poll.getName()));
        assertTrue(text.contains(poll.getQuestion()));
        Choice[] choices = poll.getChoices();
        for (int i = 0; i < choices.length; i++) {
            assertTrue(text.contains(choices[i].getText()));
            assertTrue(text.contains(Integer.toString(poll.getVotes()[i])));
        }
    }

    @Test(expected = PollException.class)
    public void SingleChoiceUpdateErrorTest() throws Exception {
        poll.run();
        Choice[] choices = new Choice[1];
        choices[0] = new Choice("Test", "Test");
        poll.update("Test", "Test", choices);
    }

    @Test(expected = PollException.class)
    public void SingleChoiceConstructorErrorTest() throws Exception {
        Choice[] choices = new Choice[1];
        choices[0] = new Choice("Test", "Test");

        Poll newPoll = new Poll("Test", "test", choices);
    }

    @Test
    public void ChangeVoteTest() throws Exception {
        poll.run();
        poll.addVote(0);
        poll.changeVote(0, 1);

        assertEquals(0, poll.getVotes()[0]);
        assertEquals(1, poll.getVotes()[1]);
        assertEquals(0, poll.getVotes()[2]);
        assertEquals(0, poll.getVotes()[3]);
    }

    @Test(expected = PollException.class)
    public void ChangeVoteErrorTest() throws Exception {
        poll.run();
        poll.addVote(1);
        poll.changeVote(0, 1);
    }

}
