package artopia.command;

import artopia.entitiy.User;
import artopia.service.DatabaseService;
import artopia.service.command.CommandResult;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * @author Vehsamrak
 */
public class LookCommandTest extends Assert
{

    @Test
    public void execute_noParameters_returnsResponseContainingDefaultLookMessage()
    {
        CommandResult commandResult = new LookCommand().execute(mock(User.class), mock(DatabaseService.class));

        assertFalse(commandResult.haveErrors());
        assertEquals("Ты осмотрелся.", commandResult.toString());
    }
}