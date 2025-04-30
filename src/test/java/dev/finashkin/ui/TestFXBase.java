package dev.finashkin.ui;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeoutException;

/**
 * Base class for TestFX UI tests.
 * This class provides common functionality for TestFX tests, including:
 * - JavaFX initialization
 * - Headless mode configuration
 * - Common test utilities
 */
@ExtendWith(ApplicationExtension.class)
public abstract class TestFXBase extends FxRobot {

    /**
     * Setup method called before each test.
     * This method initializes the JavaFX toolkit and sets up headless mode if needed.
     */
    @BeforeEach
    public void setUp() throws Exception {
        // Always configure headless mode for consistent test execution
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("java.awt.headless", "true");
        System.setProperty("glass.platform", "Monocle");
        System.setProperty("monocle.platform", "Headless");
    }

    /**
     * Cleanup method called after each test.
     * This method releases the JavaFX toolkit resources.
     */
    @AfterEach
    public void tearDown() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    /**
     * Start method called by TestFX to initialize the JavaFX application.
     * This method should be overridden by subclasses to set up the specific UI components to test.
     *
     * @param stage The primary stage for the application
     */
    @Start
    public abstract void start(Stage stage) throws Exception;

    /**
     * Utility method to wait for all JavaFX events to be processed.
     */
    protected void waitForFxEvents() {
        WaitForAsyncUtils.waitForFxEvents();
    }

    /**
     * Utility method to click on a node.
     *
     * @param node The node to click on
     */
    protected void clickOn(Node node) {
        moveTo(node);
        clickOn(MouseButton.PRIMARY);
    }

    /**
     * Utility method to type text.
     *
     * @param text The text to type
     */
    protected void type(String text) {
        for (char c : text.toCharArray()) {
            KeyCode keyCode;
            if (Character.isDigit(c)) {
                // For digits, use the DIGIT0, DIGIT1, etc. enum constants
                keyCode = KeyCode.valueOf("DIGIT" + c);
            } else {
                // For letters, use the A, B, C, etc. enum constants
                keyCode = KeyCode.valueOf(String.valueOf(c).toUpperCase());
            }
            press(keyCode);
            release(keyCode);
        }
    }
}
