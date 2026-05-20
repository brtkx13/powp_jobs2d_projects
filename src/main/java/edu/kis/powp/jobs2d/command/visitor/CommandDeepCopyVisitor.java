package edu.kis.powp.jobs2d.command.visitor;

import java.util.ArrayList;
import java.util.List;

import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.ImmutableCompoundCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

public class CommandDeepCopyVisitor implements ICommandVisitor {

    private DriverCommand copy;

    @Override
    public void visit(SetPositionCommand command) {
        copy = new SetPositionCommand(command.getPosX(), command.getPosY());
    }

    @Override
    public void visit(OperateToCommand command) {
        copy = new OperateToCommand(command.getPosX(), command.getPosY());
    }

    @Override
    public void visit(ICompoundCommand command) {
        List<DriverCommand> copiedChildren = new ArrayList<>();
        for (DriverCommand child : (Iterable<DriverCommand>) command::iterator) {
            child.accept(this);
            if (copy != null) {
                copiedChildren.add(copy);
            }
        }

        if (command instanceof ImmutableCompoundCommand) {
            String name = ((ImmutableCompoundCommand) command).getName();
            copy = new ImmutableCompoundCommand(name, copiedChildren);
        } else if (command instanceof CompoundCommand) {
            String name = ((CompoundCommand) command).getName();
            copy = new CompoundCommand(copiedChildren, name);
        } else {
            copy = new CompoundCommand(copiedChildren, command.toString());
        }
    }

    public DriverCommand getCopy() {
        return copy;
    }
}
