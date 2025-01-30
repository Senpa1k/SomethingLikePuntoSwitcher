import java.awt.Robot;
import java.awt.event.KeyEvent;


public class KeyboardEmulator {
    private final Robot robot;

    public  KeyboardEmulator() throws Exception{
        this.robot = new Robot();
    }

    public void PressingCtrlV(){
        robot.delay(200);
        robot.keyPress(KeyEvent.VK_META);

        robot.keyPress(KeyEvent.VK_V);
        robot.delay(100);

        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_META);

    }
}
