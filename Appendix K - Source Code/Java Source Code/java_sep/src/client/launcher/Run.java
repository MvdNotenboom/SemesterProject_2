package client.launcher;

import client.model.IModel;
import client.model.Model;
import client.view.MainView;
import client.viewmodel.MainViewModel;

import javafx.application.Application;
import javafx.stage.Stage;


/**
 * Launcher for the client-side
 * @author group6
 * @version 1.0.1
 */
public class Run extends Application
{
    /**
     * Launcher, instances the model, main view model, main view and starts the main view
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        IModel model = new Model();
        MainViewModel mainViewModel = MainViewModel.getInstance(model);
        MainView mainView = MainView.getInstance(mainViewModel);
        mainView.start();
    }

    @Override
    public void stop() throws Exception
    {
        super.stop();
    }
}
