import java.util.Scanner;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            ClipboardManager clipboardManager = new ClipboardManager();
            logger.info("ClipboardManager created");
            KeyboardEmulator keyboardEmulator = new KeyboardEmulator();
            Scanner scanner = new Scanner(System.in);
            Translator translator = new Translator();
            String prevText = "";
            
            System.out.println("Введите язык на который переводить");
            String neededLang = scanner.nextLine();

            clipboardManager.CleanClipboard();
            logger.info("Clipboard was cleaned");


            while (true){
                String text = clipboardManager.getContentFromClipboard();
                if (text != null && !text.isEmpty() && !prevText.equals(text)) {
                    text = translator.translate(neededLang,text);
                    logger.info("Text translated successfully");
                    clipboardManager.setTextToClipboard(text);

                    keyboardEmulator.PressingCtrlV();
                    logger.info("text was pressed");
                    prevText = text;
                }

                Thread.sleep(500);
            }
        } catch (Exception e) {
            logger.error("Error occurred", e);
            System.err.println(e.getMessage());
            logger.log(Level.FATAL, "While terminated");
        }



    }
}
