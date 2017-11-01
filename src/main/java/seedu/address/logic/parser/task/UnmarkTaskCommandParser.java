package seedu.address.logic.parser.task;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.task.UnmarkTaskCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnmarkTaskCommand object
 */
public class UnmarkTaskCommandParser implements Parser<UnmarkTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkTaskCommand
     * and returns an MarkTaskCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnmarkTaskCommand parse(String args) throws ParseException {
        if (args.length() == 2) {
            try {
                Index index = ParserUtil.parseIndex(args);
                return new UnmarkTaskCommand(index);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkTaskCommand.MESSAGE_USAGE));
            }
        } else if (args.contains("~")) {
            String[] indices = args.trim().split("~");
            List<Index> indexes = new ArrayList<>();
            for (int i = Integer.parseInt(indices[0]); i <= Integer.parseInt(indices[1]); i++) {
                try {
                    indexes.add(ParserUtil.parseIndex(Integer.toString(i)));
                } catch (IllegalValueException ive) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkTaskCommand.MESSAGE_USAGE));
                }
            }
            return new UnmarkTaskCommand(indexes);
        } else {
            String[] tokens = args.trim().split(" ");
            List<Index> indexes = new ArrayList<>();

            for (String token : tokens) {
                try {
                    indexes.add(ParserUtil.parseIndex(token));
                } catch (IllegalValueException ive) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkTaskCommand.MESSAGE_USAGE));
                }
            }
            return new UnmarkTaskCommand(indexes);
        }
    }

    @Override
    public String getCommandWord() {
        return UnmarkTaskCommand.COMMAND_WORD;
    }
}