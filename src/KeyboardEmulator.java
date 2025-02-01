import java.awt.Robot;
import java.awt.event.KeyEvent;


public class KeyboardEmulator {
    private final Robot robot;

    public  KeyboardEmulator() throws Exception{
        this.robot = new Robot();
    }

    public void PressingCtrlV(){
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.delay(50);
        robot.keyPress(KeyEvent.VK_V);

        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

    }
}
