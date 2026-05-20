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
        for (DriverCommand child : command) {
            child.accept(this);
            if (copy != null) {
                copiedChildren.add(copy);
            }
        }
        copy = new CompoundCommand(copiedChildren, command.toString());
    }

    @Override
    public void visit(CompoundCommand command) {
        List<DriverCommand> copiedChildren = new ArrayList<>();
        for (DriverCommand child : command) {
            child.accept(this);
            if (copy != null) {
                copiedChildren.add(copy);
            }
        }
        copy = new CompoundCommand(copiedChildren, command.getName());
    }

    @Override
    public void visit(ImmutableCompoundCommand command) {
        List<DriverCommand> copiedChildren = new ArrayList<>();
        for (DriverCommand child : command) {
            child.accept(this);
            if (copy != null) {
                copiedChildren.add(copy);
            }
        }
        copy = new ImmutableCompoundCommand(command.getName(), copiedChildren);
    }

    public DriverCommand getCopy() {
        return copy;
    }
}
