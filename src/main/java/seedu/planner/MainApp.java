package seedu.planner;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.planner.commons.core.Config;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.core.Version;
import seedu.planner.commons.exceptions.DataConversionException;
import seedu.planner.commons.util.ConfigUtil;
import seedu.planner.commons.util.StringUtil;
import seedu.planner.logic.Logic;
import seedu.planner.logic.LogicManager;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.Planner;
import seedu.planner.model.ReadOnlyPlanner;
import seedu.planner.model.ReadOnlyUserPrefs;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.util.SampleDataUtil;
import seedu.planner.storage.JsonPlannerStorage;
import seedu.planner.storage.JsonUserPrefsStorage;
import seedu.planner.storage.PlannerStorage;
import seedu.planner.storage.Storage;
import seedu.planner.storage.StorageManager;
import seedu.planner.storage.UserPrefsStorage;
import seedu.planner.ui.Ui;
import seedu.planner.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 6, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Planner ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        PlannerStorage plannerStorage = new JsonPlannerStorage(userPrefs.getPlannerFilePath());
        storage = new StorageManager(plannerStorage, userPrefsStorage);

        initLogging(config);

        // model = initModelManager(storage, userPrefs);
        model = initPlannerModelManager(storage, userPrefs);


        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s planner book and {@code userPrefs}. <br>
     * The data from the sample planner book will be used instead if {@code storage}'s planner book is not found,
     * or an empty planner book will be used instead if errors occur when reading {@code storage}'s planner book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyPlanner> plannerOptional;
        ReadOnlyPlanner initialData;
        try {
            plannerOptional = storage.readPlanner();
            if (!plannerOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample Planner");
            }
            initialData = plannerOptional.orElseGet(SampleDataUtil::getSamplePlanner);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Planner");
            initialData = new Planner();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Planner");
            initialData = new Planner();
        }

        return new ModelManager(initialData, userPrefs);
    }


    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s planner and {@code userPrefs}. <br>
     * The data from the sample planner will be used instead if {@code storage}'s planner is not found,
     * or an empty planner will be used instead if errors occur when reading {@code storage}'s planner.
     */
    private Model initPlannerModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyPlanner> plannerOptional;
        ReadOnlyPlanner initialData;
        try {
            plannerOptional = storage.readPlanner();
            if (!plannerOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample Planner");
            }
            initialData = plannerOptional.orElseGet(SampleDataUtil::getSamplePlanner);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Planner");
            initialData = SampleDataUtil.getSamplePlanner();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Planner");
            initialData = SampleDataUtil.getSamplePlanner();
        }
        return new ModelManager(initialData);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Planner");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting Planner " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Address Book ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
