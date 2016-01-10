package artopia.command.infrastructure.move;

import artopia.command.infrastructure.AbstractCommand;
import artopia.entitiy.User;
import artopia.entitiy.room.Exit;
import artopia.entitiy.room.Room;
import artopia.service.command.CommandResult;
import artopia.service.command.errors.AbstractCommandError;
import artopia.service.command.errors.CannotMoveThere;
import artopia.service.command.errors.RoomClosed;
import artopia.service.locator.Service;
import artopia.service.locator.ServiceLocator;
import artopia.service.room.RoomRepository;

/**
 * @author Vehsamrak
 */
abstract public class AbstractDirectionCommand extends AbstractCommand
{
    private CommandResult createResultWithError(CommandResult commandResult, AbstractCommandError error)
    {
        commandResult.addError(error);

        return commandResult;
    }

    protected CommandResult moveToDirection(
            Direction direction,
            User user,
            ServiceLocator serviceLocator
    ) throws Exception
    {
        CommandResult commandResult = new CommandResult(direction.toString());
        RoomRepository roomRepository = (RoomRepository) serviceLocator.get(Service.ROOM_REPOSITORY);
        Exit exit = this.getDestinationExit(user, roomRepository, direction);

        if (exit == null) {
            return this.createResultWithError(commandResult, new CannotMoveThere());
        }

        if (exit.isClosed()) {
            return this.createResultWithError(commandResult, new RoomClosed(exit.getDoorMessage()));
        }

        String destinationRoomId = exit.getRoomId();
        Room destinationRoom = roomRepository.findById(destinationRoomId);

        if (destinationRoom == null) {
            // TODO: 10.01.16 Логировать ошибку зоны. Выход никуда не ведет
            return this.createResultWithError(commandResult, new CannotMoveThere());
        }

        user.setRoomId(destinationRoomId);

        return commandResult;
    }

    private Exit getDestinationExit(User user, RoomRepository roomRepository, Direction direction) throws Exception
    {
        Room currentRoom = roomRepository.findById(user.getRoomId());

        if (currentRoom == null) {
            return null;
        }

        Exit exit;

        if (direction.equals(Direction.NORTH)) {
            exit = currentRoom.getNorth();
        } else if (direction.equals(Direction.EAST)) {
            exit = currentRoom.getEast();
        } else if (direction.equals(Direction.SOUTH)) {
            exit = currentRoom.getSouth();
        } else if (direction.equals(Direction.WEST)) {
            exit = currentRoom.getWest();
        } else if (direction.equals(Direction.UP)) {
            exit = currentRoom.getUp();
        } else if (direction.equals(Direction.DOWN)) {
            exit = currentRoom.getDown();
        } else {
            throw new Exception("Undefined direction");
        }

        return exit;
    }
}
