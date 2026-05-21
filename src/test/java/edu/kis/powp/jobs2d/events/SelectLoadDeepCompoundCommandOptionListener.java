package edu.kis.powp.jobs2d.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.kis.powp.jobs2d.command.CompoundCommandFactory;
import edu.kis.powp.jobs2d.command.ImmutableCompoundCommandFactory;
import edu.kis.powp.jobs2d.command.SimpleComplexCommandBuilder;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.features.CommandsFeature;

public class SelectLoadDeepCompoundCommandOptionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        SimpleComplexCommandBuilder builder = new SimpleComplexCommandBuilder("Deep Compound Command");
        
        builder.addCommand(CompoundCommandFactory.createKiteCommand());
        builder.addCommand(ImmutableCompoundCommandFactory.getRectangle(10, 10, 50, 50));
        
        builder.setPosition(0, 0);
        builder.operateTo(100, 100);
        
        CommandManager manager = CommandsFeature.getDriverCommandManager();
        manager.setCurrentCommand(builder.build());
    }
}
