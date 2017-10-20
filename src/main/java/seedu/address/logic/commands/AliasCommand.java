package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_KEYWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_REPRESENTATION;

import seedu.address.logic.Logic;
import seedu.address.model.alias.AliasToken;
import seedu.address.model.alias.ReadOnlyAliasToken;
import seedu.address.model.alias.exceptions.DuplicateTokenKeywordException;

/**
 * Command to create aliases
 */
public class AliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "alias";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates an alias for a command or shortcut. "
            + "Parameters: "
            + PREFIX_ALIAS_KEYWORD + "KEYWORD "
            + PREFIX_ALIAS_REPRESENTATION + "REPRESENTATION "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ALIAS_KEYWORD + "ph "
            + PREFIX_ALIAS_REPRESENTATION + " Public Holiday";

    public static final String MESSAGE_SUCCESS = "New alias added: %1$s";
    public static final String MESSAGE_DUPLICATE_ALIAS = "This alias already exists";
    public static final String MESSAGE_INVALID_KEYWORD = "Unable to use a command name as a keyword!";

    private final AliasToken toAdd;
    private Logic logic;

    /**
     * Creates an AliasCommand to add the specified {@code ReadOnlyAliasToken}
     */
    public AliasCommand(ReadOnlyAliasToken aliasToken) {
        toAdd = new AliasToken(aliasToken);
    }

    @Override
    public void setLogic(Logic logic) {
        requireNonNull(logic);
        this.logic = logic;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);

        for (ReadOnlyAliasToken token : model.getAddressBook().getAliasTokenList()) {
            if (token.getKeyword().keyword.equals(toAdd.getKeyword().keyword)) {
                return new CommandResult(MESSAGE_DUPLICATE_ALIAS);
            }
        }

        if (logic.isCommandWord(toAdd.getKeyword().keyword)) {
            return new CommandResult(MESSAGE_INVALID_KEYWORD);
        }
        try {
            model.addAliasToken(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTokenKeywordException e) {
            return new CommandResult(MESSAGE_DUPLICATE_ALIAS);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AliasCommand // instanceof handles nulls
                && toAdd.equals(((AliasCommand) other).toAdd));
    }

}
