import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        String prevText = "";

        Transferable contentToCheck = clipboard.getContents(null);

        if (contentToCheck!=null && contentToCheck.isDataFlavorSupported(DataFlavor.stringFlavor)){
            String s= "";
            StringSelection stringSelection = new StringSelection(s);
            clipboard.setContents(stringSelection,null);
        }

        while (true){
            try {
                Transferable content = clipboard.getContents(null);
                if (content!= null && content.isDataFlavorSupported(DataFlavor.stringFlavor)){
                    String text = (String) content.getTransferData(DataFlavor.stringFlavor);
                    if (!text.isEmpty() && !prevText.equals(text)) {
                        text = text.toUpperCase();
                        StringSelection stringSelection = new StringSelection(text);
                        clipboard.setContents(stringSelection, null);
                        pressCombination();
                        prevText = text;
                    }

                }
            }catch (IOException | UnsupportedFlavorException e){
                System.err.println(e.getMessage());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            try {
                Thread.sleep(500);
            }catch (InterruptedException e){
                System.err.println(e.getMessage());
                break;
            }
        }

    }

    public static void pressCombination() throws Exception{
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);


    }

}
